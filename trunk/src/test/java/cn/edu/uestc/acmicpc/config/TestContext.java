package cn.edu.uestc.acmicpc.config;

import static org.mockito.Mockito.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cn.edu.uestc.acmicpc.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ILanguageDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.UserSerialKeyService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.service.impl.UserServiceImpl;
import cn.edu.uestc.acmicpc.judge.JudgeService;
import cn.edu.uestc.acmicpc.service.iface.EmailService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.util.Global;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@ComponentScan(basePackages = {
    "cn.edu.uestc.acmicpc.db",
    "cn.edu.uestc.acmicpc.judge",
    "cn.edu.uestc.acmicpc.util",
    "cn.edu.uestc.acmicpc.service"
})
@PropertySource("classpath:resources.properties")
@EnableTransactionManagement
public class TestContext extends ApplicationContextConfig {

  @Override
  @Bean
  public BoneCPDataSource dataSource() {
    return null;
  }

  @Override
  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    return null;
  }

  @Override
  @Bean
  public HibernateTransactionManager transactionManager() {
    return null;
  }

  @Override
  @Bean
  @Autowired
  public Global global(IDepartmentDAO departmentDAO, ILanguageDAO languageDAO) {
    return null;
  }

  @Bean
  @Autowired
  public UserService realUserService(@Qualifier("mockUserDAO") IUserDAO userDAO) {
    return new UserServiceImpl(userDAO);
  }

  @Bean
  public UserService mockUserService() {
    return mock(UserService.class);
  }

  @Bean
  public ProblemService mockProblemService() {
    return mock(ProblemService.class);
  }

  @Bean
  public StatusService mockStatusService() {
    return mock(StatusService.class);
  }

  @Bean
  public UserSerialKeyService mockUserSerialKeyService() {
    return mock(UserSerialKeyService.class);
  }

  @Bean
  public GlobalService mockGlobalService() {
    return mock(GlobalService.class);
  }

  @Bean
  public EmailService mockEmailService() {
    return mock(EmailService.class);
  }

  @Bean
  public DepartmentService mockDepartmentService() {
    return mock(DepartmentService.class);
  }

  @Bean
  public IUserDAO mockUserDAO() {
    return mock(IUserDAO.class);
  }

  @Bean
  @Override
  public JudgeService judgeService() {
    return null;
  }
}
