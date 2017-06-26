package cn.edu.uestc.acmicpc.service;

import static com.google.common.truth.Truth.assertThat;

import cn.edu.uestc.acmicpc.db.criteria.StatusCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.StatusFields;
import cn.edu.uestc.acmicpc.db.dto.impl.StatusDto;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDto;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.testing.UserProvider;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeResultType;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * test cases for {@link cn.edu.uestc.acmicpc.service.iface.StatusService}
 */
public class StatusServiceITTest extends PersistenceITTest {

  @Autowired
  private StatusService statusService;

  @Test
  public void testCountProblemsUserTried_normalUser() throws AppException {
    statusProvider.createStatus(StatusDto.builder()
        .setUserId(testUserId)
        .setResultId(OnlineJudgeReturnType.OJ_WA.ordinal())
        .build());
    statusProvider.createStatus(StatusDto.builder()
        .setUserId(testUserId)
        .setResultId(OnlineJudgeResultType.OJ_TLE.ordinal())
        .build());
    assertThat(statusService.countProblemsThatUserTried(testUserId, false)).isEqualTo(2L);
  }

  @Test
  public void testCountProblemsUserTried_administrator() throws AppException {
    UserDto admin = UserProvider.createUnpersistedUser();
    admin.setType(AuthenticationType.ADMIN.ordinal());
    admin = userProvider.createUser(admin);
    statusProvider.createStatus(StatusDto.builder().setUserId(admin.getUserId()).build());
    assertThat(statusService.countProblemsThatUserTried(admin.getUserId(), false)).isEqualTo(0L);
  }

  @Test
  public void testCountProblemsUserAccepted_normalUser() throws AppException {
    statusProvider.createStatus(StatusDto.builder()
        .setUserId(testUserId)
        .setResultId(OnlineJudgeResultType.OJ_AC.ordinal())
        .build());
    assertThat(statusService.countProblemsThatUserSolved(testUserId, false)).isEqualTo(1L);
  }

  @Test
  public void testCountProblemsUserAccepted_administrator() throws AppException {
    UserDto admin = UserProvider.createUnpersistedUser();
    admin.setType(AuthenticationType.ADMIN.ordinal());
    admin = userProvider.createUser(admin);
    statusProvider.createStatus(StatusDto.builder()
        .setUserId(admin.getUserId())
        .setResultId(OnlineJudgeResultType.OJ_AC.ordinal())
        .build());
    assertThat(statusService.countProblemsThatUserSolved(admin.getUserId(), false)).isEqualTo(0L);
  }

  @Test
  public void testFindAllUserTriedProblemIds_normalUser() throws AppException {
    StatusDto status1 = statusProvider.createStatus(
        StatusDto.builder().setUserId(testUserId).build());
    StatusDto status2 = statusProvider.createStatus(
        StatusDto.builder().setUserId(testUserId).build());
    assertThat(statusService.findAllProblemIdsThatUserTried(testUserId, true))
        .containsExactly(status1.getProblemId(), status2.getProblemId());
  }

  @Test
  public void testFindAllUserTriedProblemIds_administrator() throws AppException {
    UserDto admin = UserProvider.createUnpersistedUser();
    admin.setType(AuthenticationType.ADMIN.ordinal());
    admin = userProvider.createUser(admin);
    statusProvider.createStatus(StatusDto.builder().setUserId(admin.getUserId()).build());
    assertThat(statusService.findAllProblemIdsThatUserTried(admin.getUserId(), false)).isEmpty();
  }

  @Test
  public void testFindAllUserAcceptedProblemIds_normalUser() throws AppException {
    StatusDto status = statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_AC.ordinal()).setUserId(testUserId).build());
    statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_WA.ordinal()).setUserId(testUserId).build());
    assertThat(statusService.findAllProblemIdsThatUserSolved(testUserId, false))
        .containsExactly(status.getProblemId());
  }

  @Test
  public void testFindAllUserAcceptedProblemIds_administrator_notForAdmin() throws AppException {
    UserDto admin = UserProvider.createUnpersistedUser();
    admin.setType(AuthenticationType.ADMIN.ordinal());
    admin = userProvider.createUser(admin);
    statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_AC.ordinal()).setUserId(testUserId).build());
    statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_WA.ordinal()).setUserId(testUserId).build());
    assertThat(statusService.findAllProblemIdsThatUserSolved(admin.getUserId(), false)).isEmpty();
  }

  @Test
  public void testCountUsersTriedProblem() throws AppException {
    StatusDto status = statusProvider.createStatus(StatusDto.builder().build());
    statusProvider.createStatus(StatusDto.builder().setProblemId(status.getProblemId()).build());
    assertThat(statusService.countUsersThatTriedThisProblem(status.getProblemId())).isEqualTo(2L);
  }

  @Test
  public void testCountUsersAcceptedProblem_shouldBeNoUser() throws AppException {
    StatusDto status = statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_WA.ordinal())
        .build());
    assertThat(statusService.countUsersThatSolvedThisProblem(status.getProblemId())).isEqualTo(0L);
  }

  @Test
  public void testCountUsersAcceptedProblem_shouldBeHasUsers() throws AppException {
    StatusDto status = statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_AC.ordinal())
        .build());
    assertThat(statusService.countUsersThatSolvedThisProblem(status.getProblemId())).isEqualTo(1L);
  }

  @Test
  public void testCount_emptyCondition_isNotAdmin() throws AppException {
    statusProvider.createStatuses(2);
    UserDto admin = UserProvider.createUnpersistedUser();
    admin.setType(AuthenticationType.ADMIN.ordinal());
    admin = userProvider.createUser(admin);
    statusProvider.createStatus(StatusDto.builder().setUserId(admin.getUserId()).build());
    StatusCriteria statusCriteria = new StatusCriteria();
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_emptyCondition_isAdmin() throws AppException {
    statusProvider.createStatuses(7);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(7L));
  }

  @Test
  public void testCount_byStartId() throws AppException {
    Integer[] statusIds = statusProvider.createStatuses(6);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.startId = statusIds[1];
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(5L));
  }

  @Test
  public void testCount_byEndId() throws AppException {
    Integer[] statusIds = statusProvider.createStatuses(5);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.endId = statusIds[4];
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(5L));
  }

  @Test
  public void testCount_byStartIdAndEndId() throws AppException {
    Integer[] statusIds = statusProvider.createStatuses(5);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.startId = statusIds[2];
    statusCriteria.endId = statusIds[3];
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byStartIdAndEndId_emptyResult() throws AppException {
    Integer[] statusIds = statusProvider.createStatuses(5);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.startId = statusIds[3];
    statusCriteria.endId = statusIds[2];
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byUserId() throws AppException {
    statusProvider.createStatus(StatusDto.builder().setUserId(testUserId).build());
    statusProvider.createStatus(StatusDto.builder().setUserId(testUserId).build());
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.userId = testUserId;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byUserId_emptyResult() throws AppException {
    statusProvider.createStatus();
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.userId = testUserId;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byProblemId() throws AppException {
    StatusDto status = statusProvider.createStatus();
    statusProvider.createStatus(StatusDto.builder()
        .setProblemId(status.getProblemId()).setUserId(testUserId).build());
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.problemId = status.getProblemId();
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byProblemId_emptyResult() throws AppException {
    statusProvider.createStatus();
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.problemId = 12345;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byResult() throws AppException {
    statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_AC.ordinal()).build());
    statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_AC.ordinal()).build());
    statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_WA.ordinal()).build());
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.result = OnlineJudgeResultType.OJ_AC;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byResult_emptyResult() throws AppException {
    statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_AC.ordinal()).build());
    statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_AC.ordinal()).build());
    statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_WA.ordinal()).build());
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.result = OnlineJudgeResultType.OJ_SE;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byResults() throws AppException {
    statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_MLE.ordinal()).build());
    statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_CE.ordinal()).build());
    statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_WA.ordinal()).build());
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.results = new HashSet<>(Arrays.asList(
        OnlineJudgeResultType.OJ_MLE,
        OnlineJudgeResultType.OJ_CE,
        OnlineJudgeResultType.OJ_OLE));
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byResults_emptyResult() throws AppException {
    statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_MLE.ordinal()).build());
    statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_CE.ordinal()).build());
    statusProvider.createStatus(StatusDto.builder()
        .setResultId(OnlineJudgeResultType.OJ_WA.ordinal()).build());
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.results = new HashSet<>(Arrays.asList(
        OnlineJudgeResultType.OJ_JUDGING,
        OnlineJudgeResultType.OJ_RE,
        OnlineJudgeResultType.OJ_RF));
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byLanguageId() throws AppException {
    statusProvider.createStatuses(5);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.languageId = 1;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(5L));
  }

  @Test
  public void testCount_byLanguageId_emptyResult() throws AppException {
    statusProvider.createStatuses(5);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.languageId = 2;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byUserName() throws AppException {
    UserDto user = userProvider.createUser();
    statusProvider.createStatus(StatusDto.builder().setUserId(user.getUserId()).build());
    statusProvider.createStatus(StatusDto.builder().setUserId(user.getUserId()).build());
    statusProvider.createStatuses(5);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.userName = user.getUserName();
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byUserName_emptyResult_noStatus() throws AppException {
    UserDto user = userProvider.createUser();
    statusProvider.createStatuses(5);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.userName = user.getUserName();
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byUserName_emptyResult_noUser() throws AppException {
    statusProvider.createStatuses(5);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.userName = "non_existed_user";
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byStartTime() throws AppException {
    statusProvider.createStatuses(2);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.startTime = Timestamp.valueOf("2014-01-01 00:00:00");
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byEndTime() throws AppException {
    statusProvider.createStatuses(2);
    StatusDto status = statusProvider.createStatus();
    status.setTime(Timestamp.valueOf("2014-03-26 10:59:59"));
    statusService.updateStatus(status);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.endTime = Timestamp.valueOf("2014-03-26 10:59:59");
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(1L));
  }

  @Test
  public void testCount_byStartTimeAndEndTime() throws AppException {
    statusProvider.createStatuses(2);
    StatusDto status1 = statusProvider.createStatus();
    status1.setTime(Timestamp.valueOf("2013-11-01 00:00:00"));
    statusService.updateStatus(status1);
    StatusDto status2 = statusProvider.createStatus();
    status2.setTime(Timestamp.valueOf("2014-04-01 00:00:00"));
    statusService.updateStatus(status2);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.startTime = Timestamp.valueOf("2013-11-01 00:00:00");
    statusCriteria.endTime = Timestamp.valueOf("2014-03-01 00:00:00");
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(1L));
  }

  @Test
  public void testCount_byStartTimeAndEndTime_emptyResult() throws AppException {
    statusProvider.createStatuses(5);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.endTime = Timestamp.valueOf("2013-01-01 00:00:00");
    statusCriteria.startTime = Timestamp.valueOf("2014-01-01 00:00:00");
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byContestId() throws AppException {
    StatusDto status = statusProvider.createStatus(StatusDto.builder()
        .setContestId(contestProvider.createContest().getContestId()).build());
    statusProvider.createStatus(StatusDto.builder()
        .setContestId(status.getContestId()).build());
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.contestId = status.getContestId();
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(2L));
  }

  @Test
  public void testGetStatusList() throws AppException {
    Integer[] statusIds = statusProvider.createStatuses(5);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    List<StatusDto> statusList = statusService.getStatusList(
        statusCriteria, null, StatusFields.FIELDS_FOR_LIST_PAGE);
    assertThat(statusList).hasSize(statusIds.length);
    for (int i = 0; i < statusIds.length; i++) {
      assertThat(statusList.get(i).getStatusId()).isEqualTo(statusIds[i]);
    }
  }

  @Test
  public void testGetStatusList_withPageInfo() throws AppException {
    statusProvider.createStatuses(6);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    PageInfo pageInfo = PageInfo.create(300L, 4L, 10, 2L);
    List<StatusDto> statusList = statusService.getStatusList(
        statusCriteria, pageInfo, StatusFields.FIELDS_FOR_LIST_PAGE);
    assertThat(statusList).hasSize(2);
  }

  @Test
  public void testGetStatusList_withPageInfo_emptyResult() throws AppException {
    statusProvider.createStatuses(6);
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    PageInfo pageInfo = PageInfo.create(300L, 4L, 10, 3L);
    List<StatusDto> statusList = statusService.getStatusList(
        statusCriteria, pageInfo, StatusFields.FIELDS_FOR_LIST_PAGE);
    assertThat(statusList).isEmpty();
  }

  @Test
  public void testGetStatusInformation() throws AppException {
    StatusDto status = statusProvider.createStatus();
    StatusDto statusInformationDto = statusService.getStatusDto(
        status.getStatusId(), StatusFields.FIELDS_FOR_STATUS_INFO);
    assertThat(statusInformationDto.getStatusId()).isEqualTo(status.getStatusId());
    assertThat(statusInformationDto.getCodeContent()).isEqualTo("content");
    assertThat(statusInformationDto.getUserId()).isEqualTo(status.getUserId());
    assertThat(statusInformationDto.getCompileInfoId()).isEqualTo(status.getCompileInfoId());
  }
}
