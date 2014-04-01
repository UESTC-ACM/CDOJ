package cn.edu.uestc.acmicpc.config;

import cn.edu.uestc.acmicpc.web.view.ContestRankListView;
import cn.edu.uestc.acmicpc.web.view.ContestRegistryReportView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.BeanNameViewResolver;

import java.util.Arrays;
import java.util.List;

/**
 * Description
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
    "cn.edu.uestc.acmicpc.web.oj.controller"
})
@PropertySource("classpath:resources.properties")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class WebMVCConfig extends WebMvcConfigurerAdapter {

  @Autowired
  private Environment environment;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/cdoj/**").addResourceLocations(getResourceLocations("cdoj"));
    registry.addResourceHandler("/font/**").addResourceLocations(getResourceLocations("font"));
    registry.addResourceHandler("/images/**").addResourceLocations(getResourceLocations("images"));
    registry.addResourceHandler("/plugins/**").addResourceLocations(getResourceLocations("plugins"));
    registry.addResourceHandler("/template/**").addResourceLocations(getResourceLocations("template"));
  }

  private String getResourceLocations(String folder) {
    String path = environment.getProperty("staticResources.path");
    return "file://" + path + "/" + folder + "/**";
  }

  @Override
  public void configureDefaultServletHandling(
      DefaultServletHandlerConfigurer defaultServletHandlerConfigurer) {
    defaultServletHandlerConfigurer.enable();
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.addAll(Arrays.asList(WebMVCResource.messageConverters()));
  }

  @Bean
  public ViewResolver viewResolver() {
    return WebMVCResource.viewResolver();
  }

  @Bean
  public MultipartResolver multipartResolver() {
    return new CommonsMultipartResolver();
  }

  @Bean
  public BeanNameViewResolver beanNameViewResolver() {
    return new BeanNameViewResolver();
  }

  @Bean
  public ContestRegistryReportView contestRegistryReportView() {
    return new ContestRegistryReportView();
  }

  @Bean
  public ContestRankListView contestRankListView() {
    return new ContestRankListView();
  }
}
