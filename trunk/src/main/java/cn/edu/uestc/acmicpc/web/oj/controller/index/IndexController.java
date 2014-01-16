package cn.edu.uestc.acmicpc.web.oj.controller.index;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

/**
 * Controller for home page.
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController {

  @Autowired
  public IndexController(DepartmentService departmentService, GlobalService globalService) {
    super(departmentService, globalService);
  }

  @RequestMapping(value={"index", "/"}, method = RequestMethod.GET)
  @LoginPermit(NeedLogin = false)
  public String index(ModelMap model) {
    model.put("message", "home page.");
    return "index/index";
  }

}
