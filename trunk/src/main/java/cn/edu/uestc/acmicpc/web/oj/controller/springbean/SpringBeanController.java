package cn.edu.uestc.acmicpc.web.oj.controller.springbean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * Get bean from Spring
 */
@Component
public class SpringBeanController implements ApplicationContextAware {

  private static ApplicationContext applicationContext;

  public void setApplicationContext(ApplicationContext applicationContext)
    throws BeansException {
    this.applicationContext = applicationContext;
  }

  public static Object getBean(String beanName) {
    return applicationContext.getBean(beanName);
  }

  public static <T> T getBean(Class<T> beanClass) {
    return applicationContext.getBean(beanClass);
  }
}

