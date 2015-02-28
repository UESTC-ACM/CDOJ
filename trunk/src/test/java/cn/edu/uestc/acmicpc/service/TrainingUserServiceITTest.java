package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.db.criteria.TrainingUserCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingUserFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingUserDto;
import cn.edu.uestc.acmicpc.service.iface.TrainingUserService;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.enums.TrainingUserType;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Integration test case for
 * {@link cn.edu.uestc.acmicpc.service.iface.TrainingUserService}
 */
public class TrainingUserServiceITTest extends PersistenceITTest {

  @Autowired
  private TrainingUserService trainingUserService;

  @Test
  public void testGetTrainingUserDto() throws AppException {
    Integer trainingUserId = 1;
    Assert.assertEquals(
        trainingUserService.getTrainingUserDto(trainingUserId, TrainingUserFields.ALL_FIELDS)
            .getTrainingUserId(), trainingUserId);
  }

  @Test
  public void testGetTrainingUserDto_nullId() throws AppException {
    try {
      trainingUserService.getTrainingUserDto(null, TrainingUserFields.ALL_FIELDS);
      Assert.fail();
    } catch (AppException e) {
      // Pass
    }
  }

  @Test
  public void testGetTrainingUserList() throws AppException {
    TrainingUserCriteria trainingUserCriteria = new TrainingUserCriteria();
    List<TrainingUserDto> trainingUserDtoList = trainingUserService
        .getTrainingUserList(trainingUserCriteria, TrainingUserFields.ALL_FIELDS);
    Assert.assertEquals(trainingUserDtoList.size(), 5);
    Integer id = 0;
    for (TrainingUserDto trainingUserDto : trainingUserDtoList) {
      Assert.assertEquals(trainingUserDto.getTrainingUserId(), ++id);
      Assert.assertNotNull(trainingUserDto.getUserId());
      Assert.assertNotNull(trainingUserDto.getTrainingId());
      Assert.assertNotNull(trainingUserDto.getTrainingUserName());
      Assert.assertEquals(trainingUserDto.getCurrentRating(), 1200.0);
      Assert.assertEquals(trainingUserDto.getCurrentVolatility(), 350.0);
    }
  }

  @Test
  public void testGetTrainingUserList_keyword() throws AppException {
    TrainingUserCriteria trainingUserCriteria = new TrainingUserCriteria();
    trainingUserCriteria.keyword = "userA";
    List<TrainingUserDto> trainingUserDtoList = trainingUserService
        .getTrainingUserList(trainingUserCriteria, TrainingUserFields.ALL_FIELDS);
    Assert.assertEquals(trainingUserDtoList.size(), 2);
    Assert.assertEquals(trainingUserDtoList.get(0).getTrainingUserId(), Integer.valueOf(1));
    Assert.assertEquals(trainingUserDtoList.get(1).getTrainingUserId(), Integer.valueOf(5));
  }

  @Test
  public void testUpdateTrainingUser() throws AppException {
    TrainingUserDto dataBeforeUpdated = trainingUserService.getTrainingUserDto(1,
        TrainingUserFields.ALL_FIELDS);
    TrainingUserDto trainingUserEditDto = TrainingUserDto.builder()
        .setTrainingUserId(1)
        .setTrainingId(2)
        .setUserId(2)
        .setTrainingUserName("new user A")
        .setType(TrainingUserType.TEAM.ordinal())
        .setCurrentRating(2500.0)
        .setCurrentVolatility(500.0)
        .setCompetitions(1)
        .setRank(1)
        .setMinimumRating(1200.0)
        .setMaximumRating(2500.0)
        .setMostRecentEventId(1)
        .setMostRecentEventName("Contest 1")
        .setRatingHistory("[{\"a\":1]")
        .build();
    trainingUserService.updateTrainingUser(trainingUserEditDto);
    TrainingUserDto updatedTrainingUserDto = trainingUserService.getTrainingUserDto(1,
        TrainingUserFields.ALL_FIELDS);
    Assert
        .assertEquals(trainingUserEditDto.getTrainingId(), updatedTrainingUserDto.getTrainingId());
    Assert.assertEquals(trainingUserEditDto.getUserId(), updatedTrainingUserDto.getUserId());
    Assert.assertEquals(trainingUserEditDto.getTrainingUserName(),
        updatedTrainingUserDto.getTrainingUserName());
    Assert.assertEquals(trainingUserEditDto.getType(), updatedTrainingUserDto.getType());
    Assert.assertEquals(trainingUserEditDto.getCurrentRating(),
        updatedTrainingUserDto.getCurrentRating());
    Assert.assertEquals(trainingUserEditDto.getCurrentVolatility(),
        updatedTrainingUserDto.getCurrentVolatility());
    Assert.assertEquals(trainingUserEditDto.getCompetitions(),
        updatedTrainingUserDto.getCompetitions());
    Assert.assertEquals(trainingUserEditDto.getRank(), updatedTrainingUserDto.getRank());
    Assert.assertEquals(trainingUserEditDto.getMinimumRating(),
        updatedTrainingUserDto.getMinimumRating());
    Assert.assertEquals(trainingUserEditDto.getMaximumRating(),
        updatedTrainingUserDto.getMaximumRating());
    Assert.assertEquals(trainingUserEditDto.getMostRecentEventId(),
        updatedTrainingUserDto.getMostRecentEventId());
    Assert.assertEquals(trainingUserEditDto.getMostRecentEventName(),
        updatedTrainingUserDto.getMostRecentEventName());
    Assert.assertEquals(trainingUserEditDto.getRatingHistory(),
        updatedTrainingUserDto.getRatingHistory());

    trainingUserService.updateTrainingUser(dataBeforeUpdated);
  }

  @Test
  public void testUpdateTrainingUser_nothing() throws AppException {
    TrainingUserDto dataBeforeUpdated = trainingUserService.getTrainingUserDto(1,
        TrainingUserFields.ALL_FIELDS);
    TrainingUserDto trainingUserEditDto = TrainingUserDto.builder()
        .setTrainingUserId(1)
        .build();
    trainingUserService.updateTrainingUser(trainingUserEditDto);
    TrainingUserDto updatedTrainingUserDto = trainingUserService.getTrainingUserDto(1,
        TrainingUserFields.ALL_FIELDS);
    Assert.assertEquals(dataBeforeUpdated, updatedTrainingUserDto);
  }

  @Test
  public void testCreateTrainingUser() throws AppException {
    Integer exceptedId = 6;
    Integer newId = trainingUserService.createNewTrainingUser(1, 1);
    Assert.assertEquals(newId, exceptedId);
  }
}
