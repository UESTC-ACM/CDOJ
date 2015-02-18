package cn.edu.uestc.acmicpc.web.oj.controller.status;

import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dto.field.ContestFields;
import cn.edu.uestc.acmicpc.db.dto.impl.CodeDto;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDto;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDto;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDto;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusDto;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusInformationDto;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.status.SubmitDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.service.iface.CodeService;
import cn.edu.uestc.acmicpc.service.iface.CompileInfoService;
import cn.edu.uestc.acmicpc.service.iface.ContestProblemService;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.service.iface.MessageService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.ContestType;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeResultType;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.ArrayUtil;
import cn.edu.uestc.acmicpc.util.helper.EnumTypeUtil;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
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

  private final StatusService statusService;
  private final ProblemService problemService;
  private final CodeService codeService;
  private final CompileInfoService compileInfoService;
  private final ContestService contestService;
  private final ContestProblemService contestProblemService;
  private final LanguageService languageService;
  private final UserService userService;
  private final Settings settings;
  private final MessageService messageService;

  @Autowired
  public StatusController(StatusService statusService,
      ProblemService problemService,
      CodeService codeService,
      CompileInfoService compileInfoService,
      ContestService contestService,
      ContestProblemService contestProblemService,
      LanguageService languageService,
      UserService userService,
      Settings settings,
      MessageService messageService) {
    this.statusService = statusService;
    this.problemService = problemService;
    this.codeService = codeService;
    this.compileInfoService = compileInfoService;
    this.contestService = contestService;
    this.contestProblemService = contestProblemService;
    this.languageService = languageService;
    this.userService = userService;
    this.settings = settings;
    this.messageService = messageService;
  }

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> search(HttpSession session,
      @RequestBody StatusCondition statusCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (statusCondition.contestId == null) {
        statusCondition.contestId = -1;
      }
      if (statusCondition.result == null) {
        statusCondition.result = OnlineJudgeResultType.OJ_ALL;
      }
      if (!isAdmin(session)) {
        statusCondition.isForAdmin = false;
        if (statusCondition.contestId != -1) {
          ContestDto contestShowDto = contestService.getContestDtoByContestId(
              statusCondition.contestId, ContestFields.FIELDS_FOR_SHOWING);
          if (contestShowDto == null) {
            throw new AppException("No such contest.");
          }
          // Check permission
          checkContestPermission(session, statusCondition.contestId);
          // Get type
          Byte type = getContestType(session, statusCondition.contestId);
          if (type == ContestType.INVITED.ordinal()) {
            // Only show current user and his member's status
            List<Integer> memberList = getContestTeamMembers(session, statusCondition.contestId);
            statusCondition.userIdList = ArrayUtil.join(memberList.toArray(), ",");
          } else {
            // Only show current user's status
            UserDto currentUser = getCurrentUser(session);
            if (currentUser == null) {
              // Avoid null point exception.
              statusCondition.userId = 0;
            } else {
              statusCondition.userId = currentUser.getUserId();
            }
          }
          // Only show status submitted in contest
          statusCondition.startTime = contestShowDto.getStartTime();
          statusCondition.endTime = contestShowDto.getEndTime();
          // Some problems is stashed when contest is running
          statusCondition.isVisible = null;
        } else {
          // Only show status submitted for visible problem
          statusCondition.isVisible = true;
        }
      } else {
        if (statusCondition.contestId != -1) {
          ContestDto contestShowDto = contestService.getContestDtoByContestId(
              statusCondition.contestId, ContestFields.FIELDS_FOR_SHOWING);
          if (contestShowDto == null) {
            throw new AppException("No such contest.");
          }
        }
        // Current user is administrator, just show all the status.
        statusCondition.isForAdmin = true;
      }

      Long count = statusService.count(statusCondition);
      Long recordPerPage = settings.RECORD_PER_PAGE;
      if (statusCondition.countPerPage != null) {
        recordPerPage = statusCondition.countPerPage;
      }
      PageInfo pageInfo = buildPageInfo(count, statusCondition.currentPage,
          recordPerPage, null);
      List<StatusListDto> statusListDtoList = statusService.getStatusList(statusCondition,
          pageInfo);
      for (StatusListDto statusListDto : statusListDtoList) {
        statusListDto.setReturnType(EnumTypeUtil.getReturnDescription(
            statusListDto.getReturnTypeId(), statusListDto.getCaseNumber()));
        if (statusListDto.getReturnTypeId() != OnlineJudgeReturnType.OJ_AC.ordinal()) {
          statusListDto.setTimeCost(null);
          statusListDto.setMemoryCost(null);
        }
      }

      json.put("pageInfo", pageInfo);
      json.put("result", "success");
      json.put("list", statusListDtoList);
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
  @LoginPermit(AuthenticationType.ADMIN)
  public @ResponseBody Map<String, Object> rejudgeStatusCount(
      @RequestBody StatusCondition statusCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      // Current user is administrator
      statusCondition.isForAdmin = true;
      if (statusCondition.result == null ||
          statusCondition.result == OnlineJudgeResultType.OJ_ALL ||
          statusCondition.result == OnlineJudgeResultType.OJ_AC ||
          statusCondition.result == OnlineJudgeResultType.OJ_JUDGING ||
          statusCondition.result == OnlineJudgeResultType.OJ_WAIT) {
        // Avoid rejudge accepted status.
        statusCondition.result = OnlineJudgeResultType.OJ_NOT_AC;
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
  @LoginPermit(AuthenticationType.ADMIN)
  public @ResponseBody Map<String, Object> rejudge(@RequestBody StatusCondition statusCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (statusCondition.userName != null) {
        UserDto userDto = userService.getUserDtoByUserName(statusCondition.userName);
        if (userDto == null) {
          throw new AppException("User not found for given user name.");
        }
        statusCondition.userId = userDto.getUserId();
      }
      if (statusCondition.result == null ||
          statusCondition.result == OnlineJudgeResultType.OJ_ALL ||
          statusCondition.result == OnlineJudgeResultType.OJ_AC ||
          statusCondition.result == OnlineJudgeResultType.OJ_JUDGING ||
          statusCondition.result == OnlineJudgeResultType.OJ_WAIT) {
        // Avoid rejudge accepted status.
        statusCondition.result = OnlineJudgeResultType.OJ_NOT_AC;
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
  public @ResponseBody Map<String, Object> submit(HttpSession session,
      @RequestBody @Valid SubmitDto submitDto,
      BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        UserDto currentUser = (UserDto) session.getAttribute("currentUser");

        if (submitDto.getLanguageId() == null) {
          throw new AppException("Please select a language.");
        }
        if (languageService.getLanguageName(submitDto.getLanguageId()) == null) {
          throw new AppException("No such language.");
        }

        if (submitDto.getProblemId() == null) {
          throw new AppException("Wrong problem id.");
        }
        ProblemDto problemDto = problemService.getProblemDtoByProblemId(submitDto.getProblemId());
        if (problemDto == null) {
          throw new AppException("Wrong problem id.");
        }
        // Status in contest
        if (submitDto.getContestId() != null) {
          ContestDto contestShowDto = contestService.getContestDtoByContestId(
              submitDto.getContestId(), ContestFields.FIELDS_FOR_SHOWING);
          if (contestShowDto == null) {
            throw new AppException("Wrong contest id.");
          }
          if (!contestProblemService.checkContestProblemInContest(submitDto.getProblemId(),
              submitDto.getContestId())) {
            throw new AppException("Wrong problem id.");
          }
          if (!isAdmin(session)) {
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            if (currentTime.before(contestShowDto.getStartTime()) ||
                currentTime.after(contestShowDto.getEndTime())) {
              throw new AppException("Out of time!");
            }
            checkContestPermission(session, submitDto.getContestId());
          }
        } else {
          // We don't allow normal user to submit code to a stashed problem.
          if (!isAdmin(session)) {
            if (!problemDto.getIsVisible()) {
              throw new AppException("You have no permission to submit this problem.");
            }
          }
        }

        Integer codeId = codeService.createNewCode(CodeDto.builder()
            .setContent(submitDto.getCodeContent())
            .setShare(false)
            .build());
        if (codeId == null) {
          throw new AppException("Error while saving you code.");
        }

        statusService.createNewStatus(StatusDto.builder()
            .setCodeId(codeId)
            .setContestId(submitDto.getContestId())
            .setLanguageId(submitDto.getLanguageId())
            .setProblemId(submitDto.getProblemId())
            .setTime(new Timestamp(new Date().getTime()))
            .setUserId(currentUser.getUserId())
            .setLength(submitDto.getCodeContent().length())
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
  public @ResponseBody Map<String, Object> info(HttpSession session,
      @PathVariable Integer statusId) {
    Map<String, Object> json = new HashMap<>();
    try {
      StatusInformationDto statusInformationDto = statusService.getStatusInformation(statusId);
      if (statusInformationDto == null) {
        throw new AppException("No such status.");
      }
      if (!isAdmin(session)) {
        UserDto currentUser = getCurrentUser(session);
        if (statusInformationDto.getContestId() == null) {
          // Status not in contest
          if (!currentUser.getUserId().equals(statusInformationDto.getUserId())) {
            throw new AppException("You have no permission to view this code.");
          }
        } else {
          // Status in contest
          checkContestPermission(session, statusInformationDto.getContestId());
          Byte type = getContestType(session, statusInformationDto.getContestId());
          if (type == ContestType.INVITED.ordinal()) {
            // Only show current user and his member's status
            // Find current user's teamId
            List<Integer> teamMembers = getContestTeamMembers(session,
                statusInformationDto.getContestId());
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
            if (!currentUser.getUserId().equals(statusInformationDto.getUserId())) {
              throw new AppException("You have no permission to view this code.");
            }
          }
        }
      }
      json.put("result", "success");
      json.put("code", statusInformationDto.getCodeContent());
      if (statusInformationDto.getCompileInfoId() != null) {
        json.put("compileInfo", compileInfoService.getCompileInfo(
            statusInformationDto.getCompileInfoId()));
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

  @RequestMapping("print")
  @LoginPermit(NeedLogin = true)
  public @ResponseBody Map<String, Object> print(HttpSession session,
      @RequestBody SubmitDto submitDto) {
    Map<String, Object> json = new HashMap<>();
    try {
      String codeContent = submitDto.getCodeContent();
      if (StringUtil.isNullOrWhiteSpace(codeContent)) {
        throw new AppException("Please print something.");
      }

      UserDto currentUser = getCurrentUser(session);
      StringBuilder messageContentBuilder = new StringBuilder();
      messageContentBuilder.append("```\n")
          .append("/**\n")
          .append("  * User name: ").append(currentUser.getUserName()).append("\n")
          .append("  * Team name: ").append(currentUser.getNickName()).append("\n")
          .append("  * Members  : ").append(currentUser.getName()).append("\n")
          .append("  * Time     : ").append(new Date(System.currentTimeMillis())).append("\n")
          .append("  */\n")
          .append(codeContent)
          .append("```");
      messageService.createNewMessage(MessageDto.builder()
          .setSenderId(currentUser.getUserId())
          .setReceiverId(1) // Administrator
          .setTime(new Timestamp(System.currentTimeMillis()))
          .setIsOpened(false)
          .setTitle("Print request.")
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
}
