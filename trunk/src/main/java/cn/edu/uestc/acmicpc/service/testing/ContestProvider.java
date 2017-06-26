package cn.edu.uestc.acmicpc.service.testing;

import cn.edu.uestc.acmicpc.db.dto.field.ContestFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDto;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Contest provider for integration testing.
 */
@Component
public class ContestProvider {

  @Autowired private ContestService contestService;

  public ContestDto createContest() throws AppException {
    Integer contestId = contestService.createNewContest();
    return contestService.getContestDtoByContestId(contestId, ContestFields.BASIC_FIELDS);
  }
}
