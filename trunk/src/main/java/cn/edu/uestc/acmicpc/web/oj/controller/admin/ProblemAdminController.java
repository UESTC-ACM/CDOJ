package cn.edu.uestc.acmicpc.web.oj.controller.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDataEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDataShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemEditorShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemListDTO;
import cn.edu.uestc.acmicpc.service.iface.FileService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.web.view.PageInfo;

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

  /**
   * Show problem list in admin dashboard
   * @return problemList view
   */
  @RequestMapping("list")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public String list() {
    return "admin/problem/problemList";
  }


  /**
   * Search problem
   *
   * @param problemCondition
   * @return json
   */
  @RequestMapping("search")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public @ResponseBody
  Map<String,Object> search(@RequestBody ProblemCondition problemCondition){
    Map<String, Object> json = new HashMap<>();
    try{
      problemCondition.isTitleEmpty = false;
      Long count = problemService.count(problemCondition);
      PageInfo pageInfo = buildPageInfo(count, problemCondition.currentPage,
          Global.RECORD_PER_PAGE, "", null);

      List<ProblemListDTO> problemListDTOList = problemService.getProblemListDTOList(
          problemCondition, pageInfo);

      json.put("pageInfo", pageInfo.getHtmlString());
      json.put("result", "success");
      json.put("problemList", problemListDTOList);
    }catch(AppException e){
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }catch(Exception e){
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  /**
   * TODO(mzry1992)
   *
   * @param targetId
   * @param field
   * @param value
   * @return
   */
  @RequestMapping("operator/{id}/{field}/{value}")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public @ResponseBody
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

  @RequestMapping("editor/{problemId}")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public String editor(@PathVariable("problemId") Integer problemId,
                       ModelMap model) {
    try {
      if (problemId == 0) {
        ProblemCondition problemCondition= new ProblemCondition();
        problemCondition.isTitleEmpty = true;
        Long count = problemService.count(problemCondition);
        if (count == 0) {
          problemId = problemService.createNewProblem();
        } else {
          PageInfo pageInfo = buildPageInfo(count, problemCondition.currentPage,
              Global.RECORD_PER_PAGE, "", null);
          List<ProblemListDTO> problemListDTOList =
              problemService.getProblemListDTOList(problemCondition, pageInfo);
          if (problemListDTOList == null || problemListDTOList.size() == 0)
            throw new AppException("Add new problem error.");
          ProblemListDTO problemListDTO = problemListDTOList.get(0);
          problemId = problemListDTO.getProblemId();
        }

        return "redirect:/admin/problem/editor/" + problemId;
      } else {
        ProblemEditorShowDTO targetProblem = problemService.getProblemEditorShowDTO(problemId);
        if (targetProblem == null)
          throw new AppException("No such problem.");
        model.put("targetProblem", targetProblem);
      }
    } catch (AppException e) {
      model.put("message", e.getMessage());
      return "error/error";
    }
    return "admin/problem/problemEditor";
  }

  @RequestMapping("edit")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public @ResponseBody
  Map<String, Object> edit(@RequestBody @Valid ProblemEditDTO problemEditDTO,
                           BindingResult validateResult) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (StringUtil.trimAllSpace(problemEditDTO.getTitle()).equals(""))
        throw new FieldException("title", "Please enter a validate title.");
      ProblemDTO problemDTO = problemService.getProblemDTOByProblemId(problemEditDTO.getProblemId());
      if (problemDTO == null)
        throw new AppException("No such problem.");

      problemDTO.setTitle(problemEditDTO.getTitle());
      problemDTO.setDescription(problemEditDTO.getDescription());
      problemDTO.setInput(problemEditDTO.getInput());
      problemDTO.setOutput(problemEditDTO.getOutput());
      problemDTO.setSampleInput(problemEditDTO.getSampleInput());
      problemDTO.setSampleOutput(problemEditDTO.getSampleOutput());
      problemDTO.setHint(problemEditDTO.getHint());
      problemDTO.setSource(problemEditDTO.getSource());

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

  @RequestMapping("getUploadedPictures/{problemId}")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public @ResponseBody
  Map<String, Object> pictureList(@PathVariable("problemId") Integer problemId) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (problemId == null)
        throw new AppException("Error, target problem id is null!");
      ProblemDTO problemDTO = problemService.getProblemDTOByProblemId(problemId);
      if (problemDTO == null)
        throw new AppException("Error, target problem doesn't exist!");
      ArrayList<String> pictures = fileService.getProblemPictures(problemDTO.getProblemId());
      json.put("success", "true");
      json.put("pictures", pictures);
    } catch (AppException e) {
      json.put("error", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("error", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping(value = "uploadProblemPicture/{problemId}",
      method = RequestMethod.POST)
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public @ResponseBody
  Map<String, Object> uploadPicture(@PathVariable("problemId") Integer problemId,
                                    @RequestParam(value="uploadFile", required=true)
                                    MultipartFile[] files) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (problemId == null)
        throw new AppException("Error, target problem id is null!");
      ProblemDTO problemDTO = problemService.getProblemDTOByProblemId(problemId);
      if (problemDTO == null)
        throw new AppException("Error, target problem doesn't exist!");

      FileInformationDTO fileInformationDTO = fileService.uploadProblemPictures(
          FileUploadDTO.builder()
          .setFiles(Arrays.asList(files))
          .build(),
          problemId);

      json.put("success", "true");
      json.put("uploadedFile", fileInformationDTO.getFileName());
      json.put("uploadedFileUrl", fileInformationDTO.getFileURL());
    } catch (AppException e) {
      e.printStackTrace();
      json.put("error", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("error", "Unknown exception occurred.");
    }
    return json;
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
