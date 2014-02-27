package cn.edu.uestc.acmicpc.config;

import static org.mockito.Mockito.mock;

import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.judge.JudgeService;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.EmailService;
import cn.edu.uestc.acmicpc.service.iface.FileService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.UserSerialKeyService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.service.impl.ProblemServiceImpl;
import cn.edu.uestc.acmicpc.service.impl.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
    return mock(BoneCPDataSource.class);
  }

  @Override
  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    return mock(LocalSessionFactoryBean.class);
  }

  @Override
  @Bean
  public HibernateTransactionManager transactionManager() {
    return mock(HibernateTransactionManager.class);
  }

  // real beans for testing.
  @Bean
  @Autowired
  public UserService realUserService(@Qualifier("mockUserDAO") IUserDAO userDAO) {
    return new UserServiceImpl(userDAO);
  }

  @Bean
  @Autowired
  public ProblemService realProblemService(@Qualifier("mockProblemDAO") IProblemDAO problemDAO) {
    return new ProblemServiceImpl(problemDAO);
  }

  // mock beans, add a new type here, the bean should be reset in ControllerTest.
  @Bean
  @Primary
  public UserService mockUserService() {
    return mock(UserService.class);
  }

  @Bean
  @Primary
  public ProblemService mockProblemService() {
    return mock(ProblemService.class);
  }

  @Bean
  @Primary
  public StatusService mockStatusService() {
    return mock(StatusService.class);
  }

  @Bean
  @Primary
  public UserSerialKeyService mockUserSerialKeyService() {
    return mock(UserSerialKeyService.class);
  }

  @Bean
  @Primary
  public GlobalService mockGlobalService() {
    return mock(GlobalService.class);
  }

  @Bean
  @Primary
  public EmailService mockEmailService() {
    return mock(EmailService.class);
  }

  @Bean
  @Primary
  public DepartmentService mockDepartmentService() {
    return mock(DepartmentService.class);
  }

  @Bean
  @Primary
  public LanguageService mockLanguageService() {
    return mock(LanguageService.class);
  }

  @Bean
  @Primary
  public PictureService mockPictureService() {
    return mock(PictureService.class);
  }

  @Bean
  @Primary
  public FileService mockFileService() {
    return mock(FileService.class);
  }

  @Bean
  @Primary
  public IUserDAO mockUserDAO() {
    return mock(IUserDAO.class);
  }

  @Bean
  @Primary
  public IProblemDAO mockProblemDAO() {
    return mock(IProblemDAO.class);
  }

  @Bean
  @Override
  public JudgeService judgeService() {
    return null;
  }
}
