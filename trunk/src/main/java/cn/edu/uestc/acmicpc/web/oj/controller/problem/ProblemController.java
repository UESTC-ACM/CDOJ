package cn.edu.uestc.acmicpc.web.oj.controller.problem;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpSession;

import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.web.view.PageInfo;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;


@Controller
@RequestMapping("/problem")
public class ProblemController extends BaseController{

  private final ProblemService problemService;
  private final StatusService statusService;
  private final LanguageService languageService;

  @Autowired
  public ProblemController(ProblemService problemService, StatusService statusService,
                           LanguageService languageService){
    this.problemService = problemService;
    this.statusService = statusService;
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
      ProblemDTO problemDTO = problemService.getProblemDTOByProblemId(problemId);
      if(problemDTO == null){
        throw new AppException("No such problem.");
      }
      model.put("targetProblem", problemDTO);
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
      Condition condition = problemCondition.getCondition();
      Long count = problemService.count(condition);
      PageInfo pageInfo = buildPageInfo(count, problemCondition.currentPage,
          Global.RECORD_PER_PAGE, "", null);

      List<ProblemListDTO> problemListDTOList = problemService.GetProblemListDTOList(
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















