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
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.judge.JudgeService;

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

import com.jolbox.bonecp.BoneCPDataSource;

import java.util.Properties;

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
  public BoneCPDataSource dataSource() {
    BoneCPDataSource dataSource = new BoneCPDataSource();

    dataSource.setDriverClass(getProperty("db.driver"));
    dataSource.setJdbcUrl(getProperty("db.url"));
    dataSource.setUsername(getProperty("db.username"));
    dataSource.setPassword(getProperty("db.password"));
    dataSource.setMaxConnectionsPerPartition(Integer
        .parseInt(getProperty("db.maxConnectionsPerPartition")));
    dataSource.setMinConnectionsPerPartition(Integer
        .parseInt(getProperty("db.minConnectionsPerPartition")));
    dataSource.setPartitionCount(Integer.parseInt(getProperty("db.partitionCount")));
    dataSource.setAcquireIncrement(Integer.parseInt(getProperty("db.acquireIncrement")));
    dataSource.setStatementsCacheSize(Integer.parseInt(getProperty("db.statementsCacheSize")));
    // dataSource.setIdleMaxAgeInSeconds(Integer.parseInt(getProperty("db.idleMaxAge")));
    // dataSource.setIdleConnectionTestPeriodInSeconds(Integer.parseInt(getProperty("db.idleConnectionTestPeriod")));
    return dataSource;
  }

  /**
   * Bean: session factory
   *
   * @return sessionFactory bean
   */
  @Bean(name = "sessionFactory")
  @Lazy(false)
  public LocalSessionFactoryBean sessionFactory() {
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
        TrainingContest.class,
        TrainingStatus.class,
        TrainingUser.class,
        User.class,
        UserSerialKey.class);

    return localSessionFactoryBean;
  }

  /**
   * Bean: transaction manager
   *
   * @return transactionManagerBean
   */
  // TODO(fish) add txAdvise
  @Bean(name = "transactionManager")
  public HibernateTransactionManager transactionManager() {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(this.sessionFactory().getObject());
    /*
     * <tx:method name="save*" propagation="REQUIRED" /> <tx:method name="add*"
     * propagation="REQUIRED" /> <tx:method name="update*"
     * propagation="REQUIRED" /> <tx:method name="del*" propagation="REQUIRED"
     * /> <tx:method name="find*" propagation="REQUIRED" read-only="true" />
     */
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
   * @param name property name
   * @return property value
   */
  private String getProperty(final String name) {
    return environment.getProperty(name);
  }
}
