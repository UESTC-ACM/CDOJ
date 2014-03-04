package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemListDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Problem service interface.
 */
public interface ProblemService extends DatabaseService<Problem, Integer> {

  /**
   * Gets all visible problems' id without any statements.
   *
   * @return all problem id list.
   * @throws AppException
   */
  public List<Integer> getAllVisibleProblemIds() throws AppException;

  /**
   * Gets {@link ProblemDTO} entity by problem's ID.
   *
   * @param problemId problem's id.
   * @return ProblemDTO {@link ProblemDTO} entity.
   * @throws AppException
   */
  public ProblemDTO getProblemDTOByProblemId(Integer problemId)
      throws AppException;

  /**
   * Counts the number of problems fit in condition.
   *
   * @param condition {@link ProblemCondition} entity.
   * @return total number of problems fit in the condition.
   * @throws AppException
   */
  public Long count(ProblemCondition condition) throws AppException;

  /**
   * Get the problems fit in condition and page range.
   *
   * @param condition {@link ProblemCondition} entity.
   * @param pageInfo  {@link PageInfo} entity.
   * @return List of {@link ProblemListDTO} entities.
   * @throws AppException
   */
  public List<ProblemListDTO> getProblemListDTOList(ProblemCondition condition,
                                                    PageInfo pageInfo) throws AppException;

  /**
   * Modify one field of multiply entities as value.
   *
   * @param field filed need to modified.
   * @param ids   entities' ID split by <code>,</code>.
   * @param value new value.
   * @throws AppException
   */
  public void operator(String field, String ids, String value) throws AppException;

  /**
   * Query one field of multiply entities.
   *
   * @param field filed need to modified.
   * @param ids   entities' ID split by <code>,</code>.
   * @return List of queried field.
   * @throws AppException
   */
  public List<Object> query(String field, String ids) throws AppException;

  /**
   * Creates a new problem record.
   *
   * @return the new problem's id.
   * @throws AppException
   */
  public Integer createNewProblem() throws AppException;

  /**
   * Updates problem record by {@link ProblemDTO} entity.
   *
   * @param problemDTO {@link ProblemDTO} entity.
   * @throws AppException
   */
  public void updateProblem(ProblemDTO problemDTO) throws AppException;

  /**
   * Updates a problem record according to dirty fields and its id.
   *
   * @param properties problem property fields.
   * @param problemId  problem's id.
   * @throws AppException
   */
  public void updateProblemByProblemId(Map<String, Object> properties, Integer problemId)
      throws AppException;

  /**
   * Create problems by problemDTOs.
   *
   * @param problemDTOs a series of {@link cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO} entity.
   * @return List of problemDTOs with problemId.
   * @throws AppException
   */
  public ArrayList<ProblemDTO> createProblems(ArrayList<ProblemDTO> problemDTOs) throws AppException;

  /**
   * Check whether a problem exists.
   *
   * @param problemId problem's id.
   * @return true if this problem exists.
   * @throws AppException
   */
  public Boolean checkProblemExists(Integer problemId) throws AppException;

}
