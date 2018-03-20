package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.criteria.StatusCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.StatusFields;
import cn.edu.uestc.acmicpc.db.dto.impl.StatusDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import java.util.List;
import java.util.Set;

/**
 * Status service interface.
 */
public interface StatusService {

  /**
   * list user's tried problem id.
   *
   * @param userId
   *          user's id.
   * @param isAdmin
   *          current user is administrator
   * @return problem id list.
   * @throws AppException
   */
  List<Integer> findAllProblemIdsThatUserTried(
      Integer userId, Boolean isAdmin) throws AppException;

  /**
   * list user's accepted problem id.
   *
   * @param userId
   *          user's id.
   * @param isAdmin
   *          current user is administrator
   * @return problem id list.
   * @throws AppException
   */
  List<Integer> findAllProblemIdsThatUserSolved(
      Integer userId, Boolean isAdmin) throws AppException;

  /**
   * Counts user's tried visible normal problems.
   *
   * @param userId
   *          user's id.
   * @param isAdmin
   *          whether this query is for administrator or not.
   * @return number of problems this user has tried.
   * @throws AppException
   */
  Long countProblemsThatUserTried(Integer userId, boolean isAdmin) throws AppException;

  /**
   * Counts user's accepted visible normal problems.
   *
   * @param userId
   *          user's id.
   * @param isAdmin
   *          whether this query is for administrator or not.
   * @return number of problems this user get accepted.
   * @throws AppException
   */
  Long countProblemsThatUserSolved(Integer userId, boolean isAdmin) throws AppException;

  /**
   * Counts users that tried specified problem.
   *
   * @param problemId
   *          problem's id.
   * @return number of users who tried this problem.
   * @throws AppException
   */
  Long countUsersThatTriedThisProblem(Integer problemId) throws AppException;

  /**
   * Counts users that accepted specified problem.
   *
   * @param problemId
   *          problem's id.
   * @return number of users who get accepted for this problem.
   * @throws AppException
   */
  Long countUsersThatSolvedThisProblem(Integer problemId) throws AppException;

  /**
   * Counts the number of status fit in condition.
   *
   * @param criteria
   *          {@link StatusCriteria} entity.
   * @return Total number of status fit in the condition.
   * @throws AppException
   */
  Long count(StatusCriteria criteria) throws AppException;

  /**
   * Get the status fit in condition and page range with required fields
   *
   * @param criteria
   *          {@link StatusCriteria} entity.
   * @param pageInfo
   *          {@link PageInfo} entity.
   * @param fields
   *          result fields to be fetched
   * @return List of {@link StatusDto} entities.
   * @throws AppException
   */
  List<StatusDto> getStatusList(
      StatusCriteria criteria, PageInfo pageInfo,
      Set<StatusFields> fields) throws AppException;

  /**
   * Get the status that pending to judge.
   *
   * @param isFirstTime
   *          whether is the first time the scheduler called.
   * @return List of {@link StatusDto} entities.
   * @throws AppException
   */
  List<StatusDto> getQueuingStatus(boolean isFirstTime) throws AppException;

  /**
   * Updates status by {@link StatusDto} entity.
   *
   * @param statusDto
   *          {@link StatusDto} entity.
   * @throws AppException
   */
  void updateStatus(StatusDto statusDto)
      throws AppException;

  /**
   * Create a new status with a specific {@link StatusDto}.
   *
   * @param statusDto
   *          {@link StatusDto} entity.
   * @return the status id of persisted status
   * @throws AppException
   */
  Integer createNewStatus(StatusDto statusDto) throws AppException;

  /**
   * Gets {@link StatusDto} by status id with required fields
   *
   * @param statusId
   *          status' id for query.
   * @param fields
   *          required fields.
   * @return status' information.
   * @throws AppException
   */
  StatusDto getStatusDto(Integer statusId, Set<StatusFields> fields) throws AppException;

  /**
   * Runs re-judge process with specific status condition.
   *
   * @param criteria
   *          {@link StatusCriteria} entity.
   * @throws AppException
   */
  void rejudge(StatusCriteria criteria) throws AppException;
}
