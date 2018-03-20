package cn.edu.uestc.acmicpc.web.oj.controller.contest;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestTeamCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TeamUserCondition;
import cn.edu.uestc.acmicpc.db.criteria.ContestCriteria;
import cn.edu.uestc.acmicpc.db.criteria.StatusCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.ContestFields;
import cn.edu.uestc.acmicpc.db.dto.field.StatusFields;
import cn.edu.uestc.acmicpc.db.dto.field.TeamFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDto;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestProblemDto;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestUserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.LanguageDto;
import cn.edu.uestc.acmicpc.db.dto.impl.MessageDto;
import cn.edu.uestc.acmicpc.db.dto.impl.StatusDto;
import cn.edu.uestc.acmicpc.db.dto.impl.TeamDto;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contestteam.ContestTeamDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contestteam.ContestTeamListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contestteam.ContestTeamReportDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contestteam.ContestTeamReviewDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserReportDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.OnsiteUserDto;
import cn.edu.uestc.acmicpc.service.iface.ContestImporterService;
import cn.edu.uestc.acmicpc.service.iface.ContestProblemService;
import cn.edu.uestc.acmicpc.service.iface.ContestRankListService;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.service.iface.ContestTeamService;
import cn.edu.uestc.acmicpc.service.iface.ContestUserService;
import cn.edu.uestc.acmicpc.service.iface.FileService;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.service.iface.MessageService;
import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.TeamService;
import cn.edu.uestc.acmicpc.service.iface.TeamUserService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.ContestRegistryStatusType;
import cn.edu.uestc.acmicpc.util.enums.ContestType;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeResultType;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.util.helper.ArrayUtil;
import cn.edu.uestc.acmicpc.util.helper.CSVUtil;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDto;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDto;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.web.rank.RankList;
import cn.edu.uestc.acmicpc.web.view.ContestRankListView;
import cn.edu.uestc.acmicpc.web.view.ContestRegistryReportView;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Contest controller.
 */
@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/contest")
public class ContestController extends BaseController {
  private static final Logger logger = Logger.getLogger(ContestController.class);

  private final ContestService contestService;
  private final ContestProblemService contestProblemService;
  private final ContestImporterService contestImporterService;
  private final FileService fileService;
  private final PictureService pictureService;
  private final ProblemService problemService;
  private final StatusService statusService;
  private final TeamService teamService;
  private final TeamUserService teamUserService;
  private final ContestTeamService contestTeamService;
  private final MessageService messageService;
  private final ContestRankListService contestRankListService;
  private final ContestRegistryReportView contestRegistryReportView;
  private final ContestRankListView contestRankListView;
  private final Settings settings;
  private final UserService userService;
  private final ContestUserService contestUserService;
  private final LanguageService languageService;

  @Autowired
  public ContestController(
      ContestService contestService,
      ContestProblemService contestProblemService,
      ContestImporterService contestImporterService,
      FileService fileService,
      PictureService pictureService,
      ProblemService problemService,
      StatusService statusService,
      TeamService teamService,
      TeamUserService teamUserService,
      ContestTeamService contestTeamService,
      MessageService messageService,
      ContestRankListService contestRankListService,
      ContestRegistryReportView contestRegistryReportView,
      ContestRankListView contestRankListView,
      Settings settings,
      UserService userService,
      ContestUserService contestUserService,
      LanguageService languageService) {
    this.contestService = contestService;
    this.contestProblemService = contestProblemService;
    this.contestImporterService = contestImporterService;
    this.fileService = fileService;
    this.pictureService = pictureService;
    this.problemService = problemService;
    this.statusService = statusService;
    this.teamService = teamService;
    this.teamUserService = teamUserService;
    this.contestTeamService = contestTeamService;
    this.messageService = messageService;
    this.contestRankListService = contestRankListService;
    this.contestRegistryReportView = contestRegistryReportView;
    this.contestRankListView = contestRankListView;
    this.settings = settings;
    this.userService = userService;
    this.contestUserService = contestUserService;
    this.languageService = languageService;
  }

  private String getTimeString(Timestamp time) {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time);
  }

  private void generateScoreboard(
      ContestDto contestShowDto,
      List<UserDto> contestUserList,
      List<ContestProblemDto> contestProblemList,
      List<LanguageDto> languageDtoList,
      ZipOutputStream zipOutputStream) throws Exception {
    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    DOMImplementation domImplementation = builder.getDOMImplementation();
    Document document = domImplementation.createDocument("", "root", null);

    // root
    Element root = document.getDocumentElement();
    // root -> scoreboard
    Element scoreboard = document.createElement("scoreboard");
    // root -> scoreboard -> ontest
    Element contest = document.createElement("contest");
    contest.setAttribute("id", contestShowDto.getContestId().toString());
    contest.setAttribute("start", getTimeString(contestShowDto.getStartTime()));
    contest.setAttribute("end", getTimeString(contestShowDto.getEndTime()));
    Timestamp frozenTime = new Timestamp(contestShowDto.getEndTime().getTime()
        - contestShowDto.getFrozenTime());
    contest.setAttribute("freeze", getTimeString(frozenTime));
    contest.appendChild(document.createTextNode(contestShowDto.getTitle()));
    scoreboard.appendChild(contest);
    // root -> scoreboard -> rows
    Element rows = document.createElement("rows");
    for (UserDto user : contestUserList) {
      // root -> scoreboard -> rows -> row
      Element row = document.createElement("row");
      // root -> scoreboard -> rows -> row -> team
      Element team = document.createElement("team");
      team.setAttribute("id", user.getUserName());
      team.setAttribute("categoryid", "1");
      team.setAttribute("affillid", "");
      team.appendChild(document.createTextNode(user.getNickName() + " " + user.getName()));
      row.appendChild(team);
      rows.appendChild(row);
    }
    scoreboard.appendChild(rows);
    // root -> scoreboard -> problem_legend
    Element problem_legend = document.createElement("problem_legend");
    for (ContestProblemDto problemInfo : contestProblemList) {
      // root -> scoreboard -> problem_legend -> problem
      Element problem = document.createElement("problem");
      problem.setAttribute("id", "" + (char) ('A' + problemInfo.getOrder()));
      problem.setAttribute("color", "#000000");
      problem.appendChild(document.createTextNode(problemInfo.getTitle()));
      problem_legend.appendChild(problem);
    }
    scoreboard.appendChild(problem_legend);
    // root -> scoreboard -> language_legend
    Element language_legend = document.createElement("language_legend");
    for (LanguageDto languageDto : languageDtoList) {
      // root -> scoreboard -> language_legend -> language
      Element language = document.createElement("language");
      language.setAttribute("id", languageDto.getLanguageId().toString());
      language.appendChild(document.createTextNode(languageDto.getName()));
      language_legend.appendChild(language);
    }
    scoreboard.appendChild(language_legend);
    // root -> scoreboard -> affiliation_legend (empty)
    Element affiliation_legend = document.createElement("affiliation_legend");
    scoreboard.appendChild(affiliation_legend);
    // root -> scoreboard -> category_legend
    Element category_legend = document.createElement("category_legend");
    // root -> scoreboard -> category_legend -> category
    Element category = document.createElement("category");
    category.setAttribute("id", "1");
    category.setAttribute("color", "#ffffff");
    category.appendChild(document.createTextNode("Participants"));
    category_legend.appendChild(category);
    scoreboard.appendChild(category_legend);
    root.appendChild(scoreboard);

    DOMSource domSource = new DOMSource(document);
    StreamResult streamResult = new StreamResult(zipOutputStream);
    transformer.transform(domSource, streamResult);
  }

  private void generateEvent(
      ContestDto contestShowDto,
      List<LanguageDto> languageDtoList,
      List<ContestProblemDto> contestProblemList,
      List<StatusDto> statusList,
      ZipOutputStream zipOutputStream) throws Exception {
    Map<Integer, String> problemIdMap = new HashMap<>();
    Map<Integer, String> problemTitleMap = new HashMap<>();
    for (ContestProblemDto problem : contestProblemList) {
      problemIdMap.put(problem.getProblemId(), "" + (char) ('A' + problem.getOrder()));
      problemTitleMap.put(problem.getProblemId(), problem.getTitle());
    }
    Map<String, Integer> languageIdMap = new HashMap<>();
    for (LanguageDto languageDto : languageDtoList) {
      languageIdMap.put(languageDto.getName(), languageDto.getLanguageId());
    }

    TransformerFactory transformerFactory = TransformerFactory.newInstance();
    Transformer transformer = transformerFactory.newTransformer();
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    DOMImplementation domImplementation = builder.getDOMImplementation();
    Document document = domImplementation.createDocument("", "root", null);

    // root
    Element root = document.getDocumentElement();
    // root -> events
    Element events = document.createElement("events");
    int eventId = 0;
    for (StatusDto status : statusList) {
      if (contestShowDto.getStartTime().after(status.getTime()) ||
          contestShowDto.getEndTime().before(status.getTime())) {
        // Out of time.
        continue;
      }

      // root -> events -> event
      Element event;

      event = document.createElement("event");
      eventId++;
      event.setAttribute("id", "" + eventId);
      event.setAttribute("time", getTimeString(status.getTime()));

      // root -> events -> event -> submission
      Element submission = document.createElement("submission");
      submission.setAttribute("id", status.getStatusId().toString());
      // root -> events -> event -> submission -> team
      Element team = document.createElement("team");
      team.setAttribute("id", status.getUserName());
      team.appendChild(document.createTextNode(status.getNickName() + " " + status.getName()));
      submission.appendChild(team);
      // root -> events -> event -> submission -> problem
      Element problem = document.createElement("problem");
      problem.setAttribute("id", problemIdMap.get(status.getProblemId()));
      problem.appendChild(document.createTextNode(problemTitleMap.get(status.getProblemId())));
      submission.appendChild(problem);
      // root -> events -> event -> submission -> language
      Element language = document.createElement("language");
      language.setAttribute("id", languageIdMap.get(status.getLanguage()).toString());
      language.appendChild(document.createTextNode(status.getLanguage()));
      submission.appendChild(language);

      event.appendChild(submission);
      events.appendChild(event);

      event = document.createElement("event");
      eventId++;
      event.setAttribute("id", "" + eventId);
      event.setAttribute("time", getTimeString(status.getTime()));
      // root -> events -> event -> judging
      Element judging = document.createElement("judging");
      judging.setAttribute("id", "");
      judging.setAttribute("submitid", status.getStatusId().toString());
      if (status.getResultId() == OnlineJudgeReturnType.OJ_AC.ordinal()) {
        judging.appendChild(document.createTextNode("correct"));
      } else {
        judging.appendChild(document.createTextNode("wrong-answer"));
      }
      event.appendChild(judging);
      events.appendChild(event);
    }
    root.appendChild(events);

    DOMSource domSource = new DOMSource(document);
    StreamResult streamResult = new StreamResult(zipOutputStream);
    transformer.transform(domSource, streamResult);
  }

  @RequestMapping("exportDOMJudgeStyleReport/{contestId}")
  @LoginPermit(AuthenticationType.ADMIN)
  public void exportDOMJudgeStyleReport(
      HttpSession session,
      @PathVariable("contestId") Integer contestId,
      HttpServletResponse response) throws Exception {
    // Fetch contest detail
    ContestDto contestShowDto =
        contestService.getContestDtoByContestId(contestId, ContestFields.FIELDS_FOR_SHOWING);
    if (contestId == null) {
      throw new AppException("Contest not found.");
    }
    // Fetch user list
    List<UserDto> contestUserList = userService.fetchAllOnsiteUsersByContestId(contestId);

    // Fetch problem list
    List<ContestProblemDto> contestProblemList = contestProblemService.
        getContestProblemSummaryDtoListByContestId(contestId);
    logger.info("Fetch problem list completed!");
    // Fetch language list
    List<LanguageDto> languageDtoList = languageService.getLanguageList();
    logger.info("Fetch language list completed!");
    // Fetch status list
    StatusCriteria statusCondition = new StatusCriteria();
    statusCondition.contestId = contestId;
    statusCondition.isForAdmin = false;
    logger.info("Fetch status list completed!");
    // Sort by time
    statusCondition.orderFields = "time";
    statusCondition.orderAsc = "true";
    List<StatusDto> statusList = statusService.getStatusList(statusCondition, null,
        StatusFields.FIELDS_FOR_LIST_PAGE);
    logger.info("Sort by time completed");
    // Create zip output stream
    ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
    ZipOutputStream zipOutputStream = new ZipOutputStream(outputBuffer);
    zipOutputStream.setLevel(ZipOutputStream.STORED);

    ZipEntry zipEntry = new ZipEntry("scoreboard.xml");
    zipOutputStream.putNextEntry(zipEntry);
    generateScoreboard(contestShowDto, contestUserList, contestProblemList, languageDtoList,
        zipOutputStream);
    zipOutputStream.closeEntry();

    zipEntry = new ZipEntry("event.xml");
    zipOutputStream.putNextEntry(zipEntry);
    generateEvent(contestShowDto, languageDtoList, contestProblemList, statusList, zipOutputStream);
    zipOutputStream.closeEntry();
    logger.info("Zip output complted ");
    // Close
    zipOutputStream.close();

    response.setContentType("application/zip");
    response.addHeader("Content-Disposition", "attachment; filename=\"domjudge.zip\"");
    response.addHeader("Content-Transfer-Encoding", "binary");

    response.getOutputStream().write(outputBuffer.toByteArray());
    response.getOutputStream().flush();
    outputBuffer.close();
    logger.info("Zip close complted ");
  }

  @RequestMapping("exportCodes/{contestId}")
  @LoginPermit(AuthenticationType.ADMIN)
  public void exportCodes(
      HttpSession session,
      @PathVariable("contestId") Integer contestId,
      HttpServletResponse response) {
    try {
      ContestDto contestShowDto =
          contestService.getContestDtoByContestId(contestId, ContestFields.FIELDS_FOR_SHOWING);
      if (contestId == null) {
        throw new AppException("Contest not found.");
      }

      // Fetch status list
      StatusCriteria statusCondition = new StatusCriteria();
      statusCondition.contestId = contestId;
      statusCondition.result = OnlineJudgeResultType.OJ_AC;
      List<StatusDto> statusList = statusService.getStatusList(statusCondition, null,
          StatusFields.FIELDS_FOR_STATUS_INFO);

      // Create zip output stream
      ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
      ZipOutputStream zipOutputStream = new ZipOutputStream(outputBuffer);
      zipOutputStream.setLevel(ZipOutputStream.STORED);

      try {
        // Put status as file
        for (StatusDto status : statusList) {
          if (status.getTime().before(contestShowDto.getStartTime()) ||
              status.getTime().after(contestShowDto.getEndTime())) {
            // Skip submission out of contest time.
            continue;
          }
          // Set file name, e.g: 1_Problem_1_User_Administrator.c
          StringBuilder fileName = new StringBuilder();
          fileName.append(status.getStatusId())
              .append("Problem_").append(status.getProblemId())
              .append("_User_").append(status.getUserName())
              .append(status.getExtension());
          ZipEntry zipEntry = new ZipEntry(fileName.toString());
          zipOutputStream.putNextEntry(zipEntry);
          zipOutputStream.write(status.getCodeContent().getBytes(Charset.forName("UTF-8")));
          zipOutputStream.closeEntry();
        }
        // Close
        zipOutputStream.close();

        response.setContentType("application/zip");
        response.addHeader("Content-Disposition", "attachment; filename=\"code.zip\"");
        response.addHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(outputBuffer.toByteArray());
        response.getOutputStream().flush();
        outputBuffer.close();
      } catch (IOException e) {
        throw new AppException("Error while export codes");
      }
    } catch (AppException e) {
      // Ignore
    }
  }

  @RequestMapping("exportRankList/{contestId}")
  @LoginPermit(AuthenticationType.ADMIN)
  public ModelAndView exportRankList(
      HttpSession session, @PathVariable("contestId") Integer contestId) {
    ModelAndView result = new ModelAndView();
    result.setView(contestRankListView);
    try {
      // Login first
      loginContest(session,
          ContestDto.builder()
              .setContestId(contestId)
              .setPassword("")
              .build());

      result.addObject("contest",
          contestService.getContestDtoByContestId(contestId, ContestFields.BASIC_FIELDS));
      result.addObject("rankList", getRankList(contestId, session));
      Byte contestType = getContestType(session, contestId);
      result.addObject("type", contestType);
      if (contestType == ContestType.INVITED.ordinal()) {
        result.addObject("teamList", getContestTeamReportDtoList(contestId, session));
      }
      result.addObject("result", "success");
    } catch (AppException e) {
      result.addObject("result", "error");
      result.addObject("error_msg", e.getMessage());
    }
    return result;
  }

  private List<ContestTeamReportDto> getContestTeamReportDtoList(
      Integer contestId, HttpSession session) throws AppException {
    ContestDto contestDto = contestService.getContestDtoByContestId(
        contestId, ContestFields.BASIC_FIELDS);
    if (contestDto.getType() == ContestType.INHERIT.ordinal()) {
      contestDto = contestService.getContestDtoByContestId(
          contestDto.getParentId(), ContestFields.BASIC_FIELDS);
    }
    contestId = contestDto.getContestId();

    List<ContestTeamReportDto> contestTeamReportDtoList =
        contestTeamService.exportContestTeamReport(contestId);
    List<Integer> teamIdList = new LinkedList<>();
    for (ContestTeamReportDto team : contestTeamReportDtoList) {
      teamIdList.add(team.getTeamId());
    }
    TeamUserCondition teamUserCondition = new TeamUserCondition();
    teamUserCondition.orderFields = "id";
    teamUserCondition.orderAsc = "true";
    teamUserCondition.teamIdList = ArrayUtil.join(teamIdList.toArray(), ",");

    // Search team users
    List<TeamUserReportDto> teamUserList = teamUserService.exportTeamUserReport(teamUserCondition);

    // Put users into teams
    for (ContestTeamReportDto team : contestTeamReportDtoList) {
      team.setTeamUsers(new LinkedList<>());
      team.setInvitedUsers(new LinkedList<>());
      for (TeamUserReportDto teamUserListDto : teamUserList) {
        if (team.getTeamId().equals(teamUserListDto.getTeamId())) {
          // Put users into current users / inactive users
          if (teamUserListDto.getAllow()) {
            team.getTeamUsers().add(teamUserListDto);
          } else if (isAdmin(session)) {
            team.getInvitedUsers().add(teamUserListDto);
          }
        }
      }
    }

    return contestTeamReportDtoList;
  }

  @RequestMapping("registryReport/{contestId}")
  @LoginPermit(AuthenticationType.ADMIN)
  public ModelAndView registryReport(HttpSession session,
                                     @PathVariable("contestId") Integer contestId) {
    ModelAndView result = new ModelAndView();
    result.setView(contestRegistryReportView);
    try {
      result.addObject("list", getContestTeamReportDtoList(contestId, session));
      result.addObject("result", "success");
    } catch (AppException e) {
      result.addObject("result", "error");
      result.addObject("error_msg", e.getMessage());
    }
    return result;
  }

  private void loginContest(HttpSession session, ContestDto contestLoginDto) throws AppException {
    ContestDto contestDto = contestService.getContestDtoByContestId(
        contestLoginDto.getContestId(), ContestFields.BASIC_FIELDS);
    if (contestDto == null ||
        (!contestDto.getIsVisible() && !isAdmin(session))) {
      throw new AppException("Contest not found.");
    }

    Integer registeredContestId = contestDto.getContestId();
    if (contestDto.getType() == ContestType.INHERIT.ordinal()) {
      // Get parent contest
      ContestDto parentContest = contestService.getContestDtoByContestId(
          contestDto.getParentId(), ContestFields.BASIC_FIELDS);
      if (parentContest == null) {
        // No parent contest.
        throw new AppException("Incorrect contest type.");
      }
      // Inherit parent properties
      contestDto.setType(parentContest.getType());
      contestDto.setPassword(parentContest.getPassword());
      registeredContestId = parentContest.getContestId();
    }
    if (contestDto.getType() == ContestType.PUBLIC.ordinal()) {
      // Do nothing
    } else if (contestDto.getType() == ContestType.PRIVATE.ordinal()) {
      // Check password
      if (!isAdmin(session)) {
        if (!contestDto.getPassword().equals(contestLoginDto.getPassword())) {
          throw new FieldException("password", "Password is wrong, please try again");
        }
      }
    } else if (contestDto.getType() == ContestType.DIY.ordinal()) {
      // Do nothing
    } else if (contestDto.getType() == ContestType.INVITED.ordinal()) {
      // Check permission
      if (!isAdmin(session)) {
        UserDto currentUser = getCurrentUser(session);
        if (currentUser == null) {
          throw new AppException("You are not invited in this contest, please register first!");
        }
        Integer teamId = contestTeamService.getTeamIdByUserIdAndContestId(currentUser.getUserId(),
            registeredContestId);
        if (teamId == null) {
          throw new AppException("You are not invited in this contest, please register first!");
        }
        // Check permission
        Set<Integer> teamMembers = new HashSet<>();
        for (TeamUserListDto user : teamUserService.getTeamUserList(teamId)) {
          if (user.getAllow()) {
            teamMembers.add(user.getUserId());
          }
        }
        // Put members map in session
        setContestTeamMembers(session, contestDto.getContestId(), teamMembers);
      }
    } else if (contestDto.getType() == ContestType.ONSITE.ordinal()) {
      // Onsite!
      if (!isAdmin(session)) {
        UserDto currentUser = getCurrentUser(session);
        if (currentUser == null) {
          throw new AppException("You are not invited in this contest!");
        }
        // Check permission
        if (!contestUserService.fetchOnsiteUsersByUserIdAndContestId(currentUser.getUserId(),
            registeredContestId)) {
          throw new AppException("You are not invited in this contest!");
        }
      }
    } else {
      // Unexpected type
      throw new AppException("Incorrect contest type.");
      // TODO(mzry1992) Record this exception
    }

    // Set type in session
    setContestType(session, contestDto.getContestId(), contestDto.getType());

    // Set permission flag in session
    setContestPermission(session, contestDto.getContestId());
  }

  @RequestMapping("loginContest")
  @LoginPermit()
  public
  @ResponseBody
  Map<String, Object> loginContest(HttpSession session,
                                   @RequestBody @Valid ContestDto contestLoginDto,
                                   BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        loginContest(session, contestLoginDto);

        json.put("result", "success");
      } catch (FieldException e) {
        putFieldErrorsIntoBindingResult(e, validateResult);
        json.put("result", "field_error");
        json.put("field", validateResult.getFieldErrors());
      } catch (AppException e) {
        json.put("result", "error");
        json.put("error_msg", e.getMessage());
      }
    }
    return json;
  }

  @RequestMapping("registryReview")
  @LoginPermit(AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> registryReview(
      @RequestBody ContestTeamReviewDto contestTeamReviewDto) {
    Map<String, Object> json = new HashMap<>();
    try {
      ContestTeamDto contestTeamDto = contestTeamService.getContestTeamDto(contestTeamReviewDto
          .getContestTeamId());
      contestTeamDto.setStatus(contestTeamReviewDto.getStatus());
      if (contestTeamReviewDto.getComment() == null) {
        contestTeamReviewDto.setComment("");
      }
      contestTeamDto.setComment(contestTeamReviewDto.getComment());
      contestTeamService.updateContestTeam(contestTeamDto);

      String messageTitle;
      StringBuilder messageContentBuilder = new StringBuilder();
      if (contestTeamReviewDto.getStatus() == ContestRegistryStatusType.REFUSED.ordinal()) {
        messageTitle = "Contest register request refused.";
        messageContentBuilder.append("You register request has been refused, reason: ")
            .append(contestTeamReviewDto.getComment())
            .append("\n\n")
            .append("Please fix the issue and register again.");
      } else {
        messageTitle = "Contest register request accepted.";
        messageContentBuilder.append("You register request has been accepted.");
      }
      messageService.createNewMessage(MessageDto.builder()
          .setSenderId(1) // Administrator
          .setReceiverId(contestTeamDto.getLeaderId())
          .setTime(new Timestamp(System.currentTimeMillis()))
          .setIsOpened(false)
          .setTitle(messageTitle)
          .setContent(messageContentBuilder.toString())
          .build());
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping("registryStatusList")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> registerStatusList(
      @RequestBody ContestTeamCondition contestTeamCondition,
      HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      Long count = contestTeamService.count(contestTeamCondition);
      PageInfo pageInfo = buildPageInfo(count, contestTeamCondition.currentPage,
          settings.RECORD_PER_PAGE, null);
      List<ContestTeamListDto> contestTeamList = contestTeamService.getContestTeamList(
          contestTeamCondition, pageInfo);

      if (contestTeamList.size() > 0) {
        // At most 20 records
        List<Integer> teamIdList = new LinkedList<>();
        for (ContestTeamListDto team : contestTeamList) {
          teamIdList.add(team.getTeamId());
        }
        TeamUserCondition teamUserCondition = new TeamUserCondition();
        teamUserCondition.orderFields = "id";
        teamUserCondition.orderAsc = "true";
        teamUserCondition.teamIdList = ArrayUtil.join(teamIdList.toArray(), ",");
        // Search team users
        List<TeamUserListDto> teamUserList = teamUserService.getTeamUserList(teamUserCondition);

        // Put users into teams
        for (ContestTeamListDto team : contestTeamList) {
          team.setTeamUsers(new LinkedList<>());
          team.setInvitedUsers(new LinkedList<>());
          for (TeamUserListDto teamUserListDto : teamUserList) {
            if (team.getTeamId().compareTo(teamUserListDto.getTeamId()) == 0) {
              // Put users into current users / inactive users
              if (teamUserListDto.getAllow()) {
                team.getTeamUsers().add(teamUserListDto);
              } else if (isAdmin(session)) {
                team.getInvitedUsers().add(teamUserListDto);
              }
            }
          }
        }
      }

      json.put("pageInfo", pageInfo);
      json.put("list", contestTeamList);
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping("register/{teamId}/{contestId}")
  @LoginPermit()
  public
  @ResponseBody
  Map<String, Object> register(@PathVariable("teamId") Integer teamId,
                               @PathVariable("contestId") Integer contestId,
                               HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      TeamDto teamDto = teamService.getTeamDtoByTeamId(teamId, TeamFields.BASIC_FIELDS);
      if (teamDto == null || !teamDto.getTeamId().equals(teamId)) {
        throw new AppException("Team not found!");
      }
      ContestDto contestShowDto = contestService.getContestDtoByContestId(
          contestId, ContestFields.FIELDS_FOR_SHOWING);
      if (contestShowDto == null || !contestShowDto.getContestId().equals(contestId)) {
        throw new AppException("Contest not found!");
      }
      // Stop register after contest stopped.
      Timestamp currentTime = new Timestamp(System.currentTimeMillis());
      if (currentTime.after(contestShowDto.getEndTime())) {
        throw new AppException("Contest is already over!");
      }
      UserDto currentUser = getCurrentUser(session);
      if (!currentUser.getUserId().equals(teamDto.getLeaderId())) {
        throw new AppException("You are not the team leader of team " + teamDto.getTeamName() + ".");
      }
      List<TeamUserListDto> teamUserList = teamUserService.getTeamUserList(teamId);
      for (TeamUserListDto teamUserDto : teamUserList) {
        if (!contestTeamService.checkUserCanRegisterInContest(teamUserDto.getUserId(),
            contestShowDto.getContestId())) {
          throw new AppException("User " + teamUserDto.getUserName() +
              " has been register into this contest in another team!");
        }
      }
      Integer contestTeamId = contestTeamService.createNewContestTeam(contestId, teamId);
      if (contestTeamId == null) {
        throw new AppException("Error while register team.");
      }
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping("status/{contestId}/{lastFetched}")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> status(@PathVariable("contestId") Integer contestId,
                             @PathVariable("lastFetched") Integer lastFetched,
                             HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      UserDto currentUser = getCurrentUser(session);
      ContestDto contestShowDto = contestService.getContestDtoByContestId(
          contestId, ContestFields.FIELDS_FOR_SHOWING);
      if (contestShowDto == null) {
        throw new AppException("No such contest.");
      }
      if (!contestShowDto.getIsVisible() && !isAdmin(session)) {
        throw new AppException("No such contest.");
      }
      // Check permission
      checkPermission(session, contestId);

      StatusCriteria statusCondition = new StatusCriteria();
      statusCondition.contestId = contestShowDto.getContestId();
      statusCondition.isForAdmin = isAdmin(session);
      // Sort by time
      statusCondition.orderFields = "time";
      statusCondition.orderAsc = "true";
      statusCondition.startId = lastFetched + 1;
      List<StatusDto> statusList = statusService.getStatusList(statusCondition, null,
          StatusFields.FIELDS_FOR_LIST_PAGE);
      if (!isAdmin(session)) {
        for (StatusDto status : statusList) {
          if (!status.getUserName().equals(currentUser.getUserName())) {
            // Stash sensitive information
            status.setLength(null);
            status.setTimeCost(null);
            status.setMemoryCost(null);
            status.setCaseNumber(null);
            status.setLanguage(null);
          }
        }
      }

      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  private RankList getRankList(Integer contestId,
                               HttpSession session) throws AppException {
    ContestDto contestShowDto = contestService.getContestDtoByContestId(
        contestId, ContestFields.FIELDS_FOR_SHOWING);
    if (contestShowDto == null) {
      throw new AppException("No such contest.");
    }
    if (!contestShowDto.getIsVisible() && !isAdmin(session)) {
      throw new AppException("No such contest.");
    }
    if (contestShowDto.getStatus().equals("Pending") && !isAdmin(session)) {
      throw new AppException("Contest not start yet.");
    }

    // Check permission
    checkContestPermission(session, contestId);

    if (isAdmin(session)) {
      // Admin or no frozen time specified
      return contestRankListService.getRankList(
          contestId,
          (int) getContestType(session, contestId),
          false,
          0
      );
    } else {
      // Frozen board on last frozenTime minutes
      return contestRankListService.getRankList(
          contestId,
          (int) getContestType(session, contestId),
          contestShowDto.getTimeLeft() <= contestShowDto.getFrozenTime(),
          contestShowDto.getFrozenTime()
      );
    }
  }

  @RequestMapping("rankList/{contestId}")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> rankList(@PathVariable("contestId") Integer contestId,
                               HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      json.put("rankList", getRankList(contestId, session));
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping("data/{contestId}")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> data(
      @PathVariable("contestId") Integer contestId,
      HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      ContestDto contestShowDto = contestService.getContestDtoByContestId(
          contestId, ContestFields.FIELDS_FOR_SHOWING);
      if (contestShowDto == null) {
        throw new AppException("No such contest.");
      }
      if (!contestShowDto.getIsVisible() && !isAdmin(session)) {
        throw new AppException("No such contest.");
      }

      // Check permission
      try {
        checkContestPermission(session, contestId);
      } catch (AppException e) {
        // Not login before
        // Try to auto re-login
        loginContest(session,
            ContestDto.builder()
                .setContestId(contestId)
                .setPassword("")
                .build());
        // And check permission again.
        checkContestPermission(session, contestId);
      }

      // Update contest type
      contestShowDto.setType(getContestType(session, contestId));

      List<ContestProblemDto> contestProblemList;
      if (contestShowDto.getStatus().equals("Pending") && !isAdmin(session)) {
        contestProblemList = new LinkedList<>();
      } else {
        contestProblemList = contestProblemService
            .getContestProblemDetailDtoListByContestId(contestId);
        if (!isAdmin(session)) {
          for (ContestProblemDto contestProblemDetailDto : contestProblemList) {
            // Stash problem source
            contestProblemDetailDto.setSource("");
          }
        }
      }
      for (int i = 0; i < contestProblemList.size(); ++i) {
        int d = i + 65;
        String wp = new String();
        wp = wp + (char) d;
        ContestProblemDto e = contestProblemList.get(i);
        e.setOrderCharacter(wp);
        contestProblemList.set(i, e);
      }

      json.put("contest", contestShowDto);
      json.put("problemList", contestProblemList);
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown 1 exception occurred.");
    }
    return json;
  }

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> search(
      HttpSession session,
      @RequestBody(required = false) ContestCriteria criteria) {
    Map<String, Object> json = new HashMap<>();
    try {
      // Avoid null pointer exception
      if (criteria == null) {
        criteria = new ContestCriteria();
      }
      if (!isAdmin(session)) {
        criteria.isVisible = true;
      }
      Long count = contestService.count(criteria);
      PageInfo pageInfo = buildPageInfo(count, criteria.currentPage,
          settings.RECORD_PER_PAGE, null);
      List<ContestDto> contestListDtoList = contestService.
          getContestList(criteria, pageInfo, ContestFields.FIELDS_FOR_LIST_PAGE);
      for (ContestDto contestListDto : contestListDtoList) {
        if (contestListDto.getType() == ContestType.INHERIT.ordinal()) {
          ContestDto contestDto = contestService.getContestDtoByContestId(
              contestListDto.getParentId(), ContestFields.BASIC_FIELDS);
          contestListDto.setParentType(contestDto.getType());
          contestListDto.setParentTypeName(ContestType.values()[contestDto.getType()]
              .getDescription());
        }
      }

      json.put("pageInfo", pageInfo);
      json.put("result", "success");
      json.put("list", contestListDtoList);
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping("operator/{id}/{field}/{value}")
  @LoginPermit(AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> operator(
      @PathVariable("id") String targetId,
      @PathVariable("field") String field,
      @PathVariable("value") String value) {
    Map<String, Object> json = new HashMap<>();
    try {
      contestService.operator(field, targetId, value);
      json.put("result", "success");
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping("edit")
  @LoginPermit(AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> edit(
      @RequestBody @Valid ContestDto contestEditDto,
      BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        if (StringUtil.trimAllSpace(contestEditDto.getTitle()).equals("")) {
          throw new FieldException("title", "Please enter a validate title.");
        }
        if (contestEditDto.getType() == ContestType.PRIVATE.ordinal()) {
          if (!contestEditDto.getPassword().equals(contestEditDto.getPasswordRepeat())) {
            throw new FieldException("newPasswordRepeat", "Password do not match.");
          }
        } else if (contestEditDto.getType() == ContestType.INHERIT.ordinal()) {
          if (contestEditDto.getParentId() == null) {
            throw new FieldException("parentId", "Please enter parent contest's id.");
          }
          if (!contestService.checkContestExists(contestEditDto.getContestId())) {
            throw new FieldException("parentId", "Contest not exists.");
          }
        }

        ContestDto contestDto;
        if (contestEditDto.getAction().compareTo("new") == 0) {
          Integer contestId = contestService.createNewContest();
          contestDto = contestService.getContestDtoByContestId(
              contestId, ContestFields.BASIC_FIELDS);
          if (contestDto == null
              || contestDto.getContestId().compareTo(contestId) != 0) {
            throw new AppException("Error while creating contest.");
          }
          if (contestEditDto.getDescription() == null) {
            contestEditDto.setDescription("");
          }
          // Move pictures
          String oldDirectory = "contest/new/";
          String newDirectory = "contest/" + contestId + "/";
          contestEditDto.setDescription(pictureService.modifyPictureLocation(
              contestEditDto.getDescription(), oldDirectory, newDirectory
          ));
        } else {
          contestDto = contestService.getContestDtoByContestId(
              contestEditDto.getContestId(), ContestFields.BASIC_FIELDS);
          if (contestDto == null) {
            throw new AppException("No such contest.");
          }
        }

        // Parser contest problem list
        List<Integer> problemIdList = new LinkedList<>();
        // Split problem list
        String[] problemList = contestEditDto.getProblemList().split(",");
        // Add new contest problems
        for (String problemIdString : problemList) {
          if (problemIdString.length() == 0) {
            continue;
          }
          Integer problemId;
          try {
            problemId = Integer.parseInt(problemIdString);
          } catch (NumberFormatException e) {
            throw new AppException("Problem format error.");
          }
          // Check problem exists.
          AppExceptionUtil.assertTrue(problemService.checkProblemExists(problemId));

          // Add problem id if success
          problemIdList.add(problemId);
        }

        // Remove old contest problems
        contestProblemService.removeContestProblemByContestId(contestDto.getContestId());
        // Add new contest problems
        for (int order = 0; order < problemIdList.size(); order++) {
          contestProblemService.createNewContestProblem(
              ContestProblemDto.builder()
                  .setContestId(contestDto.getContestId())
                  .setOrder(order)
                  .setProblemId(problemIdList.get(order))
                  .build());

          // Check problem added success.
          ContestProblemDto contestProblemDto = contestProblemService
              .getBasicContestProblemDto(contestDto.getContestId(), problemIdList.get(order));
          AppExceptionUtil.assertNotNull(contestProblemDto);
          AppExceptionUtil.assertNotNull(contestProblemDto.getContestProblemId());
        }

        contestDto.setType(contestEditDto.getType());
        contestDto.setPassword(null);
        contestDto.setParentId(null);
        if (contestEditDto.getType() == ContestType.PRIVATE.ordinal()) {
          contestDto.setPassword(contestEditDto.getPassword());
        } else if (contestEditDto.getType() == ContestType.INHERIT.ordinal()) {
          contestDto.setParentId(contestEditDto.getParentId());
        }
        contestDto.setDescription(contestEditDto.getDescription());
        contestDto.setTitle(contestEditDto.getTitle());
        contestDto.setLength(
            contestEditDto.getLengthDays() * 24 * 60 * 60 +
                contestEditDto.getLengthHours() * 60 * 60 +
                contestEditDto.getLengthMinutes() * 60
        );
        contestDto.setTime(contestEditDto.getTime());
        if (contestEditDto.getNeedFrozen()) {
          contestDto.setFrozenTime(
              contestEditDto.getFrozenLengthDays() * 24 * 60 * 60 +
                  contestEditDto.getFrozenLengthHours() * 60 * 60 +
                  contestEditDto.getFrozenLengthMinutes() * 60
          );
        } else {
          contestDto.setFrozenTime(0);
        }

        contestService.updateContest(contestDto);
        json.put("result", "success");
        json.put("contestId", contestDto.getContestId());
      } catch (FieldException e) {
        putFieldErrorsIntoBindingResult(e, validateResult);
        json.put("result", "field_error");
        json.put("field", validateResult.getFieldErrors());
      } catch (AppException e) {
        json.put("result", "error");
        json.put("error_msg", e.getMessage());
        e.printStackTrace();
      }
    }
    return json;
  }

  @RequestMapping(value = "createContestByArchiveFile",
      method = RequestMethod.POST)
  @LoginPermit(AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> createContestByArchiveFile(
      @RequestParam(value = "uploadFile") MultipartFile[] files) {
    Map<String, Object> json = new HashMap<>();
    try {
      FileInformationDto fileInformationDto = fileService.uploadContestArchive(
          FileUploadDto.builder()
              .setFiles(Arrays.asList(files))
              .build()
      );
      ContestDto contestDto = contestImporterService.parseContestZipArchive(fileInformationDto);
      json.put("success", "true");
      json.put("contestId", contestDto.getContestId());
    } catch (AppException e) {
      e.printStackTrace();
      json.put("error", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("error", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping(value = "uploadOnsiteUserFile",
      method = RequestMethod.POST)
  @LoginPermit(AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> uploadOnsiteUserFile(
      @RequestParam(value = "uploadFile") MultipartFile[] files) {
    Map<String, Object> json = new HashMap<>();
    try {
      FileInformationDto fileInformationDto = fileService.uploadContestArchive(
          FileUploadDto.builder()
              .setFiles(Arrays.asList(files))
              .build()
      );

      File csvFile = new File(settings.UPLOAD_FOLDER + fileInformationDto.getFileName());
      List<OnsiteUserDto> result = CSVUtil.parseArray(csvFile, OnsiteUserDto.class);
      json.put("success", "true");
      json.put("list", result);
    } catch (AppException e) {
      e.printStackTrace();
      json.put("error", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("error", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping("updateOnsiteUser")
  @LoginPermit(AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> updateOnsiteUser(
      @RequestBody ContestDto contestEditDto) {
    Map<String, Object> json = new HashMap<>();
    try {
      Integer contestId = contestEditDto.getContestId();
      // Remove old users
      contestUserService.removeContestUsersByContestId(contestId);
      // Add new users
      List<Integer> newUsersIDList = userService.createOnsiteUsersByUserList(contestEditDto
          .getUserList());
      for (Integer userID : newUsersIDList) {
        contestUserService.createNewContestUser(
            ContestUserDto.builder()
                .setContestId(contestId)
                .setUserId(userID)
                .setStatus((byte) ContestRegistryStatusType.ACCEPTED.ordinal())
                .setComment("Users in contest " + contestId)
                .build()
        );
      }
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("fetchAllOnsiteUsers/{contestId}")
  @LoginPermit(AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> fetchAllOnsiteUsers(
      @PathVariable("contestId") Integer contestId) {
    Map<String, Object> json = new HashMap<>();
    try {
      List<UserDto> result = userService.fetchAllOnsiteUsersByContestId(contestId);
      json.put("result", "success");
      json.put("list", result);
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }
}
