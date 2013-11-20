package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusInformationDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusListDTO;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.view.PageInfo;

import java.util.List;

/**
 * Service interface for {@link Status}.
 */
public interface StatusService extends DatabaseService<Status, Integer> {

  /**
   * list user's trieds problem ids
   *
   * @param userId user's entity id
   * @return problem id list
   * @throws AppException
   */
  public List<Integer> findAllUserTriedProblemIds(Integer userId) throws AppException;

  /**
   * list user's accepted problem ids
   *
   * @param userId user's entity id
   * @return problem id list
   * @throws AppException
   */
  public List<Integer> findAllUserAcceptedProblemIds(Integer userId) throws AppException;

  /**
   * TODO(mzry1992)
   * @param condition
   * @return
   * @throws AppException
   */
  public Long count(StatusCondition condition) throws AppException;

  /**
   * TODO(mzry1992)
   * @param condition
   * @param pageInfo
   * @return
   * @throws AppException
   */
  public List<StatusListDTO> getStatusList(StatusCondition condition,
                                           PageInfo pageInfo) throws AppException;

  /**
   * TODO(mzry1992)
   * @param statusDTO
   * @throws AppException
   */
  public void createNewStatus(StatusDTO statusDTO) throws AppException;

  public StatusInformationDTO getStatusInformation(Integer statusId) throws AppException;
}
