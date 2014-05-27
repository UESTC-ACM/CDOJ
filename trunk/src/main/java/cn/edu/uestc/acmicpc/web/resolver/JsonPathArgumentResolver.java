package cn.edu.uestc.acmicpc.web.resolver;

import cn.edu.uestc.acmicpc.util.annotation.JsonMap;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSONReader;

import javax.servlet.http.HttpServletRequest;

/**
 * Json path argument resolver
 */
public class JsonPathArgumentResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(JsonMap.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

    // Discard request with incorrect content-type
    if (!request.getContentType().contains("application/json")) {
      return null;
    }

    String desiredKey = parameter.getParameterAnnotation(JsonMap.class).value();
    Object result = null;

    JSONReader reader = new JSONReader(request.getReader());
    reader.startObject();
    while (reader.hasNext()) {
      String key = reader.readString();
      if (key.equals(desiredKey)) {
        result = reader.readObject(parameter.getParameterType());
      } else {
        // Discard
        reader.readObject();
      }
    }
    // Safe close
    reader.endObject();
    reader.close();

    return result;
  }

}
