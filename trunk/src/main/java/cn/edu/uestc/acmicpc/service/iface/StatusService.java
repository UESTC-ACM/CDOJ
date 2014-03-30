package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusForJudgeDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusInformationDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusListDTO;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import java.util.List;

/**
 * Status service interface.
 */
public interface StatusService extends DatabaseService<Status, Integer> {

  /**
   * list user's tried problem id.
   *
   * @param userId user's id.
   * @param isAdmin current user is administrator
   * @return problem id list.
   * @throws AppException
   */
  public List<Integer> findAllUserTriedProblemIds(Integer userId,
                                                  Boolean isAdmin) throws AppException;

  /**
   * list user's accepted problem id.
   *
   * @param userId user's id.
   * @param isAdmin current user is administrator
   * @return problem id list.
   * @throws AppException
   */
  public List<Integer> findAllUserAcceptedProblemIds(Integer userId,
                                                     Boolean isAdmin) throws AppException;

  /**
   * Counts user's tried problems.
   *
   * @param userId user's id.
   * @return number of problems this user has tried.
   * @throws AppException
   */
  public Long countProblemsUserTried(Integer userId) throws AppException;

  /**
   * Counts user's accepted problems.
   *
   * @param userId user's id.
   * @return number of problems this user get accepted.
   * @throws AppException
   */
  public Long countProblemsUserAccepted(Integer userId) throws AppException;

  /**
   * Counts users that tried specified problem.
   *
   * @param problemId problem's id.
   * @return number of users who tried this problem.
   * @throws AppException
   */
  public Long countUsersTriedProblem(Integer problemId) throws AppException;

  /**
   * Counts users that accepted specified problem.
   *
   * @param problemId problem's id.
   * @return number of users who get accepted for this problem.
   * @throws AppException
   */
  public Long countUsersAcceptedProblem(Integer problemId) throws AppException;

  /**
   * Counts the number of status fit in condition.
   *
   * @param condition {@link StatusCondition} entity.
   * @return Total number of status fit in the condition.
   * @throws AppException
   */
  public Long count(StatusCondition condition) throws AppException;

  /**
   * Get the status fit in condition and page range.
   *
   * @param condition {@link StatusCondition} entity.
   * @param pageInfo  {@link PageInfo} entity.
   * @return List of {@link StatusListDTO} entities.
   * @throws AppException
   */
  public List<StatusListDTO> getStatusList(StatusCondition condition,
                                           PageInfo pageInfo) throws AppException;

  /**
   * Get the status fit in condition.
   *
   * @param condition {@link StatusCondition} entity.
   * @return List of {@link StatusListDTO} entities.
   * @throws AppException
   */
  public List<StatusListDTO> getStatusList(StatusCondition condition) throws AppException;

  /**
   * Get the status that pending to judge.
   *
   * @param isFirstTime whether is the first time the scheduler called.
   * @return List of {@link StatusForJudgeDTO} entities.
   * @throws AppException
   */
  public List<StatusForJudgeDTO> getQueuingStatus(boolean isFirstTime) throws AppException;

  /**
   * Updates status by {@link StatusForJudgeDTO} entity.
   *
   * @param statusForJudgeDTO {@link StatusForJudgeDTO} entity.
   * @throws AppException
   */
  public void updateStatusByStatusForJudgeDTO(StatusForJudgeDTO statusForJudgeDTO)
      throws AppException;

  /**
   * Create a new status with a specific {@link StatusDTO}.
   *
   * @param statusDTO {@link StatusDTO} entity.
   * @throws AppException
   */
  public void createNewStatus(StatusDTO statusDTO) throws AppException;

  /**
   * Gets {@link StatusInformationDTO} by status id.
   *
   * @param statusId status' id for query.
   * @return status' information.
   * @throws AppException
   */
  public StatusInformationDTO getStatusInformation(Integer statusId) throws AppException;

  /**
   * Runs re-judge process with specific status condition.
   *
   * @param statusCondition {@link StatusCondition} entity.
   * @throws AppException
   */
  public void rejudge(StatusCondition statusCondition) throws AppException;
}
