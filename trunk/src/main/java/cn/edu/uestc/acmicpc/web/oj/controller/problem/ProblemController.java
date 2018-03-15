package cn.edu.uestc.acmicpc.web.oj.controller.problem;

import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDto;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemEditDto;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemListDto;
import cn.edu.uestc.acmicpc.service.iface.FileService;
import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.ProblemSolveStatusType;
import cn.edu.uestc.acmicpc.util.enums.ProblemType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDto;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/problem")
public class ProblemController extends BaseController {

  private final ProblemService problemService;
  private final StatusService statusService;
  private final PictureService pictureService;
  private final FileService fileService;
  private final Settings settings;

  @Autowired
  public ProblemController(ProblemService problemService,
      StatusService statusService,
      PictureService pictureService,
      FileService fileService,
      Settings settings) {
    this.problemService = problemService;
    this.statusService = statusService;
    this.pictureService = pictureService;
    this.fileService = fileService;
    this.settings = settings;
  }

  @RequestMapping("data/{problemId}")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> data(@PathVariable("problemId") Integer problemId,
      HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      ProblemDto problemDto = problemService.getProblemDtoByProblemId(problemId);
      if (problemDto == null) {
        throw new AppException("No such problem.");
      }
      if(!isAdmin(session)) {
        if(!problemDto.getIsVisible()) {
          throw new AppException("No such problem.");
        }
        if(problemDto.getType() == ProblemType.INTERNAL) {
          UserDto currentUser = getCurrentUser(session);
          if(currentUser == null || currentUser.getType() != AuthenticationType.INTERNAL.ordinal()) {
            throw new AppException("No such problem.");
          }
        }
      }
      json.put("problem", problemDto);
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

  /**
   * Search problem
   *
   * @param session
   * @param problemCondition
   * @return json
   */
  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> search(HttpSession session,
      @RequestBody ProblemCondition problemCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (!isAdmin(session)) {
        problemCondition.isVisible = true;
        UserDto user = getCurrentUser(session);
        if (user == null || user.getType() != AuthenticationType.INTERNAL.ordinal()) {
          problemCondition.type = ProblemType.NORMAL;
        }
      }
      Long count = problemService.count(problemCondition);
      PageInfo pageInfo = buildPageInfo(count, problemCondition.currentPage,
          settings.RECORD_PER_PAGE, null);

      List<ProblemListDto> problemListDtoList = problemService
          .getProblemListDtoList(
              problemCondition, pageInfo);

      UserDto currentUser = (UserDto) session.getAttribute("currentUser");
      Map<Integer, ProblemSolveStatusType> problemStatus = getProblemStatus(currentUser, session);

      for (ProblemListDto problemListDto : problemListDtoList) {
        if (problemStatus.get(problemListDto.getProblemId()) == ProblemSolveStatusType.PASS) {
          problemListDto.setStatus(1);
        } else if (problemStatus.containsKey(problemListDto.getProblemId())) {
          problemListDto.setStatus(2);
        }
      }

      json.put("pageInfo", pageInfo);
      json.put("result", "success");
      json.put("list", problemListDtoList);
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

  /**
   * Modify special field of problem
   *
   * @param targetId
   *          problem id
   * @param field
   *          field want to modified
   * @param value
   *          value
   * @return JSON
   */
  @RequestMapping("operator/{id}/{field}/{value}")
  @LoginPermit(AuthenticationType.ADMIN)
  public @ResponseBody Map<String, Object> operator(@PathVariable("id") String targetId,
      @PathVariable("field") String field,
      @PathVariable("value") String value) {
    Map<String, Object> json = new HashMap<>();
    try {
      problemService.operator(field, targetId, value);
      json.put("result", "success");
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping("query/{id}/{field}")
  @LoginPermit(AuthenticationType.ADMIN)
  public @ResponseBody Map<String, Object> query(@PathVariable("id") String targetId,
      @PathVariable("field") String field) {
    Map<String, Object> json = new HashMap<>();
    try {
      json.put("list", problemService.query(field, targetId));
      json.put("result", "success");
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  /**
   * Edit problem
   *
   * @param problemEditDto
   *          uploaded information
   * @param validateResult
   *          validate result
   * @return
   */
  @RequestMapping("edit")
  @LoginPermit(AuthenticationType.ADMIN)
  public @ResponseBody Map<String, Object> edit(@RequestBody @Valid ProblemEditDto problemEditDto,
      BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        if (StringUtil.trimAllSpace(problemEditDto.getTitle()).equals("")) {
          throw new FieldException("title", "Please enter a validate title.");
        }
        ProblemDto problemDto;
        if (problemEditDto.getAction().compareTo("new") == 0) {
          Integer problemId = problemService.createNewProblem();
          problemDto = problemService.getProblemDtoByProblemId(problemId);
          if (problemDto == null || !problemDto.getProblemId().equals(problemId)) {
            throw new AppException("Error while creating problem.");
          }
          // Move pictures
          String oldDirectory = "problem/new/";
          String newDirectory = "problem/" + problemId + "/";
          problemEditDto.setDescription(pictureService.modifyPictureLocation(
              problemEditDto.getDescription(), oldDirectory, newDirectory));
          problemEditDto.setInput(pictureService.modifyPictureLocation(
              problemEditDto.getInput(), oldDirectory, newDirectory));
          problemEditDto.setOutput(pictureService.modifyPictureLocation(
              problemEditDto.getOutput(), oldDirectory, newDirectory));
          problemEditDto.setHint(pictureService.modifyPictureLocation(
              problemEditDto.getHint(), oldDirectory, newDirectory));
        } else {
          problemDto = problemService.getProblemDtoByProblemId(problemEditDto.getProblemId());
          if (problemDto == null) {
            throw new AppException("No such problem.");
          }
        }

        problemDto.setTitle(problemEditDto.getTitle());
        problemDto.setDescription(problemEditDto.getDescription());
        problemDto.setInput(problemEditDto.getInput());
        problemDto.setOutput(problemEditDto.getOutput());
        problemDto.setSampleInput(problemEditDto.getSampleInput());
        problemDto.setSampleOutput(problemEditDto.getSampleOutput());
        problemDto.setHint(problemEditDto.getHint());
        problemDto.setSource(problemEditDto.getSource());

        problemDto.setTimeLimit(problemEditDto.getTimeLimit());
        problemDto.setMemoryLimit(problemEditDto.getMemoryLimit());
        problemDto.setOutputLimit(problemEditDto.getOutputLimit());
        problemDto.setIsSpj(problemEditDto.getIsSpj());
        problemDto.setType(problemEditDto.getType());

        Integer dataCount;
        if (problemEditDto.getAction().equals("new")) {
          dataCount = fileService.moveProblemDataFile("new",
              problemDto.getProblemId());
        } else {
          dataCount = fileService.moveProblemDataFile(problemDto.getProblemId().toString(),
              problemDto.getProblemId());
        }
        if (dataCount != 0) {
          problemDto.setDataCount(dataCount);
        }

        problemService.updateProblem(problemDto);
        json.put("result", "success");
        json.put("problemId", problemDto.getProblemId());
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

  @RequestMapping(value = "uploadProblemDataFile/{problemId}",
      method = RequestMethod.POST)
  @LoginPermit(AuthenticationType.ADMIN)
  public @ResponseBody Map<String, Object> uploadProblemDataFile(
      @PathVariable("problemId") String sProblemId,
      @RequestParam(value = "uploadFile") MultipartFile[] files) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (!sProblemId.equals("new")) {
        Integer problemId;
        try {
          problemId = Integer.parseInt(sProblemId);
        } catch (NumberFormatException e) {
          throw new AppException("Parse problem id error.");
        }
        if (!problemService.checkProblemExists(problemId)) {
          throw new AppException("No such problem.");
        }
      }

      Integer dataCount = fileService.uploadProblemDataFile(
          FileUploadDto.builder()
              .setFiles(Arrays.asList(files))
              .build(),
          sProblemId);

      json.put("total", dataCount);
      json.put("success", "true");
    } catch (AppException e) {
      e.printStackTrace();
      json.put("error", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("error", "Unknown exception occurred.");
    }
    return json;
  }

  private Map<Integer, ProblemSolveStatusType> getProblemStatus(UserDto currentUser,
      HttpSession session) {
    Map<Integer, ProblemSolveStatusType> problemStatus = new HashMap<>();
    try {
      if (currentUser != null) {
        List<Integer> triedProblems = statusService.
            findAllProblemIdsThatUserTried(currentUser.getUserId(), isAdmin(session));
        for (Integer result : triedProblems) {
          problemStatus.put(result, ProblemSolveStatusType.FAIL);
        }
        List<Integer> acceptedProblems = statusService.
            findAllProblemIdsThatUserSolved(currentUser.getUserId(), isAdmin(session));
        for (Integer result : acceptedProblems) {
          problemStatus.put(result, ProblemSolveStatusType.PASS);
        }
      }
    } catch (AppException ignored) {
    }
    return problemStatus;
  }
}
