package cn.edu.uestc.acmicpc.util.settings;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.web.xml.XmlNode;
import cn.edu.uestc.acmicpc.web.xml.XmlParser;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;

/**
 * Global static class to get configuration parameters from
 * <strong>settings.xml</strong>.
 */
@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Lazy(false)
public class Settings implements ApplicationContextAware {

  /**
   * Host
   */
  public String SETTING_HOST;

  /**
   * Global encoding
   */
  public String SETTING_ENCODING;

  /**
   * Upload file size limit(in MB)
   */
  public Integer SETTING_UPLOAD_SIZE;

  /**
   * Upload file's types
   */
  public String SETTING_UPLOAD_TYPES;

  /**
   * Upload files store folder
   */
  public String SETTING_UPLOAD_FOLDER;

  /**
   * Pictures store folder
   */
  public String SETTING_PICTURE_FOLDER;

  /**
   * Picture store folder in absolute folder format
   */
  public String SETTING_PICTURE_FOLDER_ABSOLUTE;

  /**
   * Pictures store folder for users
   */
  public String SETTING_USER_PICTURE_FOLDER;

  /**
   * Picture store folder for users in absolute folder format
   */
  public String SETTING_USER_PICTURE_FOLDER_ABSOLUTE;

  /**
   * Setting file's absolute path
   */
  public String SETTING_ABSOLUTE_PATH;

  /**
   * Judge core's name.
   */
  public String JUDGE_JUDGE_CORE;

  /**
   * Data path name
   */
  public String JUDGE_DATA_PATH;

  /**
   * Work path name
   */
  public String JUDGE_TEMP_PATH;

  /**
   * User name for email server
   */
  public String EMAIL_USERNAME;

  /**
   * User password for email server
   */
  public String EMAIL_PASSWORD;

  /**
   * Email SMTP server's URL
   */
  public String EMAIL_SMTP_SERVER;

  /**
   * Email address of email sender
   */
  public String EMAIL_ADDRESS;

  /**
   * Judge information list
   */
  public List<Map<String, String>> JUDGE_LIST;

  /**
   * setting map from configuration file.
   */
  private static Map<String, Object> settings;

  /**
   * location of <strong>settings.xml</strong>.
   */
  private static final String SETTINGS_XML_PATH = "classpath:settings.xml";

  @Autowired
  private ApplicationContext applicationContext;

  private String getAbsolutePath(String path) throws IOException {
    return applicationContext.getResource("").getFile().getAbsolutePath() + "/" + path;
  }

  /**
   * initialize configuration mappings from configuration file.
   *
   * @throws cn.edu.uestc.acmicpc.util.exception.AppException
   * @throws IOException
   */
  @SuppressWarnings("unchecked")
  @PostConstruct
  private void init() throws AppException, IOException {
    settings = new HashMap<>();
    String path;
    ResourceLoader loader = new DefaultResourceLoader();
    Resource resource = loader.getResource(SETTINGS_XML_PATH);
    try {
      path = resource.getFile().getPath();
    } catch (IOException e) {
      e.printStackTrace();
      return;
    }
    XmlParser xmlParser = new XmlParser(path);
    XmlNode root;
    try {
      root = xmlParser.parse().getChildList().get(0);
    } catch (IndexOutOfBoundsException e) {
      throw new AppException("there no root node in the settings file.");
    }
    for (XmlNode node : root.getChildList()) { // settings
      Map<String, Object> map = new HashMap<>();
      for (XmlNode childNode : node.getChildList()) { // item or list
        if (childNode.getTagName().trim().equals("list")) {
          List<Map<String, String>> list = new LinkedList<>();
          for (XmlNode childNode2 : childNode.getChildList()) { // item
            list.add(parseItem(childNode2));
          }
          map.put(childNode.getAttribute("name").trim(), list);
        } else { // item
          map.put(childNode.getAttribute("name").trim(), parseItem(childNode));
        }
      }
      settings.put(node.getAttribute("name").trim(), map);
    }

    SETTING_HOST = (String) getConfig("setting", "host", "value");
    SETTING_ENCODING = (String) getConfig("setting", "encoding", "value");
    SETTING_UPLOAD_SIZE = Integer.valueOf((String) getConfig("setting", "uploadSize", "value"));
    SETTING_UPLOAD_TYPES = (String) getConfig("setting", "uploadTypes", "value");
    SETTING_UPLOAD_FOLDER = getAbsolutePath((String) getConfig("setting", "uploadFolder", "value"));
    SETTING_PICTURE_FOLDER = (String) getConfig("setting", "pictureFolder", "value");
    SETTING_PICTURE_FOLDER_ABSOLUTE = getAbsolutePath(SETTING_PICTURE_FOLDER);
    SETTING_USER_PICTURE_FOLDER = (String) getConfig("setting", "userPictureFolder", "value");
    SETTING_USER_PICTURE_FOLDER_ABSOLUTE = getAbsolutePath(SETTING_USER_PICTURE_FOLDER);

    JUDGE_JUDGE_CORE = (String) getConfig("judge", "judgeCore", "value");
    JUDGE_DATA_PATH = getAbsolutePath((String) getConfig("judge", "dataPath", "value"));
    JUDGE_TEMP_PATH = getAbsolutePath((String) getConfig("judge", "tempPath", "value"));
    JUDGE_LIST = (List<Map<String, String>>) getConfig("judge", "judges");

    EMAIL_ADDRESS = (String) getConfig("email", "address", "value");
    EMAIL_USERNAME = (String) getConfig("email", "username", "value");
    EMAIL_PASSWORD = (String) getConfig("email", "password", "value");
    EMAIL_SMTP_SERVER = (String) getConfig("email", "smtpServer", "value");

    SETTING_ABSOLUTE_PATH = getAbsolutePath("");
  }

  /**
   * parse item's information
   *
   * @param node item node
   * @return item map
   * @throws AppException
   */
  private Map<String, String> parseItem(XmlNode node) throws AppException {
    Map<String, String> result = new HashMap<>();
    if (node.getInnerText() != null && !StringUtil.isNullOrWhiteSpace(node.getInnerText())) {
      result.put("value", node.getInnerText().trim());
    }
    for (XmlNode childNode : node.getChildList()) {
      result.put(childNode.getAttribute("name"), childNode.getAttribute("value").trim());
    }
    return result;
  }

  /**
   * Get configuration value from <strong>settings.xml</strong>.
   *
   * @param path setting path
   * @return item value
   */
  private Object getConfig(String... path) {
    try {
      Object current = settings;
      for (String aPath : path) {
        Method method = current.getClass().getMethod("get", Object.class);
        current = method.invoke(current, aPath);
      }
      if (current instanceof String) {
        return ((String) current).trim();
      } else {
        return current;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }
}
