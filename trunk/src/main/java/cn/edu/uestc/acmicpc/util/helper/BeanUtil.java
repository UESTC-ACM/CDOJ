package cn.edu.uestc.acmicpc.util.helper;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * Manage methods for Spring Beans.
 */
public class BeanUtil {

  /**
   * Get specific bean by bean name and servletContext.
   *
   * @param beanName       bean's name
   * @param servletContext servlet application
   * @return specific bean
   */
  public static Object getBeanByServletContext(String beanName, ServletContext servletContext) {
    WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    return wc.getBean(beanName);
  }
}
