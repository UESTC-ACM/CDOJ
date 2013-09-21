package cn.edu.uestc.acmicpc.service.impl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Abstract service implementation.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public abstract class AbstractService implements ApplicationContextAware {

  /**
   * Spring {@link ApplicationContext} entity for services.
   */
  @Autowired
  protected ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
