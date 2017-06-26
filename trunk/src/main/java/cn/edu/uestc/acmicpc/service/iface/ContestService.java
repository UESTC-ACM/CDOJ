package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.criteria.ContestCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.ContestFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import java.util.List;
import java.util.Set;

/**
 * Contest service interface.
 */
public interface ContestService {

  /**
   * Get all visible contests' id.
   *
   * @return all contest id list.
   * @throws AppException
   */
  public List<Integer> getAllVisibleContestIds() throws AppException;

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.ContestDto} entity by contest
   * id.
   *
   * @param contestId
   *          contest id.
   * @param fields
   *          {@link ContestFields} entity indicating fields to be fetched.
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.ContestDto} entity.
   * @throws AppException
   */
  public ContestDto getContestDtoByContestId(
      Integer contestId, Set<ContestFields> fields) throws AppException;

  /**
   * Check if specified contest exists.
   *
   * @param contestId
   *          contest's id.
   * @return true if this contest exists.
   * @throws AppException
   */
  public Boolean checkContestExists(Integer contestId) throws AppException;

  /**
   * Updates contest record by
   * {@link cn.edu.uestc.acmicpc.db.dto.impl.ContestDto} entity.
   *
   * @param contestDto
   *          {@link cn.edu.uestc.acmicpc.db.dto.impl.ContestDto} entity.
   * @throws AppException
   */
  public void updateContest(ContestDto contestDto) throws AppException;

  /**
   * Counts the number of contests fit in condition.
   *
   * @param criteria
   *          {@link ContestCriteria} entity.
   * @return total number of contests fit in the condition.
   * @throws AppException
   */
  public Long count(ContestCriteria criteria) throws AppException;

  /**
   * Get the contests fit in condition and page range.
   *
   * @param criteria
   *          {@link ContestCriteria} entity.
   * @param pageInfo
   *          {@link PageInfo} entity.
   * @param fields
   *          result fields to be fetched
   * @return List of {@link ContestDto} entities.
   * @throws AppException
   */
  public List<ContestDto> getContestList(ContestCriteria criteria, PageInfo pageInfo,
      Set<ContestFields> fields) throws AppException;

  /**
   * Modify one filed of multiply entities as value.
   *
   * @param field
   *          filed need to modified.
   * @param ids
   *          entities' ID split by <code>,</code>.
   * @param value
   *          new value.
   * @throws AppException
   */
  public void operator(String field, String ids, String value)
      throws AppException;

  /**
   * Creates a new contest record.
   *
   * @return the new contest's id.
   * @throws AppException
   */
  public Integer createNewContest() throws AppException;
}
