package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.db.criteria.TrainingUserCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingUserFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingUserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDto;
import cn.edu.uestc.acmicpc.service.iface.TrainingUserService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.enums.TrainingUserType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Integration test case for
 * {@link cn.edu.uestc.acmicpc.service.iface.TrainingUserService}
 */
public class TrainingUserServiceITTest extends PersistenceITTest {

  @Autowired
  private TrainingUserService trainingUserService;

  @Autowired
  private UserService userService;

  @Test
  public void testGetTrainingUserDto() throws AppException {
    Integer trainingUserId = trainingUserProvider.createTrainingUser().getTrainingUserId();
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
    List<TrainingUserDto> list = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      list.add(trainingUserProvider.createTrainingUser());
    }
    TrainingUserCriteria trainingUserCriteria = new TrainingUserCriteria();
    List<TrainingUserDto> trainingUserDtoList = trainingUserService
        .getTrainingUserList(trainingUserCriteria, TrainingUserFields.ALL_FIELDS);
    Assert.assertEquals(trainingUserDtoList.size(), 5);
    int id = 0;
    for (TrainingUserDto trainingUserDto : trainingUserDtoList) {
      Assert.assertEquals(trainingUserDto.getTrainingUserId(), list.get(id).getTrainingUserId());
      Assert.assertNotNull(trainingUserDto.getUserId());
      Assert.assertNotNull(trainingUserDto.getTrainingId());
      Assert.assertNotNull(trainingUserDto.getTrainingUserName());
      Assert.assertEquals(trainingUserDto.getCurrentRating(), list.get(id).getCurrentRating());
      Assert.assertEquals(trainingUserDto.getCurrentVolatility(), list.get(id).getCurrentVolatility());
      id++;
    }
  }

  @Test
  public void testGetTrainingUserList_keyword() throws AppException {
    List<TrainingUserDto> list = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      TrainingUserDto trainingUserDto = trainingUserProvider.createTrainingUser();
      if (i < 2) {
        UserDto userDto = userService.getUserDtoByUserId(trainingUserDto.getUserId());
        userDto.setUserName("userA" + Integer.valueOf(i).toString());
        userService.updateUser(userDto);
      }
      list.add(trainingUserDto);
    }
    TrainingUserCriteria trainingUserCriteria = new TrainingUserCriteria();
    trainingUserCriteria.keyword = "userA";
    List<TrainingUserDto> trainingUserDtoList = trainingUserService
        .getTrainingUserList(trainingUserCriteria, TrainingUserFields.ALL_FIELDS);
    Assert.assertEquals(trainingUserDtoList.size(), 2);
    Assert.assertEquals(trainingUserDtoList.get(0).getTrainingUserId(),
        list.get(0).getTrainingUserId());
    Assert.assertEquals(trainingUserDtoList.get(1).getTrainingUserId(),
        list.get(1).getTrainingUserId());
  }

  @Test
  public void testUpdateTrainingUser() throws AppException {
    List<TrainingUserDto> list = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      TrainingUserDto trainingUserDto = trainingUserProvider.createTrainingUser();
      list.add(trainingUserDto);
    }
    TrainingUserDto trainingUserEditDto = TrainingUserDto.builder()
        .setTrainingUserId(list.get(0).getTrainingUserId())
        .setTrainingId(list.get(0).getTrainingId())
        .setUserId(list.get(0).getUserId())
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
    TrainingUserDto updatedTrainingUserDto = trainingUserService.getTrainingUserDto(list.get(0).getTrainingUserId(),
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
  }

  @Test
  public void testUpdateTrainingUser_nothing() throws AppException {
    TrainingUserDto trainingUserDto = trainingUserProvider.createTrainingUser();
    TrainingUserDto trainingUserEditDto = TrainingUserDto.builder()
        .setTrainingUserId(trainingUserDto.getTrainingUserId())
        .build();
    trainingUserService.updateTrainingUser(trainingUserEditDto);
    TrainingUserDto updatedTrainingUserDto = trainingUserService.getTrainingUserDto(1,
        TrainingUserFields.ALL_FIELDS);
  }

  @Test
  public void testCreateTrainingUser() throws AppException {
    Integer trainingId1 = trainingProvider.createTraining().getTrainingId();
    Integer trainingId2 = trainingProvider.createTraining().getTrainingId();
    Integer userID = userProvider.createUser().getUserId();
    Integer newId1 = trainingUserService.createNewTrainingUser(userID, trainingId1);
    Integer newId2 = trainingUserService.createNewTrainingUser(userID, trainingId2);
    Integer exceptedId = newId1 + 1;
    Assert.assertEquals(newId2, exceptedId);
  }
}
