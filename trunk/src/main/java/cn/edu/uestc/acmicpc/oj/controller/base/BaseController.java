package cn.edu.uestc.acmicpc.oj.controller.base;

import cn.edu.uestc.acmicpc.util.exception.FieldException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * BaseController
 * TODO: Exception handler description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@Controller
public class BaseController {

  public void putFieldErrosIntoBindingResult(FieldException fieldException, BindingResult validateResult) {
    for (FieldError error: fieldException) {
      validateResult.addError(error);
    }
  }
}
