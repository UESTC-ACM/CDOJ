package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestProblemCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ContestProblemDao;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDetailDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemSummaryDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.service.iface.ContestProblemService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Primary
public class ContestProblemServiceImpl extends AbstractService implements ContestProblemService {

  private final ContestProblemDao contestProblemDao;

  @Autowired
  public ContestProblemServiceImpl(ContestProblemDao contestProblemDao) {
    this.contestProblemDao = contestProblemDao;
  }

  @Override
  public ContestProblemDao getDao() {
    return contestProblemDao;
  }

  @Override
  public ContestProblemDTO getContestProblemDTO(Integer contestProblemId) throws AppException {
    AppExceptionUtil.assertNotNull(contestProblemId);
    return contestProblemDao.getDTOByUniqueField(ContestProblemDTO.class, ContestProblemDTO.builder(),
        "contestProblemId", contestProblemId);
  }

  @Override
  public List<ContestProblemDetailDTO> getContestProblemDetailDTOListByContestId(Integer contestId)
      throws AppException {
    ContestProblemCondition contestCondition = new ContestProblemCondition();
    contestCondition.contestId = contestId;
    List<ContestProblemDetailDTO> contestProblemList = contestProblemDao.findAll(ContestProblemDetailDTO.class, ContestProblemDetailDTO.builder(),
        contestCondition.getCondition());

    Collections.sort(contestProblemList, new Comparator<ContestProblemDetailDTO>() {

      @Override
      public int compare(ContestProblemDetailDTO a, ContestProblemDetailDTO b) {
        return a.getOrder().compareTo(b.getOrder());
      }
    });

    return contestProblemList;
  }

  @Override
  public List<ContestProblemSummaryDTO> getContestProblemSummaryDTOListByContestId(
      Integer contestId) throws AppException {
    ContestProblemCondition contestCondition = new ContestProblemCondition();
    contestCondition.contestId = contestId;
    contestCondition.orderFields = "order";
    contestCondition.orderAsc = "true";
    List<ContestProblemSummaryDTO> contestProblemList = contestProblemDao.findAll(ContestProblemSummaryDTO.class, ContestProblemSummaryDTO.builder(),
        contestCondition.getCondition());

    Collections.sort(contestProblemList, new Comparator<ContestProblemSummaryDTO>() {

      @Override
      public int compare(ContestProblemSummaryDTO a, ContestProblemSummaryDTO b) {
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
  public Integer createNewContestProblem(ContestProblemDTO contestProblemDTO) throws AppException {
    ContestProblem contestProblem = new ContestProblem();
    contestProblem.setContestProblemId(null);
    contestProblem.setContestId(contestProblemDTO.getContestId());
    contestProblem.setOrder(contestProblemDTO.getOrder());
    contestProblem.setProblemId(contestProblemDTO.getProblemId());
    contestProblemDao.add(contestProblem);
    return contestProblem.getContestProblemId();
  }

  @Override
  public Boolean checkContestProblemInContest(Integer contestProblemId,
                                              Integer contestId) throws AppException {
    ContestProblemCondition contestCondition = new ContestProblemCondition();
    contestCondition.contestId = contestId;
    contestCondition.problemId = contestProblemId;
    return !contestProblemDao.findAll(ContestProblemDetailDTO.class, ContestProblemDetailDTO.builder(),
        contestCondition.getCondition()).isEmpty();
  }

}