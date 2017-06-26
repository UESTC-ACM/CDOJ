package cn.edu.uestc.acmicpc.config;

import cn.edu.uestc.acmicpc.judge.JudgeService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Integration test context configurations.
 */
@Configuration
@ComponentScan(basePackages = {
    "cn.edu.uestc.acmicpc.db",
    "cn.edu.uestc.acmicpc.judge",
    "cn.edu.uestc.acmicpc.util",
    "cn.edu.uestc.acmicpc.service"
})
@PropertySource("classpath:resources.properties")
@EnableTransactionManagement
public class IntegrationTestContext extends ApplicationContextConfig {

  @Bean(name = "judgeService")
  @Override
  public JudgeService judgeService() {
    return null;
  }

  @Bean(name = "httpServletRequest")
  public HttpServletRequest httpServletRequest() {
    return null;
  }

}
