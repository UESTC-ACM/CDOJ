package cn.edu.uestc.acmicpc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import cn.edu.uestc.acmicpc.service.JudgeService;

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
public class TestContext extends ApplicationContextConfig {

  @Bean(name = "judgeService")
  @Override
  public JudgeService judgeService() {
    return null;
  }
}
