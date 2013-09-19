package cn.edu.uestc.acmicpc.oj.controller;

import java.nio.charset.Charset;

import org.springframework.http.MediaType;

/** Abstract test to define constant variables for controller tests. */
public abstract class ControllerTest {

  protected static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
      MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8"));
}
