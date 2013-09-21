package cn.edu.uestc.acmicpc.config;

import static org.mockito.Mockito.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;
import cn.edu.uestc.acmicpc.oj.service.impl.UserServiceImpl;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;

@Configuration
@ComponentScan(basePackages = {
    "cn.edu.uestc.acmicpc.db",
    "cn.edu.uestc.acmicpc.util",
    "cn.edu.uestc.acmicpc.service",
    "cn.edu.uestc.acmicpc.oj.service",
    "cn.edu.uestc.acmicpc.training"
})
@PropertySource("classpath:resources.properties")
@EnableTransactionManagement
public class MockDAOContext extends TestContext {

  @Bean
  @Qualifier("service")
  @Autowired
  public UserService userService(GlobalService globalService) {
    return new UserServiceImpl(iUserDAO(), globalService);
  }

  @Bean
  @Qualifier("mock")
  public IUserDAO iUserDAO() {
    return mock(IUserDAO.class);
  }
}
