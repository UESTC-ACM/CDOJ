package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dto.field.StatusFields;
import cn.edu.uestc.acmicpc.db.dto.impl.StatusDto;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeResultType;
import cn.edu.uestc.acmicpc.util.enums.ProblemType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Status database criteria entity.
 */
public class StatusCriteria extends BaseCriteria<Status, StatusDto> {

  public StatusCriteria() {
    super(Status.class, StatusDto.class);
  }

  /**
   * Minimal status id.
   */
  public Integer startId;

  /**
   * Maximal status id.
   */
  public Integer endId;

  /**
   * Minimal submit time.
   */
  public Timestamp startTime;

  /**
   * Maximal submit time.
   */
  public Timestamp endTime;

  /**
   * Submit user name.
   */
  public String userName;

  /**
   * Submit user id.
   */
  public Integer userId;

  /**
   * Submit user id list, separated by dot.
   */
  public List<Integer> userIdList;

  /**
   * Problem id.
   */
  public Integer problemId;

  /**
   * Language.
   */
  public Integer languageId;

  /**
   * Contest id.
   */
  public Integer contestId;

  /**
   * If it's for administrators, we will show all submissions, otherwise, we
   * only show normal users' submissions.
   */
  public boolean isForAdmin = false;

  /**
   * Results.
   *
   * @see cn.edu.uestc.acmicpc.util.enums.OnlineJudgeResultType
   */
  public Set<OnlineJudgeResultType> results = new HashSet<>();

  /**
   * Single result.
   *
   * @see OnlineJudgeResultType
   */
  public OnlineJudgeResultType result;

  /**
   * is problem visible for users excluding admin.
   */
  public Boolean isProblemVisible;

  /**
   * type of the corresponding problem
   *
   * @see cn.edu.uestc.acmicpc.util.enums.ProblemType
   */
  public ProblemType problemType;

  @Override
  DetachedCriteria updateCriteria(DetachedCriteria criteria) throws AppException {
    if (startId != null) {
      criteria.add(Restrictions.ge("statusId", startId));
    }
    if (endId != null) {
      criteria.add(Restrictions.le("statusId", endId));
    }
    if (startTime != null) {
      criteria.add(Restrictions.ge("time", startTime));
    }
    if (endTime != null) {
      criteria.add(Restrictions.le("time", endTime));
    }
    if (!StringUtil.isNullOrWhiteSpace(userName)) {
      criteria.add(Restrictions.ilike("user.userName", userName));
      addAlias(StatusFields.ALIAS_USER);
    }
    if (userId != null) {
      criteria.add(Restrictions.eq("userId", userId));
    } else if (userIdList != null && !userIdList.isEmpty()) {
      criteria.add(Restrictions.in("userId", userIdList));
    }
    if (problemId != null) {
      criteria.add(Restrictions.eq("problemId", problemId));
    }
    if (languageId != null) {
      criteria.add(Restrictions.eq("languageId", languageId));
    }
    if (contestId != null) {
      if (contestId == -1) {
        criteria.add(Restrictions.isNull("contestId"));
      } else {
        criteria.add(Restrictions.eq("contestId", contestId));
      }
    }
    if (!isForAdmin) {
      criteria.add(Restrictions.ne("user.type", AuthenticationType.ADMIN.ordinal()));
      addAlias(StatusFields.ALIAS_USER);
    }
    if (result != null) {
      results.add(result);
    }
    if (!results.contains(OnlineJudgeResultType.OJ_ALL)) {
      Set<Integer> affectedResults = new HashSet<>();
      for (OnlineJudgeResultType result : results) {
        affectedResults.addAll(result.getResults());
      }
      if (!affectedResults.isEmpty()) {
        criteria.add(Restrictions.in("result", affectedResults));
      }
    }
    return criteria;
  }
}
