package cn.edu.uestc.acmicpc.web.oj.controller.contest;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.ContestTeamCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TeamUserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDetailDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemSummaryDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamReviewDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.ContestProblemService;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.service.iface.ContestTeamService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.TeamService;
import cn.edu.uestc.acmicpc.service.iface.TeamUserService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.util.helper.ArrayUtil;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.web.rank.RankListBuilder;
import cn.edu.uestc.acmicpc.web.rank.RankListStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
  private PictureService pictureService;
  private ProblemService problemService;
  private StatusService statusService;
  private GlobalService globalService;
  private TeamService teamService;
  private TeamUserService teamUserService;
  private ContestTeamService contestTeamService;

  @Autowired
  public ContestController(ContestService contestService,
                           ContestProblemService contestProblemService,
                           PictureService pictureService,
                           ProblemService problemService,
                           StatusService statusService,
                           GlobalService globalService,
                           TeamService teamService,
                           TeamUserService teamUserService,
                           ContestTeamService contestTeamService) {
    this.contestService = contestService;
    this.contestProblemService = contestProblemService;
    this.pictureService = pictureService;
    this.problemService = problemService;
    this.statusService = statusService;
    this.globalService = globalService;
    this.teamService = teamService;
    this.teamUserService = teamUserService;
    this.contestService = contestService;
    this.contestTeamService = contestTeamService;
  }

  @RequestMapping("registryReview")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> registryReview(@RequestBody ContestTeamReviewDTO contestTeamReviewDTO) {
    Map<String, Object> json = new HashMap<>();
    try {
      ContestTeamDTO contestTeamDTO = contestTeamService.getContestTeamDTO(contestTeamReviewDTO.getContestTeamId());
      contestTeamDTO.setStatus(contestTeamReviewDTO.getStatus());
      contestTeamDTO.setComment(contestTeamReviewDTO.getComment());
      contestTeamService.updateContestTeam(contestTeamDTO);
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
  Map<String, Object> registerStatusList(@RequestBody ContestTeamCondition contestTeamCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      Long count = contestTeamService.count(contestTeamCondition);
      PageInfo pageInfo = buildPageInfo(count, contestTeamCondition.currentPage,
          Global.RECORD_PER_PAGE, null);
      List<ContestTeamListDTO> contestTeamList = contestTeamService.getContestTeamList(
          contestTeamCondition, pageInfo);

      // At most 20 records
      List<Integer> teamIdList = new LinkedList<>();
      for (ContestTeamListDTO team: contestTeamList) {
        teamIdList.add(team.getTeamId());
      }
      TeamUserCondition teamUserCondition = new TeamUserCondition();
      teamUserCondition.orderFields = "id";
      teamUserCondition.orderAsc = "true";
      teamUserCondition.teamIdList = ArrayUtil.join(teamIdList.toArray(), ",");
      // Search team users
      List<TeamUserListDTO> teamUserList = teamUserService.getTeamUserList(teamUserCondition);

      // Put users into teams
      for (ContestTeamListDTO team: contestTeamList) {
        team.setTeamUsers(new LinkedList<TeamUserListDTO>());
        for (TeamUserListDTO teamUserListDTO : teamUserList) {
          if (team.getTeamId().compareTo(teamUserListDTO.getTeamId()) == 0) {
            // Put users into current users / inactive users
            if (teamUserListDTO.getAllow()) {
              team.getTeamUsers().add(teamUserListDTO);
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
      ContestDTO contestDTO = contestService.getContestDTOByContestId(contestId);
      if (contestDTO == null || !contestDTO.getContestId().equals(contestId)) {
        throw new AppException("Contest not found!");
      }
      UserDTO currentUser = getCurrentUser(session);
      if (!currentUser.getUserId().equals(teamDTO.getLeaderId())) {
        throw new AppException("You are not the team leader of team " + teamDTO.getTeamName() + ".");
      }
      List<TeamUserListDTO> teamUserList = teamUserService.getTeamUserList(teamId);
      for (TeamUserListDTO teamUserDTO: teamUserList) {
        if (contestTeamService.whetherUserHasBeenRegistered(teamUserDTO.getUserId(),
            contestDTO.getContestId())) {
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
            if (status.getReturnTypeId() != Global.OnlineJudgeReturnType.OJ_AC.ordinal()) {
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

  @RequestMapping("rankList/{contestId}")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> rankList(@PathVariable("contestId") Integer contestId,
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
      if (contestShowDTO.getStatus().equals("Pending") && !isAdmin(session)) {
        throw new AppException("Contest not start yet.");
      }

      List<ContestProblemSummaryDTO> contestProblemList = contestProblemService.
          getContestProblemSummaryDTOListByContestId(contestId);

      StatusCondition statusCondition = new StatusCondition();
      statusCondition.contestId = contestShowDTO.getContestId();
      statusCondition.isForAdmin = isAdmin(session);
      // Sort by time
      statusCondition.orderFields = "time";
      statusCondition.orderAsc = "true";
      List<StatusListDTO> statusList = statusService.getStatusList(statusCondition);

      RankListBuilder rankListBuilder = new RankListBuilder();
      for (ContestProblemSummaryDTO problem : contestProblemList) {
        rankListBuilder.addRankListProblem(problem.getProblemId().toString());
      }
      for (StatusListDTO status : statusList) {
        if (contestShowDTO.getStartTime().after(status.getTime()) ||
            contestShowDTO.getEndTime().before(status.getTime())) {
          // Out of time.
          continue;
        }
        rankListBuilder.addStatus(new RankListStatus(
            1, // Total tried
            status.getReturnTypeId(), // Return type id
            status.getProblemId().toString(), // Problem id
            status.getUserName(), // User name
            status.getNickName(), // Nick name
            status.getTime().getTime() - contestShowDTO.getStartTime().getTime())); // Time
      }

      json.put("rankList", rankListBuilder.build());
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
      if (contestShowDTO.getStatus().equals("Pending") && !isAdmin(session)) {
        throw new AppException("Contest not start yet.");
      }

      List<ContestProblemDetailDTO> contestProblemList = contestProblemService.
          getContestProblemDetailDTOListByContestId(contestId);

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
          Global.RECORD_PER_PAGE, null);
      List<ContestListDTO> contestListDTOList = contestService.
          getContestListDTOList(contestCondition, pageInfo);

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
  @LoginPermit(Global.AuthenticationType.ADMIN)
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
  @LoginPermit(Global.AuthenticationType.ADMIN)
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
        ContestDTO contestDTO;
        if (contestEditDTO.getAction().compareTo("new") == 0) {
          Integer contestId = contestService.createNewContest();
          contestDTO = contestService.getContestDTOByContestId(contestId);
          if (contestDTO == null
              || contestDTO.getContestId().compareTo(contestId) != 0) {
            throw new AppException("Error while creating contest.");
          }
          // Move pictures
          String oldDirectory = "/images/contest/new/";
          String newDirectory = "/images/contest/" + contestId + "/";
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
                  .build());

          // Check problem added success.
          ContestProblemDTO contestProblemDTO = contestProblemService.getContestProblemDTO(contestProblemId);
          AppExceptionUtil.assertNotNull(contestProblemDTO);
          AppExceptionUtil.assertNotNull(contestProblemDTO.getContestProblemId());
        }

        contestDTO.setType(contestEditDTO.getType());
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
}
