package cn.edu.uestc.acmicpc.web.oj.controller.status;

import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.code.CodeDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusInformationDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.SubmitDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.CodeService;
import cn.edu.uestc.acmicpc.service.iface.CompileInfoService;
import cn.edu.uestc.acmicpc.service.iface.ContestProblemService;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.ArrayUtil;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/status")
public class StatusController extends BaseController {

  private StatusService statusService;
  private ProblemService problemService;
  private CodeService codeService;
  private CompileInfoService compileInfoService;
  private ContestService contestService;
  private ContestProblemService contestProblemService;
  private GlobalService globalService;
  private LanguageService languageService;
  private UserService userService;

  @Autowired
  public StatusController(StatusService statusService, ProblemService problemService,
                          CodeService codeService, CompileInfoService compileInfoService,
                          ContestService contestService, ContestProblemService contestProblemService,
                          GlobalService globalService, LanguageService languageService,
                          UserService userService) {
    this.statusService = statusService;
    this.problemService = problemService;
    this.codeService = codeService;
    this.compileInfoService = compileInfoService;
    this.contestService = contestService;
    this.contestProblemService = contestProblemService;
    this.globalService = globalService;
    this.languageService = languageService;
    this.userService = userService;
  }

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> search(HttpSession session,
                             @RequestBody StatusCondition statusCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (statusCondition.contestId == null) {
        statusCondition.contestId = -1;
      }
      if (statusCondition.result == null) {
        statusCondition.result = Global.OnlineJudgeResultType.OJ_ALL;
      }
      if (!isAdmin(session)) {
        statusCondition.isForAdmin = false;
        if (statusCondition.contestId != -1) {
          ContestShowDTO contestShowDTO = contestService.getContestShowDTOByContestId(statusCondition.contestId);
          if (contestShowDTO == null) {
            throw new AppException("No such contest.");
          }
          // Check permission
          checkContestPermission(session, statusCondition.contestId);
          // Get type
          Byte type = getContestType(session, statusCondition.contestId);
          if (type == Global.ContestType.INVITED.ordinal()) {
            // Only show current user and his member's status
            List<Integer> memberList = getContestTeamMembers(session, statusCondition.contestId);
            statusCondition.userIdList = ArrayUtil.join(memberList.toArray(), ",");
          } else {
            // Only show current user's status
            UserDTO currentUser = getCurrentUser(session);
            statusCondition.userId = currentUser.getUserId();
          }
          // Only show status submitted in contest
          statusCondition.startTime = contestShowDTO.getStartTime();
          statusCondition.endTime = contestShowDTO.getEndTime();
          // Some problems is stashed when contest is running
          statusCondition.isVisible = null;
        } else {
          // Only show status submitted for visible problem
          statusCondition.isVisible = true;
        }
      } else {
        if (statusCondition.contestId != -1) {
          ContestShowDTO contestShowDTO = contestService.getContestShowDTOByContestId(statusCondition.contestId);
          if (contestShowDTO == null) {
            throw new AppException("No such contest.");
          }
        }
        // Current user is administrator, just show all the status.
        statusCondition.isForAdmin = true;
      }

      Long count = statusService.count(statusCondition);
      Long recordPerPage = Global.RECORD_PER_PAGE;
      if (statusCondition.countPerPage != null) {
        recordPerPage = statusCondition.countPerPage;
      }
      PageInfo pageInfo = buildPageInfo(count, statusCondition.currentPage,
          recordPerPage, null);
      List<StatusListDTO> statusListDTOList = statusService.getStatusList(statusCondition,
          pageInfo);
      for (StatusListDTO statusListDTO : statusListDTOList) {
        statusListDTO.setReturnType(globalService.getReturnDescription(
            statusListDTO.getReturnTypeId(), statusListDTO.getCaseNumber()));
        if (statusListDTO.getReturnTypeId() != Global.OnlineJudgeReturnType.OJ_AC.ordinal()) {
          statusListDTO.setTimeCost(null);
          statusListDTO.setMemoryCost(null);
        }
      }

      json.put("pageInfo", pageInfo);
      json.put("result", "success");
      json.put("list", statusListDTOList);
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

  @RequestMapping("rejudgeStatusCount")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> rejudgeStatusCount(@RequestBody StatusCondition statusCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      // Current user is administrator
      statusCondition.isForAdmin = true;
      if (statusCondition.result == null ||
          statusCondition.result == Global.OnlineJudgeResultType.OJ_ALL ||
          statusCondition.result == Global.OnlineJudgeResultType.OJ_AC ||
          statusCondition.result == Global.OnlineJudgeResultType.OJ_JUDGING ||
          statusCondition.result == Global.OnlineJudgeResultType.OJ_WAIT) {
        // Avoid rejudge accepted status.
        statusCondition.result = Global.OnlineJudgeResultType.OJ_NOT_AC;
      }
      if (statusCondition.contestId == null) {
        statusCondition.contestId = -1;
      }
      Long count = statusService.count(statusCondition);

      json.put("result", "success");
      json.put("count", count);
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

  @RequestMapping("rejudge")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> rejudge(@RequestBody StatusCondition statusCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (statusCondition.userName != null) {
        UserDTO userDTO = userService.getUserDTOByUserName(statusCondition.userName);
        if (userDTO == null) {
          throw new AppException("User not found for given user name.");
        }
        statusCondition.userId = userDTO.getUserId();
      }
      if (statusCondition.result == null ||
          statusCondition.result == Global.OnlineJudgeResultType.OJ_ALL ||
          statusCondition.result == Global.OnlineJudgeResultType.OJ_AC ||
          statusCondition.result == Global.OnlineJudgeResultType.OJ_JUDGING ||
          statusCondition.result == Global.OnlineJudgeResultType.OJ_WAIT) {
        // Avoid rejudge accepted status.
        statusCondition.result = Global.OnlineJudgeResultType.OJ_NOT_AC;
      }
      if (statusCondition.contestId == null) {
        statusCondition.contestId = -1;
      }
      statusService.rejudge(statusCondition);

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

  @RequestMapping("submit")
  @LoginPermit(NeedLogin = true)
  public
  @ResponseBody
  Map<String, Object> submit(HttpSession session,
                             @RequestBody @Valid SubmitDTO submitDTO,
                             BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        UserDTO currentUser = (UserDTO) session.getAttribute("currentUser");

        if (submitDTO.getLanguageId() == null) {
          throw new AppException("Please select a language.");
        }
        if (languageService.getLanguageName(submitDTO.getLanguageId()) == null) {
          throw new AppException("No such language.");
        }

        if (submitDTO.getProblemId() == null) {
          throw new AppException("Wrong problem id.");
        }
        ProblemDTO problemDTO = problemService.getProblemDTOByProblemId(submitDTO.getProblemId());
        if (problemDTO == null) {
          throw new AppException("Wrong problem id.");
        }
        // Status in contest
        if (submitDTO.getContestId() != null) {
          ContestShowDTO contestShowDTO = contestService.getContestShowDTOByContestId(submitDTO.getContestId());
          if (contestShowDTO == null) {
            throw new AppException("Wrong contest id.");
          }
          if (!contestProblemService.checkContestProblemInContest(submitDTO.getProblemId(), submitDTO.getContestId())) {
            throw new AppException("Wrong problem id.");
          }
          if (!isAdmin(session)) {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            if (currentTime.before(contestShowDTO.getStartTime()) ||
                currentTime.after(contestShowDTO.getEndTime())) {
              throw new AppException("Out of time!");
            }
            checkContestPermission(session, submitDTO.getContestId());
          }
        } else {
          // We don't allow normal user to submit code to a stashed problem.
          if (!isAdmin(session)) {
            if (!problemDTO.getIsVisible()) {
              throw new AppException("You have no permission to submit this problem.");
            }
          }
        }

        Integer codeId = codeService.createNewCode(CodeDTO.builder()
            .setContent(submitDTO.getCodeContent())
            .setShare(false)
            .build());
        if (codeId == null) {
          throw new AppException("Error while saving you code.");
        }

        statusService.createNewStatus(StatusDTO.builder()
            .setCodeId(codeId)
            .setContestId(submitDTO.getContestId())
            .setLanguageId(submitDTO.getLanguageId())
            .setProblemId(submitDTO.getProblemId())
            .setTime(new Timestamp(new Date().getTime()))
            .setUserId(currentUser.getUserId())
            .setLength(submitDTO.getCodeContent().length())
            .build());
        json.put("result", "success");
      } catch (AppException e) {
        json.put("result", "error");
        json.put("error_msg", e.getMessage());
      }
    }
    return json;
  }

  @RequestMapping("info/{statusId}")
  @LoginPermit(NeedLogin = true)
  public
  @ResponseBody
  Map<String, Object> info(HttpSession session,
                           @PathVariable Integer statusId) {
    Map<String, Object> json = new HashMap<>();
    try {
      StatusInformationDTO statusInformationDTO = statusService.getStatusInformation(statusId);
      if (statusInformationDTO == null) {
        throw new AppException("No such status.");
      }
      if (!isAdmin(session)) {
        UserDTO currentUser = getCurrentUser(session);
        if (statusInformationDTO.getContestId() == null) {
          // Status not in contest
          if (!currentUser.getUserId().equals(statusInformationDTO.getUserId())) {
            throw new AppException("You have no permission to view this code.");
          }
        } else {
          // Status in contest
          checkContestPermission(session, statusInformationDTO.getContestId());
          Byte type = getContestType(session, statusInformationDTO.getContestId());
          if (type == Global.ContestType.INVITED.ordinal()) {
            // Only show current user and his member's status
            // Find current user's teamId
            List<Integer> teamMembers = getContestTeamMembers(session, statusInformationDTO.getContestId());
            // Check permission
            Boolean valid = false;
            for (Integer memberId : teamMembers) {
              if (memberId.equals(currentUser.getUserId())) {
                valid = true;
              }
            }
            if (!valid) {
              throw new AppException("You have no permission to view this code.");
            }
          } else {
            // Status in normal contest
            if (!currentUser.getUserId().equals(statusInformationDTO.getUserId())) {
              throw new AppException("You have no permission to view this code.");
            }
          }
        }
      }
      json.put("result", "success");
      json.put("code", statusInformationDTO.getCodeContent());
      if (statusInformationDTO.getCompileInfoId() != null) {
        json.put("compileInfo", compileInfoService.getCompileInfo(
            statusInformationDTO.getCompileInfoId()));
      }
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
}
