package cn.edu.uestc.acmicpc.web.oj.controller.problem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.web.view.PageInfo;


@Controller
@RequestMapping("/problem")
public class ProblemController extends BaseController{

  private ProblemService problemService;
  private StatusService statusService;
  private LanguageService languageService;

  @Autowired
  public void setProblemService(ProblemService problemService) {
    this.problemService = problemService;
  }

  @Autowired
  public void setStatusService(StatusService statusService) {
    this.statusService = statusService;
  }

  @Autowired
  public void setLanguageService(LanguageService languageService) {
    this.languageService = languageService;
  }

  /**
   * Show a problem
   *
   * @param problemId
   * @return String
   */
  @RequestMapping("show/{problemId}")
  @LoginPermit(NeedLogin = false)
  public String show(@PathVariable("problemId") Integer problemId, ModelMap model){
    try{
      ProblemShowDTO problemShowDTO = problemService.getProblemShowDTO(problemId);
      if(problemShowDTO == null){
        throw new AppException("No such problem.");
      }
      model.put("targetProblem", problemShowDTO);
      model.put("brToken", "\n");
      model.put("languageList", languageService.getLanguageList());
    }catch (AppException e){
      return "error/404";
    }catch (Exception e){
      e.printStackTrace();
      return "error/404";
    }
    return "problem/problemShow";
  }

  /**
   * Show problem list
   *
   * @return String
   */
  @RequestMapping("list")
  @LoginPermit(NeedLogin = false)
  public String list(){
    return "problem/problemList";
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
  public @ResponseBody Map<String,Object> search(HttpSession session,
                                                 @RequestBody ProblemCondition problemCondition){
    Map<String, Object> json = new HashMap<>();
    try{
      UserDTO currentUser = (UserDTO) session.getAttribute("currentUser");
      if(currentUser == null ||
          currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal()){
        problemCondition.isVisible = true;
      }
      problemCondition.isTitleEmpty = false;
      Long count = problemService.count(problemCondition);
      PageInfo pageInfo = buildPageInfo(count, problemCondition.currentPage,
          Global.RECORD_PER_PAGE, "", null);

      List<ProblemListDTO> problemListDTOList = problemService.getProblemListDTOList(
          problemCondition, pageInfo);

      Map<Integer, Global.AuthorStatusType> problemStatus = GetProblemStatus(currentUser);

      for(ProblemListDTO problemListDTO: problemListDTOList){
        if (problemStatus.get(problemListDTO.getProblemId()) ==
            Global.AuthorStatusType.PASS){
          problemListDTO.setStatus(1);
        }
        else if (problemStatus.containsKey(problemListDTO.getProblemId())) {
          problemListDTO.setStatus(2);
        }
      }

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

  private Map<Integer, Global.AuthorStatusType> GetProblemStatus(UserDTO currentUser) {
    Map<Integer, Global.AuthorStatusType> problemStatus = new HashMap<>();
    try{
      if(currentUser != null){

        List<Integer> acceptedProblems = statusService.
            findAllUserAcceptedProblemIds(currentUser.getUserId());

        for (Integer result : acceptedProblems){
          problemStatus.put(result, Global.AuthorStatusType.PASS);
        }

        List<Integer> triedProblems = statusService.
            findAllUserTriedProblemIds(currentUser.getUserId());

        for (Integer result : triedProblems){
          problemStatus.put(result, Global.AuthorStatusType.FAIL);
        }

      }
    }catch(AppException ignored){
    }
    return problemStatus;
  }
}















