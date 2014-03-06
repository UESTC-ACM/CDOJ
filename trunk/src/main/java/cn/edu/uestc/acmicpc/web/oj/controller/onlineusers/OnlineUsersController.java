package cn.edu.uestc.acmicpc.web.oj.controller.onlineusers;

import cn.edu.uestc.acmicpc.service.iface.OnlineUsersService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for onlineusers page.
 */
@Controller
@RequestMapping("/onlineusers")
public class OnlineUsersController extends BaseController {

  private OnlineUsersService onlineUsersService;

  @Autowired
  public void OnlineUsersController (OnlineUsersService onlineUsersService) {
    this.onlineUsersService = onlineUsersService;
  }
  
  @RequestMapping("list")
  @LoginPermit(NeedLogin = false)
  public String list() {
    return "onlineusers/onlineusersList";
  }

}
