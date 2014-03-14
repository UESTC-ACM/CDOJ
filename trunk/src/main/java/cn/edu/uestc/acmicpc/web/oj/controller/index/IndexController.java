package cn.edu.uestc.acmicpc.web.oj.controller.index;

import cn.edu.uestc.acmicpc.db.condition.impl.MessageCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.service.iface.MessageService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;

/**
 * Controller for home page.
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

  private DepartmentService departmentService;
  private GlobalService globalService;
  private LanguageService languageService;
  private MessageService messageService;

  @Autowired
  public IndexController(DepartmentService departmentService, GlobalService globalService,
                         LanguageService languageService, MessageService messageService) {
    this.departmentService = departmentService;
    this.globalService = globalService;
    this.languageService = languageService;
    this.messageService = messageService;
  }

  @RequestMapping(value = {"index", "/"}, method = RequestMethod.GET)
  public String index(ModelMap model) {
    model.put("message", "home page.");
    return "index";
  }

  @RequestMapping(value = "userData")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> userData(HttpSession session) {
    Map<String, Object> result = new HashMap<>();
    try {
      UserDTO currentUser = getCurrentUser(session);
      if (currentUser == null) {
        result.put("hasLogin", false);
      } else {
        result.put("hasLogin", true);
        result.put("currentUser", currentUser);
        MessageCondition messageCondition = new MessageCondition();
        messageCondition.receiverId = currentUser.getUserId();
        messageCondition.isOpened = false;
        messageCondition.orderFields = "time";
        messageCondition.orderAsc = "false";
        Long totalUnreadMessage = messageService.count(messageCondition);
        result.put("totalUnreadMessages", totalUnreadMessage);
        // Show first 10 unread messages
        PageInfo pageInfo = buildPageInfo(totalUnreadMessage, 1L,
            10L, null);
        result.put("unreadMessages", messageService.getMessageForReceiverDTOList(messageCondition,
            pageInfo));
      }
      result.put("result", "success");
    } catch (AppException e) {
      result.put("result", "error");
      result.put("error_msg", e.getMessage());
    }
    return result;
  }

  @RequestMapping(value = "globalData")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> globalData(HttpSession session) {
    Map<String, Object> result = new HashMap<>();
    result.put("departmentList", departmentService.getDepartmentList());
    result.put("authenticationTypeList", globalService.getAuthenticationTypeList());
    result.put("languageList", languageService.getLanguageList());
    result.put("resultTypeList", globalService.getOnlineJudgeResultTypeList());
    result.put("contestTypeList", globalService.getContestTypeList());
    result.put("genderTypeList", globalService.getGenderTypeList());
    result.put("gradeTypeList", globalService.getGradeTypeList());
    result.put("tShirtsSizeTypeList", globalService.getTShirtsSizeTypeList());
    result.put("contestRegistryStatusList", globalService.getContestRegistryStatusList());
    result.put("result", "success");
    return result;
  }
}
