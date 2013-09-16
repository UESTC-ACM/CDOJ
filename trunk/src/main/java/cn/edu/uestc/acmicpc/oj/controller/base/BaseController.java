package cn.edu.uestc.acmicpc.oj.controller.base;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * BaseController
 * TODO: Exception handler description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@Controller
public class BaseController {

  /**
   * Redirect when exception happened
   *
   * @param modelMap model map
   * @param e exception
   * @return error view
   */
  @ExceptionHandler
  protected String exceptionRedirect(ModelMap modelMap, Exception e) {
    modelMap.put("error", e.getMessage());
    return "error";
  }

  /**
   * JSON result
   */
  protected Map<String, Object> json = new HashMap<>();

}
