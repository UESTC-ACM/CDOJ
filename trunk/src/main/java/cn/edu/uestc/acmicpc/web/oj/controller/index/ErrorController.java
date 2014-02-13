package cn.edu.uestc.acmicpc.web.oj.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

/**
 * Controller for error information
 */
@Controller
@RequestMapping("/error")
public class ErrorController extends BaseController {

  public ErrorController() {
  }

  @RequestMapping("authenticationError")
  @LoginPermit(NeedLogin = false)
  public String authenticationError(ModelMap model) {
    model.put("message", "Please login as administrator first!");
    return "error/error";
  }
}
