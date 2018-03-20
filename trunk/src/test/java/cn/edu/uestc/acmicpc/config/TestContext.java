package cn.edu.uestc.acmicpc.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cn.edu.uestc.acmicpc.db.dao.iface.ContestUserDao;
import cn.edu.uestc.acmicpc.db.dao.iface.ProblemDao;
import cn.edu.uestc.acmicpc.db.dao.iface.UserDao;
import cn.edu.uestc.acmicpc.db.dto.impl.SettingDto;
import cn.edu.uestc.acmicpc.judge.JudgeService;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.EmailService;
import cn.edu.uestc.acmicpc.service.iface.FileService;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.SettingService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.TrainingPlatformInfoService;
import cn.edu.uestc.acmicpc.service.iface.TrainingService;
import cn.edu.uestc.acmicpc.service.iface.TrainingUserService;
import cn.edu.uestc.acmicpc.service.iface.UserSerialKeyService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.service.impl.ProblemServiceImpl;
import cn.edu.uestc.acmicpc.service.impl.UserServiceImpl;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.settings.SettingsID;
import com.alibaba.druid.pool.DruidDataSource;
import org.mockito.Mockito;
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

@Configuration
@ComponentScan(basePackages = {
    "cn.edu.uestc.acmicpc.db",
    "cn.edu.uestc.acmicpc.judge",
    "cn.edu.uestc.acmicpc.util",
    "cn.edu.uestc.acmicpc.service",
    "cn.edu.uestc.acmicpc.web.aspect"
})
@PropertySource("classpath:resources.properties")
@EnableTransactionManagement
public class TestContext extends ApplicationContextConfig {

  @Override
  @Bean
  public DruidDataSource dataSource() {
    return mock(DruidDataSource.class);
  }

  @Override
  @Bean
  public LocalSessionFactoryBean localSessionFactoryBean() {
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
  public UserService realUserService(
      @Qualifier("mockContestUserDao") ContestUserDao contestUserDao,
      @Qualifier("mockUserDao") UserDao userDao) {
    return new UserServiceImpl(contestUserDao, userDao);
  }

  @Bean
  @Autowired
  public ProblemService realProblemService(@Qualifier("mockProblemDao") ProblemDao problemDao) {
    return new ProblemServiceImpl(problemDao);
  }

  // mock beans, add a new type here, the bean should be reset in ControllerTest.
  @Bean
  @Primary
  public ArticleService mockArticleService() {
    return mock(ArticleService.class);
  }

  @Bean
  @Primary
  public ContestUserDao mockContestUserDao() {
    return mock(ContestUserDao.class);
  }

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
  public TrainingService mockTrainingService() {
    return mock(TrainingService.class);
  }

  @Bean
  @Primary
  public TrainingUserService mockTrainingUserService() {
    return mock(TrainingUserService.class);
  }

  @Bean
  @Primary
  public TrainingPlatformInfoService mockTrainingPlatformInfoService() {
    return mock(TrainingPlatformInfoService.class);
  }

  @Bean
  @Primary
  public SettingService mockSettingService() throws AppException {
    SettingService mockSettingService = mock(SettingService.class);
    when(mockSettingService.getSettingDto(Mockito.anyInt())).thenReturn(
        SettingDto.builder()
            .setName("name")
            .setDescription("description")
            .setValue("value")
            .build()
    );
    when(mockSettingService.getSettingDto(SettingsID.EMAIL.getId())).thenReturn(
        SettingDto.builder()
            .setName("name")
            .setDescription("description")
            .setValue("{\"address\":\"cdoj_test@163.com\",\"userName\":\"cdoj_test@163.com\",\"password\":\"135678942570\",\"smtpServer\":\"smtp.163.com\"}")
            .build()
    );
    when(mockSettingService.getSettingDto(SettingsID.JUDGES.getId())).thenReturn(
        SettingDto.builder()
            .setName("name")
            .setDescription("description")
            .setValue("[{\"name\":\"fish\"},{\"name\":\"mzry1992\"},{\"name\":\"gongbaoa\"},{\"name\":\"kennethsnow\"}]")
            .build()
    );
    when(mockSettingService.getSettingDto(SettingsID.RECORD_PER_PAGE.getId())).thenReturn(
        SettingDto.builder()
            .setName("name")
            .setDescription("description")
            .setValue("20")
            .build()
    );
    return mockSettingService;
  }

  @Bean
  @Primary
  public FileService mockFileService() {
    return mock(FileService.class);
  }

  @Bean
  @Primary
  public UserDao mockUserDao() {
    return mock(UserDao.class);
  }

  @Bean
  @Primary
  public ProblemDao mockProblemDao() {
    return mock(ProblemDao.class);
  }

  @Bean
  @Override
  public JudgeService judgeService() {
    return null;
  }
}
