package cn.edu.uestc.acmicpc.oj.controller.index;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
public class IndexController extends BaseController {

  private static final Logger LOGGER = LogManager.getLogger(IndexController.class);

  @RequestMapping(method = RequestMethod.GET)
  @LoginPermit(NeedLogin = false)
  public String toIndex(ModelMap model) {
    LOGGER.debug("call toIndex successfully.");
    model.put("message", "home page.");
    return "index/index";
  }

}
