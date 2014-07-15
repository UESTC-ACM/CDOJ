package cn.edu.uestc.acmicpc.web.oj.controller.index;

import cn.edu.uestc.acmicpc.db.condition.impl.MessageCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.service.iface.MessageService;
import cn.edu.uestc.acmicpc.service.iface.OnlineUsersService;
import cn.edu.uestc.acmicpc.service.iface.RecentContestService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.ContestRegistryStatusType;
import cn.edu.uestc.acmicpc.util.enums.ContestType;
import cn.edu.uestc.acmicpc.util.enums.GenderType;
import cn.edu.uestc.acmicpc.util.enums.GradeType;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeResultType;
import cn.edu.uestc.acmicpc.util.enums.TShirtsSizeType;
import cn.edu.uestc.acmicpc.util.enums.TrainingPlatformType;
import cn.edu.uestc.acmicpc.util.enums.TrainingUserType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
  private OnlineUsersService onlineUsersService;
  private RecentContestService recentContestService;

  @Autowired
  public IndexController(DepartmentService departmentService, GlobalService globalService,
                         LanguageService languageService, MessageService messageService,
                         OnlineUsersService onlineUsersService,
                         RecentContestService recentContestService) {
    this.departmentService = departmentService;
    this.globalService = globalService;
    this.languageService = languageService;
    this.messageService = messageService;
    this.onlineUsersService = onlineUsersService;
    this.recentContestService = recentContestService;
  }

  @RequestMapping("/")
  public String index() {
    return "index";
  }

  /**
   * Force redirect to this page when user use IE 6 7 8
   */
  @RequestMapping("fuckIE")
  public String fuckIE() {
    return "fuckIE";
  }

  @RequestMapping("recentContest")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> recentContestList() {
    Map<String, Object> result = new HashMap<>();
    result.put("recentContestList", recentContestService.getRecentContestList());
    return result;
  }

  @RequestMapping("data")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> data(HttpSession session) {
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
      Integer onlineUsersCount = onlineUsersService.getNumberOfOnlineUsers();
      result.put("onlineUsersCount", onlineUsersCount);
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
  Map<String, Object> globalData() {
    Map<String, Object> result = new HashMap<>();
    result.put("departmentList", departmentService.getDepartmentList());
    result.put("authenticationTypeList", globalService.getEnumTypeList("authenticationTypeId", AuthenticationType.values()));
    result.put("languageList", languageService.getLanguageList());
    result.put("resultTypeList", globalService.getEnumTypeList("onlineJudgeResultTypeId", OnlineJudgeResultType.values()));
    result.put("contestTypeList", globalService.getEnumTypeList("contestTypeId", ContestType.values()));
    result.put("genderTypeList", globalService.getEnumTypeList("genderTypeId", GenderType.values()));
    result.put("gradeTypeList", globalService.getEnumTypeList("gradeTypeId", GradeType.values()));
    result.put("tShirtsSizeTypeList", globalService.getEnumTypeList("sizeTypeId", TShirtsSizeType.values()));
    result.put("contestRegistryStatusList", globalService.getEnumTypeList("statusId", ContestRegistryStatusType.values()));
    result.put("trainingUserTypeList", globalService.getEnumTypeList("trainingUserTypeId", TrainingUserType.values()));
    result.put("trainingPlatformTypeList", globalService.getEnumTypeList("trainingPlatformTypeId", TrainingPlatformType.values()));
    result.put("result", "success");
    return result;
  }
}
