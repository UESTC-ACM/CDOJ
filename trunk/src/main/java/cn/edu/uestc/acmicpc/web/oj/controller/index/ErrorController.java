package cn.edu.uestc.acmicpc.web.oj.controller.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

/**
 * Controller for error information
 */
@Controller
@RequestMapping("/error")
public class ErrorController extends BaseController {

  @Autowired
  public ErrorController(DepartmentService departmentService, GlobalService globalService) {
    super(departmentService, globalService);
  }

  @RequestMapping("authenticationError")
  @LoginPermit(NeedLogin = false)
  public String authenticationError(ModelMap model) {
    model.put("message", "Please login as administrator first!");
    return "error/error";
  }
}
