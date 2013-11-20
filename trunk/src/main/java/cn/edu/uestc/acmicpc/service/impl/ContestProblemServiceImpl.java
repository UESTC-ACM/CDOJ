package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestProblemCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestProblemDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestProblemDTO;
import cn.edu.uestc.acmicpc.service.iface.ContestProblemService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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

}