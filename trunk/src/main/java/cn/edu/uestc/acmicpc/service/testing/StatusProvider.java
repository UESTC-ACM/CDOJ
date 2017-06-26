package cn.edu.uestc.acmicpc.service.testing;

import cn.edu.uestc.acmicpc.db.dto.field.StatusFields;
import cn.edu.uestc.acmicpc.db.dto.impl.StatusDto;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeResultType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Status provider for integration testing.
 */
@Component
public class StatusProvider {

  @Autowired
  private StatusService statusService;

  @Autowired
  private UserProvider userProvider;

  @Autowired
  private ProblemProvider problemProvider;

  @Autowired
  private CodeProvider codeProvider;

  public StatusDto createStatus(StatusDto status) throws AppException {
    prepareDependentFields(status);
    Integer statusId = statusService.createNewStatus(status);
    return statusService.getStatusDto(statusId, StatusFields.ALL_FIELDS);
  }

  public StatusDto createStatus() throws AppException {
    return createStatus(StatusDto.builder().build());
  }

  public Integer[] createStatuses(int count) throws AppException {
    Integer[] statusIds = new Integer[count];
    for (int i = 0; i < count; i++) {
      statusIds[i] = createStatus().getStatusId();
    }
    return statusIds;
  }

  private void prepareDependentFields(StatusDto status) throws AppException {
    if (status.getUserId() == null) {
      status.setUserId(userProvider.createUser().getUserId());
    }
    if (status.getProblemId() == null) {
      status.setProblemId(problemProvider.createProblem().getProblemId());
    }
    if (status.getCodeId() == null) {
      status.setCodeId(codeProvider.createCode().getCodeId());
    }
    if (status.getResultId() == null) {
      status.setResultId(OnlineJudgeResultType.OJ_AC.ordinal());
    }
    status.setLanguageId(1);
    status.setCaseNumber(1);
    status.setDataCount(1);
    status.setLength(123);
    status.setTime(new Timestamp(System.currentTimeMillis()));
    status.setTimeCost(123);
  }
}
