package cn.edu.uestc.acmicpc.web.oj.controller.problem;

import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.FileService;
import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.AuthorStatusType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/problem")
public class ProblemController extends BaseController {

  private ProblemService problemService;
  private StatusService statusService;
  private PictureService pictureService;
  private FileService fileService;
  private Settings settings;

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
  public
  @ResponseBody
  Map<String, Object> data(@PathVariable("problemId") Integer problemId,
                           HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      ProblemDTO problemDTO = problemService.getProblemDTOByProblemId(problemId);
      if (problemDTO == null) {
        throw new AppException("No such problem.");
      }
      if (!problemDTO.getIsVisible() && !isAdmin(session)) {
        throw new AppException("No such problem.");
      }

      json.put("problem", problemDTO);
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
  public
  @ResponseBody
  Map<String, Object> search(HttpSession session,
                             @RequestBody ProblemCondition problemCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (!isAdmin(session)) {
        problemCondition.isVisible = true;
      }
      Long count = problemService.count(problemCondition);
      PageInfo pageInfo = buildPageInfo(count, problemCondition.currentPage,
          settings.RECORD_PER_PAGE, null);

      List<ProblemListDTO> problemListDTOList = problemService
          .getProblemListDTOList(
              problemCondition, pageInfo);

      UserDTO currentUser = (UserDTO) session.getAttribute("currentUser");
      Map<Integer, AuthorStatusType> problemStatus = getProblemStatus(currentUser, session);

      for (ProblemListDTO problemListDTO : problemListDTOList) {
        if (problemStatus.get(problemListDTO.getProblemId()) == AuthorStatusType.PASS) {
          problemListDTO.setStatus(1);
        } else if (problemStatus.containsKey(problemListDTO.getProblemId())) {
          problemListDTO.setStatus(2);
        }
      }

      json.put("pageInfo", pageInfo);
      json.put("result", "success");
      json.put("list", problemListDTOList);
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
   * @param targetId problem id
   * @param field    field want to modified
   * @param value    value
   * @return JSON
   */
  @RequestMapping("operator/{id}/{field}/{value}")
  @LoginPermit(AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> operator(@PathVariable("id") String targetId,
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
  public
  @ResponseBody
  Map<String, Object> query(@PathVariable("id") String targetId,
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
   * @param problemEditDTO uploaded information
   * @param validateResult validate result
   * @return
   */
  @RequestMapping("edit")
  @LoginPermit(AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> edit(@RequestBody @Valid ProblemEditDTO problemEditDTO,
                           BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        if (StringUtil.trimAllSpace(problemEditDTO.getTitle()).equals(""))
          throw new FieldException("title", "Please enter a validate title.");
        ProblemDTO problemDTO;
        if (problemEditDTO.getAction().compareTo("new") == 0) {
          Integer problemId = problemService.createNewProblem();
          problemDTO = problemService.getProblemDTOByProblemId(problemId);
          if (problemDTO == null || !problemDTO.getProblemId().equals(problemId)) {
            throw new AppException("Error while creating problem.");
          }
          // Move pictures
          String oldDirectory = "problem/new/";
          String newDirectory = "problem/" + problemId + "/";
          problemEditDTO.setDescription(pictureService.modifyPictureLocation(
              problemEditDTO.getDescription(), oldDirectory, newDirectory));
          problemEditDTO.setInput(pictureService.modifyPictureLocation(
              problemEditDTO.getInput(), oldDirectory, newDirectory));
          problemEditDTO.setOutput(pictureService.modifyPictureLocation(
              problemEditDTO.getOutput(), oldDirectory, newDirectory));
          problemEditDTO.setHint(pictureService.modifyPictureLocation(
              problemEditDTO.getHint(), oldDirectory, newDirectory));
        } else {
          problemDTO = problemService.getProblemDTOByProblemId(problemEditDTO.getProblemId());
          if (problemDTO == null)
            throw new AppException("No such problem.");
        }

        problemDTO.setTitle(problemEditDTO.getTitle());
        problemDTO.setDescription(problemEditDTO.getDescription());
        problemDTO.setInput(problemEditDTO.getInput());
        problemDTO.setOutput(problemEditDTO.getOutput());
        problemDTO.setSampleInput(problemEditDTO.getSampleInput());
        problemDTO.setSampleOutput(problemEditDTO.getSampleOutput());
        problemDTO.setHint(problemEditDTO.getHint());
        problemDTO.setSource(problemEditDTO.getSource());

        problemDTO.setTimeLimit(problemEditDTO.getTimeLimit());
        problemDTO.setJavaTimeLimit(problemEditDTO.getJavaTimeLimit());
        problemDTO.setMemoryLimit(problemEditDTO.getMemoryLimit());
        problemDTO.setJavaMemoryLimit(problemEditDTO.getJavaMemoryLimit());
        problemDTO.setOutputLimit(problemEditDTO.getOutputLimit());
        problemDTO.setIsSpj(problemEditDTO.getIsSpj());

        Integer dataCount;
        if (problemEditDTO.getAction().equals("new")) {
          dataCount = fileService.moveProblemDataFile("new",
              problemDTO.getProblemId());
        } else {
          dataCount = fileService.moveProblemDataFile(problemDTO.getProblemId().toString(),
              problemDTO.getProblemId());
        }
        if (dataCount != 0) {
          problemDTO.setDataCount(dataCount);
        }

        problemService.updateProblem(problemDTO);
        json.put("result", "success");
        json.put("problemId", problemDTO.getProblemId());
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
  public
  @ResponseBody
  Map<String, Object> uploadProblemDataFile(
      @PathVariable("problemId") String sProblemId,
      @RequestParam(value = "uploadFile", required = true) MultipartFile[] files) {
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
          FileUploadDTO.builder()
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

  private Map<Integer, AuthorStatusType> getProblemStatus(UserDTO currentUser,
                                                                 HttpSession session) {
    Map<Integer, AuthorStatusType> problemStatus = new HashMap<>();
    try {
      if (currentUser != null) {
        List<Integer> triedProblems = statusService.
            findAllUserTriedProblemIds(currentUser.getUserId(), isAdmin(session));
        for (Integer result : triedProblems) {
          problemStatus.put(result, AuthorStatusType.FAIL);
        }
        List<Integer> acceptedProblems = statusService.
            findAllUserAcceptedProblemIds(currentUser.getUserId(), isAdmin(session));
        for (Integer result : acceptedProblems) {
          problemStatus.put(result, AuthorStatusType.PASS);
        }
      }
    } catch (AppException ignored) {
    }
    return problemStatus;
  }
}
