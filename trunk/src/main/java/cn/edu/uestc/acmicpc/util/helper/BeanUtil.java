package cn.edu.uestc.acmicpc.util.helper;

import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Manage methods for Spring Beans.
 */
@Component
public class BeanUtil {

  @Autowired
  private static ApplicationContext applicationContext;

  /**
   * Get specific bean by bean name and servletContext.
   *
   * @param beanName
   *          bean's name
   * @param servletContext
   *          servlet application
   * @return specific bean
   */
  public static Object getBeanByServletContext(String beanName, ServletContext servletContext) {
    WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    return wc.getBean(beanName);
  }

  /**
   * Get specific bean by bean class.
   *
   * @param beanClass
   *          bean's class
   * @return specific bean
   */
  public static <T> T getBean(Class<T> beanClass) {
    return applicationContext.getBean(beanClass);
  }
}
