package cn.edu.uestc.acmicpc.web.resolver;

import cn.edu.uestc.acmicpc.util.annotation.JsonMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.BufferedReader;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Json path argument resolver
 */
public class JsonPathArgumentResolver implements HandlerMethodArgumentResolver {

  private final String LOADED_FLAG = "JSON_PATH_ARGUMENT_RESOLVER_LOADED_FLAG";
  private final String JSON_PATH_CACHE_PREFIX = "JSON_PATH_CACHE_PREFIX";

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.hasParameterAnnotation(JsonMap.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

    // Discard request with incorrect content-type
    if (!request.getContentType().contains("application/json")) {
      return null;
    }

    // Get model map
    ModelMap modelMap = mavContainer.getModel();

    // Load request data once and cache the result into model map.
    if (!modelMap.containsAttribute(LOADED_FLAG)) {
      modelMap.put(LOADED_FLAG, 0);

      Method method = parameter.getMethod();
      Map<String, Class<?>> objectTypeMap = new HashMap<>();

      int totalParameters = method.getParameterTypes().length;
      for (int parameterIndex = 0; parameterIndex < totalParameters; parameterIndex++) {
        MethodParameter currentParameter = MethodParameter.forMethodOrConstructor(method,
            parameterIndex);
        if (currentParameter.hasParameterAnnotation(JsonMap.class)) {
          objectTypeMap.put(
              currentParameter.getParameterAnnotation(JsonMap.class).value(),
              currentParameter.getParameterType());
        }
      }

      BufferedReader reader = request.getReader();
      StringBuilder stringBuilder = new StringBuilder();
      String line = null; ;
      while((line = reader.readLine()) != null) {
        stringBuilder.append(line);
      }
      reader.close();
      JSONObject jObject = JSON.parseObject(stringBuilder.toString());
      for (Map.Entry<String, Object> entry: jObject.entrySet()) {
        String key = entry.getKey();
        if (objectTypeMap.containsKey(key)) {
          Object value = jObject.getObject(key, objectTypeMap.get(key));
          modelMap.addAttribute(JSON_PATH_CACHE_PREFIX + key, value);
        }
      }
    }

    String desiredKey = parameter.getParameterAnnotation(JsonMap.class).value();
    return modelMap.get(JSON_PATH_CACHE_PREFIX + desiredKey);
  }

}
