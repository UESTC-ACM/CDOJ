package cn.edu.uestc.acmicpc.web.oj.controller.team;

import cn.edu.uestc.acmicpc.db.condition.impl.TeamCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TeamUserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDto;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamDto;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamEditDto;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.service.iface.MessageService;
import cn.edu.uestc.acmicpc.service.iface.TeamService;
import cn.edu.uestc.acmicpc.service.iface.TeamUserService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.helper.ArrayUtil;
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

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/team")
public class TeamController extends BaseController {

  private final TeamService teamService;
  private final UserService userService;
  private final TeamUserService teamUserService;
  private final MessageService messageService;
  private final Settings settings;

  @Autowired
  public TeamController(TeamService teamService, UserService userService,
      TeamUserService teamUserService, MessageService messageService,
      Settings settings) {
    this.teamService = teamService;
    this.userService = userService;
    this.teamUserService = teamUserService;
    this.messageService = messageService;
    this.settings = settings;
  }

  @RequestMapping("typeAHeadItem/{teamName}")
  @LoginPermit(NeedLogin = true)
  public @ResponseBody Map<String, Object> typeAHeadItem(@PathVariable("teamName") String teamName,
      HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      Integer teamId = teamService.getTeamIdByTeamName(teamName);
      TeamCondition teamCondition = new TeamCondition();
      teamCondition.teamId = teamId;
      PageInfo pageInfo = buildPageInfo(1L, 1L, 1L, null);
      List<TeamListDto> teamList = getTeamListDto(teamCondition, pageInfo, session);
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
  @LoginPermit(NeedLogin = true)
  public @ResponseBody Map<String, Object> typeAHeadSearch(@RequestBody TeamCondition teamCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (teamCondition.teamName == null) {
        teamCondition.teamName = "";
      }
      // Search teams
      Long count = teamService.count(teamCondition);
      PageInfo pageInfo = buildPageInfo(count, 1L,
          6L, null);
      json.put("list", teamService.getTeamTypeAHeadList(teamCondition, pageInfo));
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  private List<TeamListDto> getTeamListDto(TeamCondition teamCondition, PageInfo pageInfo,
      HttpSession session) throws AppException {
    List<TeamListDto> teamList = teamService.getTeamList(teamCondition, pageInfo);

    if (teamList.size() == 0) {
      return teamList;
    }

    // At most 20 records
    List<Integer> teamIdList = new LinkedList<>();
    for (TeamListDto teamListDto : teamList) {
      teamIdList.add(teamListDto.getTeamId());
    }
    TeamUserCondition teamUserCondition = new TeamUserCondition();
    teamUserCondition.orderFields = "id";
    teamUserCondition.orderAsc = "true";
    teamUserCondition.teamIdList = ArrayUtil.join(teamIdList.toArray(), ",");
    // Search team users
    List<TeamUserListDto> teamUserList = teamUserService.getTeamUserList(teamUserCondition);

    // Put users into teams
    for (TeamListDto teamListDto : teamList) {
      teamListDto.setTeamUsers(new LinkedList<TeamUserListDto>());
      teamListDto.setInvitedUsers(new LinkedList<TeamUserListDto>());
      for (TeamUserListDto teamUserListDto : teamUserList) {
        if (teamListDto.getTeamId().compareTo(teamUserListDto.getTeamId()) == 0) {
          // Put users into current users / inactive users
          if (teamUserListDto.getAllow()) {
            teamListDto.getTeamUsers().add(teamUserListDto);
          } else if (checkPermission(session, teamCondition.userId)) {
            teamListDto.getInvitedUsers().add(teamUserListDto);
          }

          if (checkPermission(session, teamCondition.userId)
              && teamUserListDto.getUserId().equals(teamCondition.userId)) {
            teamListDto.setAllow(teamUserListDto.getAllow());
          }
        }
      }
    }
    return teamList;
  }

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> search(@RequestBody TeamCondition teamCondition,
      HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (teamCondition.userId != null) {
        if (!checkPermission(session, teamCondition.userId)) {
          teamCondition.allow = true;
        }
        // Search teams
        Long count = teamService.count(teamCondition);
        PageInfo pageInfo = buildPageInfo(count, teamCondition.currentPage,
            settings.RECORD_PER_PAGE, null);

        json.put("pageInfo", pageInfo);
        json.put("list", getTeamListDto(teamCondition, pageInfo, session));
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
  @LoginPermit(NeedLogin = true)
  public @ResponseBody Map<String, Object> changeAllowState(@PathVariable("userId") Integer userId,
      @PathVariable("teamId") Integer teamId,
      @PathVariable("value") Boolean value,
      HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      AppExceptionUtil.assertTrue(checkPermission(session, userId));
      TeamDto teamDto = teamService.getTeamDtoByTeamId(teamId);
      if (teamDto.getLeaderId().equals(userId)) {
        throw new AppException("Can not change team leader's state.");
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
  @LoginPermit(NeedLogin = true)
  public @ResponseBody Map<String, Object> deleteTeam(@RequestBody TeamEditDto teamEditDto,
      HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      TeamDto teamDto = teamService.getTeamDtoByTeamId(teamEditDto.getTeamId());
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
  @LoginPermit(NeedLogin = true)
  public @ResponseBody Map<String, Object> createTeam(@RequestBody TeamEditDto teamEditDto,
      HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      AppExceptionUtil.assertNotNull(teamEditDto.getTeamName(), "Please input a valid team name.");
      teamEditDto.setTeamName(StringUtil.trimAllSpace(teamEditDto.getTeamName()));
      AppExceptionUtil.assertTrue(
          teamEditDto.getTeamName().length() > 0 && teamEditDto.getTeamName().length() < 50
          , "Please input a valid team name.");
      if (teamService.checkTeamExists(teamEditDto.getTeamName())) {
        throw new AppException("Team name has been used!");
      }

      UserDto currentUser = getCurrentUser(session);
      Integer teamId = teamService
          .createNewTeam(teamEditDto.getTeamName(), currentUser.getUserId());
      TeamDto teamDto = teamService.getTeamDtoByTeamId(teamId);
      if (teamDto == null) {
        throw new AppException("Error while creating team.");
      }

      Integer teamUserId = teamUserService.createNewTeamUser(TeamUserDto.builder()
          .setTeamId(teamId)
          .setUserId(currentUser.getUserId())
          .setAllow(true)
          .build());
      TeamUserDto teamUserDto = teamUserService.getTeamUserDto(teamUserId);
      AppExceptionUtil.assertNotNull(teamUserDto, "Error while creating team user.");

      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("removeMember")
  @LoginPermit(NeedLogin = true)
  public @ResponseBody Map<String, Object> removeMember(@RequestBody TeamEditDto teamEditDto,
      HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      TeamDto teamDto = teamService.getTeamDtoByTeamId(teamEditDto.getTeamId());
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
  @LoginPermit(NeedLogin = true)
  public @ResponseBody Map<String, Object> addMember(@RequestBody TeamEditDto teamEditDto,
      HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      TeamDto teamDto = teamService.getTeamDtoByTeamId(teamEditDto.getTeamId());
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
