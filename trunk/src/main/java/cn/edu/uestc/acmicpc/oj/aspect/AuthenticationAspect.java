package cn.edu.uestc.acmicpc.oj.aspect;

import cn.edu.uestc.acmicpc.db.dto.impl.UserRegisterDTO;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Authentication aspect
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@Component
@Aspect
public class AuthenticationAspect {

  /**
   * Http request
   */
  @Autowired(required = true)
  private HttpServletRequest request;

  @Around("@annotation(cn.edu.uestc.acmicpc.util.annotation.LoginPermit)")
  public Object checkAuth(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
    Method method = methodSignature.getMethod();
    LoginPermit permit = method.getAnnotation(LoginPermit.class);

    try {
      if (permit.NeedLogin()) {
        UserRegisterDTO userRegisterDTO = (UserRegisterDTO)request.getSession().getAttribute("currentUser");
        System.out.println("need login");
        if (userRegisterDTO == null)
          throw new AppException("Permission denied");
        if (permit.value() != Global.AuthenticationType.NORMAL) {
          if (userRegisterDTO.getType() != permit.value().ordinal()) {
            throw new AppException("Permission denied");
          }
        }
      }
      return proceedingJoinPoint.proceed();
    } catch (AppException e) {
      if (method.getReturnType() == String.class)
        //@TODO Need test
        return "index/index";
      else {
        Map<String, Object> json = new HashMap<>();
        json.put("result", "error");
        json.put("error_msg", e.getMessage());
        return json;
      }
    }
  }

}
