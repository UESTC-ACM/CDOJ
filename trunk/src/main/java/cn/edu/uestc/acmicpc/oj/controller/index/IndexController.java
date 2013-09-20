package cn.edu.uestc.acmicpc.oj.controller.index;

import cn.edu.uestc.acmicpc.ioc.service.GlobalServiceAware;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.edu.uestc.acmicpc.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;

/**
 * Controller for home page.
 */
@Controller
@RequestMapping("/")
public class IndexController extends BaseController implements GlobalServiceAware {

  @Autowired
  private GlobalService globalService;

  @RequestMapping(method = RequestMethod.GET)
  @LoginPermit(NeedLogin = false)
  public String toIndex(ModelMap model) {
    model.put("message", "home page.");
    model.put("departmentList", globalService.getDepartmentList());
    return "index/index";
  }

  @Override
  public void setGlobalService(GlobalService globalService) {
    this.globalService = globalService;
  }
}
