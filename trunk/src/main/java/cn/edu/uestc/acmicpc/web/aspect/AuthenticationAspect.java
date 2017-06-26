package cn.edu.uestc.acmicpc.web.aspect;

import cn.edu.uestc.acmicpc.db.dto.impl.UserDto;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.lang.reflect.Method;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Authentication aspect
 */
@Component
@Aspect
@Order(1)
public class AuthenticationAspect {

  /**
   * Http request
   */
  private HttpServletRequest request;

  @Autowired()
  public void setRequest(HttpServletRequest request) {
    this.request = request;
  }

  @SuppressWarnings("unchecked")
  @Around("@annotation(cn.edu.uestc.acmicpc.util.annotation.LoginPermit)")
  public Map<String, Object> checkAuth(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
    Method method = methodSignature.getMethod();
    LoginPermit permit = method.getAnnotation(LoginPermit.class);

    if (permit.NeedLogin()) {
      UserDto userDto = (UserDto) request.getSession().getAttribute("currentUser");
      if (userDto == null) {
        throw new AppException("Please login first.");
      }
      if (permit.value() != AuthenticationType.NORMAL) {
        if (userDto.getType() != permit.value().ordinal()) {
          throw new AppException("Permission denied");
        }
      }
    }
    return (Map<String, Object>) proceedingJoinPoint.proceed();
  }

}
