package cn.edu.uestc.acmicpc.web.oj.controller.admin;

import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Administrator controller.
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

  public AdminController() {
  }

  @RequestMapping(value = { "index", "/" })
  @LoginPermit(AuthenticationType.ADMIN)
  public String index() {
    return "admin/index";
  }
}
