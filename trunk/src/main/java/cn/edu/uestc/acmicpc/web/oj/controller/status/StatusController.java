package cn.edu.uestc.acmicpc.web.oj.controller.status;

import cn.edu.uestc.acmicpc.db.criteria.StatusCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.ContestFields;
import cn.edu.uestc.acmicpc.db.dto.field.StatusFields;
import cn.edu.uestc.acmicpc.db.dto.impl.CodeDto;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDto;
import cn.edu.uestc.acmicpc.db.dto.impl.MessageDto;
import cn.edu.uestc.acmicpc.db.dto.impl.StatusDto;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDto;
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
import cn.edu.uestc.acmicpc.util.enums.ProblemType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
  public
  @ResponseBody
  Map<String, Object> search(HttpSession session,
                             @RequestBody StatusCriteria statusCriteria) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (statusCriteria.contestId == null) {
        statusCriteria.contestId = -1;
      }
      if (statusCriteria.result == null) {
        statusCriteria.result = OnlineJudgeResultType.OJ_ALL;
      }
      if (!isAdmin(session)) {
        statusCriteria.isForAdmin = false;
        if (statusCriteria.contestId != -1) {
          ContestDto contestShowDto = contestService.getContestDtoByContestId(
              statusCriteria.contestId, ContestFields.FIELDS_FOR_SHOWING);
          if (contestShowDto == null) {
            throw new AppException("No such contest.");
          }
          // Check permission
          checkContestPermission(session, statusCriteria.contestId);
          // Get type
          Byte type = getContestType(session, statusCriteria.contestId);
          if (type == ContestType.INVITED.ordinal()) {
            // Only show current user and his member's status
            statusCriteria.userIdList = getContestTeamMembers(session, statusCriteria.contestId);
          } else {
            // Only show current user's status
            UserDto currentUser = getCurrentUser(session);
            if (currentUser == null) {
              // Avoid null point exception.
              statusCriteria.userId = 0;
            } else {
              statusCriteria.userId = currentUser.getUserId();
            }
          }
          // Only show status submitted in contest
          statusCriteria.startTime = contestShowDto.getStartTime();
          statusCriteria.endTime = contestShowDto.getEndTime();
          // Some problems is stashed when contest is running
          statusCriteria.isProblemVisible = null;
        } else {
          // Only show status submitted for visible problem
          statusCriteria.isProblemVisible = true;
          statusCriteria.problemType = ProblemType.NORMAL;
          if (statusCriteria.problemId != null) {
            ProblemDto problemDto = problemService.getProblemDtoByProblemId(statusCriteria.problemId);
            UserDto currentUser = getCurrentUser(session);
            //internal problem' status only show for internal user who are searching.
            if (currentUser != null && problemDto != null && problemDto.getType() == ProblemType.INTERNAL
                && currentUser.getType() == AuthenticationType.INTERNAL.ordinal()) {
              statusCriteria.problemType = ProblemType.INTERNAL;
            }
          }
        }
      } else {
        if (statusCriteria.contestId != -1) {
          ContestDto contestShowDto = contestService.getContestDtoByContestId(
              statusCriteria.contestId, ContestFields.FIELDS_FOR_SHOWING);
          if (contestShowDto == null) {
            throw new AppException("No such contest.");
          }
        }
        // Current user is administrator, just show all the status.
        statusCriteria.isForAdmin = true;
      }

      Long count = statusService.count(statusCriteria);
      Long recordPerPage = settings.RECORD_PER_PAGE;
      if (statusCriteria.countPerPage != null) {
        recordPerPage = statusCriteria.countPerPage;
      }
      PageInfo pageInfo = buildPageInfo(count, statusCriteria.currentPage,
          recordPerPage, null);
      List<StatusDto> statusListDtoList = statusService.getStatusList(statusCriteria,
          pageInfo, StatusFields.FIELDS_FOR_LIST_PAGE);
      for (@SuppressWarnings("unused") StatusDto statusListDto : statusListDtoList) {
        // TODO
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
  public
  @ResponseBody
  Map<String, Object> rejudgeStatusCount(
      @RequestBody StatusCriteria statusCriteria) {
    Map<String, Object> json = new HashMap<>();
    try {
      // Current user is administrator
      statusCriteria.isForAdmin = true;
      if (statusCriteria.result == null ||
          statusCriteria.result == OnlineJudgeResultType.OJ_ALL ||
          statusCriteria.result == OnlineJudgeResultType.OJ_AC ||
          statusCriteria.result == OnlineJudgeResultType.OJ_JUDGING ||
          statusCriteria.result == OnlineJudgeResultType.OJ_WAIT) {
        // Avoid rejudge accepted status.
        statusCriteria.result = OnlineJudgeResultType.OJ_NOT_AC;
      }
      if (statusCriteria.contestId == null) {
        statusCriteria.contestId = -1;
      }
      Long count = statusService.count(statusCriteria);

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
  public
  @ResponseBody
  Map<String, Object> rejudge(@RequestBody StatusCriteria statusCriteria) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (statusCriteria.userName != null) {
        UserDto userDto = userService.getUserDtoByUserName(statusCriteria.userName);
        if (userDto == null) {
          throw new AppException("User not found for given user name.");
        }
        statusCriteria.userId = userDto.getUserId();
      }
      if (statusCriteria.result == null ||
          statusCriteria.result == OnlineJudgeResultType.OJ_ALL ||
          statusCriteria.result == OnlineJudgeResultType.OJ_AC ||
          statusCriteria.result == OnlineJudgeResultType.OJ_JUDGING ||
          statusCriteria.result == OnlineJudgeResultType.OJ_WAIT) {
        // Avoid rejudge accepted status.
        statusCriteria.result = OnlineJudgeResultType.OJ_NOT_AC;
      }
      if (statusCriteria.contestId == null) {
        statusCriteria.contestId = -1;
      }
      statusService.rejudge(statusCriteria);

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
  @LoginPermit()
  public
  @ResponseBody
  Map<String, Object> submit(HttpSession session,
                             @RequestBody @Valid StatusDto submitDto,
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
              throw new AppException("No such problem.");
            }
            if (problemDto.getType() == ProblemType.INTERNAL
                && (currentUser == null
                || currentUser.getType() != AuthenticationType.INTERNAL.ordinal())) {
              throw new AppException("No such problem.");
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
            .setResultId(OnlineJudgeReturnType.OJ_WAIT.ordinal())
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
  @LoginPermit()
  public
  @ResponseBody
  Map<String, Object> info(HttpSession session,
                           @PathVariable Integer statusId) {
    Map<String, Object> json = new HashMap<>();
    try {
      StatusDto statusInformationDto =
          statusService.getStatusDto(statusId, StatusFields.FIELDS_FOR_STATUS_INFO);
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
  @LoginPermit()
  public
  @ResponseBody
  Map<String, Object> print(HttpSession session,
                            @RequestBody StatusDto submitDto) {
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
