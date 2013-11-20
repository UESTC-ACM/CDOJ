package cn.edu.uestc.acmicpc.web.oj.controller.admin;

import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDataEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDataShowDTO;
import cn.edu.uestc.acmicpc.service.iface.FileService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * Description
 * TODO(mzry1992)
 */
@Controller
@RequestMapping("/admin/problem")
public class ProblemAdminController extends BaseController {

  private ProblemService problemService;
  private FileService fileService;

  @Autowired
  public void setFileService(FileService fileService) {
    this.fileService = fileService;
  }

  @Autowired
  public void setProblemService(ProblemService problemService) {
    this.problemService = problemService;
  }

  @RequestMapping("data/{problemId}")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public String dataEditor(@PathVariable("problemId") Integer problemId,
                           ModelMap model) {
    try {
      ProblemDataShowDTO targetProblem = problemService.getProblemDataShowDTO(problemId);
      if (targetProblem == null)
        throw new AppException("No such problem.");
      model.put("targetProblem", targetProblem);
    } catch (AppException e) {
      model.put("message", e.getMessage());
      return "error/error";
    }
    return "admin/problem/problemDataEditor";
  }

  @RequestMapping(value = "uploadProblemDataFile/{problemId}",
      method = RequestMethod.POST)
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public @ResponseBody
  Map<String, Object> uploadProblemDataFile(@PathVariable("problemId") Integer problemId,
                                    @RequestParam(value="uploadFile", required=true)
                                    MultipartFile[] files) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (problemId == null)
        throw new AppException("Error, target problem id is null!");
      ProblemDTO problemDTO = problemService.getProblemDTOByProblemId(problemId);
      if (problemDTO == null)
        throw new AppException("Error, target problem doesn't exist!");

      Integer dataCount = fileService.uploadProblemDataFile(
          FileUploadDTO.builder()
              .setFiles(Arrays.asList(files))
              .build(),
          problemId);

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

  @RequestMapping("updateProblemData")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public @ResponseBody
  Map<String, Object> updateProblemData(@RequestBody @Valid ProblemDataEditDTO problemDataEditDTO,
                                        BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (problemDataEditDTO.getTimeLimit() < 0 ||
          problemDataEditDTO.getTimeLimit() > 60000)
        throw new FieldException("timeLimit", "Time limit should between 0 and 60000");
      if (problemDataEditDTO.getJavaTimeLimit() < 0 ||
          problemDataEditDTO.getJavaTimeLimit() > 60000)
        throw new FieldException("javaTimeLimit", "Time limit should between 0 and 60000");
      if (problemDataEditDTO.getMemoryLimit() < 0 ||
          problemDataEditDTO.getMemoryLimit() > 262144)
        throw new FieldException("memoryLimit", "Memory limit should between 0 and 262144");
      if (problemDataEditDTO.getJavaMemoryLimit() < 0 ||
          problemDataEditDTO.getJavaMemoryLimit() > 262144)
        throw new FieldException("javaMemoryLimit", "Memory limit should between 0 and 262144");
      if (problemDataEditDTO.getOutputLimit() < 0 ||
          problemDataEditDTO.getOutputLimit() > 262144)
        throw new FieldException("outputLimit", "Output limit should between 0 and 262144");
      ProblemDTO problemDTO = problemService.getProblemDTOByProblemId(problemDataEditDTO.getProblemId());
      if (problemDTO == null)
        throw new AppException("No such problem.");

      problemDTO.setTimeLimit(problemDataEditDTO.getTimeLimit());
      problemDTO.setJavaTimeLimit(problemDataEditDTO.getJavaTimeLimit());
      problemDTO.setMemoryLimit(problemDataEditDTO.getMemoryLimit());
      problemDTO.setJavaMemoryLimit(problemDataEditDTO.getJavaMemoryLimit());
      problemDTO.setOutputLimit(problemDataEditDTO.getOutputLimit());
      problemDTO.setIsSpj(problemDataEditDTO.getIsSpj());

      Integer dataCount = fileService.moveProblemDataFile(problemDTO.getProblemId());
      if (dataCount != 0)
        problemDTO.setDataCount(dataCount);

      problemService.updateProblem(problemDTO);
      json.put("result", "success");
    } catch (FieldException e) {
      putFieldErrorsIntoBindingResult(e, validateResult);
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

}
