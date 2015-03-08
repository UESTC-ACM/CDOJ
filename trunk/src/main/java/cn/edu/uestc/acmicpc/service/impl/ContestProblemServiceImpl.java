package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestProblemCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ContestProblemDao;
import cn.edu.uestc.acmicpc.db.dto.impl.contestproblem.ContestProblemDetailDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contestproblem.ContestProblemDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contestproblem.ContestProblemSummaryDto;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.service.iface.ContestProblemService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("deprecation")
@Service
@Primary
@Transactional(rollbackFor = Exception.class)
public class ContestProblemServiceImpl extends AbstractService implements ContestProblemService {

  private final ContestProblemDao contestProblemDao;

  @Autowired
  public ContestProblemServiceImpl(ContestProblemDao contestProblemDao) {
    this.contestProblemDao = contestProblemDao;
  }

  @Override
  public ContestProblemDto getContestProblemDto(Integer contestProblemId) throws AppException {
    AppExceptionUtil.assertNotNull(contestProblemId);
    return contestProblemDao.getDtoByUniqueField(ContestProblemDto.class,
        ContestProblemDto.builder(),
        "contestProblemId", contestProblemId);
  }

  @Override
  public List<ContestProblemDetailDto> getContestProblemDetailDtoListByContestId(Integer contestId)
      throws AppException {
    ContestProblemCondition contestCondition = new ContestProblemCondition();
    contestCondition.contestId = contestId;
    List<ContestProblemDetailDto> contestProblemList = contestProblemDao.findAll(
        ContestProblemDetailDto.class, ContestProblemDetailDto.builder(),
        contestCondition.getCondition());

    Collections.sort(contestProblemList, new Comparator<ContestProblemDetailDto>() {

      @Override
      public int compare(ContestProblemDetailDto a, ContestProblemDetailDto b) {
        return a.getOrder().compareTo(b.getOrder());
      }
    });

    return contestProblemList;
  }

  @Override
  public List<ContestProblemSummaryDto> getContestProblemSummaryDtoListByContestId(
      Integer contestId) throws AppException {
    ContestProblemCondition contestCondition = new ContestProblemCondition();
    contestCondition.contestId = contestId;
    contestCondition.orderFields = "order";
    contestCondition.orderAsc = "true";
    List<ContestProblemSummaryDto> contestProblemList = contestProblemDao.findAll(
        ContestProblemSummaryDto.class, ContestProblemSummaryDto.builder(),
        contestCondition.getCondition());

    Collections.sort(contestProblemList, new Comparator<ContestProblemSummaryDto>() {

      @Override
      public int compare(ContestProblemSummaryDto a, ContestProblemSummaryDto b) {
        return a.getOrder().compareTo(b.getOrder());
      }
    });

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
  public Boolean checkContestProblemInContest(Integer contestProblemId,
      Integer contestId) throws AppException {
    ContestProblemCondition contestCondition = new ContestProblemCondition();
    contestCondition.contestId = contestId;
    contestCondition.problemId = contestProblemId;
    return !contestProblemDao.findAll(ContestProblemDetailDto.class,
        ContestProblemDetailDto.builder(),
        contestCondition.getCondition()).isEmpty();
  }

}