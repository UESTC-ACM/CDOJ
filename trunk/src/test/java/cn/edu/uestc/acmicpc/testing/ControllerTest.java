package cn.edu.uestc.acmicpc.testing;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import cn.edu.uestc.acmicpc.config.TestContext;
import cn.edu.uestc.acmicpc.config.WebMVCConfig;
import cn.edu.uestc.acmicpc.config.WebMVCResource;
import cn.edu.uestc.acmicpc.db.dao.iface.ProblemDao;
import cn.edu.uestc.acmicpc.db.dao.iface.UserDao;
import cn.edu.uestc.acmicpc.db.dto.impl.DepartmentDto;
import cn.edu.uestc.acmicpc.db.dto.impl.LanguageDto;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.EmailService;
import cn.edu.uestc.acmicpc.service.iface.FileService;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.TrainingPlatformInfoService;
import cn.edu.uestc.acmicpc.service.iface.TrainingService;
import cn.edu.uestc.acmicpc.service.iface.TrainingUserService;
import cn.edu.uestc.acmicpc.service.iface.UserSerialKeyService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

/**
 * Abstract test to define constant variables for controller tests.
 */
@WebAppConfiguration
@ContextConfiguration(classes = {TestContext.class, WebMVCConfig.class})
public abstract class ControllerTest extends AbstractTestNGSpringContextTests {

  protected MockMvc mockMvc;
  protected MockHttpSession session;
  protected List<DepartmentDto> departmentList = new ArrayList<DepartmentDto>();
  @Mock protected List<LanguageDto> languageList;

  @Autowired protected ArticleService articleService;
  @Autowired protected UserService userService;
  @Autowired protected ProblemService problemService;
  @Autowired protected StatusService statusService;
  @Autowired protected UserSerialKeyService userSerialKeyService;
  @Autowired protected EmailService emailService;
  @Autowired protected DepartmentService departmentService;
  @Autowired protected LanguageService languageService;
  @Autowired protected PictureService pictureService;
  @Autowired protected FileService fileService;
  @Autowired protected TrainingService trainingService;
  @Autowired protected TrainingUserService trainingUserService;
  @Autowired protected TrainingPlatformInfoService trainingPlatformInfoService;
  @Autowired protected UserDao userDao;
  @Autowired protected ProblemDao problemDao;

  /**
   * Application JSON type with UTF-8 character set.
   */
  protected static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
      MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8"));

  protected void init() {
    for (Field field : ControllerTest.class.getDeclaredFields()) {
      if (field.isAnnotationPresent(Autowired.class)) {
        try {
          Mockito.reset(field.get(this));
        } catch (Exception ignored) {
        }
      }
    }
    when(departmentService.getDepartmentList()).thenReturn(departmentList);
    when(languageService.getLanguageList()).thenReturn(languageList);
  }

  @Autowired
  protected WebApplicationContext context;

  protected MockMvc initControllers(Object... objects) {
    List<HandlerMethodArgumentResolver> resolverList = WebMVCResource.argumentResolvers();
    HandlerMethodArgumentResolver[] resolversArray = new HandlerMethodArgumentResolver[resolverList.size()];

    return standaloneSetup(objects)
        .setViewResolvers(WebMVCResource.viewResolver())
        .setMessageConverters(WebMVCResource.messageConverters())
        .setCustomArgumentResolvers(resolverList.toArray(resolversArray))
        .build();
  }
}
