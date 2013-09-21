package cn.edu.uestc.acmicpc.config;

import static org.mockito.Mockito.mock;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;

@Configuration
@ComponentScan(basePackages = {
    "cn.edu.uestc.acmicpc.util",
    "cn.edu.uestc.acmicpc.service",
    "cn.edu.uestc.acmicpc.oj.service",
    "cn.edu.uestc.acmicpc.training"
})
@PropertySource("classpath:resources.properties")
@EnableTransactionManagement
public class MockDAOContext extends TestContext {

  private static final Logger LOGGER = LogManager.getLogger(MockDAOContext.class);

  @Bean
  public IUserDAO iUserDAO() {
    LOGGER.warn("inject completed");
    return mock(IUserDAO.class);
  }
}
