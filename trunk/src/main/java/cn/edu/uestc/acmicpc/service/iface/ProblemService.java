package cn.edu.uestc.acmicpc.service.iface;

import java.util.List;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemListDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.web.view.PageInfo;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Service interface for {@link Problem}.
 */
public interface ProblemService extends DatabaseService<Problem, Integer> {

  /**
   * Get all visible problems' id without any statements.
   *
   * @return all problem id list
   * @throws AppException
   */
  public List<Integer> getAllVisibleProblemIds() throws AppException;
  
  /**
   * Get specific problem by problem's ID.
   * 
   * @param problemId
   * @return ProblemDTO
   * @throw AppException
   */
  public ProblemDTO getProblemDTOByProblemId(Integer problemId) throws AppException;

  /**
   * Get number of problems that meet the condition.
   * 
   * @param condition
   * @return Long
   * @throws AppException
   */
  public Long count(Condition condition) throws AppException;
  
  /**
   * Get problems list that meet the condition and inside the range of page
   * @param problemCondition
   * @param pageInfo
   * @return ProblemDTO List
   * @throws AppException
   */
  public List<ProblemListDTO> getProblemListDTOList(ProblemCondition problemCondition,
                                                    PageInfo pageInfo) throws AppException;

  /**
   * TODO(mzry1992)
   * @param field
   * @param ids
   * @param value
   * @throws AppException
   */
  public void operator(String field, String ids, String value) throws AppException;

  /**
   * TODO(mzry1992)
   * @return
   * @throws AppException
   */
  public Integer createNewProblem() throws AppException;

  /**
   * TODO(mzry1992)
   * @param problemDTO
   * @throws AppException
   */
  public void updateProblem(ProblemDTO problemDTO) throws AppException;
}
