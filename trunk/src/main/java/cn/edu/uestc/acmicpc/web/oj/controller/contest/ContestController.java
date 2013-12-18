
package cn.edu.uestc.acmicpc.web.oj.controller.contest;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
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
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestEditorShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDTO;
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
      ContestDTO contestDTO = contestService.
          getContestDTO(contestId);
      if(contestDTO == null) {
        throw new AppException("NO such contest");
      }
      List<ContestProblemDTO> contestProblemList = contestProblemService.
          getContestProblemDTOListByContestId(contestId);
      Collections.sort(contestProblemList, new Comparator<ContestProblemDTO>() {

        @Override
        public int compare(ContestProblemDTO a, ContestProblemDTO b) {
          // TODO Auto-generated method stub
          return a.getOrder().compareTo(b.getOrder());
        }
      });
      model.put("targetContest", contestDTO);
      model.put("brToken", "\n");
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
      Long count = contestService.count(contestCondition);
      PageInfo pageInfo = buildPageInfo(count, contestCondition.currentPage,
          Global.RECORD_PER_PAGE, "", null);
      List<ContestListDTO> contestListDTOList = contestService.
          getContestListDTOList(contestCondition, pageInfo);
      json.put("pageInfo", pageInfo.getHtmlString());
      json.put("result", "success");
      json.put("list", contestListDTOList);
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
   * Modify special field of contest
   *
   * @param targetId
   *          contest id
   * @param field
   *          field want to modified
   * @param value
   *          value
   * @return JSON
   */
  @RequestMapping("operator/{id}/{field}/{value}")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public @ResponseBody
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

  /**
   * Open contest editor
   *
   * @param sContestId
   *          target contest id or "new"
   * @param model
   *          model
   * @return editor view
   */
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
        model.put("action", "edit");
        model.put("targetContest", targetContest);
      }
      model.put("contestTypeList", globalService.getContestTypeList());
    } catch (AppException e) {
      model.put("message", e.getMessage());
      return "error/error";
    }
    return "/contest/contestEditor";
  }

}
