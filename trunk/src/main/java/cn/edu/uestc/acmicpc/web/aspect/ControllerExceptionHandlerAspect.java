package cn.edu.uestc.acmicpc.web.aspect;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

/**
 * Description
 */
@Component
@Aspect
@Order(0)
public class ControllerExceptionHandlerAspect {

  @Around("execution(* cn.edu.uestc.acmicpc.web.oj.controller..*.*(..))")
  public Object exceptionHandler(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
    Method method = methodSignature.getMethod();

    Object proceedResult;
    try {
      proceedResult = proceedingJoinPoint.proceed();
    } catch (FieldException e) {
      if (method.getReturnType() == Map.class) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "field_error");
        List<FieldError> fieldErrorList = new LinkedList<>();
        for (FieldError error: e) {
          fieldErrorList.add(error);
        }
        result.put("field", fieldErrorList);
        return result;
      } else {
        // TODO(mzry1992) redirect to error page.
        throw new Exception("Unknown exception occurred.");
      }
    } catch (AppException e) {
      if (method.getReturnType() == Map.class) {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "error");
        result.put("error_msg", e.getMessage());
        return result;
      } else {
        // TODO(mzry1992) redirect to error page.
        throw new Exception("Unknown exception occurred.");
      }
    }

    return proceedResult;
  }

}
