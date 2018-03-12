package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.ContestProblemCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.ContestProblemDao;
import cn.edu.uestc.acmicpc.db.dto.field.ContestProblemFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestProblemDto;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.service.iface.ContestProblemService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("deprecation")
@Service
@Primary
@Transactional(rollbackFor = Exception.class)
public class ContestProblemServiceImpl extends AbstractService implements ContestProblemService {

  private static final Comparator<ContestProblemDto> ORDER_COMPARATOR =
      Comparator.comparing(ContestProblemDto::getOrder);

  private final ContestProblemDao contestProblemDao;

  @Autowired
  public ContestProblemServiceImpl(ContestProblemDao contestProblemDao) {
    this.contestProblemDao = contestProblemDao;
  }

  @Override
  public ContestProblemDto getBasicContestProblemDto(Integer contestId, Integer problemId) throws AppException {
    ContestProblemCriteria criteria = new ContestProblemCriteria();
    criteria.contestId = contestId;
    criteria.problemId = problemId;
    List<ContestProblemDto> result =
        contestProblemDao.findAll(criteria, null, ContestProblemFields.BASIC_FIELDS);
    return result.isEmpty() ? null : result.iterator().next();
  }

  @Override
  public List<ContestProblemDto> getContestProblemDetailDtoListByContestId(
      Integer contestId) throws AppException {
    ContestProblemCriteria criteria = new ContestProblemCriteria();
    criteria.contestId = contestId;
    List<ContestProblemDto> contestProblemList =
        contestProblemDao.findAll(criteria, null, ContestProblemFields.DETAIL_FIELDS);

    Collections.sort(contestProblemList, ORDER_COMPARATOR);

    return contestProblemList;
  }

  @Override
  public List<ContestProblemDto> getContestProblemSummaryDtoListByContestId(
      Integer contestId) throws AppException {
    ContestProblemCriteria criteria = new ContestProblemCriteria();
    criteria.contestId = contestId;
    criteria.orderFields = "order";
    criteria.orderAsc = "true";
    List<ContestProblemDto> contestProblemList = contestProblemDao.findAll(
        criteria, null, ContestProblemFields.SUMMARY_FIELDS);

    Collections.sort(contestProblemList, ORDER_COMPARATOR);

    return contestProblemList;
  }

  @Override
  public void removeContestProblemByContestId(Integer contestId) throws AppException {
    AppExceptionUtil.assertNotNull(contestId);
    contestProblemDao.deleteEntitiesByField("contestId", contestId.toString());
  }

  @Override
  public Integer createNewContestProblem(ContestProblemDto contestProblemDto) throws AppException {
    ContestProblem contestProblem = new ContestProblem();
    contestProblem.setContestProblemId(null);
    contestProblem.setContestId(contestProblemDto.getContestId());
    contestProblem.setOrder(contestProblemDto.getOrder());
    contestProblem.setProblemId(contestProblemDto.getProblemId());
    contestProblemDao.addOrUpdate(contestProblem);
    return contestProblem.getContestProblemId();
  }

  @Override
  public Boolean checkContestProblemInContest(
      Integer contestProblemId, Integer contestId) throws AppException {
    ContestProblemCriteria criteria = new ContestProblemCriteria();
    criteria.contestId = contestId;
    criteria.problemId = contestProblemId;
    return contestProblemDao.count(criteria) != 0;
  }
}
