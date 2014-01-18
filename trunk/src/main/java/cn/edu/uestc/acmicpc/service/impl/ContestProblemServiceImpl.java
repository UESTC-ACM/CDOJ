package cn.edu.uestc.acmicpc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestProblemCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestProblemDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemSummaryDTO;
import cn.edu.uestc.acmicpc.service.iface.ContestProblemService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

@Service
@Primary
public class ContestProblemServiceImpl extends AbstractService implements ContestProblemService{

  private final IContestProblemDAO contestProblemDAO;

  @Autowired
  public ContestProblemServiceImpl(IContestProblemDAO contestProblemDAO) {
    this.contestProblemDAO = contestProblemDAO;
  }

  @Override
  public IContestProblemDAO getDAO() {
    return contestProblemDAO;
  }

  @Override
  public List<ContestProblemDTO> getContestProblemDTOListByContestId(Integer contestId)
      throws AppException {
    ContestProblemCondition contestCondition = new ContestProblemCondition();
    contestCondition.contestId = contestId;
    return contestProblemDAO.findAll(ContestProblemDTO.class, ContestProblemDTO.builder(),
        contestCondition.getCondition());
  }

  @Override
  public List<ContestProblemSummaryDTO> getContestProblemSummaryDTOListByContestId(
      Integer contestId) throws AppException {
    ContestProblemCondition contestCondition = new ContestProblemCondition();
    contestCondition.contestId = contestId;
    contestCondition.orderFields = "order";
    contestCondition.orderAsc = "true";
    return contestProblemDAO.findAll(ContestProblemSummaryDTO.class, ContestProblemSummaryDTO.builder(),
        contestCondition.getCondition());
  }

  @Override
  public Boolean checkContestProblemInContest(Integer contestProblemId,
      Integer contestId) throws AppException {
    ContestProblemCondition contestCondition = new ContestProblemCondition();
    contestCondition.contestId = contestId;
    contestCondition.problemId = contestProblemId;
    return !contestProblemDAO.findAll(ContestProblemDTO.class, ContestProblemDTO.builder(),
        contestCondition.getCondition()).isEmpty();
  }

}