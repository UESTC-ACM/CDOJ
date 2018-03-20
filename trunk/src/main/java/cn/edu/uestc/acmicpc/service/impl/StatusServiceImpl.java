package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.StatusCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.StatusDao;
import cn.edu.uestc.acmicpc.db.dto.field.StatusFields;
import cn.edu.uestc.acmicpc.db.dto.impl.StatusDto;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeResultType;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.helper.ArrayUtil;
import cn.edu.uestc.acmicpc.util.helper.EnumTypeUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import com.google.common.collect.ImmutableSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for {@link StatusService}.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class StatusServiceImpl extends AbstractService implements StatusService {

  private static final Set<StatusFields> STATUS_FIELDS_FOR_REJUDGE =
      ImmutableSet.of(StatusFields.STATUS_ID, StatusFields.RESULT);
  private final StatusDao statusDao;

  @Autowired
  public StatusServiceImpl(StatusDao statusDao) {
    this.statusDao = statusDao;
  }

  @Override
  public List<Integer> findAllProblemIdsThatUserSolved(Integer userId, Boolean isAdmin)
      throws AppException {
    return findAllProblemIdsThatUser(true, userId, isAdmin);
  }

  @Override
  public List<Integer> findAllProblemIdsThatUserTried(Integer userId, Boolean isAdmin)
      throws AppException {
    return findAllProblemIdsThatUser(false, userId, isAdmin);
  }

  @SuppressWarnings("unchecked")
  private List<Integer> findAllProblemIdsThatUser(Boolean solved, Integer userId, Boolean isAdmin)
      throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.userId = userId;
    if (solved) {
      statusCriteria.results.add(OnlineJudgeResultType.OJ_AC);
    }
    statusCriteria.isForAdmin = isAdmin;
    DetachedCriteria criteria = statusCriteria.getCriteria();
    criteria.setProjection(Projections.distinct(
        Projections.property(StatusFields.PROBLEM_ID.getProjection().getName())));

    return (List<Integer>) statusDao.customFindAll(criteria, null, null);
  }

  @Override
  public Long countProblemsThatUserSolved(Integer userId, boolean isAdmin) throws AppException {
    return countProblemsThatUser(true, userId, isAdmin);
  }

  @Override
  public Long countProblemsThatUserTried(Integer userId, boolean isAdmin) throws AppException {
    return countProblemsThatUser(false, userId, isAdmin);
  }

  private Long countProblemsThatUser(Boolean solved, Integer userId, boolean isAdmin)
      throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.userId = userId;
    if (solved) {
      statusCriteria.results.add(OnlineJudgeResultType.OJ_AC);
    }
    statusCriteria.isForAdmin = isAdmin;
    DetachedCriteria criteria = statusCriteria.getCriteria();
    criteria.setProjection(
        Projections.countDistinct(StatusFields.PROBLEM_ID.getProjection().getName()));

    return statusDao.customCount(criteria);
  }

  @Override
  public Long countUsersThatTriedThisProblem(Integer problemId) throws AppException {
    return countUsersThatDoingThisProblem(false, problemId);
  }

  @Override
  public Long countUsersThatSolvedThisProblem(Integer problemId)
      throws AppException {
    return countUsersThatDoingThisProblem(true, problemId);
  }

  private Long countUsersThatDoingThisProblem(Boolean solved, Integer problemId)
      throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.problemId = problemId;
    if (solved) {
      statusCriteria.results.add(OnlineJudgeResultType.OJ_AC);
    }
    statusCriteria.isForAdmin = false;
    DetachedCriteria criteria = statusCriteria.getCriteria();
    criteria.setProjection(
        Projections.countDistinct(StatusFields.USER_ID.getProjection().getName()));

    return statusDao.customCount(criteria);
  }

  @Override
  public Long count(StatusCriteria criteria) throws AppException {
    return statusDao.count(criteria);
  }

  @Override
  public List<StatusDto> getStatusList(StatusCriteria statusCriteria,
                                       PageInfo pageInfo, Set<StatusFields> fields) throws AppException {
    List<StatusDto> result = statusDao.findAll(statusCriteria, pageInfo, fields);
    for (StatusDto statusDto : result) {
      updateStatusDto(statusDto, fields);
    }
    return result;
  }

  private void updateStatusDto(StatusDto statusDto, Set<StatusFields> fields) {
    for (StatusFields field : fields) {
      switch (field) {
        case RESULT:
          statusDto.setResult(EnumTypeUtil.getReturnDescription(
              statusDto.getResultId(), statusDto.getCaseNumber()));
          if (statusDto.getResultId() != OnlineJudgeReturnType.OJ_AC.ordinal()) {
            statusDto.setTimeCost(null);
            statusDto.setMemoryCost(null);
          }
          break;
        default:
          break;
      }
    }
  }

  private void updateStatusByStatusDto(Status status, StatusDto statusDto) {
    if (statusDto.getResultId() != null) {
      status.setResult(statusDto.getResultId());
    }
    if (statusDto.getMemoryCost() != null) {
      status.setMemoryCost(statusDto.getMemoryCost());
    }
    if (statusDto.getTimeCost() != null) {
      status.setTimeCost(statusDto.getTimeCost());
    }
    if (statusDto.getLength() != null) {
      status.setLength(statusDto.getLength());
    }
    if (statusDto.getTime() != null) {
      status.setTime(statusDto.getTime());
    }
    if (statusDto.getCaseNumber() != null) {
      status.setCaseNumber(statusDto.getCaseNumber());
    }
    if (statusDto.getCodeId() != null) {
      status.setCodeId(statusDto.getCodeId());
    }
    if (statusDto.getCompileInfoId() != null) {
      status.setCompileInfoId(statusDto.getCompileInfoId());
    }
    if (statusDto.getContestId() != null) {
      status.setContestId(statusDto.getContestId());
    }
    if (statusDto.getLanguageId() != null) {
      status.setLanguageId(statusDto.getLanguageId());
    }
    if (statusDto.getProblemId() != null) {
      status.setProblemId(statusDto.getProblemId());
    }
    if (statusDto.getUserId() != null) {
      status.setUserId(statusDto.getUserId());
    }
  }

  @Override
  public Integer createNewStatus(StatusDto statusDto) throws AppException {
    Status status = new Status();
    updateStatusByStatusDto(status, statusDto);
    statusDao.addOrUpdate(status);
    return status.getStatusId();
  }

  @Override
  public StatusDto getStatusDto(Integer statusId, Set<StatusFields> fields)
      throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.startId = statusId;
    statusCriteria.endId = statusId;
    statusCriteria.isForAdmin = true;
    StatusDto statusDto = statusDao.getUniqueDto(statusCriteria, fields);
    if (statusDto != null) {
      updateStatusDto(statusDto, fields);
    }
    return statusDto;
  }

  @Override
  public void rejudge(StatusCriteria statusCriteria) throws AppException {
    Map<String, Object> properties = new HashMap<>();
    properties.put("result", OnlineJudgeReturnType.OJ_REJUDGING.ordinal());
    statusCriteria.isProblemVisible = null;
    statusCriteria.userName = null;
    statusCriteria.isForAdmin = true;

    List<StatusDto> statuses = statusDao.findAll(
        statusCriteria, null, STATUS_FIELDS_FOR_REJUDGE);
    Set<Integer> statusIds = new HashSet<>();
    statuses.stream().map(StatusDto::getStatusId)
        .filter(Objects::nonNull).forEach(statusIds::add);

    if (statusIds.isEmpty()) {
      throw new AppException("There is no status matched for rejudging.");
    }

    statusDao.updateEntitiesByField(
        properties, StatusFields.STATUS_ID.getProjection().getField(),
        ArrayUtil.join(statusIds.toArray(), ","));
  }

  @Override
  public List<StatusDto> getQueuingStatus(boolean isFirstTime) throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.results.add(OnlineJudgeResultType.OJ_WAIT);
    if (isFirstTime) {
      statusCriteria.results.add(OnlineJudgeResultType.OJ_JUDGING);
    }
    statusCriteria.isForAdmin = true;
    statusCriteria.orderFields = "statusId";
    statusCriteria.orderAsc = "true";
    return getStatusList(statusCriteria, null, StatusFields.FIELDS_FOR_JUDGE);
  }

  @Override
  public void updateStatus(StatusDto statusDto) throws AppException {
    Status status = statusDao.get(statusDto.getStatusId());
    AppExceptionUtil.assertNotNull(status);
    AppExceptionUtil.assertNotNull(status.getStatusId());
    updateStatusByStatusDto(status, statusDto);
    statusDao.addOrUpdate(status);
  }
}
