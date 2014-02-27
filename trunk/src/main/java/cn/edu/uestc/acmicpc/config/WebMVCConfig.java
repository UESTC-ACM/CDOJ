package cn.edu.uestc.acmicpc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

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
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class WebMVCConfig extends WebMvcConfigurerAdapter {

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/images/**").addResourceLocations("/images/**");
    registry.addResourceHandler("/plugins/**").addResourceLocations("/plugins/**");
    registry.addResourceHandler("/scripts/**").addResourceLocations("/scripts/**");
    registry.addResourceHandler("/styles/**").addResourceLocations("/styles/**");
    registry.addResourceHandler("/font/**").addResourceLocations("/font/**");
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

}
