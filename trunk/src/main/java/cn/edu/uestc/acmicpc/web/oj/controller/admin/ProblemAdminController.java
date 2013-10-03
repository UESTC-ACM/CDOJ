package cn.edu.uestc.acmicpc.web.oj.controller.admin;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemListDTO;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.web.view.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * TODO(mzry1992)
 */
@Controller
@RequestMapping("/admin/problem")
public class ProblemAdminController extends BaseController {

  private ProblemService problemService;

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
      Condition condition = problemCondition.getCondition();
      Long count = problemService.count(condition);
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
        Condition condition = problemCondition.getCondition();
        Long count = problemService.count(condition);
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
        ProblemDTO targetProblem = problemService.getProblemDTOByProblemId(problemId);
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
}
