package cn.edu.uestc.acmicpc.web.oj.controller;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import cn.edu.uestc.acmicpc.config.WebMVCResource;

/** Abstract test to define constant variables for controller tests. */
public abstract class ControllerTest extends AbstractTestNGSpringContextTests {

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
