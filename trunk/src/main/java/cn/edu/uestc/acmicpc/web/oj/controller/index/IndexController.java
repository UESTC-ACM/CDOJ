package cn.edu.uestc.acmicpc.web.oj.controller.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;

/**
 * Controller for home page.
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

  private DepartmentService departmentService;

  @Autowired
  public void setDepartmentService(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @RequestMapping(method = RequestMethod.GET)
  @LoginPermit(NeedLogin = false)
  public String index(ModelMap model) {
    model.put("message", "home page.");
    model.put("departmentList", departmentService.getDepartmentList());
    return "index/index";
  }

}
