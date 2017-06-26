package cn.edu.uestc.acmicpc.web.oj.controller.team;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestTeamCondition;
import cn.edu.uestc.acmicpc.db.criteria.TeamCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.ContestFields;
import cn.edu.uestc.acmicpc.db.dto.field.TeamFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDto;
import cn.edu.uestc.acmicpc.db.dto.impl.MessageDto;
import cn.edu.uestc.acmicpc.db.dto.impl.TeamDto;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contestteam.ContestTeamListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDto;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.service.iface.ContestTeamService;
import cn.edu.uestc.acmicpc.service.iface.MessageService;
import cn.edu.uestc.acmicpc.service.iface.TeamService;
import cn.edu.uestc.acmicpc.service.iface.TeamUserService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.enums.ContestRegistryStatusType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import com.google.common.collect.Lists;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/team")
public class TeamController extends BaseController {

  private final TeamService teamService;
  private final UserService userService;
  private final TeamUserService teamUserService;
  private final MessageService messageService;
  private final ContestTeamService contestTeamService;
  private final ContestService contestService;
  private final Settings settings;

  @Autowired
  public TeamController(
      TeamService teamService,
      UserService userService,
      TeamUserService teamUserService,
      MessageService messageService,
      ContestTeamService contestTeamService,
      ContestService contestService,
      Settings settings) {
    this.teamService = teamService;
    this.userService = userService;
    this.teamUserService = teamUserService;
    this.messageService = messageService;
    this.contestTeamService = contestTeamService;
    this.contestService = contestService;
    this.settings = settings;
  }

  @RequestMapping("typeAHeadItem/{teamName}")
  @LoginPermit()
  @ResponseBody
  public Map<String, Object> typeAHeadItem(@PathVariable("teamName") String teamName,
                                           HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      Integer teamId = teamService.getTeamDtoByTeamName(teamName, TeamFields.SUMMARY_FIELDS)
          .getTeamId();
      TeamCriteria criteria = new TeamCriteria();
      criteria.teamId = teamId;
      PageInfo pageInfo = buildPageInfo(1L, 1L, 1L, null);
      List<TeamDto> teamList = getTeamListDto(criteria, pageInfo, session);
      if (teamList == null || teamList.size() != 1) {
        throw new AppException("Fetch team error.");
      }
      json.put("team", teamList.get(0));
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("typeAHeadSearch")
  @LoginPermit()
  @ResponseBody
  public Map<String, Object> typeAHeadSearch(@RequestBody TeamCriteria criteria) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (criteria.teamName == null) {
        criteria.teamName = "";
      }
      // Search teams
      Long count = teamService.count(criteria);
      PageInfo pageInfo = buildPageInfo(count, 1L, 6L, null);
      json.put("list", teamService.getTeams(criteria, pageInfo, TeamFields.SUMMARY_FIELDS));
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  private List<TeamDto> getTeamListDto(TeamCriteria criteria, PageInfo pageInfo,
                                       HttpSession session) throws AppException {
    List<TeamDto> teamList = teamService.getTeams(criteria, pageInfo,
        TeamFields.FIELDS_FOR_LIST_PAGE);

    if (teamList.size() == 0) {
      return teamList;
    }

    for (TeamDto team : teamList) {
      if (checkPermission(session, criteria.userId)) {
        for (TeamUserListDto teamUser : team.getTeamUsers()) {
          if (teamUser.getUserId().equals(criteria.userId)) {
            team.setAllow(teamUser.getAllow());
          }
        }
      } else {
        // Hide invited users when no permission.
        team.setInvitedUsers(Lists.newLinkedList());
      }
    }
    return teamList;
  }

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  @ResponseBody
  public Map<String, Object> search(
      @RequestBody TeamCriteria criteria,
      HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (criteria.userId != null) {
        if (!checkPermission(session, criteria.userId)) {
          criteria.allow = true;
        }
        // Search teams
        Long count = teamService.count(criteria);
        PageInfo pageInfo = buildPageInfo(count, criteria.currentPage,
            settings.RECORD_PER_PAGE, null);

        json.put("pageInfo", pageInfo);
        json.put("list", getTeamListDto(criteria, pageInfo, session));
      } else {
        PageInfo pageInfo = buildPageInfo(0L, 1L,
            settings.RECORD_PER_PAGE, null);
        json.put("list", new LinkedList<>());
        json.put("pageInfo", pageInfo);
      }
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("changeAllowState/{userId}/{teamId}/{value}")
  @LoginPermit()
  @ResponseBody
  public Map<String, Object> changeAllowState(
      @PathVariable("userId") Integer userId,
      @PathVariable("teamId") Integer teamId,
      @PathVariable("value") Boolean value,
      HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      AppExceptionUtil.assertTrue(checkPermission(session, userId));
      TeamDto teamDto = teamService.getTeamDtoByTeamId(teamId, TeamFields.BASIC_FIELDS);
      if (teamDto.getLeaderId().equals(userId)) {
        throw new AppException("Can not change team leader's state.");
      }
      // A workaround to avoid team member changes before contest start.
      ContestTeamCondition contestTeamCondition = new ContestTeamCondition();
      contestTeamCondition.teamId = teamDto.getTeamId();
      List<ContestTeamListDto> contestTeams =
          contestTeamService.getContestTeamList(contestTeamCondition, null);
      for (ContestTeamListDto contestTeam : contestTeams) {
        if (contestTeam.getStatus() == ContestRegistryStatusType.ACCEPTED.ordinal()) {
          ContestDto contest = contestService.getContestDtoByContestId(
              contestTeam.getContestTeamId(), ContestFields.FIELDS_FOR_LIST_PAGE);
          if (!contest.getStatus().equals("Ended")) {
            String action = value ? "join" : "quit";
            throw new AppException("Can not " + action + " this team until " + contest.getTitle()
                + " ended (contestId: " + contest.getContestId() + ").");
          }
        }
      }
      teamUserService.changeAllowState(userId, teamId, value);
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("deleteTeam")
  @LoginPermit()
  @ResponseBody
  public Map<String, Object> deleteTeam(@RequestBody TeamDto teamEditDto, HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      TeamDto teamDto = teamService.getTeamDtoByTeamId(teamEditDto.getTeamId(),
          TeamFields.BASIC_FIELDS);
      if (teamDto == null) {
        throw new AppException("Team not found.");
      }
      if (!checkPermission(session, teamDto.getLeaderId())) {
        throw new AppException("Permission denied");
      }
      UserDto currentUser = getCurrentUser(session);

      List<TeamUserListDto> teamUserList = teamUserService.getTeamUserList(teamDto.getTeamId());
      for (TeamUserListDto teamUser : teamUserList) {
        if (currentUser.getUserId().equals(teamUser.getUserId())) {
          continue;
        }
        messageService.createNewMessage(MessageDto
            .builder()
            .setSenderId(currentUser.getUserId())
            .setReceiverId(teamUser.getUserId())
            .setIsOpened(false)
            .setTime(new Timestamp(System.currentTimeMillis()))
            .setTitle("Team " + teamDto.getTeamName() + " has been fallen out.")
            .setContent(
                "Team " + teamDto.getTeamName() + " has been fallen out by "
                    + StringUtil.getAtLink(currentUser.getUserName()) + ".")
            .build()
        );
      }
      teamService.deleteTeam(teamDto);
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("createTeam")
  @LoginPermit()
  public
  @ResponseBody
  Map<String, Object> createTeam(
      @RequestBody TeamDto teamEditDto, HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      AppExceptionUtil.assertNotNull(teamEditDto.getTeamName(), "Please input a valid team name.");
      teamEditDto.setTeamName(StringUtil.trimAllSpace(teamEditDto.getTeamName()));
      AppExceptionUtil.assertTrue(
          teamEditDto.getTeamName().length() > 0 && teamEditDto.getTeamName().length() < 50,
          "Please input a valid team name.");
      if (teamService.checkTeamExists(teamEditDto.getTeamName())) {
        throw new AppException("Team name has been used!");
      }

      UserDto currentUser = getCurrentUser(session);
      Integer teamId = teamService
          .createNewTeam(teamEditDto.getTeamName(), currentUser.getUserId());
      TeamDto teamDto = teamService.getTeamDtoByTeamId(teamId, TeamFields.BASIC_FIELDS);
      if (teamDto == null) {
        throw new AppException("Error while creating team.");
      }

      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("removeMember")
  @LoginPermit()
  @ResponseBody
  public Map<String, Object> removeMember(@RequestBody TeamDto teamEditDto, HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      TeamDto teamDto = teamService.getTeamDtoByTeamId(teamEditDto.getTeamId(),
          TeamFields.BASIC_FIELDS);
      if (teamDto == null) {
        throw new AppException("Team not found.");
      }
      if (!checkPermission(session, teamDto.getLeaderId())) {
        throw new AppException("Permission denied");
      }
      List<TeamUserListDto> teamUserList = teamUserService.getTeamUserList(teamDto.getTeamId());
      if (teamUserList.size() == 1) {
        throw new AppException("Member number should between 1 and 3.");
      }
      Integer userId;
      try {
        userId = Integer.parseInt(teamEditDto.getMemberList());
      } catch (NumberFormatException e) {
        throw new AppException("User format error.");
      }
      UserDto userDto = userService.getUserDtoByUserId(userId);
      // Check user exists.
      AppExceptionUtil.assertNotNull(userDto, "User not found!");

      for (TeamUserListDto teamUser : teamUserList) {
        if (teamUser.getUserId().equals(userId)) {
          teamUserService.removeTeamUser(teamUser.getTeamUserId());

          // Send a notification
          UserDto currentUser = getCurrentUser(session);

          String userCenterUrl = settings.HOST
              + "/#/user/center/" + userDto.getUserName() + "/teams";
          StringBuilder messageContent = new StringBuilder();
          messageContent.append(StringUtil.getAtLink(currentUser.getUserName()))
              .append(" has remove you from team ")
              .append(teamDto.getTeamName())
              .append(".\n\n")
              .append("See [your teams](")
              .append(userCenterUrl)
              .append(") for more details.");
          Integer messageId = messageService.createNewMessage(MessageDto.builder()
              .setSenderId(currentUser.getUserId())
              .setReceiverId(userDto.getUserId())
              .setTime(new Timestamp(System.currentTimeMillis()))
              .setIsOpened(false)
              .setTitle("Team notification.")
              .setContent(messageContent.toString())
              .build());
          MessageDto messageDto = messageService.getMessageDto(messageId);
          AppExceptionUtil.assertNotNull(messageDto, "Error while sending notification.");
        }
      }

      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("addMember")
  @LoginPermit()
  @ResponseBody
  public Map<String, Object> addMember(
      @RequestBody TeamDto teamEditDto, HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      TeamDto teamDto = teamService.getTeamDtoByTeamId(teamEditDto.getTeamId(),
          TeamFields.BASIC_FIELDS);
      if (teamDto == null) {
        throw new AppException("Team not found.");
      }
      if (!checkPermission(session, teamDto.getLeaderId())) {
        throw new AppException("Permission denied");
      }
      List<TeamUserListDto> teamUserList = teamUserService.getTeamUserList(teamDto.getTeamId());
      if (teamUserList.size() == 3) {
        throw new AppException("Member number should between 1 and 3.");
      }
      Integer userId;
      try {
        userId = Integer.parseInt(teamEditDto.getMemberList());
      } catch (NumberFormatException e) {
        throw new AppException("User format error.");
      }
      UserDto userDto = userService.getUserDtoByUserId(userId);
      // Check user exists.
      AppExceptionUtil.assertNotNull(userDto, "User not found!");

      for (TeamUserListDto teamUser : teamUserList) {
        if (teamUser.getUserId().equals(userId)) {
          throw new AppException("You can not add the same member twice");
        }
      }

      Integer teamUserId = teamUserService.createNewTeamUser(TeamUserDto.builder()
          .setTeamId(teamDto.getTeamId())
          .setUserId(userId)
          .setAllow(false)
          .build());
      TeamUserDto teamUserDto = teamUserService.getTeamUserDto(teamUserId);
      AppExceptionUtil.assertNotNull(teamUserDto, "Error while creating team user.");
      // Send a notification
      UserDto currentUser = getCurrentUser(session);

      String userCenterUrl = settings.HOST
          + "/#/user/center/" + userDto.getUserName() + "/teams";
      StringBuilder messageContent = new StringBuilder();
      messageContent.append(StringUtil.getAtLink(currentUser.getUserName()))
          .append(" has invited you to join team ")
          .append(teamDto.getTeamName())
          .append(".\n\n")
          .append("See [your teams](")
          .append(userCenterUrl)
          .append(") for more details.");
      Integer messageId = messageService.createNewMessage(MessageDto.builder()
          .setSenderId(currentUser.getUserId())
          .setReceiverId(userDto.getUserId())
          .setTime(new Timestamp(System.currentTimeMillis()))
          .setIsOpened(false)
          .setTitle("Team invitation.")
          .setContent(messageContent.toString())
          .build());
      MessageDto messageDto = messageService.getMessageDto(messageId);
      AppExceptionUtil.assertNotNull(messageDto, "Error while sending notification.");

      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

}
