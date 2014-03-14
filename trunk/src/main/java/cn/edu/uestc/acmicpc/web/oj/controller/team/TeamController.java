package cn.edu.uestc.acmicpc.web.oj.controller.team;

import cn.edu.uestc.acmicpc.db.condition.impl.TeamCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TeamUserCondition;
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
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

  @Autowired
  public TeamController(TeamService teamService, UserService userService,
                        TeamUserService teamUserService, MessageService messageService) {
    this.teamService = teamService;
    this.userService = userService;
    this.teamUserService = teamUserService;
    this.messageService = messageService;
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
            Global.RECORD_PER_PAGE, null);

        json.put("pageInfo", pageInfo);
        json.put("list", getTeamListDTO(teamCondition, pageInfo, session));
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
      String userList[] = teamEditDTO.getMemberList().split(",");
      List<Integer> userIdList = new LinkedList<>();
      for (String userIdString : userList) {
        if (userIdString.length() == 0) {
          continue;
        }
        Integer userId;
        try {
          userId = Integer.parseInt(userIdString);
        } catch (NumberFormatException e) {
          throw new AppException("User format error.");
        }

        // Check user exists.
        AppExceptionUtil.assertTrue(userService.checkUserExists(userId));
        userIdList.add(userId);
      }
      if (userIdList.size() < 1 || userIdList.size() > 3) {
        throw new AppException("Member number should between 1 and 3.");
      }
      for (Integer id1 = 0; id1 < userIdList.size(); id1++) {
        for (Integer id2 = id1 + 1; id2 < userIdList.size(); id2++) {
          if (userIdList.get(id1).compareTo(userIdList.get(id2)) == 0) {
            throw new AppException("You can not add the same member twice");
          }
        }
      }
      UserDTO userDTO = getCurrentUser(session);
      if (userIdList.get(0).compareTo(userDTO.getUserId()) != 0) {
        throw new AppException("You are not the team leader.");
      }

      Integer teamId = teamService.createNewTeam(teamEditDTO.getTeamName(), userIdList.get(0));
      TeamDTO teamDTO = teamService.getTeamDTOByTeamId(teamId);
      if (teamDTO == null || teamDTO.getTeamId().compareTo(teamId) != 0
          || teamDTO.getLeaderId().compareTo(userIdList.get(0)) != 0) {
        throw new AppException("Error while creating team.");
      }
      for (Integer userId : userIdList) {
        UserDTO memberDTO = userService.getUserDTOByUserId(userId);
        AppExceptionUtil.assertNotNull(memberDTO, "No such user.");
        Integer teamUserId = teamUserService.createNewTeamUser(TeamUserDTO.builder()
            .setTeamId(teamId)
            .setUserId(userId)
            .setAllow(userId.equals(userDTO.getUserId()))
            .build());
        TeamUserDTO teamUserDTO = teamUserService.getTeamUserDTO(teamUserId);
        // Send a notification
        if (memberDTO.getUserId().compareTo(userDTO.getUserId()) != 0) {
          messageService.sendTeamInvitation(userDTO, memberDTO, teamDTO);
        }
        AppExceptionUtil.assertNotNull(teamUserDTO, "Error while creating team user.");
      }

      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

}
