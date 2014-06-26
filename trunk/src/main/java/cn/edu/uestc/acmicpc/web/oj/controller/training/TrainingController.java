package cn.edu.uestc.acmicpc.web.oj.controller.training;

import cn.edu.uestc.acmicpc.db.criteria.impl.TrainingCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingDto;
import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.service.iface.TrainingService;
import cn.edu.uestc.acmicpc.util.annotation.JsonMap;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/training")
public class TrainingController extends BaseController {

  private TrainingService trainingService;
  private PictureService pictureService;
  private Settings settings;

  @Autowired
  public TrainingController(TrainingService trainingService,
                            PictureService pictureService,
                            Settings settings) {
    this.trainingService = trainingService;
    this.pictureService = pictureService;
    this.settings = settings;
  }

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> search(@RequestBody(required = false) TrainingCriteria trainingCriteria) throws AppException {
    Map<String, Object> json = new HashMap<>();

    if (trainingCriteria == null) {
      trainingCriteria = new TrainingCriteria();
    }
    trainingCriteria.setResultFields(TrainingFields.FIELDS_FOR_LIST_PAGE);
    Long count = trainingService.count(trainingCriteria);
    PageInfo pageInfo = buildPageInfo(count, trainingCriteria.currentPage, settings.RECORD_PER_PAGE, null);
    List<TrainingDto> trainingDtoList = trainingService.getTrainingList(trainingCriteria, pageInfo);

    json.put("pageInfo", pageInfo);
    json.put("list", trainingDtoList);
    json.put("result", "success");

    return json;
  }

  @RequestMapping("data/{trainingId}")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> data(@PathVariable("trainingId") Integer trainingId) throws AppException {
    Map<String, Object> json = new HashMap<>();

    TrainingDto trainingDto = trainingService.getTrainingDto(trainingId, TrainingFields.ALL_FIELDS);
    json.put("trainingDto", trainingDto);
    json.put("result", "success");

    return json;
  }

  @RequestMapping("edit")
  @LoginPermit(value = AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> edit(@JsonMap("trainingEditDto") TrainingDto trainingEditDto,
                           @JsonMap("action") String action) throws AppException {
    Map<String, Object> json = new HashMap<>();

    // Check title
    if (StringUtil.trimAllSpace(trainingEditDto.getTitle()).equals("")) {
      throw new FieldException("title", "Please enter a validate title.");
    }

    TrainingDto trainingDto;
    if (action.equals("new")) {
      Integer trainingId = trainingService.createNewTraining(trainingEditDto.getTitle());
      trainingDto = trainingService.getTrainingDto(trainingId, TrainingFields.ALL_FIELDS);

      if (trainingDto == null) {
        throw new AppException("Error while creating training.");
      }
      // Move pictures
      String oldDirectory = "training/new/";
      String newDirectory = "training/" + trainingId + "/";

      trainingEditDto.setDescription(pictureService.modifyPictureLocation(
          trainingEditDto.getDescription(), oldDirectory, newDirectory));

    } else {
      trainingDto = trainingService.getTrainingDto(trainingEditDto.getTrainingId(), TrainingFields.ALL_FIELDS);
      if (trainingDto == null) {
        throw new AppException("No such training.");
      }
    }

    trainingDto.setTitle(trainingEditDto.getTitle());
    trainingDto.setDescription(trainingEditDto.getDescription());

    trainingService.updateTraining(trainingDto);

    json.put("trainingId", trainingDto.getTrainingId());
    json.put("result", "success");

    return json;
  }
}
