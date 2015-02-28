package cn.edu.uestc.acmicpc.web.oj.controller.index;

import cn.edu.uestc.acmicpc.db.condition.impl.MessageCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.service.iface.MessageService;
import cn.edu.uestc.acmicpc.service.iface.RecentContestService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.ContestRegistryStatusType;
import cn.edu.uestc.acmicpc.util.enums.ContestType;
import cn.edu.uestc.acmicpc.util.enums.GenderType;
import cn.edu.uestc.acmicpc.util.enums.GradeType;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeResultType;
import cn.edu.uestc.acmicpc.util.enums.TShirtsSizeType;
import cn.edu.uestc.acmicpc.util.enums.TrainingContestType;
import cn.edu.uestc.acmicpc.util.enums.TrainingPlatformType;
import cn.edu.uestc.acmicpc.util.enums.TrainingResultFieldType;
import cn.edu.uestc.acmicpc.util.enums.TrainingUserType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.EnumTypeUtil;
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

  private final DepartmentService departmentService;
  private final LanguageService languageService;
  private final MessageService messageService;
  private final RecentContestService recentContestService;

  @Autowired
  public IndexController(DepartmentService departmentService,
      LanguageService languageService, MessageService messageService,
      RecentContestService recentContestService) {
    this.departmentService = departmentService;
    this.languageService = languageService;
    this.messageService = messageService;
    this.recentContestService = recentContestService;
  }

  @RequestMapping("/")
  public String index() {
    return "index";
  }

  @RequestMapping("/v2")
  public String index2() {
    return "index2";
  }

  /**
   * Force redirect to this page when user use IE 6 7 8
   * @return
   */
  @RequestMapping("fuckIE")
  public String fuckIE() {
    return "fuckIE";
  }

  @RequestMapping("recentContest")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> recentContestList() {
    Map<String, Object> result = new HashMap<>();
    result.put("recentContestList", recentContestService.getRecentContestList());
    return result;
  }

  @RequestMapping("data")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> data(HttpSession session) {
    Map<String, Object> result = new HashMap<>();
    try {
      UserDto currentUser = getCurrentUser(session);
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
        result.put("unreadMessages", messageService.getMessageForReceiverDtoList(messageCondition,
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
  public @ResponseBody Map<String, Object> globalData() {
    Map<String, Object> result = new HashMap<>();
    result.put("departmentList", departmentService.getDepartmentList());
    result.put("authenticationTypeList",
        EnumTypeUtil.getEnumTypeList("authenticationTypeId", AuthenticationType.values()));
    result.put("languageList", languageService.getLanguageList());
    result.put("resultTypeList",
        EnumTypeUtil.getEnumTypeList("onlineJudgeResultTypeId", OnlineJudgeResultType.values()));
    result.put("contestTypeList",
        EnumTypeUtil.getEnumTypeList("contestTypeId", ContestType.values()));
    result.put("genderTypeList", EnumTypeUtil.getEnumTypeList("genderTypeId", GenderType.values()));
    result.put("gradeTypeList", EnumTypeUtil.getEnumTypeList("gradeTypeId", GradeType.values()));
    result.put("tShirtsSizeTypeList",
        EnumTypeUtil.getEnumTypeList("sizeTypeId", TShirtsSizeType.values()));
    result.put("contestRegistryStatusList",
        EnumTypeUtil.getEnumTypeList("statusId", ContestRegistryStatusType.values()));
    result.put("trainingUserTypeList",
        EnumTypeUtil.getEnumTypeList("trainingUserTypeId", TrainingUserType.values()));
    result.put("trainingPlatformTypeList",
        EnumTypeUtil.getEnumTypeList("trainingPlatformTypeId", TrainingPlatformType.values()));
    result.put("trainingContestTypeList",
        EnumTypeUtil.getEnumTypeList("trainingContestTypeId", TrainingContestType.values()));
    result
        .put(
            "trainingResultFieldTypeList",
            EnumTypeUtil.getEnumTypeList("trainingResultFieldTypeId",
                TrainingResultFieldType.values()));
    result.put("result", "success");
    return result;
  }
}
