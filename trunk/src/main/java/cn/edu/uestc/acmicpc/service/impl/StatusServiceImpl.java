package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.StatusDao;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusDto;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusForJudgeDto;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusInformationDto;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusListDto;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeResultType;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.enums.ProblemType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
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

  private final StatusDao statusDao;

  @Autowired
  public StatusServiceImpl(StatusDao statusDao) {
    this.statusDao = statusDao;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Integer> findAllUserAcceptedProblemIds(Integer userId, Boolean isAdmin)
      throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.userId = userId;
    statusCondition.results.add(OnlineJudgeResultType.OJ_AC);
    statusCondition.isForAdmin = isAdmin;
    return (List<Integer>) statusDao.findAll("distinct problemByProblemId.problemId",
        statusCondition.getCondition());
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Integer> findAllUserTriedProblemIds(Integer userId, Boolean isAdmin)
      throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.userId = userId;
    statusCondition.isForAdmin = isAdmin;
    return (List<Integer>) statusDao.findAll("distinct problemByProblemId.problemId",
        statusCondition.getCondition());
  }

  @Override
  public Long countProblemsUserTried(Integer userId) throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.userId = userId;
    statusCondition.type = ProblemType.NORMAL.ordinal();
    return statusDao.customCount("distinct problemId", statusCondition.getCondition());
  }

  @Override
  public Long countProblemsUserAccepted(Integer userId) throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.userId = userId;
    statusCondition.results.add(OnlineJudgeResultType.OJ_AC);
    statusCondition.type = ProblemType.NORMAL.ordinal();
    return statusDao.customCount("distinct problemId", statusCondition.getCondition());
  }

  @Override
  public Long countUsersTriedProblem(Integer problemId) throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.problemId = problemId;
    return statusDao.customCount("distinct userId", statusCondition.getCondition());
  }

  @Override
  public Long countUsersAcceptedProblem(Integer problemId) throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.problemId = problemId;
    statusCondition.results.add(OnlineJudgeResultType.OJ_AC);
    return statusDao.customCount("distinct userId", statusCondition.getCondition());
  }

  @Override
  public Long count(StatusCondition condition) throws AppException {
    return statusDao.count(condition.getCondition());
  }

  @Override
  public List<StatusListDto> getStatusList(StatusCondition statusCondition,
      PageInfo pageInfo) throws AppException {
    Condition condition = statusCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return statusDao.findAll(StatusListDto.class, StatusListDto.builder(),
        condition);
  }

  @Override
  public List<StatusListDto> getStatusList(StatusCondition condition) throws AppException {
    return statusDao
        .findAll(StatusListDto.class, StatusListDto.builder(), condition.getCondition());
  }

  private void updateStatusByStatusDto(Status status, StatusDto statusDto) {
    if (statusDto.getResult() != null) {
      status.setResult(statusDto.getResult());
    }
    if (statusDto.getMemoryCost() != null) {
      status.setMemoryCost(statusDto.getMemoryCost());
    }
    if (statusDto.getTimeCost() != null) {
      status.setTimeCost(statusDto.getTimeCost());
    }
    if (statusDto.getLength() != null) {
      status.setLength(statusDto.getLength());
    }
    if (statusDto.getTime() != null) {
      status.setTime(statusDto.getTime());
    }
    if (statusDto.getCaseNumber() != null) {
      status.setCaseNumber(statusDto.getCaseNumber());
    }
    if (statusDto.getCodeId() != null) {
      status.setCodeId(statusDto.getCodeId());
    }
    if (statusDto.getCompileInfoId() != null) {
      status.setCompileInfoId(statusDto.getCompileInfoId());
    }
    if (statusDto.getContestId() != null) {
      status.setContestId(statusDto.getContestId());
    }
    if (statusDto.getLanguageId() != null) {
      status.setLanguageId(statusDto.getLanguageId());
    }
    if (statusDto.getProblemId() != null) {
      status.setProblemId(statusDto.getProblemId());
    }
    if (statusDto.getUserId() != null) {
      status.setUserId(statusDto.getUserId());
    }
  }

  @Override
  public void createNewStatus(StatusDto statusDto) throws AppException {
    Status status = new Status();
    updateStatusByStatusDto(status, statusDto);
    statusDao.addOrUpdate(status);
  }

  @Override
  public StatusInformationDto getStatusInformation(Integer statusId)
      throws AppException {
    return statusDao.getDtoByUniqueField(StatusInformationDto.class,
        StatusInformationDto.builder(), "statusId", statusId);
  }

  @Override
  public void rejudge(StatusCondition statusCondition) throws AppException {
    Map<String, Object> properties = new HashMap<>();
    properties.put("result", OnlineJudgeReturnType.OJ_REJUDGING.ordinal());
    statusCondition.isVisible = null;
    statusCondition.userName = null;
    statusCondition.isForAdmin = true;
    statusDao.updateEntitiesByCondition(properties,
        statusCondition.getCondition());
  }

  @Override
  public List<StatusInformationDto> getStatusInformationDtoList(StatusCondition statusCondition)
      throws AppException {
    return statusDao.findAll(StatusInformationDto.class,
        StatusInformationDto.builder(), statusCondition.getCondition());
  }

  @Override
  public List<StatusForJudgeDto> getQueuingStatus(boolean isFirstTime) throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.results.add(OnlineJudgeResultType.OJ_WAIT);
    if (isFirstTime) {
      statusCondition.results.add(OnlineJudgeResultType.OJ_JUDGING);
    }
    statusCondition.isForAdmin = true;
    statusCondition.orderFields = "statusId";
    statusCondition.orderAsc = "true";
    return statusDao.findAll(StatusForJudgeDto.class,
        StatusForJudgeDto.builder(), statusCondition.getCondition());
  }

  @Override
  public void updateStatusByStatusForJudgeDto(
      StatusForJudgeDto statusForJudgeDto) throws AppException {
    Map<String, Object> properties = new HashMap<>();
    if (statusForJudgeDto.getResult() != null) {
      properties.put("result", statusForJudgeDto.getResult());
    }
    if (statusForJudgeDto.getCaseNumber() != null) {
      properties.put("caseNumber", statusForJudgeDto.getCaseNumber());
    }
    if (statusForJudgeDto.getTimeCost() != null) {
      properties.put("timeCost", statusForJudgeDto.getTimeCost());
    }
    if (statusForJudgeDto.getMemoryCost() != null) {
      properties.put("memoryCost", statusForJudgeDto.getMemoryCost());
    }
    if (statusForJudgeDto.getCompileInfoId() != null) {
      properties.put("compileInfoId", statusForJudgeDto.getCompileInfoId());
    }
    statusDao.updateEntitiesByField(properties, "statusId", statusForJudgeDto
        .getStatusId().toString());
  }

}
