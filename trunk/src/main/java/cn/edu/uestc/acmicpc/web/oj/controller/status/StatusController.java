package cn.edu.uestc.acmicpc.web.oj.controller.status;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.web.view.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description
 * TODO(mzry1992)
 */
@Controller
@RequestMapping("/status")
public class StatusController extends BaseController {

  private StatusService statusService;

  @Autowired
  public void setStatusService(StatusService statusService) {
    this.statusService = statusService;
  }

  /**
   * TODO(mzry1992)
   * @return
   */
  @RequestMapping("list")
  @LoginPermit(NeedLogin = false)
  public String list() {
    return "status/statusList";
  }

  /**
   * TODO(mzry1992)
   * @return
   */
  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody
  Map<String, Object> search(HttpSession session,
                             @RequestBody StatusCondition statusCondition) {
    Map<String, Object> json = new HashMap<>();
    try{
      UserDTO currentUser = (UserDTO) session.getAttribute("currentUser");
      statusCondition.contestId = -1;
      if(currentUser == null ||
          currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal())
        statusCondition.isVisible = true;
      Long count = statusService.count(statusCondition);
      PageInfo pageInfo = buildPageInfo(count, statusCondition.currentPage,
          Global.RECORD_PER_PAGE, "", null);
      List<StatusListDTO> statusListDTOList = statusService.getStatusList(statusCondition,
          pageInfo);

      json.put("result", "success");
      json.put("pageInfo", pageInfo.getHtmlString());
      json.put("statusList", statusListDTOList);
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
