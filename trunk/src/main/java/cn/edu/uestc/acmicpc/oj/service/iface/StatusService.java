package cn.edu.uestc.acmicpc.oj.service.iface;

import java.util.List;

import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Service interface for {@link Status}.
 */
public interface StatusService extends OnlineJudgeService<Status, Integer> {

  /**
   * list user's trieds problem ids
   *
   * @param userId user's entity id
   * @return problem id list
   * @throws AppException
   */
  List<Integer> findAllUserTriedProblemIds(Integer userId) throws AppException;

  /**
   * list user's accepted problem ids
   *
   * @param userId user's entity id
   * @return problem id list
   * @throws AppException
   */
  List<Integer> findAllUserAcceptedProblemIds(Integer userId) throws AppException;
}
