package cn.edu.uestc.acmicpc.web.oj.controller.training;

import cn.edu.uestc.acmicpc.db.criteria.TrainingContestCriteria;
import cn.edu.uestc.acmicpc.db.criteria.TrainingCriteria;
import cn.edu.uestc.acmicpc.db.criteria.TrainingPlatformInfoCriteria;
import cn.edu.uestc.acmicpc.db.criteria.TrainingUserCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingContestFields;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingFields;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingPlatformInfoFields;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingUserFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingContestDto;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingDto;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingPlatformInfoDto;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingUserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDto;
import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.service.iface.TrainingContestService;
import cn.edu.uestc.acmicpc.service.iface.TrainingPlatformInfoService;
import cn.edu.uestc.acmicpc.service.iface.TrainingService;
import cn.edu.uestc.acmicpc.service.iface.TrainingUserService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.annotation.JsonMap;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.TrainingContestType;
import cn.edu.uestc.acmicpc.util.enums.TrainingPlatformType;
import cn.edu.uestc.acmicpc.util.enums.TrainingUserType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.parser.TrainingContestResultParser;
import cn.edu.uestc.acmicpc.util.rating.RatingCalculator;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.dto.TrainingRating;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.web.rank.TrainingRankList;
import cn.edu.uestc.acmicpc.web.rank.TrainingRankListUser;
import com.alibaba.fastjson.JSON;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/training")
public class TrainingController extends BaseController {

  private final TrainingService trainingService;
  private final TrainingUserService trainingUserService;
  private final TrainingPlatformInfoService trainingPlatformInfoService;
  private final TrainingContestService trainingContestService;
  private final UserService userService;
  private final PictureService pictureService;
  private final Settings settings;

  @Autowired
  public TrainingController(TrainingService trainingService,
                            TrainingUserService trainingUserService,
                            TrainingPlatformInfoService trainingPlatformInfoService,
                            TrainingContestService trainingContestService,
                            UserService userService,
                            PictureService pictureService,
                            Settings settings) {
    this.trainingService = trainingService;
    this.trainingUserService = trainingUserService;
    this.trainingPlatformInfoService = trainingPlatformInfoService;
    this.trainingContestService = trainingContestService;
    this.userService = userService;
    this.pictureService = pictureService;
    this.settings = settings;
  }

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> search(
      @RequestBody(required = false) TrainingCriteria trainingCriteria) throws AppException {
    Map<String, Object> json = new HashMap<>();

    if (trainingCriteria == null) {
      trainingCriteria = new TrainingCriteria();
    }
    Long count = trainingService.count(trainingCriteria);
    PageInfo pageInfo = buildPageInfo(count, trainingCriteria.currentPage,
        settings.RECORD_PER_PAGE, null);
    List<TrainingDto> trainingDtoList = trainingService.getTrainingList(trainingCriteria, pageInfo,
        TrainingFields.FIELDS_FOR_LIST_PAGE);

    json.put("pageInfo", pageInfo);
    json.put("list", trainingDtoList);
    json.put("result", "success");

    return json;
  }

  @RequestMapping("data/{trainingId}")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> data(@PathVariable("trainingId") Integer trainingId)
      throws AppException {
    Map<String, Object> json = new HashMap<>();

    TrainingDto trainingDto = trainingService.getTrainingDto(trainingId, TrainingFields.ALL_FIELDS);
    if (trainingDto == null) {
      throw new AppException("Training not found!");
    }

    json.put("trainingDto", trainingDto);
    json.put("result", "success");

    return json;
  }

  @RequestMapping("edit")
  @LoginPermit(value = AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> edit(
      @JsonMap("trainingEditDto") TrainingDto trainingEditDto,
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
      trainingDto = trainingService.getTrainingDto(trainingEditDto.getTrainingId(),
          TrainingFields.ALL_FIELDS);
      if (trainingDto == null) {
        throw new AppException("Training not found.");
      }
    }

    trainingDto.setTitle(trainingEditDto.getTitle());
    trainingDto.setDescription(trainingEditDto.getDescription());

    trainingService.updateTraining(trainingDto);

    json.put("trainingId", trainingDto.getTrainingId());
    json.put("result", "success");

    return json;
  }

  @RequestMapping("editTrainingUser")
  @LoginPermit(value = AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> editTrainingUser(
      @JsonMap("trainingUserEditDto") TrainingUserDto trainingUserEditDto,
      @JsonMap("action") String action) throws AppException {
    Map<String, Object> json = new HashMap<>();

    if (trainingUserEditDto.getTrainingUserName().length() > 30 ||
        trainingUserEditDto.getTrainingUserName().length() < 2) {
      throw new FieldException("trainingUserName", "Please enter 2-30 characters.");
    }

    // Check user name
    UserDto userDto = userService.getUserDtoByUserName(trainingUserEditDto.getUserName());
    if (userDto == null) {
      throw new FieldException("userName", "Invalid OJ user name.");
    }
    trainingUserEditDto.setUserId(userDto.getUserId());
    // Check type
    if (trainingUserEditDto.getType() < 0
        || trainingUserEditDto.getType() >= TrainingUserType.values().length) {
      throw new FieldException("type", "Invalid type.");
    }

    TrainingUserDto trainingUserDto;
    if (action.equals("new")) {
      Integer trainingUserId = trainingUserService.createNewTrainingUser(
          trainingUserEditDto.getUserId(), trainingUserEditDto.getTrainingId());
      trainingUserDto = trainingUserService.getTrainingUserDto(trainingUserId,
          TrainingUserFields.ALL_FIELDS);
      if (trainingUserDto == null) {
        throw new AppException("Error while creating training user.");
      }
    } else {
      trainingUserDto = trainingUserService.getTrainingUserDto(
          trainingUserEditDto.getTrainingUserId(), TrainingUserFields.ALL_FIELDS);
      if (trainingUserDto == null) {
        throw new AppException("Training user not found.");
      }
    }

    trainingUserDto.setUserId(trainingUserEditDto.getUserId());
    trainingUserDto.setTrainingUserName(trainingUserEditDto.getTrainingUserName());
    trainingUserDto.setType(trainingUserEditDto.getType());

    trainingUserService.updateTrainingUser(trainingUserDto);
    json.put("result", "success");

    return json;
  }

  @RequestMapping("searchTrainingUser/{trainingId}")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> searchTrainingUser(
      @RequestBody(required = false) TrainingUserCriteria trainingUserCriteria,
      @PathVariable("trainingId") Integer trainingId) throws AppException {
    Map<String, Object> json = new HashMap<>();

    if (trainingUserCriteria == null) {
      trainingUserCriteria = new TrainingUserCriteria();
    }
    trainingUserCriteria.trainingId = trainingId;

    List<TrainingUserDto> trainingUserDtoList = trainingUserService
        .getTrainingUserList(trainingUserCriteria, TrainingUserFields.ALL_FIELDS);
    for (int id = 0; id < trainingUserDtoList.size(); ++id) {
      trainingUserDtoList.get(id).setRank(id + 1);
      String ratingHistory = trainingUserDtoList.get(id).getRatingHistory();
      List<TrainingRating> ratingHistoryList = JSON.parseArray(ratingHistory, TrainingRating.class);
      trainingUserDtoList.get(id).setRatingHistoryList(ratingHistoryList);
      trainingUserDtoList.get(id).setRatingHistory(null);
    }

    PageInfo pageInfo = buildPageInfo((long) trainingUserDtoList.size(),
        1L, (long) trainingUserDtoList.size(), null);

    json.put("pageInfo", pageInfo);
    json.put("list", trainingUserDtoList);
    json.put("result", "success");

    return json;
  }

  @RequestMapping("trainingUserData/{trainingUserId}")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> trainingUserData(
      @PathVariable("trainingUserId") Integer trainingUserId) throws AppException {
    Map<String, Object> json = new HashMap<>();

    TrainingUserDto trainingUserDto = trainingUserService.getTrainingUserDto(trainingUserId,
        TrainingUserFields.ALL_FIELDS);
    if (trainingUserDto == null) {
      throw new AppException("Training user not found!");
    }

    TrainingPlatformInfoCriteria trainingPlatformInfoCriteria = new TrainingPlatformInfoCriteria();
    trainingPlatformInfoCriteria.trainingUserId = trainingUserId;
    List<TrainingPlatformInfoDto> trainingPlatformInfoDtoList = trainingPlatformInfoService
        .getTrainingPlatformInfoList(trainingPlatformInfoCriteria,
            TrainingPlatformInfoFields.ALL_FIELDS);

    json.put("trainingUserDto", trainingUserDto);
    json.put("trainingPlatformInfoDtoList", trainingPlatformInfoDtoList);
    json.put("result", "success");

    return json;
  }

  @RequestMapping("editTrainingPlatformInfo")
  @LoginPermit(value = AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> editTrainingPlatformInfo(
      @JsonMap("trainingPlatformInfoEditDto") TrainingPlatformInfoDto trainingPlatformInfoEditDto,
      @JsonMap("action") String action) throws AppException {
    Map<String, Object> json = new HashMap<>();

    if (action.equals("remove")) {
      trainingPlatformInfoService.removeTrainingPlatformInfo(trainingPlatformInfoEditDto
          .getTrainingPlatformInfoId());
    } else {
      TrainingPlatformInfoDto trainingPlatformInfoDto;
      if (action.equals("new")) {
        Integer trainingPlatformInfoId = trainingPlatformInfoService
            .createNewTrainingPlatformInfo(trainingPlatformInfoEditDto.getTrainingUserId());
        trainingPlatformInfoDto = trainingPlatformInfoService.getTrainingPlatformInfoDto(
            trainingPlatformInfoId, TrainingPlatformInfoFields.ALL_FIELDS);
        if (trainingPlatformInfoDto == null) {
          throw new AppException("Error while creating training platform info.");
        }
      } else {
        trainingPlatformInfoDto = trainingPlatformInfoService.getTrainingPlatformInfoDto(
            trainingPlatformInfoEditDto.getTrainingPlatformInfoId(),
            TrainingPlatformInfoFields.ALL_FIELDS);
        if (trainingPlatformInfoDto == null) {
          throw new AppException("Training platform info not found.");
        }
      }

      trainingPlatformInfoDto.setType(trainingPlatformInfoEditDto.getType());
      trainingPlatformInfoDto.setUserName(trainingPlatformInfoEditDto.getUserName());
      trainingPlatformInfoDto.setUserId(trainingPlatformInfoEditDto.getUserId());

      trainingPlatformInfoService.updateTrainingPlatformInfo(trainingPlatformInfoDto);
      json.put("trainingPlatformInfoDto",
          trainingPlatformInfoService.getTrainingPlatformInfoDto(
              trainingPlatformInfoDto.getTrainingPlatformInfoId(),
              TrainingPlatformInfoFields.ALL_FIELDS
          )
      );
    }

    json.put("result", "success");
    return json;
  }

  @RequestMapping("searchTrainingContest/{trainingId}")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> searchTrainingContest(
      @RequestBody(required = false) TrainingContestCriteria trainingContestCriteria,
      @PathVariable("trainingId") Integer trainingId) throws AppException {
    Map<String, Object> json = new HashMap<>();

    if (trainingContestCriteria == null) {
      trainingContestCriteria = new TrainingContestCriteria();
    }
    trainingContestCriteria.trainingId = trainingId;

    List<TrainingContestDto> trainingContestDtoList = trainingContestService
        .getTrainingContestList(trainingContestCriteria,
            TrainingContestFields.FIELDS_FOR_LIST_PAGE);

    PageInfo pageInfo = buildPageInfo((long) trainingContestDtoList.size(),
        1L, (long) trainingContestDtoList.size(), null);

    json.put("pageInfo", pageInfo);
    json.put("list", trainingContestDtoList);
    json.put("result", "success");

    return json;
  }

  @RequestMapping(value = "uploadTrainingContestResult/{trainingId}/{type}/{platformType}", method = RequestMethod.POST)
  @LoginPermit(AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> uploadTrainingContestResult(
      @PathVariable("trainingId") Integer trainingId,
      @PathVariable("type") Integer type,
      @PathVariable("platformType") Integer platformType,
      @RequestParam(value = "uploadFile") MultipartFile[] files)
      throws AppException {
    Map<String, Object> json = new HashMap<>();

    if (files.length > 1) {
      throw new AppException("Fetch uploaded file error.");
    }
    MultipartFile file = files[0];

    try {
      String fileName = "training_" + trainingId + "_" + System.currentTimeMillis() + ".xls";

      File targetFile = new File(settings.UPLOAD_FOLDER + "/" + fileName);
      if (targetFile.exists() && !targetFile.delete()) {
        throw new AppException("Internal exception: target file exists and can not be deleted.");
      }
      try {
        file.transferTo(targetFile);
      } catch (IOException e) {
        throw new AppException("Error while save files");
      }

      TrainingRankList trainingRankList = parseXlsFile(targetFile, trainingId, type, platformType);

      json.put("fileName", fileName);
      json.put("trainingRankList", trainingRankList);
      json.put("success", "true");
    } catch (Exception e) {
      e.printStackTrace();
      json.put("success", "false");
      json.put("error", "Error while parse rank list.");
    }
    return json;
  }

  private TrainingRankList parseXlsFile(File targetFile, Integer trainingId, Integer type,
                                        Integer platformType) throws AppException, IOException, BiffException {
    Workbook workbook = Workbook.getWorkbook(targetFile);
    Sheet sheet = workbook.getSheet(0);
    int totalRows = sheet.getRows();
    int totalColumns = sheet.getColumns();

    String[] fields = new String[totalColumns];
    Integer[] fieldType = new Integer[totalColumns];
    for (int column = 0; column < totalColumns; column++) {
      fields[column] = sheet.getCell(column, 0).getContents();
      fieldType[column] = TrainingContestResultParser.getType(fields[column]).ordinal();
    }

    TrainingRankListUser[] users = new TrainingRankListUser[totalRows - 1];
    for (int row = 1; row < totalRows; row++) {
      TrainingRankListUser trainingRankListUser = new TrainingRankListUser();
      trainingRankListUser.rawData = new String[totalColumns];
      for (int column = 0; column < totalColumns; column++) {
        trainingRankListUser.rawData[column] = sheet.getCell(column, row).getContents();
      }
      users[row - 1] = trainingRankListUser;
    }

    TrainingRankList trainingRankList = new TrainingRankList();
    trainingRankList.fields = fields;
    trainingRankList.fieldType = fieldType;
    trainingRankList.users = users;

    parseRankList(trainingRankList, trainingId, TrainingContestType.values()[type],
        TrainingPlatformType.values()[platformType]);

    return trainingRankList;
  }

  private void parseRankList(TrainingRankList trainingRankList, Integer trainingId,
                             TrainingContestType trainingContestType, TrainingPlatformType platformType)
      throws AppException {
    TrainingPlatformInfoCriteria trainingPlatformInfoCriteria = new TrainingPlatformInfoCriteria();
    trainingPlatformInfoCriteria.trainingId = trainingId;
    if (trainingContestType != TrainingContestType.ADJUST) {
      trainingPlatformInfoCriteria.type = platformType;
    }
    List<TrainingPlatformInfoDto> platformList = trainingPlatformInfoService
        .getTrainingPlatformInfoList(trainingPlatformInfoCriteria,
            TrainingPlatformInfoFields.ALL_FIELDS);
    TrainingContestResultParser parser = new TrainingContestResultParser(platformList);
    parser.parse(trainingRankList, trainingContestType, platformType);
  }

  @RequestMapping(value = "editTrainingContest")
  @LoginPermit(AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> editTrainingContest(@JsonMap("action") String action,
                                          @JsonMap("trainingContestEditDto") TrainingContestDto trainingContestEditDto,
                                          @JsonMap("fileName") String fileName) throws AppException, IOException, BiffException {
    Map<String, Object> json = new HashMap<>();

    if (trainingContestEditDto.getTitle().length() > 255 ||
        trainingContestEditDto.getTitle().length() < 2) {
      throw new FieldException("title", "Please enter 2-255 characters.");
    }

    TrainingContestDto trainingContestDto;
    if (action.equals("new")) {
      Integer trainingContestId = trainingContestService
          .createNewTrainingContest(trainingContestEditDto.getTrainingId());
      trainingContestDto = trainingContestService.getTrainingContestDto(trainingContestId,
          TrainingContestFields.ALL_FIELDS);
      if (trainingContestDto == null) {
        throw new AppException("Error while creating training contest.");
      }
    } else {
      trainingContestDto = trainingContestService.getTrainingContestDto(
          trainingContestEditDto.getTrainingContestId(), TrainingContestFields.ALL_FIELDS);
      if (trainingContestDto == null) {
        throw new AppException("Training contest not found.");
      }
    }

    trainingContestDto.setTitle(trainingContestEditDto.getTitle());
    trainingContestDto.setLink(trainingContestEditDto.getLink());
    trainingContestDto.setType(trainingContestEditDto.getType());
    trainingContestDto.setPlatformType(trainingContestEditDto.getPlatformType());

    if (fileName != null && !fileName.equals("")) {
      File targetFile = new File(settings.UPLOAD_FOLDER + "/" + fileName);
      if (!targetFile.exists()) {
        throw new AppException("Internal exception: uploaded xls file disappeared.");
      }
      TrainingRankList trainingRankList = parseXlsFile(targetFile,
          trainingContestEditDto.getTrainingId(), trainingContestEditDto.getType(),
          trainingContestEditDto.getPlatformType());
      trainingContestDto.setRankList(JSON.toJSONString(trainingRankList));
    }

    trainingContestService.updateTrainingContest(trainingContestDto);

    json.put("trainingContestId", trainingContestDto.getTrainingContestId());
    json.put("result", "success");
    return json;
  }

  @RequestMapping("trainingContestData/{trainingContestId}")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> trainingContestData(
      @PathVariable("trainingContestId") Integer trainingContestId) throws AppException {
    Map<String, Object> json = new HashMap<>();

    TrainingContestDto trainingContestDto = trainingContestService.getTrainingContestDto(
        trainingContestId, TrainingContestFields.ALL_FIELDS);
    if (trainingContestDto == null) {
      throw new AppException("Training contest not found!");
    }

    json.put("trainingContestDto", trainingContestDto);
    TrainingRankList trainingRankList;
    if (trainingContestDto.getRankList().equals("")) {
      trainingRankList = new TrainingRankList();
    } else {
      trainingRankList = JSON.parseObject(trainingContestDto.getRankList(), TrainingRankList.class);
      trainingContestDto.setRankList(null);
    }
    json.put("rankList", trainingRankList);
    json.put("result", "success");
    return json;
  }

  @RequestMapping("calculateRating/{trainingId}")
  public
  @ResponseBody
  Map<String, Object> calculateRating(
      @PathVariable("trainingId") Integer trainingId) throws AppException {
    Map<String, Object> json = new HashMap<>();

    TrainingDto trainingDto = trainingService.getTrainingDto(trainingId, TrainingFields.ALL_FIELDS);
    if (trainingDto == null) {
      throw new AppException("Training not found!");
    }

    TrainingContestCriteria trainingContestCriteria = new TrainingContestCriteria();
    trainingContestCriteria.trainingId = trainingId;
    List<TrainingContestDto> contestList = trainingContestService
        .getTrainingContestList(trainingContestCriteria,
            TrainingContestFields.ALL_FIELDS);

    TrainingUserCriteria trainingUserCriteria = new TrainingUserCriteria();
    trainingUserCriteria.trainingId = trainingId;
    List<TrainingUserDto> userList = trainingUserService.getTrainingUserList(trainingUserCriteria,
        TrainingUserFields.ALL_FIELDS);

    for (TrainingUserDto user : userList) {
      user.setCurrentRating(1200.0);
      user.setCurrentVolatility(550.0);
      user.setRank(1);
      user.setCompetitions(0);
      user.setMaximumRating(1200.0);
      user.setMinimumRating(1200.0);
      user.setMostRecentEventId(null);
      user.setMostRecentEventName("");
      user.setRatingHistoryList(new LinkedList<>());
    }

    RatingCalculator ratingCalculator = new RatingCalculator(userList);
    for (TrainingContestDto contest : contestList) {
      // Parse rank list
      TrainingRankList trainingRankList = JSON.parseObject(contest.getRankList(),
          TrainingRankList.class);
      parseRankList(trainingRankList, trainingId, TrainingContestType.values()[contest.getType()],
          TrainingPlatformType.values()[contest.getPlatformType()]);
      contest.setRankList(JSON.toJSONString(trainingRankList));
      trainingContestService.updateTrainingContest(contest);

      ratingCalculator.calculate(contest, trainingRankList);
    }

    // Sort by rating
    Collections.sort(userList, (o1, o2) -> o2.getCurrentRating().compareTo(o1.getCurrentRating()));

    int rank = 1;
    for (TrainingUserDto userDto : userList) {
      userDto.setRank(rank++);
      userDto.setRatingHistory(JSON.toJSONString(userDto.getRatingHistoryList()));
      trainingUserService.updateTrainingUser(userDto);
    }

    json.put("result", "success");
    return json;
  }
}
