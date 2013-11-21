
package cn.edu.uestc.acmicpc.web.oj.controller.contest;

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

import cn.edu.uestc.acmicpc.db.condition.impl.ContestCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestStatusShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.ContestProblemService;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.web.view.PageInfo;

/**
 *
 * @author liverliu
 *
 */
@Controller
@RequestMapping("/contest")
public class ContestController extends BaseController{

  private ContestService contestService;
  private LanguageService languageService;
  private ContestProblemService contestProblemService;

  @Autowired
  public void setContestService(ContestService contestService) {
    this.contestService = contestService;
  }

  @Autowired
  public void setLanguageService(LanguageService languageService) {
    this.languageService = languageService;
  }

  @Autowired
  public void setContestProblemService(ContestProblemService contestProblemService) {
    this.contestProblemService = contestProblemService;
  }

  /**
   * Show a contest.
   *
   * @param contestId
   * @param model
   * @return
   */
  @RequestMapping("show/{contestId}")
  @LoginPermit(NeedLogin = false)
  public String show(@PathVariable("contestId") Integer contestId, ModelMap model) {
    try {
      ContestStatusShowDTO contestStatusShowDTO = contestService.
          getcontestStatusShowDTOByContestId(contestId);
      if(contestStatusShowDTO == null) {
        throw new AppException("NO such contest");
      }
      List<ContestProblemDTO> contestProblemList = contestProblemService.
          getContestProblemDTOListByContestId(contestId);
      model.put("targetcontest", contestStatusShowDTO);
      model.put("contestProblems", contestProblemList);
      model.put("languageList", languageService.getLanguageList());
    }catch (AppException e){
      return "error/404";
    }catch (Exception e){
      e.printStackTrace();
      return "error/404";
    }
    return "contest/contestShow";
  }

  /**
   * Show contest list.
   *
   * @return String
   */
  @RequestMapping("list")
  @LoginPermit(NeedLogin = false)
  public String list() {
    return "contest/contestList";
  }

  /**
   * Search contest
   *
   * @param session
   * @param contestCondition
   * @return
   */
  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> search(HttpSession session,
      @RequestBody ContestCondition contestCondition){
    Map<String, Object> json = new HashMap<>();
    try{
      UserDTO currentUser = (UserDTO) session.getAttribute("currentUser");
      if(currentUser == null ||
          currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal()){
        contestCondition.isVisible = true;
      }
      contestCondition.isTitleEmpty = false;
      Long count = contestService.count(contestCondition);
      PageInfo pageInfo = buildPageInfo(count, contestCondition.currentPage,
          Global.RECORD_PER_PAGE, "", null);
      List<ContestListDTO> contestListDTOList = contestService.
          getContestListDTOList(contestCondition, pageInfo);
      json.put("pageInfo", pageInfo.getHtmlString());
      json.put("result", "success");
      json.put("contestList", contestListDTOList);
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
}
