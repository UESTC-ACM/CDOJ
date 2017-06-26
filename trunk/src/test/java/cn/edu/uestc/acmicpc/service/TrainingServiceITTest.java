package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.db.criteria.TrainingCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingDto;
import cn.edu.uestc.acmicpc.service.iface.TrainingService;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Integration test cases for
 * {@link cn.edu.uestc.acmicpc.service.iface.TrainingService}
 */
public class TrainingServiceITTest extends PersistenceITTest {

  @Autowired
  private TrainingService trainingService;

  @Test
  public void testGetTrainingDto() throws AppException {
    Integer trainingId = trainingProvider.createTraining().getTrainingId();
    Assert.assertEquals(trainingService.getTrainingDto(trainingId, TrainingFields.ALL_FIELDS)
        .getTrainingId(), trainingId);
  }

  @Test
  public void testGetTrainingDto_noSuchTraining() throws AppException {
    Integer trainingId = 10000;
    Assert.assertNull(trainingService.getTrainingDto(trainingId, TrainingFields.ALL_FIELDS));
  }

  @Test
  public void testGetTrainingDto_nullId() throws AppException {
    try {
      trainingService.getTrainingDto(null, TrainingFields.ALL_FIELDS);
      Assert.fail();
    } catch (AppException e) {
      // Pass
    }
  }

  @Test
  public void testCount() throws AppException {
    for(int i = 0; i < 7; i++) {
      trainingProvider.createTraining();
    }
    TrainingCriteria trainingCriteria = new TrainingCriteria();
    Long totalRecords = 7L;
    Assert.assertEquals(trainingService.count(trainingCriteria), totalRecords);
  }

  @Test
  public void testCount_range() throws AppException {
    TrainingCriteria trainingCriteria = new TrainingCriteria();
    for(int i = 0; i < 7; i++) {
      TrainingDto trainingDto = trainingProvider.createTraining();
      if(i < 4) {
        trainingDto.setTitle("training");
      } else {
        trainingDto.setTitle("keyword");
      }
      if(i == 3) {
        trainingCriteria.startId = trainingDto.getTrainingId();
      }
      if(i == 6) {
        trainingCriteria.endId = trainingDto.getTrainingId();
      }
    }
    Long totalRecords = 4L;
    Assert.assertEquals(trainingService.count(trainingCriteria), totalRecords);
  }

  @Test
  public void testCount_keyword1() throws AppException {
    for(int i = 0; i < 7; i++) {
      TrainingDto trainingDto = trainingProvider.createTraining();
      if(i < 4) {
        trainingDto.setTitle("hello" + Integer.valueOf(i).toString());
      } else {
        trainingDto.setTitle("mzry" + Integer.valueOf(i).toString());
      }
      trainingService.updateTraining(trainingDto);
    }
    TrainingCriteria trainingCriteria = new TrainingCriteria();
    trainingCriteria.keyword = "hello";
    Long totalRecords = 4L;
    Assert.assertEquals(trainingService.count(trainingCriteria), totalRecords);
  }

  @Test
  public void testCount_keyword2() throws AppException {
    for(int i = 0; i < 7; i++) {
      TrainingDto trainingDto = trainingProvider.createTraining();
      if(i < 4) {
        trainingDto.setTitle("hello" + Integer.valueOf(i).toString());
      } else {
        trainingDto.setTitle("mzry" + Integer.valueOf(i).toString());
      }
      trainingService.updateTraining(trainingDto);
    }
    TrainingCriteria trainingCriteria = new TrainingCriteria();
    trainingCriteria.keyword = "mzry";
    Long totalRecords = 3L;
    Assert.assertEquals(trainingService.count(trainingCriteria), totalRecords);
  }

  @Test
  public void testGetTrainingList() throws AppException {
    Integer id = 0;
    for(int i = 0; i < 7; i++) {
      TrainingDto trainingDto = trainingProvider.createTraining();
      if(i == 0) {
        id = trainingDto.getTrainingId();
      }
    }
    TrainingCriteria trainingCriteria = new TrainingCriteria();
    List<TrainingDto> trainingDtoList = trainingService.getTrainingList(trainingCriteria, null,
        TrainingFields.ALL_FIELDS);
    Assert.assertEquals(trainingDtoList.size(), 7);
    ;
    for (TrainingDto trainingDto : trainingDtoList) {
      Assert.assertEquals(trainingDto.getTrainingId(), id++);
      Assert.assertNotNull(trainingDto.getTitle());
      Assert.assertNotNull(trainingDto.getDescription());
    }
  }

  @Test
  public void testGetTrainingList_reverse() throws AppException {
    Integer id = 6;
    for(int i = 0; i < 7; i++) {
      TrainingDto trainingDto = trainingProvider.createTraining();
      if(i == 6) {
        id = trainingDto.getTrainingId();
      }
    }
    TrainingCriteria trainingCriteria = new TrainingCriteria();
    trainingCriteria.orderFields = "trainingId";
    trainingCriteria.orderAsc = "false";
    List<TrainingDto> trainingDtoList = trainingService.getTrainingList(trainingCriteria, null,
        TrainingFields.ALL_FIELDS);
    Assert.assertEquals(trainingDtoList.size(), 7);
    for (TrainingDto trainingDto : trainingDtoList) {
      Assert.assertEquals(trainingDto.getTrainingId(), id--);
      Assert.assertNotNull(trainingDto.getTitle());
      Assert.assertNotNull(trainingDto.getDescription());
    }
  }

  @Test
  public void testUpdateTraining() throws AppException {
    TrainingDto trainingDto = trainingProvider.createTraining();
    TrainingDto editTrainingDto = TrainingDto.builder()
        .setTrainingId(trainingDto.getTrainingId())
        .setTitle("new title")
        .setDescription("new description")
        .build();
    trainingService.updateTraining(editTrainingDto);
    TrainingDto result = trainingService.getTrainingDto(trainingDto.getTrainingId(),
        TrainingFields.ALL_FIELDS);
    Assert.assertEquals(result, editTrainingDto);
  }

  @Test
  public void testUpdateTraining_without_title() throws AppException {
    TrainingDto trainingDto = trainingProvider.createTraining();
    TrainingDto editTrainingDto = TrainingDto.builder()
        .setTrainingId(trainingDto.getTrainingId())
        .setDescription("new description")
        .build();
    trainingService.updateTraining(editTrainingDto);
    TrainingDto result = trainingService.getTrainingDto(trainingDto.getTrainingId(),
        TrainingFields.ALL_FIELDS);
    Assert.assertEquals(result.getTrainingId(), editTrainingDto.getTrainingId());
    Assert.assertEquals(result.getDescription(), editTrainingDto.getDescription());
    Assert.assertNotEquals(result.getTitle(), editTrainingDto.getTitle());
  }

  @Test
  public void testUpdateTraining_without_description() throws AppException {
    TrainingDto trainingDto = trainingProvider.createTraining();
    TrainingDto editTrainingDto = TrainingDto.builder()
        .setTrainingId(trainingDto.getTrainingId())
        .setTitle("new title")
        .build();
    trainingService.updateTraining(editTrainingDto);
    TrainingDto result = trainingService.getTrainingDto(trainingDto.getTrainingId(),
        TrainingFields.ALL_FIELDS);
    Assert.assertEquals(result.getTrainingId(), editTrainingDto.getTrainingId());
    Assert.assertNotEquals(result.getDescription(), editTrainingDto.getDescription());
    Assert.assertEquals(result.getTitle(), editTrainingDto.getTitle());
  }

  @Test
  public void testUpdateTraining_without_trainingId() throws AppException {
    trainingProvider.createTraining();
    TrainingDto trainingDto = TrainingDto.builder()
        .setTitle("new title")
        .build();
    try {
      trainingService.updateTraining(trainingDto);
      Assert.fail();
    } catch (AppException e) {
      // Pass
    }
  }

  @Test
  public void testCreateTraining() throws AppException {
    Integer newId1 = trainingService.createNewTraining("new training1!");
    Integer newId2 = trainingService.createNewTraining("new training2!");
    Long exceptedId = Long.valueOf(newId1 + 1);
    Assert.assertEquals(newId2.intValue(), exceptedId.intValue());
  }
}
