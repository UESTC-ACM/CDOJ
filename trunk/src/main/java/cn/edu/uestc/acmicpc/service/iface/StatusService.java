package cn.edu.uestc.acmicpc.service.iface;

import java.util.List;

import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusForJudgeDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusInformationDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusListDTO;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.view.PageInfo;

/**
 * Service interface for {@link Status}.
 */
public interface StatusService extends DatabaseService<Status, Integer> {

  /**
   * list user's trieds problem ids
   * 
   * @param userId
   *          user's entity id
   * @return problem id list
   * @throws AppException
   */
  public List<Integer> findAllUserTriedProblemIds(Integer userId) throws AppException;

  /**
   * list user's accepted problem ids
   * 
   * @param userId
   *          user's entity id
   * @return problem id list
   * @throws AppException
   */
  public List<Integer> findAllUserAcceptedProblemIds(Integer userId) throws AppException;

  /**
   * @param userId
   *          user id.
   * @return number of problems this user has tried.
   * @throws AppException
   */
  public Long countProblemsUserTired(Integer userId) throws AppException;

  /**
   * @param userId
   *          user id.
   * @return number of problems this user get accepted.
   * @throws AppException
   */
  public Long countProblemsUserAccepted(Integer userId) throws AppException;

  /**
   * @param problemId
   *          problem id.
   * @return number of users who tried this problem.
   * @throws AppException
   */
  public Long countUsersTiredProblem(Integer problemId) throws AppException;

  /**
   * @param problemId
   *          problem id.
   * @return number of users who get accepted for this problem.
   * @throws AppException
   */
  public Long countUsersAcceptedProblem(Integer problemId) throws AppException;

  /**
   * @param condition
   *          DB query condition.
   * @return number of DB records against DB query condition.
   * @throws AppException
   */
  public Long count(StatusCondition condition) throws AppException;

  /**
   * @param condition
   *          DB query condition.
   * @param pageInfo
   *          page information.
   * @return status list according to DB query condition and page information.
   * @throws AppException
   */
  public List<StatusListDTO> getStatusList(StatusCondition condition,
      PageInfo pageInfo) throws AppException;

  /**
   * @return status DTO for judge.
   * @throws AppException
   */
  public List<StatusForJudgeDTO> getQueuingStatus() throws AppException;

  /**
   * Updates status according a {@link StatusForJudgeDTO} object.
   * 
   * @param statusForJudgeDTO
   *          status DTO for judge.
   * @throws AppException
   */
  public void updateStatusByStatusForJudgeDTO(StatusForJudgeDTO statusForJudgeDTO)
      throws AppException;

  /**
   * Create a new status with a specific {@link StatusDTO}.
   * 
   * @param statusDTO
   *          status DTO newly created.
   * @throws AppException
   */
  public void createNewStatus(StatusDTO statusDTO) throws AppException;

  /**
   * Gets {@link StatusInformationDTO} by its status' id.
   * 
   * @param statusId
   *          status' id for query.
   * @return status' information.
   * @throws AppException
   */
  public StatusInformationDTO getStatusInformation(Integer statusId) throws AppException;

  /**
   * Runs rejudge process with specific status condition.
   * 
   * @param statusCondition
   *          DB query condition for rejudge.
   * @throws AppException
   */
  public void rejudge(StatusCondition statusCondition) throws AppException;
}
