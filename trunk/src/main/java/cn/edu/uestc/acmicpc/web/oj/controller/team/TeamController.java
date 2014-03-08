package cn.edu.uestc.acmicpc.web.oj.controller.team;

import cn.edu.uestc.acmicpc.db.condition.impl.TeamCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TeamUserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.TeamService;
import cn.edu.uestc.acmicpc.service.iface.TeamUserService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.helper.ArrayUtil;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

  @Autowired
  public TeamController(TeamService teamService, UserService userService, TeamUserService teamUserService) {
    this.teamService = teamService;
    this.userService = userService;
    this.teamUserService = teamUserService;
  }

  @RequestMapping("search")
  @LoginPermit(NeedLogin = true)
  public
  @ResponseBody
  Map<String, Object> search(@RequestBody TeamCondition teamCondition,
                             HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      UserDTO userDTO =  getCurrentUser(session);
      teamCondition.userId = userDTO.getUserId();

      Long count = teamService.count(teamCondition);
      PageInfo pageInfo = buildPageInfo(count, teamCondition.currentPage,
          Global.RECORD_PER_PAGE, null);
      List<TeamListDTO> teamList = teamService.getTeamList(teamCondition, pageInfo);
      // At most 20 records
      List<Integer> teamIdList = new LinkedList<>();
      for (TeamListDTO teamListDTO: teamList) {
        teamIdList.add(teamListDTO.getTeamId());
      }
      TeamUserCondition teamUserCondition = new TeamUserCondition();
      teamUserCondition.orderFields = "id";
      teamUserCondition.orderAsc = "true";
      teamUserCondition.teamIdList = ArrayUtil.join(teamIdList.toArray(), ",");
      List<TeamUserListDTO> teamUserList = teamUserService.getTeamUserList(teamUserCondition);
      for (TeamListDTO teamListDTO: teamList) {
        teamListDTO.setTeamUsers(new LinkedList<TeamUserListDTO>());
        for (TeamUserListDTO teamUserListDTO: teamUserList) {
          if (teamListDTO.getTeamId().compareTo(teamUserListDTO.getTeamId()) == 0) {
            teamListDTO.getTeamUsers().add(teamUserListDTO);
          }
        }
      }

      json.put("list", teamList);
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
      if (teamService.checkTeamExists(teamEditDTO.getTeamName())) {
        throw new AppException("Team name has been used!");
      }
      String userList[] = teamEditDTO.getMemberList().split(",");
      List<Integer> userIdList = new LinkedList<>();
      for (String userIdString: userList) {
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
      UserDTO userDTO =  getCurrentUser(session);
      if (userIdList.get(0).compareTo(userDTO.getUserId()) != 0) {
        throw new AppException("You are not the team leader.");
      }

      Integer teamId = teamService.createNewTeam(teamEditDTO.getTeamName(), userIdList.get(0));
      TeamDTO teamDTO = teamService.getTeamDTOByTeamId(teamId);
      if (teamDTO == null || teamDTO.getTeamId().compareTo(teamId) != 0
          || teamDTO.getLeaderId().compareTo(userIdList.get(0)) != 0) {
        throw new AppException("Error while creating team.");
      }
      for (Integer userId: userIdList) {
        Integer teamUserId = teamUserService.createNewTeamUser(TeamUserDTO.builder()
            .setTeamId(teamId)
            .setUserId(userId)
            .build());
        TeamUserDTO teamUserDTO = teamUserService.getTeamUserDTO(teamUserId);
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
