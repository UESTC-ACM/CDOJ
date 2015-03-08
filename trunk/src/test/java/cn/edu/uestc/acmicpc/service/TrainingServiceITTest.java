package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.db.criteria.TrainingCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingDto;
import cn.edu.uestc.acmicpc.service.iface.TrainingService;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Integration test cases for
 * {@link cn.edu.uestc.acmicpc.service.iface.TrainingService}
 */
public class TrainingServiceITTest extends PersistenceITTest {

  @Autowired
  private TrainingService trainingService;

  @Test
  public void testGetTrainingDto() throws AppException {
    Integer trainingId = 1;
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
    TrainingCriteria trainingCriteria = new TrainingCriteria();
    Long totalRecords = 7L;
    Assert.assertEquals(trainingService.count(trainingCriteria), totalRecords);
  }

  @Test
  public void testCount_range() throws AppException {
    TrainingCriteria trainingCriteria = new TrainingCriteria();
    trainingCriteria.startId = 3;
    trainingCriteria.endId = 6;
    Long totalRecords = 4L;
    Assert.assertEquals(trainingService.count(trainingCriteria), totalRecords);
  }

  @Test
  public void testCount_keyword1() throws AppException {
    TrainingCriteria trainingCriteria = new TrainingCriteria();
    trainingCriteria.keyword = "training";
    Long totalRecords = 4L;
    Assert.assertEquals(trainingService.count(trainingCriteria), totalRecords);
  }

  @Test
  public void testCount_keyword2() throws AppException {
    TrainingCriteria trainingCriteria = new TrainingCriteria();
    trainingCriteria.keyword = "keyword";
    Long totalRecords = 3L;
    Assert.assertEquals(trainingService.count(trainingCriteria), totalRecords);
  }

  @Test
  public void testGetTrainingList() throws AppException {
    TrainingCriteria trainingCriteria = new TrainingCriteria();
    List<TrainingDto> trainingDtoList = trainingService.getTrainingList(trainingCriteria, null,
        TrainingFields.ALL_FIELDS);
    Assert.assertEquals(trainingDtoList.size(), 7);
    Integer id = 0;
    for (TrainingDto trainingDto : trainingDtoList) {
      Assert.assertEquals(trainingDto.getTrainingId(), ++id);
      Assert.assertNotNull(trainingDto.getTitle());
      Assert.assertNotNull(trainingDto.getDescription());
    }
  }

  @Test
  public void testGetTrainingList_reverse() throws AppException {
    TrainingCriteria trainingCriteria = new TrainingCriteria();
    trainingCriteria.orderFields = "trainingId";
    trainingCriteria.orderAsc = "false";
    List<TrainingDto> trainingDtoList = trainingService.getTrainingList(trainingCriteria, null,
        TrainingFields.ALL_FIELDS);
    Assert.assertEquals(trainingDtoList.size(), 7);
    Integer id = 7;
    for (TrainingDto trainingDto : trainingDtoList) {
      Assert.assertEquals(trainingDto.getTrainingId(), id--);
      Assert.assertNotNull(trainingDto.getTitle());
      Assert.assertNotNull(trainingDto.getDescription());
    }
  }

  @Test
  public void testUpdateTraining() throws AppException {
    TrainingDto trainingDto = TrainingDto.builder()
        .setTrainingId(1)
        .setTitle("new title")
        .setDescription("new description")
        .build();
    trainingService.updateTraining(trainingDto);
    TrainingDto result = trainingService.getTrainingDto(trainingDto.getTrainingId(),
        TrainingFields.ALL_FIELDS);
    Assert.assertEquals(result, trainingDto);
  }

  @Test
  public void testUpdateTraining_without_title() throws AppException {
    TrainingDto trainingDto = TrainingDto.builder()
        .setTrainingId(1)
        .setDescription("new description")
        .build();
    trainingService.updateTraining(trainingDto);
    TrainingDto result = trainingService.getTrainingDto(trainingDto.getTrainingId(),
        TrainingFields.ALL_FIELDS);
    Assert.assertEquals(result.getTrainingId(), trainingDto.getTrainingId());
    Assert.assertEquals(result.getDescription(), trainingDto.getDescription());
    Assert.assertNotEquals(result.getTitle(), trainingDto.getTitle());
  }

  @Test
  public void testUpdateTraining_without_description() throws AppException {
    TrainingDto trainingDto = TrainingDto.builder()
        .setTrainingId(1)
        .setTitle("new title")
        .build();
    trainingService.updateTraining(trainingDto);
    TrainingDto result = trainingService.getTrainingDto(trainingDto.getTrainingId(),
        TrainingFields.ALL_FIELDS);
    Assert.assertEquals(result.getTrainingId(), trainingDto.getTrainingId());
    Assert.assertNotEquals(result.getDescription(), trainingDto.getDescription());
    Assert.assertEquals(result.getTitle(), trainingDto.getTitle());
  }

  @Test
  public void testUpdateTraining_without_trainingId() throws AppException {
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
    Long exceptedId = trainingService.count(new TrainingCriteria()) + 1;
    Integer newId = trainingService.createNewTraining("new training!");
    Assert.assertEquals(newId.intValue(), exceptedId.intValue());
  }
}
