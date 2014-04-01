package cn.edu.uestc.acmicpc.web.oj.controller.team;

import cn.edu.uestc.acmicpc.db.condition.impl.TeamCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TeamUserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
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

  private TeamService teamService;
  private UserService userService;
  private TeamUserService teamUserService;
  private MessageService messageService;
  private Settings settings;

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
  public
  @ResponseBody
  Map<String, Object> typeAHeadItem(@PathVariable("teamName") String teamName,
                                    HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      Integer teamId = teamService.getTeamIdByTeamName(teamName);
      TeamCondition teamCondition = new TeamCondition();
      teamCondition.teamId = teamId;
      PageInfo pageInfo = buildPageInfo(1L, 1L, 1L, null);
      List<TeamListDTO> teamList = getTeamListDTO(teamCondition, pageInfo, session);
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
  public
  @ResponseBody
  Map<String, Object> typeAHeadSearch(@RequestBody TeamCondition teamCondition) {
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

  private List<TeamListDTO> getTeamListDTO(TeamCondition teamCondition, PageInfo pageInfo,
                                           HttpSession session) throws AppException {
    List<TeamListDTO> teamList = teamService.getTeamList(teamCondition, pageInfo);

    if (teamList.size() == 0) {
      return teamList;
    }

    // At most 20 records
    List<Integer> teamIdList = new LinkedList<>();
    for (TeamListDTO teamListDTO : teamList) {
      teamIdList.add(teamListDTO.getTeamId());
    }
    TeamUserCondition teamUserCondition = new TeamUserCondition();
    teamUserCondition.orderFields = "id";
    teamUserCondition.orderAsc = "true";
    teamUserCondition.teamIdList = ArrayUtil.join(teamIdList.toArray(), ",");
    // Search team users
    List<TeamUserListDTO> teamUserList = teamUserService.getTeamUserList(teamUserCondition);

    // Put users into teams
    for (TeamListDTO teamListDTO : teamList) {
      teamListDTO.setTeamUsers(new LinkedList<TeamUserListDTO>());
      teamListDTO.setInvitedUsers(new LinkedList<TeamUserListDTO>());
      for (TeamUserListDTO teamUserListDTO : teamUserList) {
        if (teamListDTO.getTeamId().compareTo(teamUserListDTO.getTeamId()) == 0) {
          // Put users into current users / inactive users
          if (teamUserListDTO.getAllow()) {
            teamListDTO.getTeamUsers().add(teamUserListDTO);
          } else if (checkPermission(session, teamCondition.userId)) {
            teamListDTO.getInvitedUsers().add(teamUserListDTO);
          }

          if (checkPermission(session, teamCondition.userId) && teamUserListDTO.getUserId().equals(teamCondition.userId)) {
            teamListDTO.setAllow(teamUserListDTO.getAllow());
          }
        }
      }
    }
    return teamList;
  }

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> search(@RequestBody TeamCondition teamCondition,
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
        json.put("list", getTeamListDTO(teamCondition, pageInfo, session));
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
  public
  @ResponseBody
  Map<String, Object> changeAllowState(@PathVariable("userId") Integer userId,
                                       @PathVariable("teamId") Integer teamId,
                                       @PathVariable("value") Boolean value,
                                       HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      AppExceptionUtil.assertTrue(checkPermission(session, userId));
      TeamDTO teamDTO = teamService.getTeamDTOByTeamId(teamId);
      if (teamDTO.getLeaderId().equals(userId)) {
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
  public
  @ResponseBody
  Map<String, Object> deleteTeam(@RequestBody TeamEditDTO teamEditDTO,
                                 HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      TeamDTO teamDTO = teamService.getTeamDTOByTeamId(teamEditDTO.getTeamId());
      if (teamDTO == null) {
        throw new AppException("Team not found.");
      }
      if (!checkPermission(session, teamDTO.getLeaderId())) {
        throw new AppException("Permission denied");
      }
      UserDTO currentUser = getCurrentUser(session);

      List<TeamUserListDTO> teamUserList = teamUserService.getTeamUserList(teamDTO.getTeamId());
      for (TeamUserListDTO teamUser : teamUserList) {
        if (currentUser.getUserId().equals(teamUser.getUserId())) {
          continue;
        }
        messageService.createNewMessage(MessageDTO.builder()
            .setSenderId(currentUser.getUserId())
            .setReceiverId(teamUser.getUserId())
            .setIsOpened(false)
            .setTime(new Timestamp(System.currentTimeMillis()))
            .setTitle("Team " + teamDTO.getTeamName() + " has been fallen out.")
            .setContent("Team " + teamDTO.getTeamName() + " has been fallen out by " + StringUtil.getAtLink(currentUser.getUserName()) + ".")
            .build()
        );
      }
      teamService.deleteTeam(teamDTO);
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("createTeam")
  @LoginPermit(NeedLogin = true)
  public
  @ResponseBody
  Map<String, Object> createTeam(@RequestBody TeamEditDTO teamEditDTO,
                                 HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      AppExceptionUtil.assertNotNull(teamEditDTO.getTeamName(), "Please input a valid team name.");
      teamEditDTO.setTeamName(StringUtil.trimAllSpace(teamEditDTO.getTeamName()));
      AppExceptionUtil.assertTrue(
          teamEditDTO.getTeamName().length() > 0 && teamEditDTO.getTeamName().length() < 50
          , "Please input a valid team name.");
      if (teamService.checkTeamExists(teamEditDTO.getTeamName())) {
        throw new AppException("Team name has been used!");
      }

      UserDTO currentUser = getCurrentUser(session);
      Integer teamId = teamService.createNewTeam(teamEditDTO.getTeamName(), currentUser.getUserId());
      TeamDTO teamDTO = teamService.getTeamDTOByTeamId(teamId);
      if (teamDTO == null) {
        throw new AppException("Error while creating team.");
      }

      Integer teamUserId = teamUserService.createNewTeamUser(TeamUserDTO.builder()
          .setTeamId(teamId)
          .setUserId(currentUser.getUserId())
          .setAllow(true)
          .build());
      TeamUserDTO teamUserDTO = teamUserService.getTeamUserDTO(teamUserId);
      AppExceptionUtil.assertNotNull(teamUserDTO, "Error while creating team user.");

      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("removeMember")
  @LoginPermit(NeedLogin = true)
  public
  @ResponseBody
  Map<String, Object> removeMember(@RequestBody TeamEditDTO teamEditDTO,
                                   HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      TeamDTO teamDTO = teamService.getTeamDTOByTeamId(teamEditDTO.getTeamId());
      if (teamDTO == null) {
        throw new AppException("Team not found.");
      }
      if (!checkPermission(session, teamDTO.getLeaderId())) {
        throw new AppException("Permission denied");
      }
      List<TeamUserListDTO> teamUserList = teamUserService.getTeamUserList(teamDTO.getTeamId());
      if (teamUserList.size() == 1) {
        throw new AppException("Member number should between 1 and 3.");
      }
      Integer userId;
      try {
        userId = Integer.parseInt(teamEditDTO.getMemberList());
      } catch (NumberFormatException e) {
        throw new AppException("User format error.");
      }
      UserDTO userDTO = userService.getUserDTOByUserId(userId);
      // Check user exists.
      AppExceptionUtil.assertNotNull(userDTO, "User not found!");

      for (TeamUserListDTO teamUser : teamUserList) {
        if (teamUser.getUserId().equals(userId)) {
          teamUserService.removeTeamUser(teamUser.getTeamUserId());

          // Send a notification
          UserDTO currentUser = getCurrentUser(session);

          String userCenterUrl = settings.HOST
              + "/#/user/center/" + userDTO.getUserName() + "/teams";
          StringBuilder messageContent = new StringBuilder();
          messageContent.append(StringUtil.getAtLink(currentUser.getUserName()))
              .append(" has remove you from team ")
              .append(teamDTO.getTeamName())
              .append(".\n\n")
              .append("See [your teams](")
              .append(userCenterUrl)
              .append(") for more details.");
          Integer messageId = messageService.createNewMessage(MessageDTO.builder()
              .setSenderId(currentUser.getUserId())
              .setReceiverId(userDTO.getUserId())
              .setTime(new Timestamp(System.currentTimeMillis()))
              .setIsOpened(false)
              .setTitle("Team notification.")
              .setContent(messageContent.toString())
              .build());
          MessageDTO messageDTO = messageService.getMessageDTO(messageId);
          AppExceptionUtil.assertNotNull(messageDTO, "Error while sending notification.");
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
  public
  @ResponseBody
  Map<String, Object> addMember(@RequestBody TeamEditDTO teamEditDTO,
                                HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      TeamDTO teamDTO = teamService.getTeamDTOByTeamId(teamEditDTO.getTeamId());
      if (teamDTO == null) {
        throw new AppException("Team not found.");
      }
      if (!checkPermission(session, teamDTO.getLeaderId())) {
        throw new AppException("Permission denied");
      }
      List<TeamUserListDTO> teamUserList = teamUserService.getTeamUserList(teamDTO.getTeamId());
      if (teamUserList.size() == 3) {
        throw new AppException("Member number should between 1 and 3.");
      }
      Integer userId;
      try {
        userId = Integer.parseInt(teamEditDTO.getMemberList());
      } catch (NumberFormatException e) {
        throw new AppException("User format error.");
      }
      UserDTO userDTO = userService.getUserDTOByUserId(userId);
      // Check user exists.
      AppExceptionUtil.assertNotNull(userDTO, "User not found!");

      for (TeamUserListDTO teamUser : teamUserList) {
        if (teamUser.getUserId().equals(userId)) {
          throw new AppException("You can not add the same member twice");
        }
      }

      Integer teamUserId = teamUserService.createNewTeamUser(TeamUserDTO.builder()
          .setTeamId(teamDTO.getTeamId())
          .setUserId(userId)
          .setAllow(false)
          .build());
      TeamUserDTO teamUserDTO = teamUserService.getTeamUserDTO(teamUserId);
      AppExceptionUtil.assertNotNull(teamUserDTO, "Error while creating team user.");
      // Send a notification
      UserDTO currentUser = getCurrentUser(session);

      String userCenterUrl = settings.HOST
          + "/#/user/center/" + userDTO.getUserName() + "/teams";
      StringBuilder messageContent = new StringBuilder();
      messageContent.append(StringUtil.getAtLink(currentUser.getUserName()))
          .append(" has invited you to join team ")
          .append(teamDTO.getTeamName())
          .append(".\n\n")
          .append("See [your teams](")
          .append(userCenterUrl)
          .append(") for more details.");
      Integer messageId = messageService.createNewMessage(MessageDTO.builder()
          .setSenderId(currentUser.getUserId())
          .setReceiverId(userDTO.getUserId())
          .setTime(new Timestamp(System.currentTimeMillis()))
          .setIsOpened(false)
          .setTitle("Team invitation.")
          .setContent(messageContent.toString())
          .build());
      MessageDTO messageDTO = messageService.getMessageDTO(messageId);
      AppExceptionUtil.assertNotNull(messageDTO, "Error while sending notification.");

      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

}
