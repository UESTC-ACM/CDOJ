package cn.edu.uestc.acmicpc.util.annotation;

import cn.edu.uestc.acmicpc.util.type.AuthenticationType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Login permission controller.
 * <p/>
 * Use this annotation to validate users' types.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginPermit {

  /**
   * Set user type needed. The user type can refer to
   * {@link AuthenticationType}.
   *
   * @return User type needed.
   * @see AuthenticationType
   */
  public AuthenticationType value() default AuthenticationType.NORMAL;

  /**
   * Need user toLogin or not
   *
   * @return if this action will need user toLogin, set it true.
   */
  public boolean NeedLogin() default true;
}
