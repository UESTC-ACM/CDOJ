package cn.edu.uestc.acmicpc.web.oj.controller.contest;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.ContestTeamCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TeamUserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestLoginDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDetailDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamReportDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamReviewDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestUser.ContestUserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusInformationDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserReportDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.OnsiteUserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.ContestImporterService;
import cn.edu.uestc.acmicpc.service.iface.ContestProblemService;
import cn.edu.uestc.acmicpc.service.iface.ContestRankListService;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.service.iface.ContestTeamService;
import cn.edu.uestc.acmicpc.service.iface.ContestUserService;
import cn.edu.uestc.acmicpc.service.iface.FileService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
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
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.web.rank.RankList;
import cn.edu.uestc.acmicpc.web.view.ContestRankListView;
import cn.edu.uestc.acmicpc.web.view.ContestRegistryReportView;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Timestamp;
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

/**
 * @author liverliu
 */
@Controller
@RequestMapping("/contest")
public class ContestController extends BaseController {

  private ContestService contestService;
  private ContestProblemService contestProblemService;
  private ContestImporterService contestImporterService;
  private FileService fileService;
  private PictureService pictureService;
  private ProblemService problemService;
  private StatusService statusService;
  private GlobalService globalService;
  private TeamService teamService;
  private TeamUserService teamUserService;
  private ContestTeamService contestTeamService;
  private MessageService messageService;
  private ContestRankListService contestRankListService;
  private ContestRegistryReportView contestRegistryReportView;
  private ContestRankListView contestRankListView;
  private Settings settings;
  private UserService userService;
  private ContestUserService contestUserService;

  @Autowired
  public ContestController(ContestService contestService,
                           ContestProblemService contestProblemService,
                           ContestImporterService contestImporterService,
                           FileService fileService,
                           PictureService pictureService,
                           ProblemService problemService,
                           StatusService statusService,
                           GlobalService globalService,
                           TeamService teamService,
                           TeamUserService teamUserService,
                           ContestTeamService contestTeamService,
                           MessageService messageService,
                           ContestRankListService contestRankListService,
                           ContestRegistryReportView contestRegistryReportView,
                           ContestRankListView contestRankListView,
                           Settings settings,
                           UserService userService,
                           ContestUserService contestUserService) {
    this.contestService = contestService;
    this.contestProblemService = contestProblemService;
    this.contestImporterService = contestImporterService;
    this.fileService = fileService;
    this.pictureService = pictureService;
    this.problemService = problemService;
    this.statusService = statusService;
    this.globalService = globalService;
    this.teamService = teamService;
    this.teamUserService = teamUserService;
    this.contestService = contestService;
    this.contestTeamService = contestTeamService;
    this.messageService = messageService;
    this.contestRankListService = contestRankListService;
    this.contestRegistryReportView = contestRegistryReportView;
    this.contestRankListView = contestRankListView;
    this.settings = settings;
    this.userService = userService;
    this.contestUserService = contestUserService;
  }

  @RequestMapping("exportCodes/{contestId}")
  public void exportCodes(HttpSession session,
                          @PathVariable("contestId") Integer contestId,
                          HttpServletResponse response) {
    try {
      if (!isAdmin(session)) {
        throw new AppException("Permission denied!");
      }
      ContestShowDTO contestShowDTO = contestService.getContestShowDTOByContestId(contestId);
      if (contestId == null) {
        throw new AppException("Contest not found.");
      }

      // Fetch status list
      StatusCondition statusCondition = new StatusCondition();
      statusCondition.contestId = contestId;
      statusCondition.result = OnlineJudgeResultType.OJ_AC;
      List<StatusInformationDTO> statusList = statusService.getStatusInformationDTOList(statusCondition);

      // Create zip output stream
      ByteArrayOutputStream outputBuffer = new ByteArrayOutputStream();
      ZipOutputStream zipOutputStream = new ZipOutputStream(outputBuffer);
      zipOutputStream.setLevel(ZipOutputStream.STORED);

      try {
        // Put status as file
        for (StatusInformationDTO status : statusList) {
          if (status.getTime().before(contestShowDTO.getStartTime()) ||
              status.getTime().after(contestShowDTO.getEndTime())) {
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
  public ModelAndView exportRankList(HttpSession session,
                                     @PathVariable("contestId") Integer contestId) {
    ModelAndView result = new ModelAndView();
    result.setView(contestRankListView);
    try {
      if (!isAdmin(session)) {
        throw new AppException("Permission denied!");
      }

      // Login first
      loginContest(session,
          ContestLoginDTO.builder()
              .setContestId(contestId)
              .setPassword("")
              .build()
      );

      result.addObject("contest", contestService.getContestDTOByContestId(contestId));
      result.addObject("rankList", getRankList(contestId, session));
      Byte contestType = getContestType(session, contestId);
      result.addObject("type", contestType);
      if (contestType == ContestType.INVITED.ordinal()) {
        result.addObject("teamList", getContestTeamReportDTOList(contestId, session));
      }
      result.addObject("result", "success");
    } catch (AppException e) {
      result.addObject("result", "error");
      result.addObject("error_msg", e.getMessage());
    }
    return result;
  }

  private List<ContestTeamReportDTO> getContestTeamReportDTOList(Integer contestId,
                                                                 HttpSession session) throws AppException {
    ContestDTO contestDTO = contestService.getContestDTOByContestId(contestId);
    if (contestDTO.getType() == ContestType.INHERIT.ordinal()) {
      contestDTO = contestService.getContestDTOByContestId(contestDTO.getParentId());
    }
    contestId = contestDTO.getContestId();

    List<ContestTeamReportDTO> contestTeamReportDTOList =
        contestTeamService.exportContestTeamReport(contestId);
    List<Integer> teamIdList = new LinkedList<>();
    for (ContestTeamReportDTO team : contestTeamReportDTOList) {
      teamIdList.add(team.getTeamId());
    }
    TeamUserCondition teamUserCondition = new TeamUserCondition();
    teamUserCondition.orderFields = "id";
    teamUserCondition.orderAsc = "true";
    teamUserCondition.teamIdList = ArrayUtil.join(teamIdList.toArray(), ",");

    // Search team users
    List<TeamUserReportDTO> teamUserList = teamUserService.exportTeamUserReport(teamUserCondition);

    // Put users into teams
    for (ContestTeamReportDTO team : contestTeamReportDTOList) {
      team.setTeamUsers(new LinkedList<TeamUserReportDTO>());
      team.setInvitedUsers(new LinkedList<TeamUserReportDTO>());
      for (TeamUserReportDTO teamUserListDTO : teamUserList) {
        if (team.getTeamId().equals(teamUserListDTO.getTeamId())) {
          // Put users into current users / inactive users
          if (teamUserListDTO.getAllow()) {
            team.getTeamUsers().add(teamUserListDTO);
          } else if (isAdmin(session)) {
            team.getInvitedUsers().add(teamUserListDTO);
          }
        }
      }
    }

    return contestTeamReportDTOList;
  }

  @RequestMapping("registryReport/{contestId}")
  public ModelAndView registryReport(HttpSession session,
                                     @PathVariable("contestId")
                                     Integer contestId) {
    ModelAndView result = new ModelAndView();
    result.setView(contestRegistryReportView);
    try {
      if (!isAdmin(session)) {
        throw new AppException("Permission denied!");
      }

      result.addObject("list", getContestTeamReportDTOList(contestId, session));
      result.addObject("result", "success");
    } catch (AppException e) {
      result.addObject("result", "error");
      result.addObject("error_msg", e.getMessage());
    }
    return result;
  }

  private void loginContest(HttpSession session,
                            ContestLoginDTO contestLoginDTO) throws AppException {
    ContestDTO contestDTO = contestService.getContestDTOByContestId(contestLoginDTO.getContestId());
    if (contestDTO == null ||
        (!contestDTO.getIsVisible() && !isAdmin(session))) {
      throw new AppException("Contest not found.");
    }

    Integer registeredContestId = contestDTO.getContestId();
    if (contestDTO.getType() == ContestType.INHERIT.ordinal()) {
      // Get parent contest
      ContestDTO parentContest = contestService.getContestDTOByContestId(contestDTO.getParentId());
      if (parentContest == null) {
        // No parent contest.
        throw new AppException("Incorrect contest type.");
      }
      // Inherit parent properties
      contestDTO.setType(parentContest.getType());
      contestDTO.setPassword(parentContest.getPassword());
      registeredContestId = parentContest.getContestId();
    }
    if (contestDTO.getType() == ContestType.PUBLIC.ordinal()) {
      // Do nothing
    } else if (contestDTO.getType() == ContestType.PRIVATE.ordinal()) {
      // Check password
      if (!isAdmin(session)) {
        if (!contestDTO.getPassword().equals(contestLoginDTO.getPassword())) {
          throw new FieldException("password", "Password is wrong, please try again");
        }
      }
    } else if (contestDTO.getType() == ContestType.DIY.ordinal()) {
      // Do nothing
    } else if (contestDTO.getType() == ContestType.INVITED.ordinal()) {
      // Check permission
      if (!isAdmin(session)) {
        UserDTO currentUser = getCurrentUser(session);
        if (currentUser == null) {
          throw new AppException("You are not invited in this contest, please register first!");
        }
        Integer teamId = contestTeamService.getTeamIdByUserIdAndContestId(currentUser.getUserId(), registeredContestId);
        if (teamId == null) {
          throw new AppException("You are not invited in this contest, please register first!");
        }
        // Check permission
        Set<Integer> teamMembers = new HashSet<>();
        for (TeamUserListDTO user : teamUserService.getTeamUserList(teamId)) {
          if (user.getAllow()) {
            teamMembers.add(user.getUserId());
          }
        }
        // Put members map in session
        setContestTeamMembers(session, contestDTO.getContestId(), teamMembers);
      }
    } else if (contestDTO.getType() == ContestType.ONSITE.ordinal()) {
      // Onsite!
      if (!isAdmin(session)) {
        UserDTO currentUser = getCurrentUser(session);
        if (currentUser == null) {
          throw new AppException("You are not invited in this contest!");
        }
        // Check permission
        if (!contestUserService.fetchOnsiteUsersByUserIdAndContestId(currentUser.getUserId(), registeredContestId)) {
          throw new AppException("You are not invited in this contest!");
        }
      }
    } else {
      // Unexpected type
      throw new AppException("Incorrect contest type.");
      // TODO(mzry1992) Record this exception
    }

    // Set type in session
    setContestType(session, contestDTO.getContestId(), contestDTO.getType());

    // Set permission flag in session
    setContestPermission(session, contestDTO.getContestId());
  }

  @RequestMapping("loginContest")
  @LoginPermit(NeedLogin = true)
  public
  @ResponseBody
  Map<String, Object> loginContest(HttpSession session,
                                   @RequestBody @Valid ContestLoginDTO contestLoginDTO,
                                   BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        loginContest(session, contestLoginDTO);

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
  Map<String, Object> registryReview(@RequestBody ContestTeamReviewDTO contestTeamReviewDTO) {
    Map<String, Object> json = new HashMap<>();
    try {
      ContestTeamDTO contestTeamDTO = contestTeamService.getContestTeamDTO(contestTeamReviewDTO.getContestTeamId());
      contestTeamDTO.setStatus(contestTeamReviewDTO.getStatus());
      if (contestTeamReviewDTO.getComment() == null) {
        contestTeamReviewDTO.setComment("");
      }
      contestTeamDTO.setComment(contestTeamReviewDTO.getComment());
      contestTeamService.updateContestTeam(contestTeamDTO);

      String messageTitle;
      StringBuilder messageContentBuilder = new StringBuilder();
      if (contestTeamReviewDTO.getStatus() == ContestRegistryStatusType.REFUSED.ordinal()) {
        messageTitle = "Contest register request refused.";
        messageContentBuilder.append("You register request has been refused, reason: ")
            .append(contestTeamReviewDTO.getComment())
            .append("\n\n")
            .append("Please fix the issue and register again.");
      } else {
        messageTitle = "Contest register request accepted.";
        messageContentBuilder.append("You register request has been accepted.");
      }
      messageService.createNewMessage(MessageDTO.builder()
          .setSenderId(1) // Administrator
          .setReceiverId(contestTeamDTO.getLeaderId())
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
  Map<String, Object> registerStatusList(@RequestBody ContestTeamCondition contestTeamCondition,
                                         HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      Long count = contestTeamService.count(contestTeamCondition);
      PageInfo pageInfo = buildPageInfo(count, contestTeamCondition.currentPage,
          settings.RECORD_PER_PAGE, null);
      List<ContestTeamListDTO> contestTeamList = contestTeamService.getContestTeamList(
          contestTeamCondition, pageInfo);

      if (contestTeamList.size() > 0) {
        // At most 20 records
        List<Integer> teamIdList = new LinkedList<>();
        for (ContestTeamListDTO team : contestTeamList) {
          teamIdList.add(team.getTeamId());
        }
        TeamUserCondition teamUserCondition = new TeamUserCondition();
        teamUserCondition.orderFields = "id";
        teamUserCondition.orderAsc = "true";
        teamUserCondition.teamIdList = ArrayUtil.join(teamIdList.toArray(), ",");
        // Search team users
        List<TeamUserListDTO> teamUserList = teamUserService.getTeamUserList(teamUserCondition);

        // Put users into teams
        for (ContestTeamListDTO team : contestTeamList) {
          team.setTeamUsers(new LinkedList<TeamUserListDTO>());
          team.setInvitedUsers(new LinkedList<TeamUserListDTO>());
          for (TeamUserListDTO teamUserListDTO : teamUserList) {
            if (team.getTeamId().compareTo(teamUserListDTO.getTeamId()) == 0) {
              // Put users into current users / inactive users
              if (teamUserListDTO.getAllow()) {
                team.getTeamUsers().add(teamUserListDTO);
              } else if (isAdmin(session)) {
                team.getInvitedUsers().add(teamUserListDTO);
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
  @LoginPermit(NeedLogin = true)
  public
  @ResponseBody
  Map<String, Object> register(@PathVariable("teamId") Integer teamId,
                               @PathVariable("contestId") Integer contestId,
                               HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      TeamDTO teamDTO = teamService.getTeamDTOByTeamId(teamId);
      if (teamDTO == null || !teamDTO.getTeamId().equals(teamId)) {
        throw new AppException("Team not found!");
      }
      ContestShowDTO contestShowDTO = contestService.getContestShowDTOByContestId(contestId);
      if (contestShowDTO == null || !contestShowDTO.getContestId().equals(contestId)) {
        throw new AppException("Contest not found!");
      }
      // Stop register after contest stopped.
      Timestamp currentTime = new Timestamp(System.currentTimeMillis());
      if (currentTime.after(contestShowDTO.getEndTime())) {
        throw new AppException("Contest is already over!");
      }
      UserDTO currentUser = getCurrentUser(session);
      if (!currentUser.getUserId().equals(teamDTO.getLeaderId())) {
        throw new AppException("You are not the team leader of team " + teamDTO.getTeamName() + ".");
      }
      List<TeamUserListDTO> teamUserList = teamUserService.getTeamUserList(teamId);
      for (TeamUserListDTO teamUserDTO : teamUserList) {
        if (!contestTeamService.checkUserCanRegisterInContest(teamUserDTO.getUserId(),
            contestShowDTO.getContestId())) {
          throw new AppException("User " + teamUserDTO.getUserName() +
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
      UserDTO currentUser = getCurrentUser(session);
      ContestShowDTO contestShowDTO = contestService.getContestShowDTOByContestId(contestId);
      if (contestShowDTO == null) {
        throw new AppException("No such contest.");
      }
      if (!contestShowDTO.getIsVisible() && !isAdmin(session)) {
        throw new AppException("No such contest.");
      }
      // Check permission
      checkPermission(session, contestId);

      StatusCondition statusCondition = new StatusCondition();
      statusCondition.contestId = contestShowDTO.getContestId();
      statusCondition.isForAdmin = isAdmin(session);
      // Sort by time
      statusCondition.orderFields = "time";
      statusCondition.orderAsc = "true";
      statusCondition.startId = lastFetched + 1;
      List<StatusListDTO> statusList = statusService.getStatusList(statusCondition);
      if (!isAdmin(session)) {
        for (StatusListDTO status : statusList) {
          if (!status.getUserName().equals(currentUser.getUserName())) {
            // Stash sensitive information
            status.setLength(null);
            status.setTimeCost(null);
            status.setMemoryCost(null);
            status.setCaseNumber(null);
            status.setLanguage(null);
          } else {
            status.setReturnType(globalService.getReturnDescription(status.getReturnTypeId(),
                status.getCaseNumber()));
            if (status.getReturnTypeId() != OnlineJudgeReturnType.OJ_AC.ordinal()) {
              status.setTimeCost(null);
              status.setMemoryCost(null);
            }
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
    ContestShowDTO contestShowDTO = contestService.getContestShowDTOByContestId(contestId);
    if (contestShowDTO == null) {
      throw new AppException("No such contest.");
    }
    if (!contestShowDTO.getIsVisible() && !isAdmin(session)) {
      throw new AppException("No such contest.");
    }
    if (contestShowDTO.getStatus().equals("Pending") && !isAdmin(session)) {
      throw new AppException("Contest not start yet.");
    }

    // Check permission
    checkContestPermission(session, contestId);

    if (getContestType(session, contestId) == ContestType.INVITED.ordinal()) {
      return contestRankListService.getRankList(contestId, true);
    } else {
      return contestRankListService.getRankList(contestId, false);
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
  Map<String, Object> data(@PathVariable("contestId") Integer contestId,
                           HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      ContestShowDTO contestShowDTO = contestService.getContestShowDTOByContestId(contestId);
      if (contestShowDTO == null) {
        throw new AppException("No such contest.");
      }
      if (!contestShowDTO.getIsVisible() && !isAdmin(session)) {
        throw new AppException("No such contest.");
      }

      // Check permission
      try {
        checkContestPermission(session, contestId);
      } catch (AppException e) {
        // Not login before
        // Try to auto re-login
        loginContest(session,
            ContestLoginDTO.builder()
                .setContestId(contestId)
                .setPassword("")
                .build()
        );
        // And check permission again.
        checkContestPermission(session, contestId);
      }

      // Update contest type
      contestShowDTO.setType(getContestType(session, contestId));

      List<ContestProblemDetailDTO> contestProblemList;
      if (contestShowDTO.getStatus().equals("Pending") && !isAdmin(session)) {
        contestProblemList = new LinkedList<>();
      } else {
        contestProblemList = contestProblemService
            .getContestProblemDetailDTOListByContestId(contestId);
        if (!isAdmin(session)) {
          for (ContestProblemDetailDTO contestProblemDetailDTO : contestProblemList) {
            // Stash problem source
            contestProblemDetailDTO.setSource("");
          }
        }
      }

      json.put("contest", contestShowDTO);
      json.put("problemList", contestProblemList);
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

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> search(HttpSession session,
                             @RequestBody ContestCondition contestCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (!isAdmin(session)) {
        contestCondition.isVisible = true;
      }
      Long count = contestService.count(contestCondition);
      PageInfo pageInfo = buildPageInfo(count, contestCondition.currentPage,
          settings.RECORD_PER_PAGE, null);
      List<ContestListDTO> contestListDTOList = contestService.
          getContestListDTOList(contestCondition, pageInfo);
      for (ContestListDTO contestListDTO : contestListDTOList) {
        if (contestListDTO.getType() == ContestType.INHERIT.ordinal()) {
          ContestDTO contestDTO = contestService.getContestDTOByContestId(contestListDTO.getParentId());
          contestListDTO.setParentType(contestDTO.getType());
          contestListDTO.setParentTypeName(ContestType.values()[contestDTO.getType()].getDescription());
        }
      }

      json.put("pageInfo", pageInfo);
      json.put("result", "success");
      json.put("list", contestListDTOList);
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
  Map<String, Object> operator(@PathVariable("id") String targetId,
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
  Map<String, Object> edit(@RequestBody @Valid ContestEditDTO contestEditDTO,
                           BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        if (StringUtil.trimAllSpace(contestEditDTO.getTitle()).equals("")) {
          throw new FieldException("title", "Please enter a validate title.");
        }
        if (contestEditDTO.getType() == ContestType.PRIVATE.ordinal()) {
          if (!contestEditDTO.getPassword().equals(contestEditDTO.getPasswordRepeat())) {
            throw new FieldException("newPasswordRepeat", "Password do not match.");
          }
        } else if (contestEditDTO.getType() == ContestType.INHERIT.ordinal()) {
          if (contestEditDTO.getParentId() == null) {
            throw new FieldException("parentId", "Please enter parent contest's id.");
          }
          if (!contestService.checkContestExists(contestEditDTO.getContestId())) {
            throw new FieldException("parentId", "Contest not exists.");
          }
        }

        ContestDTO contestDTO;
        if (contestEditDTO.getAction().compareTo("new") == 0) {
          Integer contestId = contestService.createNewContest();
          contestDTO = contestService.getContestDTOByContestId(contestId);
          if (contestDTO == null
              || contestDTO.getContestId().compareTo(contestId) != 0) {
            throw new AppException("Error while creating contest.");
          }
          if (contestEditDTO.getDescription() == null) {
            contestEditDTO.setDescription("");
          }
          // Move pictures
          String oldDirectory = "contest/new/";
          String newDirectory = "contest/" + contestId + "/";
          contestEditDTO.setDescription(pictureService.modifyPictureLocation(
              contestEditDTO.getDescription(), oldDirectory, newDirectory
          ));
        } else {
          contestDTO = contestService.getContestDTOByContestId(contestEditDTO.getContestId());
          if (contestDTO == null) {
            throw new AppException("No such contest.");
          }
        }

        // Parser contest problem list
        List<Integer> problemIdList = new LinkedList<>();
        // Split problem list
        String[] problemList = contestEditDTO.getProblemList().split(",");
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
        contestProblemService.removeContestProblemByContestId(contestDTO.getContestId());
        // Add new contest problems
        for (int order = 0; order < problemIdList.size(); order++) {
          Integer contestProblemId = contestProblemService.createNewContestProblem(
              ContestProblemDTO.builder()
                  .setContestId(contestDTO.getContestId())
                  .setOrder(order)
                  .setProblemId(problemIdList.get(order))
                  .build()
          );

          // Check problem added success.
          ContestProblemDTO contestProblemDTO = contestProblemService.getContestProblemDTO(contestProblemId);
          AppExceptionUtil.assertNotNull(contestProblemDTO);
          AppExceptionUtil.assertNotNull(contestProblemDTO.getContestProblemId());
        }

        contestDTO.setType(contestEditDTO.getType());
        if (contestEditDTO.getType() == ContestType.PRIVATE.ordinal()) {
          contestDTO.setPassword(contestEditDTO.getPassword());
        } else if (contestEditDTO.getType() == ContestType.INHERIT.ordinal()) {
          contestDTO.setParentId(contestEditDTO.getParentId());
        }
        contestDTO.setDescription(contestEditDTO.getDescription());
        contestDTO.setTitle(contestEditDTO.getTitle());
        contestDTO.setLength(
            contestEditDTO.getLengthDays() * 24 * 60 * 60 +
                contestEditDTO.getLengthHours() * 60 * 60 +
                contestEditDTO.getLengthMinutes() * 60
        );
        contestDTO.setTime(contestEditDTO.getTime());

        contestService.updateContest(contestDTO);
        json.put("result", "success");
        json.put("contestId", contestDTO.getContestId());
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

  @RequestMapping(value = "createContestByArchiveFile",
      method = RequestMethod.POST)
  @LoginPermit(AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> createContestByArchiveFile(
      @RequestParam(value = "uploadFile", required = true) MultipartFile[] files) {
    Map<String, Object> json = new HashMap<>();
    try {
      FileInformationDTO fileInformationDTO = fileService.uploadContestArchive(
          FileUploadDTO.builder()
              .setFiles(Arrays.asList(files))
              .build()
      );
      ContestDTO contestDTO = contestImporterService.parseContestZipArchive(fileInformationDTO);
      json.put("success", "true");
      json.put("contestId", contestDTO.getContestId());
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
      @RequestParam(value = "uploadFile", required = true) MultipartFile[] files) {
    Map<String, Object> json = new HashMap<>();
    try {
      FileInformationDTO fileInformationDTO = fileService.uploadContestArchive(
          FileUploadDTO.builder()
              .setFiles(Arrays.asList(files))
              .build()
      );

      File csvFile = new File(settings.UPLOAD_FOLDER + fileInformationDTO.getFileName());
      List<OnsiteUserDTO> result = CSVUtil.parseArray(csvFile, OnsiteUserDTO.class);
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
  Map<String, Object> updateOnsiteUser(@RequestBody ContestEditDTO contestEditDTO) {
    Map<String, Object> json = new HashMap<>();
    try {
      Integer contestId = contestEditDTO.getContestId();
      // Remove old users
      contestUserService.removeContestUsersByContestId(contestId);
      // Add new users
      List<Integer> newUsersIDList = userService.createOnsiteUsersByUserList(contestEditDTO.getUserList());
      for (Integer userID : newUsersIDList) {
        contestUserService.createNewContestUser(
            ContestUserDTO.Builder()
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
  Map<String, Object> fetchAllOnsiteUsers(@PathVariable("contestId") Integer contestId) {
    Map<String, Object> json = new HashMap<>();
    try {
      List<UserDTO> result = userService.fetchAllOnsiteUsersByContestId(contestId);
      json.put("result", "success");
      json.put("list", result);
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }
}
