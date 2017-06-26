package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.db.criteria.TrainingPlatformInfoCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingPlatformInfoFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingPlatformInfoDto;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingUserDto;
import cn.edu.uestc.acmicpc.service.iface.TrainingPlatformInfoService;
import cn.edu.uestc.acmicpc.service.testing.TrainingPlatformInfoProvider;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.enums.TrainingPlatformType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Integration test case for
 * {@link cn.edu.uestc.acmicpc.service.iface.TrainingPlatformInfoService}
 */
public class TrainingPlatformInfoServiceITTest extends PersistenceITTest {

  @Autowired
  private TrainingPlatformInfoService trainingPlatformInfoService;

  @Autowired
  TrainingPlatformInfoProvider trainingPlatformInfoProvider;

  @Test
  public void testGetTrainingPlatformInfoDto() throws AppException {
    TrainingPlatformInfoDto trainingPlatformInfo = trainingPlatformInfoProvider
        .createTrainingPlatformInfo();
    Integer trainingPlatformInfoId = trainingPlatformInfo.getTrainingPlatformInfoId();
    TrainingPlatformInfoDto result = trainingPlatformInfoService.getTrainingPlatformInfoDto(
        trainingPlatformInfoId,
        TrainingPlatformInfoFields.ALL_FIELDS);
    Assert.assertEquals(
        result.getTrainingPlatformInfoId(),
        trainingPlatformInfo.getTrainingPlatformInfoId());
    Assert.assertEquals(
        result.getTrainingUserId(),
        trainingPlatformInfo.getTrainingUserId());
    Assert.assertEquals(
        result.getUserName(),
        trainingPlatformInfo.getUserName());
    Assert.assertEquals(
        result.getUserId(),
        trainingPlatformInfo.getUserId());
    Assert.assertEquals(
        result.getType(),
        trainingPlatformInfo.getType());
    Assert.assertEquals(
        result.getTrainingUserName(),
        trainingPlatformInfo.getTrainingUserName());
  }

  @Test
  public void testGetTrainingPlatformInfoList() throws AppException {
    List<TrainingPlatformInfoDto>newTrainingPlatformInfolist = new ArrayList<>();
    for(int i = 0; i < 8; i++) {
      newTrainingPlatformInfolist.add(trainingPlatformInfoProvider.createTrainingPlatformInfo());
    }
    TrainingPlatformInfoCriteria trainingPlatformInfoCriteria = new TrainingPlatformInfoCriteria();
    List<TrainingPlatformInfoDto> result = trainingPlatformInfoService
        .getTrainingPlatformInfoList(trainingPlatformInfoCriteria,
            TrainingPlatformInfoFields.ALL_FIELDS);
    Assert.assertEquals(result.size(), 8);
    for (int i = 0; i < 8; i++) {
      Assert.assertEquals(result.get(i).getTrainingPlatformInfoId(),
          newTrainingPlatformInfolist.get(i).getTrainingPlatformInfoId());
    }
  }

  @Test
  public void testGetTrainingPlatformInfoList_byIdRange() throws AppException {
    List<TrainingPlatformInfoDto>newTrainingPlatformInfolist = new ArrayList<>();
    for(int i = 0; i < 8; i++) {
      newTrainingPlatformInfolist.add(trainingPlatformInfoProvider.createTrainingPlatformInfo());
    }
    TrainingPlatformInfoCriteria trainingPlatformInfoCriteria = new TrainingPlatformInfoCriteria();
    trainingPlatformInfoCriteria.startId = newTrainingPlatformInfolist.get(1).getTrainingUserId();
    trainingPlatformInfoCriteria.endId = trainingPlatformInfoCriteria.startId + 3;
    List<TrainingPlatformInfoDto> result = trainingPlatformInfoService
        .getTrainingPlatformInfoList(trainingPlatformInfoCriteria,
            TrainingPlatformInfoFields.ALL_FIELDS);
    Assert.assertEquals(result.size(), 4);
    for (int i = 0; i < 4; i++) {
      Assert.assertEquals(result.get(i).getTrainingPlatformInfoId(),
          newTrainingPlatformInfolist.get(i + 1).getTrainingPlatformInfoId());
    }
  }

  @Test
  public void testGetTrainingPlatformInfoList_byTrainingUserId() throws AppException {
    List<TrainingPlatformInfoDto>newTrainingPlatformInfolist = new ArrayList<>();
    for(int i = 0; i < 8; i++) {
      newTrainingPlatformInfolist.add(trainingPlatformInfoProvider.createTrainingPlatformInfo());
    }
    TrainingPlatformInfoCriteria trainingPlatformInfoCriteria = new TrainingPlatformInfoCriteria();
    trainingPlatformInfoCriteria.trainingUserId = newTrainingPlatformInfolist.get(0)
        .getTrainingUserId();
    List<TrainingPlatformInfoDto> result = trainingPlatformInfoService
        .getTrainingPlatformInfoList(trainingPlatformInfoCriteria,
            TrainingPlatformInfoFields.ALL_FIELDS);
    Assert.assertEquals(result.size(), 1);
    for (int i = 0; i < 1; i++) {
      Assert.assertEquals(result.get(i).getTrainingUserId(), newTrainingPlatformInfolist.get(0)
          .getTrainingUserId());
    }
  }

  @Test
  public void testGetTrainingPlatformInfoList_byUserName() throws AppException {
    for(int i = 0; i < 8; i++) {
      TrainingPlatformInfoDto trainingPlatformInfo = trainingPlatformInfoProvider
          .createTrainingPlatformInfo();
      if(i < 2) {
        trainingPlatformInfo.setUserName("UESTC_Izayoi");
        trainingPlatformInfoService.updateTrainingPlatformInfo(trainingPlatformInfo);
      } else {
        trainingPlatformInfo.setUserId("UESTC_Fish");
        trainingPlatformInfoService.updateTrainingPlatformInfo(trainingPlatformInfo);
      }
    }
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
    for(int i = 0; i < 8; i++) {
      TrainingPlatformInfoDto trainingPlatformInfo = trainingPlatformInfoProvider
          .createTrainingPlatformInfo();
      if(i < 7) {
        trainingPlatformInfo.setUserId("123");
        trainingPlatformInfoService.updateTrainingPlatformInfo(trainingPlatformInfo);
      } else {
        trainingPlatformInfo.setUserId("321");
        trainingPlatformInfoService.updateTrainingPlatformInfo(trainingPlatformInfo);
      }
    }
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
    for(int i = 0; i < 8; i++) {
      TrainingPlatformInfoDto trainingPlatformInfo = trainingPlatformInfoProvider
          .createTrainingPlatformInfo();
      if(i < 4) {
        trainingPlatformInfo.setType(TrainingPlatformType.TC.ordinal());
        trainingPlatformInfoService.updateTrainingPlatformInfo(trainingPlatformInfo);
      } else {
        trainingPlatformInfo.setType(TrainingPlatformType.CF.ordinal());
        trainingPlatformInfoService.updateTrainingPlatformInfo(trainingPlatformInfo);
      }
    }
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
    for(int i = 0; i < 8; i++) {
      TrainingPlatformInfoDto trainingPlatformInfo = trainingPlatformInfoProvider
          .createTrainingPlatformInfo();
      if(i < 2) {
        trainingPlatformInfo.setUserName("Mzry123");
        trainingPlatformInfoService.updateTrainingPlatformInfo(trainingPlatformInfo);
      }
    }
    TrainingPlatformInfoCriteria trainingPlatformInfoCriteria = new TrainingPlatformInfoCriteria();
    trainingPlatformInfoCriteria.keyword = "Mzry";
    List<TrainingPlatformInfoDto> result = trainingPlatformInfoService
        .getTrainingPlatformInfoList(trainingPlatformInfoCriteria,
            TrainingPlatformInfoFields.ALL_FIELDS);
    Assert.assertEquals(result.size(), 2);
  }

  @Test
  public void testUpdateTrainingPlatformInfo() throws AppException {
    TrainingPlatformInfoDto trainingPlatformInfo = trainingPlatformInfoProvider
        .createTrainingPlatformInfo();
    Integer trainingPlatformInfoId = trainingPlatformInfo.getTrainingPlatformInfoId();
    TrainingPlatformInfoDto editDto = TrainingPlatformInfoDto.builder()
        .setTrainingPlatformInfoId(trainingPlatformInfoId)
        .setTrainingUserId(trainingPlatformInfo.getTrainingUserId())
        .setUserName("mzry1992")
        .setUserId("456")
        .setType(TrainingPlatformType.CF.ordinal())
        .build();
    trainingPlatformInfoService.updateTrainingPlatformInfo(editDto);
    TrainingPlatformInfoDto updatedData = trainingPlatformInfoService.getTrainingPlatformInfoDto(
        trainingPlatformInfoId, TrainingPlatformInfoFields.ALL_FIELDS);
    Assert.assertEquals(
        updatedData.getTrainingPlatformInfoId(),
        editDto.getTrainingPlatformInfoId());
    Assert.assertEquals(updatedData.getTrainingUserId(), editDto.getTrainingUserId());
    Assert.assertEquals(updatedData.getUserName(), editDto.getUserName());
    Assert.assertEquals(updatedData.getUserId(), editDto.getUserId());
    Assert.assertEquals(updatedData.getType(), editDto.getType());
  }

  @Test
  public void testUpdateTrainingPlatformInfo_nothing() throws AppException {
    TrainingPlatformInfoDto trainingPlatformInfoDto = trainingPlatformInfoProvider.createTrainingPlatformInfo();
    TrainingPlatformInfoDto dataBeforeUpdated = trainingPlatformInfoService
        .getTrainingPlatformInfoDto(trainingPlatformInfoDto.getTrainingPlatformInfoId(), TrainingPlatformInfoFields.ALL_FIELDS);
    TrainingPlatformInfoDto editDto = TrainingPlatformInfoDto.builder()
        .setTrainingPlatformInfoId(trainingPlatformInfoDto.getTrainingPlatformInfoId())
        .build();
    trainingPlatformInfoService.updateTrainingPlatformInfo(editDto);
    TrainingPlatformInfoDto updatedData = trainingPlatformInfoService.getTrainingPlatformInfoDto(trainingPlatformInfoDto.getTrainingPlatformInfoId(),
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
    Integer exceptedId = 1;
    TrainingUserDto trainingUser = trainingUserProvider.createTrainingUser();
    Integer newId = trainingPlatformInfoService.createNewTrainingPlatformInfo(trainingUser
        .getTrainingUserId());
    Assert.assertEquals(newId, exceptedId);

    trainingPlatformInfoService.removeTrainingPlatformInfo(exceptedId);
  }
}
