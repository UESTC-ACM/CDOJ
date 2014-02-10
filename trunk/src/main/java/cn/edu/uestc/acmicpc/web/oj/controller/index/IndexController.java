package cn.edu.uestc.acmicpc.web.oj.controller.index;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

/**
 * Controller for home page.
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

  private DepartmentService departmentService;
  private GlobalService globalService;
  private LanguageService languageService;

  @Autowired
  public IndexController(DepartmentService departmentService, GlobalService globalService, LanguageService languageService) {
    this.departmentService = departmentService;
    this.globalService = globalService;
    this.languageService = languageService;
  }

  @RequestMapping(value = {"index", "/"}, method = RequestMethod.GET)
  @LoginPermit(NeedLogin = false)
  public String index(ModelMap model) {
    model.put("message", "home page.");
    return "index/index";
  }

  @RequestMapping(value="globalData")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> globalData(HttpSession session) {
    Map<String, Object> result = new HashMap<>();
    try {
      result.put("departmentList", departmentService.getDepartmentList());
      result.put("authenticationTypeList", globalService.getAuthenticationTypeList());
      result.put("languageList", languageService.getLanguageList());
      result.put("resultTypeList", globalService.getOnlineJudgeResultTypeList());
      result.put("contestTypeList", globalService.getContestTypeList());
      UserDTO currentUser = getCurrentUser(session);
      if (currentUser == null) {
        result.put("hasLogin", false);
      } else {
        result.put("hasLogin", true);
        result.put("currentUser", currentUser);
      }
    } catch (AppException e) {
      result.put("result", "error");
      result.put("error_msg", e.getMessage());
    }
    return result;
  }
}
