package cn.edu.uestc.acmicpc.oj.controller;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import cn.edu.uestc.acmicpc.config.WebMVCResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/** Abstract test to define constant variables for controller tests. */
public abstract class ControllerTest {

  /** Application JSON type with UTF-8 character set. */
  protected static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
      MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8"));

  @Autowired
  protected WebApplicationContext context;

  protected MockMvc initControllers(Object... objects) {
    return standaloneSetup(objects)
        .setViewResolvers(WebMVCResource.viewResolver())
        .setMessageConverters(WebMVCResource.messageConverters())
        .build();
  }

}
