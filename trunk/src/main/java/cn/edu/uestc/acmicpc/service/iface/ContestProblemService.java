package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestProblemDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.util.List;

public interface ContestProblemService extends DatabaseService<ContestProblem, Integer>{

  /**
   * Get problems of contest.
   * @param contestId
   * @return
   * @throws AppException
   */
  public List<ContestProblemDTO> getContestProblemDTOListByContestId(Integer contestId) throws AppException;
}