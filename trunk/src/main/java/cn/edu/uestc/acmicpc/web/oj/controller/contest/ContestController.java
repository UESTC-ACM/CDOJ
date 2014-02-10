package cn.edu.uestc.acmicpc.web.oj.controller.contest;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestEditorShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDetailDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemSummaryDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusListDTO;
import cn.edu.uestc.acmicpc.service.iface.ContestProblemService;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.web.rank.RankListBuilder;
import cn.edu.uestc.acmicpc.web.rank.RankListStatus;

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

  @Autowired
  public ContestController(ContestService contestService,
                           ContestProblemService contestProblemService,
                           PictureService pictureService,
                           ProblemService problemService,
                           StatusService statusService) {
    this.contestService = contestService;
    this.contestProblemService = contestProblemService;
    this.pictureService = pictureService;
    this.problemService = problemService;
    this.statusService = statusService;
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

      List<ContestProblemSummaryDTO> contestProblemList = contestProblemService.
          getContestProblemSummaryDTOListByContestId(contestId);

      StatusCondition statusCondition = new StatusCondition();
      statusCondition.contestId = contestShowDTO.getContestId();
      // Sort by time
      statusCondition.orderFields = "time";
      statusCondition.orderAsc = "true";
      List<StatusListDTO> statusList = statusService.getStatusList(statusCondition);

      RankListBuilder rankListBuilder = new RankListBuilder();
      for (ContestProblemSummaryDTO problem: contestProblemList) {
        rankListBuilder.addRankListProblem(problem.getProblemId().toString());
      }
      for (StatusListDTO status: statusList) {
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

  @RequestMapping("show/{contestId}")
  @LoginPermit(NeedLogin = false)
  public String show(@PathVariable("contestId") Integer contestId, ModelMap model) {
    try {
      if (!contestService.checkContestExists(contestId)) {
        throw new AppException("No such contest");
      }
      model.put("contestId", contestId);
    } catch (AppException e) {
      return "error/404";
    } catch (Exception e) {
      e.printStackTrace();
      return "error/404";
    }
    return "contest/contestShow";
  }

  @RequestMapping("list")
  @LoginPermit(NeedLogin = false)
  public String list() {
    return "contest/contestList";
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

  @RequestMapping("editor/{contestId}")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public String editor(@PathVariable("contestId") String sContestId,
                       ModelMap model) {
    try {
      if (sContestId.compareTo("new") == 0) {
        model.put("action", "new");
        ContestEditorShowDTO targetContest = ContestEditorShowDTO.builder()
            .setTime(new Timestamp(System.currentTimeMillis()))
            .setLengthDays(0)
            .setLengthHours(5)
            .setLengthMinutes(0)
            .build();
        model.put("targetContest", targetContest);
      } else {
        Integer contestId;
        try {
          contestId = Integer.parseInt(sContestId);
        } catch (NumberFormatException e) {
          throw new AppException("Parse contest id error.");
        }
        ContestEditorShowDTO targetContest = contestService.getContestEditorShowDTO(contestId);
        if (targetContest == null) {
          throw new AppException("No such contest.");
        }
        model.put("action", contestId);
        model.put("targetContest", targetContest);
        model.put("contestProblems",
            contestProblemService.getContestProblemSummaryDTOListByContestId(contestId));
      }

    } catch (AppException e) {
      model.put("message", e.getMessage());
      return "error/error";
    }
    return "/contest/contestEditor";
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
              || !contestDTO.getContestId().equals(contestId)) {
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

        // Remove old contest problems
        contestProblemService.removeContestProblemByContestId(contestDTO.getContestId());
        // Add new contest problems
        String[] problemList = contestEditDTO.getProblemList().split(",");
        for (int order = 0; order < problemList.length; order++) {
          Integer problemId = Integer.parseInt(problemList[order]);
          // Check problem exists.
          AppExceptionUtil.assertTrue(problemService.checkProblemExists(problemId));

          Integer contestProblemId = contestProblemService.createNewContestProblem(
              ContestProblemDTO.builder()
                  .setContestId(contestDTO.getContestId())
                  .setOrder(order)
                  .setProblemId(problemId)
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
