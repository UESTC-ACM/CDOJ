package cn.edu.uestc.acmicpc.oj.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import cn.edu.uestc.acmicpc.util.exception.FieldException;

/**
 * BaseController TODO: Exception handler description
 */
@Controller
public class BaseController {

  public void putFieldErrorsIntoBindingResult(FieldException fieldException,
      BindingResult validateResult) {
    for (FieldError error : fieldException) {
      validateResult.addError(error);
    }
  }
}
