package cn.edu.uestc.acmicpc.web.oj.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

/**
 * Administrator controller for these pages:
 * <table>
 *   <tr><td>admin/index</td><td>dashboard</td></tr>
 * </table>
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

  @Autowired
  public AdminController(DepartmentService departmentService, GlobalService globalService) {
    super(departmentService, globalService);
  }

  @RequestMapping(value={"index", "/"})
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public String index() {
    return "admin/index";
  }
}
