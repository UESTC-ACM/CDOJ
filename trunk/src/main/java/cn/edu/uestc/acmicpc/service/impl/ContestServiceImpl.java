package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ContestCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ContestDao;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestShowDto;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class ContestServiceImpl extends AbstractService implements
    ContestService {

  private final ContestDao contestDao;
  @SuppressWarnings("unused")
  private final Settings settings;

  @Autowired
  public ContestServiceImpl(ContestDao contestDao, Settings settings) {
    this.contestDao = contestDao;
    this.settings = settings;
  }

  @Override
  public ContestDao getDao() {
    return contestDao;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Integer> getAllVisibleContestIds() throws AppException {
    ContestCondition contestCondition = new ContestCondition();
    contestCondition.isVisible = true;
    return (List<Integer>) contestDao.findAll("contestId",
        contestCondition.getCondition());
  }

  @Override
  public ContestDto getContestDtoByContestId(
      Integer contestId)
      throws AppException {
    AppExceptionUtil.assertNotNull(contestId);
    return contestDao.getDtoByUniqueField(ContestDto.class,
        ContestDto.builder(), "contestId", contestId);
  }

  @Override
  public ContestShowDto getContestShowDtoByContestId(Integer contestId) throws AppException {
    AppExceptionUtil.assertNotNull(contestId);
    return contestDao.getDtoByUniqueField(ContestShowDto.class,
        ContestShowDto.builder(), "contestId", contestId);
  }

  @Override
  public Boolean checkContestExists(Integer contestId) throws AppException {
    ContestCondition contestCondition = new ContestCondition();
    contestCondition.startId = contestId;
    contestCondition.endId = contestId;
    return contestDao.count(contestCondition.getCondition()) == 1;
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

  @Override
  public Long count(ContestCondition contestCondition) throws AppException {
    return contestDao.count(contestCondition.getCondition());
  }

  @Override
  public List<ContestListDto> getContestListDtoList(
      ContestCondition contestCondition,
      PageInfo pageInfo) throws AppException {
    Condition condition = contestCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return contestDao.findAll(ContestListDto.class, ContestListDto.builder(),
        condition);
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
