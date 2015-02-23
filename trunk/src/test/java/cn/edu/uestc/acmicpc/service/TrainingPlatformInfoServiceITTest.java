package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.db.criteria.TrainingPlatformInfoCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingPlatformInfoFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingPlatformInfoDto;
import cn.edu.uestc.acmicpc.service.iface.TrainingPlatformInfoService;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.enums.TrainingPlatformType;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Integration test case for
 * {@link cn.edu.uestc.acmicpc.service.iface.TrainingPlatformInfoService}
 */
public class TrainingPlatformInfoServiceITTest extends PersistenceITTest {

  @Autowired
  private TrainingPlatformInfoService trainingPlatformInfoService;

  @Test
  public void testGetTrainingPlatformInfoDto() throws AppException {
    Integer trainingPlatformInfoId = 1;
    TrainingPlatformInfoDto result = trainingPlatformInfoService.getTrainingPlatformInfoDto(
        trainingPlatformInfoId,
        TrainingPlatformInfoFields.ALL_FIELDS
        );
    Assert.assertEquals(
        result.getTrainingPlatformInfoId(),
        trainingPlatformInfoId
        );
    Assert.assertEquals(
        result.getTrainingUserId(),
        Integer.valueOf(1)
        );
    Assert.assertEquals(
        result.getUserName(),
        "RectaFlex"
        );
    Assert.assertEquals(
        result.getUserId(),
        "123"
        );
    Assert.assertEquals(
        result.getType(),
        Integer.valueOf(TrainingPlatformType.TC.ordinal())
        );
    Assert.assertEquals(
        result.getTrainingUserName(),
        "userA"
        );
  }

  @Test
  public void testGetTrainingPlatformInfoList() throws AppException {
    TrainingPlatformInfoCriteria trainingPlatformInfoCriteria = new TrainingPlatformInfoCriteria();
    List<TrainingPlatformInfoDto> result = trainingPlatformInfoService
        .getTrainingPlatformInfoList(trainingPlatformInfoCriteria,
            TrainingPlatformInfoFields.ALL_FIELDS);
    Assert.assertEquals(result.size(), 8);
    for (int i = 0; i < 8; i++) {
      Assert.assertEquals(result.get(i).getTrainingPlatformInfoId(), Integer.valueOf(i + 1));
    }
  }

  @Test
  public void testGetTrainingPlatformInfoList_byIdRange() throws AppException {
    TrainingPlatformInfoCriteria trainingPlatformInfoCriteria = new TrainingPlatformInfoCriteria();
    trainingPlatformInfoCriteria.startId = 2;
    trainingPlatformInfoCriteria.endId = 5;
    List<TrainingPlatformInfoDto> result = trainingPlatformInfoService
        .getTrainingPlatformInfoList(trainingPlatformInfoCriteria,
            TrainingPlatformInfoFields.ALL_FIELDS);
    Assert.assertEquals(result.size(), 4);
    for (int i = 0; i < 4; i++) {
      Assert.assertEquals(result.get(i).getTrainingPlatformInfoId(), Integer.valueOf(i + 2));
    }
  }

  @Test
  public void testGetTrainingPlatformInfoList_byTrainingUserId() throws AppException {
    TrainingPlatformInfoCriteria trainingPlatformInfoCriteria = new TrainingPlatformInfoCriteria();
    trainingPlatformInfoCriteria.trainingUserId = 1;
    List<TrainingPlatformInfoDto> result = trainingPlatformInfoService
        .getTrainingPlatformInfoList(trainingPlatformInfoCriteria,
            TrainingPlatformInfoFields.ALL_FIELDS);
    Assert.assertEquals(result.size(), 5);
    for (int i = 0; i < 5; i++) {
      Assert.assertEquals(result.get(i).getTrainingUserId(), Integer.valueOf(1));
    }
  }

  @Test
  public void testGetTrainingPlatformInfoList_byUserName() throws AppException {
    TrainingPlatformInfoCriteria trainingPlatformInfoCriteria = new TrainingPlatformInfoCriteria();
    trainingPlatformInfoCriteria.userName = "UESTC_Izayoi";
    List<TrainingPlatformInfoDto> result = trainingPlatformInfoService
        .getTrainingPlatformInfoList(trainingPlatformInfoCriteria,
            TrainingPlatformInfoFields.ALL_FIELDS);
    Assert.assertEquals(result.size(), 2);
    for (int i = 0; i < 2; i++) {
      Assert.assertEquals(result.get(i).getUserName(), "UESTC_Izayoi");
    }
  }

  @Test
  public void testGetTrainingPlatformInfoList_byUserId() throws AppException {
    TrainingPlatformInfoCriteria trainingPlatformInfoCriteria = new TrainingPlatformInfoCriteria();
    trainingPlatformInfoCriteria.userId = "123";
    List<TrainingPlatformInfoDto> result = trainingPlatformInfoService
        .getTrainingPlatformInfoList(trainingPlatformInfoCriteria,
            TrainingPlatformInfoFields.ALL_FIELDS);
    Assert.assertEquals(result.size(), 7);
    for (int i = 0; i < 7; i++) {
      Assert.assertEquals(result.get(i).getUserId(), "123");
    }
  }

  @Test
  public void testGetTrainingPlatformInfoList_byType() throws AppException {
    TrainingPlatformInfoCriteria trainingPlatformInfoCriteria = new TrainingPlatformInfoCriteria();
    trainingPlatformInfoCriteria.type = TrainingPlatformType.TC;
    List<TrainingPlatformInfoDto> result = trainingPlatformInfoService
        .getTrainingPlatformInfoList(trainingPlatformInfoCriteria,
            TrainingPlatformInfoFields.ALL_FIELDS);
    Assert.assertEquals(result.size(), 4);
    for (int i = 0; i < 4; i++) {
      Assert.assertEquals(result.get(i).getType(),
          Integer.valueOf(TrainingPlatformType.TC.ordinal()));
    }
  }

  @Test
  public void testGetTrainingPlatformInfoList_byKeyword() throws AppException {
    TrainingPlatformInfoCriteria trainingPlatformInfoCriteria = new TrainingPlatformInfoCriteria();
    trainingPlatformInfoCriteria.keyword = "Mzry";
    List<TrainingPlatformInfoDto> result = trainingPlatformInfoService
        .getTrainingPlatformInfoList(trainingPlatformInfoCriteria,
            TrainingPlatformInfoFields.ALL_FIELDS);
    Assert.assertEquals(result.size(), 2);
  }

  @Test
  public void testUpdateTrainingPlatformInfo() throws AppException {
    TrainingPlatformInfoDto dataBeforeUpdated = trainingPlatformInfoService
        .getTrainingPlatformInfoDto(1, TrainingPlatformInfoFields.ALL_FIELDS);
    TrainingPlatformInfoDto editDto = TrainingPlatformInfoDto.builder()
        .setTrainingPlatformInfoId(1)
        .setTrainingUserId(2)
        .setUserName("mzry1992")
        .setUserId("456")
        .setType(TrainingPlatformType.CF.ordinal())
        .build();
    trainingPlatformInfoService.updateTrainingPlatformInfo(editDto);
    TrainingPlatformInfoDto updatedData = trainingPlatformInfoService.getTrainingPlatformInfoDto(1,
        TrainingPlatformInfoFields.ALL_FIELDS);
    Assert.assertEquals(updatedData.getTrainingPlatformInfoId(),
        editDto.getTrainingPlatformInfoId());
    Assert.assertEquals(updatedData.getTrainingUserId(), editDto.getTrainingUserId());
    Assert.assertEquals(updatedData.getUserName(), editDto.getUserName());
    Assert.assertEquals(updatedData.getUserId(), editDto.getUserId());
    Assert.assertEquals(updatedData.getType(), editDto.getType());

    trainingPlatformInfoService.updateTrainingPlatformInfo(dataBeforeUpdated);
  }

  @Test
  public void testUpdateTrainingPlatformInfo_nothing() throws AppException {
    TrainingPlatformInfoDto dataBeforeUpdated = trainingPlatformInfoService
        .getTrainingPlatformInfoDto(1, TrainingPlatformInfoFields.ALL_FIELDS);
    TrainingPlatformInfoDto editDto = TrainingPlatformInfoDto.builder()
        .setTrainingPlatformInfoId(1)
        .build();
    trainingPlatformInfoService.updateTrainingPlatformInfo(editDto);
    TrainingPlatformInfoDto updatedData = trainingPlatformInfoService.getTrainingPlatformInfoDto(1,
        TrainingPlatformInfoFields.ALL_FIELDS);
    Assert.assertEquals(updatedData.getTrainingPlatformInfoId(),
        dataBeforeUpdated.getTrainingPlatformInfoId());
    Assert.assertEquals(updatedData.getTrainingUserId(), dataBeforeUpdated.getTrainingUserId());
    Assert.assertEquals(updatedData.getUserName(), dataBeforeUpdated.getUserName());
    Assert.assertEquals(updatedData.getUserId(), dataBeforeUpdated.getUserId());
    Assert.assertEquals(updatedData.getType(), dataBeforeUpdated.getType());
  }

  @Test
  public void testCreateTrainingPlatformInfo() throws AppException {
    Integer exceptedId = 9;
    Integer newId = trainingPlatformInfoService.createNewTrainingPlatformInfo(1);
    Assert.assertEquals(exceptedId, newId);

    trainingPlatformInfoService.removeTrainingPlatformInfo(exceptedId);
  }
}
