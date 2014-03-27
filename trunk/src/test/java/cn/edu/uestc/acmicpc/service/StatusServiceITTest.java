package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusInformationDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusListDTO;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * test cases for {@link cn.edu.uestc.acmicpc.service.iface.StatusService}
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class StatusServiceITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private StatusService statusService;

  @Test
  public void testCountProblemsUserTried_normalUser() throws AppException {
    Integer userId = 3;
    Assert.assertEquals(statusService.countProblemsUserTried(userId), Long.valueOf(2L));
  }

  @Test
  public void testCountProblemsUserTried_administrator() throws AppException {
    Integer userId = 1;
    Assert.assertEquals(statusService.countProblemsUserTried(userId), Long.valueOf(0L));
  }

  @Test
  public void testCountProblemsUserAccepted_normalUser() throws AppException {
    Integer userId = 3;
    Assert.assertEquals(statusService.countProblemsUserAccepted(userId), Long.valueOf(1L));
  }

  @Test
  public void testCountProblemsUserAccepted_administrator() throws AppException {
    Integer userId = 1;
    Assert.assertEquals(statusService.countProblemsUserAccepted(userId), Long.valueOf(0L));
  }

  @Test
  public void testFindAllUserTriedProblemIds_normalUser_forAdmin() throws AppException {
    Integer userId = 3;
    Boolean isAdmin = true;
    Assert.assertEquals(statusService.findAllUserTriedProblemIds(userId, isAdmin), Arrays.asList(new Object[] {1, 2}));
  }

  @Test
  public void testFindAllUserTriedProblemIds_normalUser_notForAdmin() throws AppException {
    Integer userId = 3;
    Boolean isAdmin = false;
    Assert.assertEquals(statusService.findAllUserTriedProblemIds(userId, isAdmin), Arrays.asList(new Object[] {1, 2}));
  }

  @Test
  public void testFindAllUserTriedProblemIds_administrator_forAdmin() throws AppException {
    Integer userId = 1;
    Boolean isAdmin = true;
    Assert.assertEquals(statusService.findAllUserTriedProblemIds(userId, isAdmin), Arrays.asList(new Object[] {1}));
  }

  @Test
  public void testFindAllUserTriedProblemIds_administrator_notForAdmin() throws AppException {
    Integer userId = 1;
    Boolean isAdmin = false;
    Assert.assertEquals(statusService.findAllUserTriedProblemIds(userId, isAdmin), Arrays.asList(new Object[] {}));
  }

  @Test
  public void testFindAllUserAcceptedProblemIds_normalUser_forAdmin() throws AppException {
    Integer userId = 3;
    Boolean isAdmin = true;
    Assert.assertEquals(statusService.findAllUserAcceptedProblemIds(userId, isAdmin), Arrays.asList(new Object[] {2}));
  }

  @Test
  public void testFindAllUserAcceptedProblemIds_normalUser_notForAdmin() throws AppException {
    Integer userId = 3;
    Boolean isAdmin = false;
    Assert.assertEquals(statusService.findAllUserAcceptedProblemIds(userId, isAdmin), Arrays.asList(new Object[] {2}));
  }

  @Test
  public void testFindAllUserAcceptedProblemIds_administrator_forAdmin() throws AppException {
    Integer userId = 1;
    Boolean isAdmin = true;
    Assert.assertEquals(statusService.findAllUserAcceptedProblemIds(userId, isAdmin), Arrays.asList(new Object[] {1}));
  }

  @Test
  public void testFindAllUserAcceptedProblemIds_administrator_notForAdmin() throws AppException {
    Integer userId = 1;
    Boolean isAdmin = false;
    Assert.assertEquals(statusService.findAllUserAcceptedProblemIds(userId, isAdmin), Arrays.asList(new Object[] {}));
  }

  @Test
  public void testCountUsersTriedProblem() throws AppException {
    Integer problemId1 = 1;
    Assert.assertEquals(statusService.countUsersTriedProblem(problemId1), Long.valueOf(1));
  }

  @Test
  public void testCountUsersAcceptedProblem_shouldBeNoUser() throws AppException {
    Integer problemId1 = 1;
    Assert.assertEquals(statusService.countUsersAcceptedProblem(problemId1), Long.valueOf(0));
  }

  @Test
  public void testCountUsersAcceptedProblem_shouldBeHasUsers() throws AppException {
    Integer problemId2 = 2;
    Assert.assertEquals(statusService.countUsersAcceptedProblem(problemId2), Long.valueOf(1));
  }

  @Test
  public void testCount_emptyCondition_isNotAdmin() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(2L));
  }

  @Test
  public void testCount_emptyCondition_isAdmin() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(7L));
  }

  @Test
  public void testCount_byStartId() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.startId = 2;
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(6L));
  }

  @Test
  public void testCount_byEndId() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.endId = 5;
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(5L));
  }

  @Test
  public void testCount_byStartIdAndEndId() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.startId = 3;
    statusCondition.endId = 4;
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(2L));
  }

  @Test
  public void testCount_byStartIdAndEndId_emptyResult() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.startId = 4;
    statusCondition.endId = 3;
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(0L));
  }

  @Test
  public void testCount_byUserId() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.userId = 3;
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(2L));
  }

  @Test
  public void testCount_byUserId_emptyResult() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.userId = 4;
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(0L));
  }

  @Test
  public void testCount_byProblemId() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.problemId = 1;
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(6L));
  }

  @Test
  public void testCount_byProblemId_emptyResult() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.problemId = 3;
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(0L));
  }

  @Test
  public void testCount_byResult() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.result = Global.OnlineJudgeResultType.OJ_AC;
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(2L));
  }

  @Test
  public void testCount_byResult_emptyResult() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.result = Global.OnlineJudgeResultType.OJ_SE;
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(0L));
  }

  @Test
  public void testCount_byResults() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.results = new HashSet<>(Arrays.asList(
        Global.OnlineJudgeResultType.OJ_MLE,
        Global.OnlineJudgeResultType.OJ_CE,
        Global.OnlineJudgeResultType.OJ_OLE
    ));
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(2L));
  }

  @Test
  public void testCount_byResults_emptyResult() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.results = new HashSet<>(Arrays.asList(
        Global.OnlineJudgeResultType.OJ_JUDGING,
        Global.OnlineJudgeResultType.OJ_RE,
        Global.OnlineJudgeResultType.OJ_RF
    ));
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(0L));
  }

  @Test
  public void testCount_byLanguageId() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.languageId = 1;
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(7L));
  }

  @Test
  public void testCount_byLanguageId_emptyResult() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.languageId = 2;
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(0L));
  }

  @Test
  public void testCount_byUserName() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.userName = "administrator";
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(3L));
  }

  @Test
  public void testCount_byUserName_emptyResult_noStatus() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.userName = "user5";
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(0L));
  }

  @Test
  public void testCount_byUserName_emptyResult_noUser() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.userName = "non_existed_user";
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(0L));
  }

  @Test
  public void testCount_byStartTime() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.startTime = Timestamp.valueOf("2014-01-01 00:00:00");
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(2L));
  }

  @Test
  public void testCount_byEndTime() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.endTime = Timestamp.valueOf("2014-03-26 10:59:59");
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(6L));
  }

  @Test
  public void testCount_byStartTimeAndEndTime() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.endTime = Timestamp.valueOf("2014-03-01 00:00:00");
    statusCondition.startTime = Timestamp.valueOf("2013-11-01 00:00:00");
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(1L));
  }

  @Test
  public void testCount_byStartTimeAndEndTime_emptyResult() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.endTime = Timestamp.valueOf("2013-01-01 00:00:00");
    statusCondition.startTime = Timestamp.valueOf("2014-01-01 00:00:00");
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(0L));
  }

  @Test
  public void testCount_byContestId() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.isForAdmin = true;
    statusCondition.contestId = 1;
    Assert.assertEquals(statusService.count(statusCondition), Long.valueOf(2L));
  }

  @Test
  public void testGetStatusList() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.problemId = 1;
    statusCondition.isForAdmin = true;
    List<StatusListDTO> statusListDTOs = statusService.getStatusList(statusCondition);
    Assert.assertEquals(statusListDTOs.size(), 6);
    Assert.assertEquals(statusListDTOs.get(0).getStatusId(), Integer.valueOf(1));
    Assert.assertEquals(statusListDTOs.get(1).getStatusId(), Integer.valueOf(2));
    Assert.assertEquals(statusListDTOs.get(2).getStatusId(), Integer.valueOf(3));
    Assert.assertEquals(statusListDTOs.get(3).getStatusId(), Integer.valueOf(4));
    Assert.assertEquals(statusListDTOs.get(4).getStatusId(), Integer.valueOf(5));
    Assert.assertEquals(statusListDTOs.get(5).getStatusId(), Integer.valueOf(6));
  }

  @Test
  public void testGetStatusList_withPageInfo() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.problemId = 1;
    statusCondition.isForAdmin = true;
    PageInfo pageInfo = PageInfo.create(300L, 4L, 10, 2L);
    List<StatusListDTO> statusListDTOs = statusService.getStatusList(statusCondition, pageInfo);
    Assert.assertEquals(statusListDTOs.size(), 2);
  }

  @Test
  public void testGetStatusList_withPageInfo_emptyResult() throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.problemId = 1;
    statusCondition.isForAdmin = true;
    PageInfo pageInfo = PageInfo.create(300L, 4L, 10, 3L);
    List<StatusListDTO> statusListDTOs = statusService.getStatusList(statusCondition, pageInfo);
    Assert.assertEquals(statusListDTOs.size(), 0);
  }

  @Test
  public void testGetStatusInformation() throws AppException {
    Integer statusId = 5;
    StatusInformationDTO statusInformationDTO = statusService.getStatusInformation(statusId);
    Assert.assertEquals(statusInformationDTO.getStatusId(), Integer.valueOf(5));
    Assert.assertEquals(statusInformationDTO.getCodeContent(), "code");
    Assert.assertEquals(statusInformationDTO.getUserId(), Integer.valueOf(2));
    Assert.assertEquals(statusInformationDTO.getCompileInfoId(), Integer.valueOf(1));
  }
}
