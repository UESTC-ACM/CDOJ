package cn.edu.uestc.acmicpc.config;

import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.db.entity.CompileInfo;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.db.entity.ContestTeam;
import cn.edu.uestc.acmicpc.db.entity.ContestUser;
import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.db.entity.Language;
import cn.edu.uestc.acmicpc.db.entity.Message;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.ProblemTag;
import cn.edu.uestc.acmicpc.db.entity.Setting;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.db.entity.Tag;
import cn.edu.uestc.acmicpc.db.entity.Team;
import cn.edu.uestc.acmicpc.db.entity.TeamUser;
import cn.edu.uestc.acmicpc.db.entity.Training;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.db.entity.TrainingPlatformInfo;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.judge.JudgeService;
import com.alibaba.druid.pool.DruidDataSource;
import java.util.Properties;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Application Context Configuration
 */
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
public class ApplicationContextConfig {

  @Autowired
  private Environment environment;

  /**
   * Bean: Judge service
   *
   * @return judgeService bean
   */
  @Bean(name = "judgeService")
  @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
  @Lazy(false)
  public JudgeService judgeService() {
    return new JudgeService();
  }

  /**
   * Bean: Data source
   *
   * @return dataSource bean
   */
  @Bean(name = "dataSource", destroyMethod = "close")
  public DruidDataSource dataSource() {
    DruidDataSource dataSource = new DruidDataSource();

    // Basic attribute
    dataSource.setUrl(getProperty("db.url"));
    dataSource.setUsername(getProperty("db.username"));
    dataSource.setPassword(getProperty("db.password"));

    // Pool size
    dataSource.setInitialSize(8);
    dataSource.setMinIdle(8);
    dataSource.setMaxActive(128);

    // Connection wait time limit
    dataSource.setMaxWait(60 * 1000);

    // Time between connection check
    dataSource.setTimeBetweenEvictionRunsMillis(60 * 1000);

    // Time of connection max live time
    dataSource.setMinEvictableIdleTimeMillis(5 * 60 * 1000);

    // Connection test
    dataSource.setValidationQuery("SELECT 'x'");
    dataSource.setTestWhileIdle(true);
    dataSource.setTestOnBorrow(false);
    dataSource.setTestOnReturn(false);

    // PS cache
    dataSource.setPoolPreparedStatements(true);
    dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);

    return dataSource;
  }

  /**
   * Bean: local session factory bean
   *
   * @return localSessionFactoryBean bean
   */
  @Bean(name = "localSessionFactoryBean")
  @Lazy(false)
  public LocalSessionFactoryBean localSessionFactoryBean() {
    LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();

    localSessionFactoryBean.setDataSource(this.dataSource());
    localSessionFactoryBean.setHibernateProperties(this.getHibernateProperties());
    localSessionFactoryBean.setAnnotatedClasses(Article.class,
        Code.class,
        CompileInfo.class,
        Contest.class,
        ContestProblem.class,
        ContestTeam.class,
        ContestUser.class,
        Department.class,
        Language.class,
        Message.class,
        Problem.class,
        ProblemTag.class,
        Setting.class,
        Status.class,
        Tag.class,
        Team.class,
        TeamUser.class,
        Training.class,
        TrainingContest.class,
        TrainingPlatformInfo.class,
        TrainingUser.class,
        User.class,
        UserSerialKey.class);

    return localSessionFactoryBean;
  }

  /**
   * Bean: session factory
   *
   * @return sessionFactory bean
   */
  @Bean(name = "sessionFactory")
  @Lazy(false)
  public SessionFactory sessionFactory() {
    return this.localSessionFactoryBean().getObject();
  }

  /**
   * Bean: transaction manager
   *
   * @return transactionManagerBean
   */
  @Bean(name = "transactionManager")
  public HibernateTransactionManager transactionManager() {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactory());
    transactionManager.setDataSource(dataSource());
    return transactionManager;
  }

  /**
   * Hibernate properties
   *
   * @return properties
   */
  private Properties getHibernateProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", environment.getProperty("hibernate.dialect"));
    properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate.show_sql"));
    properties.setProperty("hibernate.format", environment.getProperty("hibernate.format_sql"));
    properties.setProperty("hibernate.current_session_context_class",
        environment.getProperty("hibernate.current_session_context_class"));
    return properties;
  }

  /**
   * Simply get property in PropertySource
   *
   * @param name
   *          property name
   * @return property value
   */
  private String getProperty(final String name) {
    return environment.getProperty(name);
  }
}
