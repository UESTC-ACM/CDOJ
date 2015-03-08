package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.db.criteria.StatusCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.StatusFields;
import cn.edu.uestc.acmicpc.db.dto.impl.StatusDto;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeResultType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * test cases for {@link cn.edu.uestc.acmicpc.service.iface.StatusService}
 */
public class StatusServiceITTest extends PersistenceITTest {

  @Autowired
  private StatusService statusService;

  @Test
  public void testCountProblemsUserTried_normalUser() throws AppException {
    Integer userId = 3;
    Assert.assertEquals(statusService.countProblemsThatUserTried(userId, false), Long.valueOf(2L));
  }

  @Test
  public void testCountProblemsUserTried_administrator() throws AppException {
    Integer userId = 1;
    Assert.assertEquals(statusService.countProblemsThatUserTried(userId, false), Long.valueOf(0L));
  }

  @Test
  public void testCountProblemsUserAccepted_normalUser() throws AppException {
    Integer userId = 3;
    Assert.assertEquals(statusService.countProblemsThatUserSolved(userId, false), Long.valueOf(1L));
  }

  @Test
  public void testCountProblemsUserAccepted_administrator() throws AppException {
    Integer userId = 1;
    Assert.assertEquals(statusService.countProblemsThatUserSolved(userId, false), Long.valueOf(0L));
  }

  @Test
  public void testFindAllUserTriedProblemIds_normalUser_forAdmin() throws AppException {
    Integer userId = 3;
    Boolean isAdmin = true;
    Assert.assertEquals(statusService.findAllProblemIdsThatUserTried(userId, isAdmin),
        Arrays.asList(1, 2));
  }

  @Test
  public void testFindAllUserTriedProblemIds_normalUser_notForAdmin() throws AppException {
    Integer userId = 3;
    Boolean isAdmin = false;
    Assert.assertEquals(statusService.findAllProblemIdsThatUserTried(userId, isAdmin),
        Arrays.asList(1, 2));
  }

  @Test
  public void testFindAllUserTriedProblemIds_administrator_forAdmin() throws AppException {
    Integer userId = 1;
    Boolean isAdmin = true;
    Assert.assertEquals(statusService.findAllProblemIdsThatUserTried(userId, isAdmin),
        Arrays.asList(1));
  }

  @Test
  public void testFindAllUserTriedProblemIds_administrator_notForAdmin() throws AppException {
    Integer userId = 1;
    Boolean isAdmin = false;
    Assert.assertEquals(statusService.findAllProblemIdsThatUserTried(userId, isAdmin),
        Arrays.asList(new Object[] {}));
  }

  @Test
  public void testFindAllUserAcceptedProblemIds_normalUser_forAdmin() throws AppException {
    Integer userId = 3;
    Boolean isAdmin = true;
    Assert.assertEquals(statusService.findAllProblemIdsThatUserSolved(userId, isAdmin),
        Arrays.asList(2));
  }

  @Test
  public void testFindAllUserAcceptedProblemIds_normalUser_notForAdmin() throws AppException {
    Integer userId = 3;
    Boolean isAdmin = false;
    Assert.assertEquals(statusService.findAllProblemIdsThatUserSolved(userId, isAdmin),
        Arrays.asList(2));
  }

  @Test
  public void testFindAllUserAcceptedProblemIds_administrator_forAdmin() throws AppException {
    Integer userId = 1;
    Boolean isAdmin = true;
    Assert.assertEquals(statusService.findAllProblemIdsThatUserSolved(userId, isAdmin),
        Arrays.asList(1));
  }

  @Test
  public void testFindAllUserAcceptedProblemIds_administrator_notForAdmin() throws AppException {
    Integer userId = 1;
    Boolean isAdmin = false;
    Assert.assertEquals(statusService.findAllProblemIdsThatUserSolved(userId, isAdmin),
        Arrays.asList());
  }

  @Test
  public void testCountUsersTriedProblem() throws AppException {
    Integer problemId = 1;
    Assert.assertEquals(statusService.countUsersThatTriedThisProblem(problemId), Long.valueOf(1));
  }

  @Test
  public void testCountUsersAcceptedProblem_shouldBeNoUser() throws AppException {
    Integer problemId = 1;
    Assert.assertEquals(statusService.countUsersThatSolvedThisProblem(problemId), Long.valueOf(0));
  }

  @Test
  public void testCountUsersAcceptedProblem_shouldBeHasUsers() throws AppException {
    Integer problemId = 2;
    Assert.assertEquals(statusService.countUsersThatSolvedThisProblem(problemId), Long.valueOf(1));
  }

  @Test
  public void testCount_emptyCondition_isNotAdmin() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_emptyCondition_isAdmin() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(7L));
  }

  @Test
  public void testCount_byStartId() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.startId = 2;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(6L));
  }

  @Test
  public void testCount_byEndId() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.endId = 5;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(5L));
  }

  @Test
  public void testCount_byStartIdAndEndId() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.startId = 3;
    statusCriteria.endId = 4;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byStartIdAndEndId_emptyResult() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.startId = 4;
    statusCriteria.endId = 3;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byUserId() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.userId = 3;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byUserId_emptyResult() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.userId = 4;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byProblemId() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.problemId = 1;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(6L));
  }

  @Test
  public void testCount_byProblemId_emptyResult() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.problemId = 3;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byResult() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.result = OnlineJudgeResultType.OJ_AC;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byResult_emptyResult() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.result = OnlineJudgeResultType.OJ_SE;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byResults() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.results = new HashSet<>(Arrays.asList(
        OnlineJudgeResultType.OJ_MLE,
        OnlineJudgeResultType.OJ_CE,
        OnlineJudgeResultType.OJ_OLE
    ));
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byResults_emptyResult() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.results = new HashSet<>(Arrays.asList(
        OnlineJudgeResultType.OJ_JUDGING,
        OnlineJudgeResultType.OJ_RE,
        OnlineJudgeResultType.OJ_RF
    ));
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byLanguageId() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.languageId = 1;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(7L));
  }

  @Test
  public void testCount_byLanguageId_emptyResult() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.languageId = 2;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byUserName() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.userName = "administrator";
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(3L));
  }

  @Test
  public void testCount_byUserName_emptyResult_noStatus() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.userName = "user5";
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byUserName_emptyResult_noUser() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.userName = "non_existed_user";
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byStartTime() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.startTime = Timestamp.valueOf("2014-01-01 00:00:00");
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byEndTime() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.endTime = Timestamp.valueOf("2014-03-26 10:59:59");
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(6L));
  }

  @Test
  public void testCount_byStartTimeAndEndTime() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.endTime = Timestamp.valueOf("2014-03-01 00:00:00");
    statusCriteria.startTime = Timestamp.valueOf("2013-11-01 00:00:00");
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(1L));
  }

  @Test
  public void testCount_byStartTimeAndEndTime_emptyResult() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.endTime = Timestamp.valueOf("2013-01-01 00:00:00");
    statusCriteria.startTime = Timestamp.valueOf("2014-01-01 00:00:00");
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byContestId() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.isForAdmin = true;
    statusCriteria.contestId = 1;
    Assert.assertEquals(statusService.count(statusCriteria), Long.valueOf(2L));
  }

  @Test
  public void testGetStatusList() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.problemId = 1;
    statusCriteria.isForAdmin = true;
    List<StatusDto> statusListDtos = statusService.getStatusList(statusCriteria, null, 
        StatusFields.FIELDS_FOR_LIST_PAGE);
    Assert.assertEquals(statusListDtos.size(), 6);
    Assert.assertEquals(statusListDtos.get(0).getStatusId(), Integer.valueOf(1));
    Assert.assertEquals(statusListDtos.get(1).getStatusId(), Integer.valueOf(2));
    Assert.assertEquals(statusListDtos.get(2).getStatusId(), Integer.valueOf(3));
    Assert.assertEquals(statusListDtos.get(3).getStatusId(), Integer.valueOf(4));
    Assert.assertEquals(statusListDtos.get(4).getStatusId(), Integer.valueOf(5));
    Assert.assertEquals(statusListDtos.get(5).getStatusId(), Integer.valueOf(6));
  }

  @Test
  public void testGetStatusList_withPageInfo() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.problemId = 1;
    statusCriteria.isForAdmin = true;
    PageInfo pageInfo = PageInfo.create(300L, 4L, 10, 2L);
    List<StatusDto> statusListDtos = statusService.getStatusList(statusCriteria, pageInfo, 
        StatusFields.FIELDS_FOR_LIST_PAGE);
    Assert.assertEquals(statusListDtos.size(), 2);
  }

  @Test
  public void testGetStatusList_withPageInfo_emptyResult() throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.problemId = 1;
    statusCriteria.isForAdmin = true;
    PageInfo pageInfo = PageInfo.create(300L, 4L, 10, 3L);
    List<StatusDto> statusListDtos = statusService.getStatusList(statusCriteria, pageInfo,
        StatusFields.FIELDS_FOR_LIST_PAGE);
    Assert.assertEquals(statusListDtos.size(), 0);
  }

  @Test
  public void testGetStatusInformation() throws AppException {
    Integer statusId = 5;
    StatusDto statusInformationDto = statusService.getStatusDto(statusId,
        StatusFields.FIELDS_FOR_STATUS_INFO);
    Assert.assertEquals(statusInformationDto.getStatusId(), Integer.valueOf(5));
    Assert.assertEquals(statusInformationDto.getCodeContent(), "code");
    Assert.assertEquals(statusInformationDto.getUserId(), Integer.valueOf(2));
    Assert.assertEquals(statusInformationDto.getCompileInfoId(), Integer.valueOf(1));
  }
}
