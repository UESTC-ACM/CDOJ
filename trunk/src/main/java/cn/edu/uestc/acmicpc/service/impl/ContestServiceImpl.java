package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.ContestCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.ContestDao;
import cn.edu.uestc.acmicpc.db.dto.field.ContestFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDto;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.util.enums.ContestType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
@Transactional(rollbackFor = Exception.class)
public class ContestServiceImpl extends AbstractService implements ContestService {
  private static final Logger logger = Logger.getLogger(ContestServiceImpl.class);

  // This group of fields make up all progress information of a contest.
  private final static Set<ContestFields> CONTEST_STATUS_FIELDS = ImmutableSet.of(
      ContestFields.END_TIME, ContestFields.TIME_LEFT, ContestFields.STATUS);
  private final ContestDao contestDao;

  @Autowired
  public ContestServiceImpl(ContestDao contestDao) {
    this.contestDao = contestDao;
  }

  @Override
  public List<Integer> getAllVisibleContestIds() throws AppException {
    ContestCriteria criteria = new ContestCriteria();
    criteria.isVisible = true;
    List<ContestDto> contests = contestDao.findAll(criteria, null,
        ImmutableSet.of(ContestFields.CONTEST_ID));
    List<Integer> results = new ArrayList<>(contests.size());
    contests.stream().forEach(dto -> results.add(dto.getContestId()));
    return results;
  }

  @Override
  public ContestDto getContestDtoByContestId(
      Integer contestId, Set<ContestFields> fields) throws AppException {
    AppExceptionUtil.assertNotNull(contestId);
    ContestCriteria criteria = new ContestCriteria();
    criteria.contestId = contestId;
    ContestDto contest = contestDao.getUniqueDto(criteria, fields);
    return updateContestDto(contest, fields);
  }

  @Override
  public Boolean checkContestExists(Integer contestId) throws AppException {
    AppExceptionUtil.assertNotNull(contestId);
    ContestCriteria criteria = new ContestCriteria();
    criteria.contestId = contestId;
    return contestDao.count(criteria) == 1;
  }

  private void updateContestByContestDto(Contest contest, ContestDto contestDto) {
    if (contestDto.getDescription() != null) {
      contest.setDescription(contestDto.getDescription());
    }
    if (contestDto.getIsVisible() != null) {
      contest.setIsVisible(contestDto.getIsVisible());
    }
    if (contestDto.getLength() != null) {
      contest.setLength(contestDto.getLength());
    }
    if (contestDto.getTime() != null) {
      contest.setTime(contestDto.getTime());
    }
    if (contestDto.getTitle() != null) {
      contest.setTitle(contestDto.getTitle());
    }
    if (contestDto.getType() != null) {
      contest.setType(contestDto.getType());
    }
    contest.setPassword(contestDto.getPassword());
    contest.setParentId(contestDto.getParentId());
    if (contestDto.getFrozenTime() != null) {
      contest.setFrozenTime(contestDto.getFrozenTime());
    }
  }

  @Override
  public void updateContest(ContestDto contestDto) throws AppException {
    Contest contest = contestDao.get(contestDto.getContestId());
    AppExceptionUtil.assertNotNull(contest);
    AppExceptionUtil.assertNotNull(contest.getContestId());
    updateContestByContestDto(contest, contestDto);
    contestDao.addOrUpdate(contest);
  }

  private ContestDto updateContestDto(ContestDto contest, Set<ContestFields> fields) {
    for (ContestFields field : fields) {
      switch (field) {
        case LENGTH:
          contest.setLength(contest.getLength() * 1000);
          break;
        case FROZEN_TIME:
          if (contest.getFrozenTime() != null) {
            contest.setFrozenTime(contest.getFrozenTime() * 1000);
          }
          break;
        case TYPE_NAME:
          contest.setTypeName(ContestType.values()[contest.getType()].getDescription());
          break;
        case START_TIME:
          contest.setStartTime(contest.getTime());
          break;
        case CURRENT_TIME:
          contest.setCurrentTime(new Timestamp(System.currentTimeMillis()));
          break;
        default:
          break;
      }
    }
    if (!Sets.intersection(fields, CONTEST_STATUS_FIELDS).isEmpty()) {
      Timestamp endTime = new Timestamp(contest.getStartTime().getTime() + contest.getLength());
      Long timeLeft = Math.max(endTime.getTime() - contest.getCurrentTime().getTime(), 0L);
      String status;
      if (timeLeft > contest.getLength()) {
        status = "Pending";
      } else if (timeLeft > 0) {
        status = "Running";
      } else {
        status = "Ended";
      }
      contest.setEndTime(endTime);
      contest.setTimeLeft(timeLeft);
      contest.setStatus(status);
    }
    return contest;
  }

  @Override
  public Long count(ContestCriteria criteria) throws AppException {
    return contestDao.count(criteria);
  }

  @Override
  public List<ContestDto> getContestList(
      ContestCriteria criteria, PageInfo pageInfo, Set<ContestFields> fields) throws AppException {
    List<ContestDto> result = contestDao.findAll(criteria, pageInfo, fields);
    for (ContestDto contest : result) {
      updateContestDto(contest, fields);
    }
    return result;
  }

  @Override
  public void operator(String field, String ids, String sValue)
      throws AppException {
    Object value;
    if (field.equals("isVisible")) {
      value = Boolean.valueOf(sValue);
    } else {
      value = sValue;
    }
    contestDao.updateEntitiesByField(field, value, "contestId", ids);
  }

  @Override
  public Integer createNewContest() throws AppException {
    Contest contest = new Contest();
    contestDao.addOrUpdate(contest);
    return contest.getContestId();
  }

}
