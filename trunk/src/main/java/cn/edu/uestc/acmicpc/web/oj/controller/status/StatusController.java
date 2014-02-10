package cn.edu.uestc.acmicpc.web.oj.controller.status;

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

import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.code.CodeDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDTO;
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
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

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

  @Autowired
  public StatusController(StatusService statusService, ProblemService problemService, CodeService codeService, CompileInfoService compileInfoService, ContestService contestService, ContestProblemService contestProblemService, GlobalService globalService, LanguageService languageService) {
    this.statusService = statusService;
    this.problemService = problemService;
    this.codeService = codeService;
    this.compileInfoService = compileInfoService;
    this.contestService = contestService;
    this.contestProblemService = contestProblemService;
    this.globalService = globalService;
    this.languageService = languageService;
  }

  @RequestMapping("list")
  @LoginPermit(NeedLogin = false)
  public String list() {
    return "status/statusList";
  }

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> search(HttpSession session,
                             @RequestBody StatusCondition statusCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (!isAdmin(session)) {
        statusCondition.isVisible = true;
        if (statusCondition.contestId != -1) {
          ContestShowDTO contestShowDTO = contestService.getContestShowDTOByContestId(statusCondition.contestId);
          if (contestShowDTO == null) {
            throw new AppException("No such contest.");
          }
          UserDTO currentUser = getCurrentUser(session);
          if (currentUser == null) {
            // Return nothing
            statusCondition.userId = 0;
          } else {
            statusCondition.userId = currentUser.getUserId();
          }
          statusCondition.startTime = contestShowDTO.getStartTime();
          statusCondition.endTime = contestShowDTO.getEndTime();
        }
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

  @RequestMapping("count")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> count(@RequestBody StatusCondition statusCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
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

        if (submitDTO.getProblemId() == null) {
          throw new AppException("Wrong problem id.");
        }
        ProblemDTO problemDTO = problemService.getProblemDTOByProblemId(submitDTO.getProblemId());
        if (problemDTO == null) {
          throw new AppException("Wrong problem id.");
        }
        if (!problemDTO.getIsVisible() &&
            currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal()) {
          throw new AppException("You have no permission to submit this problem.");
        }
        if (submitDTO.getContestId() != null) {
          // Is this contest exist?
          ContestDTO contestDTO = contestService.getContestDTOByContestId(submitDTO.getContestId());
          if (contestDTO == null) {
            throw new AppException("Wrong contest id.");
          }
          // Is this contest contains this problem?
          if (contestProblemService.checkContestProblemInContest(submitDTO.getProblemId(), submitDTO.getContestId()) == false) {
            throw new AppException("Wrong problem id.");
          }
        }

        if (submitDTO.getLanguageId() == null) {
          throw new AppException("Please select a language.");
        }
        if (languageService.getLanguageName(submitDTO.getLanguageId()) == null) {
          throw new AppException("No such language.");
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
        throw new AppException("You have no permission to view this code.");
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
