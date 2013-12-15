package cn.edu.uestc.acmicpc.service.iface;

import java.util.List;
import java.util.Map;

import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDataShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemEditorShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemShowDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.view.PageInfo;

/**
 * Service interface for {@link Problem}.
 */
public interface ProblemService extends DatabaseService<Problem, Integer> {

  /**
   * Gets all visible problems' id without any statements.
   *
   * @return all problem id list
   * @throws AppException
   */
  public List<Integer> getAllVisibleProblemIds() throws AppException;

  /**
   * Gets specific problem by problem's ID.
   *
   * @param problemId
   * @return ProblemDTO
   * @throws AppException
   */
  public ProblemDTO getProblemDTOByProblemId(Integer problemId)
      throws AppException;

  /**
   * Gets number of problems that meet the condition.
   *
   * @param condition
   * @return Long
   * @throws AppException
   */
  public Long count(ProblemCondition condition) throws AppException;

  /**
   * Gets problems list that meet the condition and inside the range of page
   *
   * @param problemCondition
   * @param pageInfo
   * @return ProblemDTO List
   * @throws AppException
   */
  public List<ProblemListDTO> getProblemListDTOList(ProblemCondition problemCondition,
      PageInfo pageInfo) throws AppException;

  /**
   * @param field
   * @param ids
   * @param value
   * @throws AppException
   */
  // TODO(mzry1992): it's confuse in API doc. I think you can rename it. please
  // add java doc for it.
  public void operator(String field, String ids, String value) throws AppException;

  /**
   * Creates a new problem record.
   *
   * @return the new problem's id.
   * @throws AppException
   */
  public Integer createNewProblem() throws AppException;

  /**
   * Updates problem record according to a {@link ProblemDTO}.
   *
   * @param problemDTO
   *          {@link ProblemDTO} to be updated.
   * @throws AppException
   */
  public void updateProblem(ProblemDTO problemDTO) throws AppException;

  /**
   * Updates a problem record according to dirty fields and its id.
   *
   * @param properties
   *          problem property fields.
   * @param problemId
   *          problem id.
   * @throws AppException
   */
  public void updateProblemByProblemId(Map<String, Object> properties, Integer problemId)
      throws AppException;

  /**
   * @param problemId
   *          problem id.
   * @return a DTO for problem showing.
   * @throws AppException
   */
  public ProblemShowDTO getProblemShowDTO(Integer problemId)
      throws AppException;

  /**
   * @param problemId
   *          problem id.
   * @return a DTO for problem editor showing.
   * @throws AppException
   */
  public ProblemEditorShowDTO getProblemEditorShowDTO(Integer problemId)
      throws AppException;

  /**
   * @param problemId
   *          problem id.
   * @return a DTO for problem data showing.
   * @throws AppException
   */
  public ProblemDataShowDTO getProblemDataShowDTO(Integer problemId)
      throws AppException;
}
