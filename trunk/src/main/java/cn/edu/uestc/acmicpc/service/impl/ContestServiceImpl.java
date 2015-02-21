package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.impl.ContestCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.ContestDao;
import cn.edu.uestc.acmicpc.db.dto.Fields;
import cn.edu.uestc.acmicpc.db.dto.field.ContestFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDto;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import com.google.common.collect.ImmutableSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Primary
public class ContestServiceImpl extends AbstractService implements ContestService {

  private final ContestDao contestDao;
  @SuppressWarnings("unused")
  private final Settings settings;

  @Autowired
  public ContestServiceImpl(ContestDao contestDao, Settings settings) {
    this.contestDao = contestDao;
    this.settings = settings;
  }

  @Override
  public List<Integer> getAllVisibleContestIds() throws AppException {
    ContestCriteria criteria = new ContestCriteria(ImmutableSet.of(ContestFields.CONTEST_ID));
    criteria.isVisible = true;
    List<ContestDto> contests = contestDao.findAll(criteria.getCriteria(), null);
    List<Integer> results = new ArrayList<>(contests.size());
    contests.stream().forEach(dto -> results.add(dto.getContestId()));
    return results;
  }

  @Override
  public ContestDto getContestDtoByContestId(
      Integer contestId, Set<Fields> fields) throws AppException {
    AppExceptionUtil.assertNotNull(contestId);
    ContestCriteria criteria = new ContestCriteria(fields);
    criteria.contestId = contestId;
    return contestDao.getDtoByUniqueField(criteria.getCriteria());
  }

  @Override
  public Boolean checkContestExists(Integer contestId) throws AppException {
    AppExceptionUtil.assertNotNull(contestId);
    ContestCriteria criteria = new ContestCriteria(ImmutableSet.of(ContestFields.CONTEST_ID));
    criteria.contestId = contestId;
    return contestDao.count(criteria.getCriteria()) == 1;
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
  public Long count(ContestCriteria criteria) throws AppException {
    return contestDao.count(criteria.getCriteria());
  }

  @Override
  public List<ContestDto> getContestListDtoList(
      ContestCriteria criteria, PageInfo pageInfo) throws AppException {
    return contestDao.findAll(criteria.getCriteria(), pageInfo);
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
