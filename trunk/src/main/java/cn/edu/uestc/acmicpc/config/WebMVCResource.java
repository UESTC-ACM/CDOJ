package cn.edu.uestc.acmicpc.config;

import cn.edu.uestc.acmicpc.web.resolver.JsonPathArgumentResolver;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import java.util.LinkedList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Description
 */
public class WebMVCResource {

  public static ViewResolver viewResolver() {
    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

    viewResolver.setPrefix("/WEB-INF/html/");
    viewResolver.setSuffix(".html");

    return viewResolver;
  }

  public static HttpMessageConverter<?>[] messageConverters() {
    HttpMessageConverter<?>[] converters = new HttpMessageConverter<?>[1];
    FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
    fastJsonHttpMessageConverter.setFeatures(SerializerFeature.DisableCircularReferenceDetect);
    List<MediaType> mediaTypes = new LinkedList<>();
    mediaTypes.add(MediaType.APPLICATION_JSON);
    fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
    converters[0] = fastJsonHttpMessageConverter;
    return converters;
  }

  public static List<HandlerMethodArgumentResolver> argumentResolvers() {
    List<HandlerMethodArgumentResolver> resolverList = new LinkedList<>();

    resolverList.add(new JsonPathArgumentResolver());

    return resolverList;
  }
}
