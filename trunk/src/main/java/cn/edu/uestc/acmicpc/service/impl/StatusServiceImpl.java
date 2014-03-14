package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusForJudgeDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusInformationDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusListDTO;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.util.settings.Global.OnlineJudgeResultType;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation for {@link StatusService}.
 */
@Service
public class StatusServiceImpl extends AbstractService implements StatusService {

  private final IStatusDAO statusDAO;

  @Autowired
  public StatusServiceImpl(IStatusDAO statusDAO) {
    this.statusDAO = statusDAO;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Integer> findAllUserAcceptedProblemIds(Integer userId, Boolean isAdmin)
      throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.userId = userId;
    statusCondition.results.add(Global.OnlineJudgeResultType.OJ_AC);
    statusCondition.isForAdmin = isAdmin;
    return (List<Integer>) statusDAO.findAll("problemByProblemId.problemId",
        statusCondition.getCondition());
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Integer> findAllUserTriedProblemIds(Integer userId, Boolean isAdmin)
      throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.userId = userId;
    statusCondition.isForAdmin = isAdmin;
    return (List<Integer>) statusDAO.findAll("problemByProblemId.problemId",
        statusCondition.getCondition());
  }

  @Override
  public Long countProblemsUserTired(Integer userId) throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.userId = userId;
    return statusDAO.customCount("distinct problemId", statusCondition.getCondition());
  }

  @Override
  public Long countProblemsUserAccepted(Integer userId) throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.userId = userId;
    statusCondition.results.add(Global.OnlineJudgeResultType.OJ_AC);
    return statusDAO.customCount("distinct problemId", statusCondition.getCondition());
  }

  @Override
  public Long countUsersTiredProblem(Integer problemId) throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.problemId = problemId;
    return statusDAO.customCount("distinct userId", statusCondition.getCondition());
  }

  @Override
  public Long countUsersAcceptedProblem(Integer problemId) throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.problemId = problemId;
    statusCondition.results.add(Global.OnlineJudgeResultType.OJ_AC);
    return statusDAO.customCount("distinct userId", statusCondition.getCondition());
  }

  @Override
  public Long count(StatusCondition condition) throws AppException {
    return statusDAO.count(condition.getCondition());
  }

  @Override
  public List<StatusListDTO> getStatusList(StatusCondition statusCondition,
                                           PageInfo pageInfo) throws AppException {
    Condition condition = statusCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return statusDAO.findAll(StatusListDTO.class, StatusListDTO.builder(),
        condition);
  }

  @Override
  public List<StatusListDTO> getStatusList(StatusCondition condition) throws AppException {
    return statusDAO.findAll(StatusListDTO.class, StatusListDTO.builder(), condition.getCondition());
  }

  private void updateStatusByStatusDTO(Status status, StatusDTO statusDTO) {
    if (statusDTO.getResult() != null)
      status.setResult(statusDTO.getResult());
    if (statusDTO.getMemoryCost() != null)
      status.setMemoryCost(statusDTO.getMemoryCost());
    if (statusDTO.getTimeCost() != null)
      status.setTimeCost(statusDTO.getTimeCost());
    if (statusDTO.getLength() != null)
      status.setLength(statusDTO.getLength());
    if (statusDTO.getTime() != null)
      status.setTime(statusDTO.getTime());
    if (statusDTO.getCaseNumber() != null)
      status.setCaseNumber(statusDTO.getCaseNumber());
    if (statusDTO.getCodeId() != null)
      status.setCodeId(statusDTO.getCodeId());
    if (statusDTO.getCompileInfoId() != null)
      status.setCompileInfoId(statusDTO.getCompileInfoId());
    if (statusDTO.getContestId() != null)
      status.setContestId(statusDTO.getContestId());
    if (statusDTO.getLanguageId() != null)
      status.setLanguageId(statusDTO.getLanguageId());
    if (statusDTO.getProblemId() != null)
      status.setProblemId(statusDTO.getProblemId());
    if (statusDTO.getUserId() != null)
      status.setUserId(statusDTO.getUserId());
  }

  @Override
  public void createNewStatus(StatusDTO statusDTO) throws AppException {
    Status status = new Status();
    updateStatusByStatusDTO(status, statusDTO);
    statusDAO.add(status);
  }

  @Override
  public StatusInformationDTO getStatusInformation(Integer statusId)
      throws AppException {
    return statusDAO.getDTOByUniqueField(StatusInformationDTO.class,
        StatusInformationDTO.builder(), "statusId", statusId);
  }

  @Override
  public IStatusDAO getDAO() {
    return statusDAO;
  }

  @Override
  public void rejudge(StatusCondition statusCondition) throws AppException {
    Map<String, Object> properties = new HashMap<>();
    properties.put("result",
        Global.OnlineJudgeReturnType.OJ_REJUDGING.ordinal());
    statusDAO.updateEntitiesByCondition(properties,
        statusCondition.getCondition());
  }

  @Override
  public List<StatusForJudgeDTO> getQueuingStatus(boolean isFirstTime) throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.results.add(OnlineJudgeResultType.OJ_WAIT);
    if (isFirstTime) {
     statusCondition.results.add(OnlineJudgeResultType.OJ_JUDGING);
    }
    statusCondition.isForAdmin = true;
    statusCondition.orderFields = "statusId";
    statusCondition.orderAsc = "true";
    return statusDAO.findAll(StatusForJudgeDTO.class,
        StatusForJudgeDTO.builder(), statusCondition.getCondition());
  }

  @Override
  public void updateStatusByStatusForJudgeDTO(
      StatusForJudgeDTO statusForJudgeDTO) throws AppException {
    Map<String, Object> properties = new HashMap<>();
    if (statusForJudgeDTO.getResult() != null)
      properties.put("result", statusForJudgeDTO.getResult());
    if (statusForJudgeDTO.getCaseNumber() != null)
      properties.put("caseNumber", statusForJudgeDTO.getCaseNumber());
    if (statusForJudgeDTO.getTimeCost() != null)
      properties.put("timeCost", statusForJudgeDTO.getTimeCost());
    if (statusForJudgeDTO.getMemoryCost() != null)
      properties.put("memoryCost", statusForJudgeDTO.getMemoryCost());
    if (statusForJudgeDTO.getCompileInfoId() != null)
      properties.put("compileInfoId", statusForJudgeDTO.getCompileInfoId());
    statusDAO.updateEntitiesByField(properties, "statusId", statusForJudgeDTO
        .getStatusId().toString());
  }

}
