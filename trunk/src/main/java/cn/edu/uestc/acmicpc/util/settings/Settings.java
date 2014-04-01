package cn.edu.uestc.acmicpc.util.settings;

import cn.edu.uestc.acmicpc.db.dto.impl.setting.SettingDTO;
import cn.edu.uestc.acmicpc.service.iface.SettingService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.settings.entity.EmailSetting;
import cn.edu.uestc.acmicpc.util.settings.entity.JudgeSetting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;

import java.util.List;
import javax.annotation.PostConstruct;

/**
 * Global settings.
 */
@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Lazy(false)
public class Settings {

  private SettingService settingService;

  @Autowired
  public Settings(SettingService settingService) {
    this.settingService = settingService;
  }

  /**
   * Host
   */
  public String HOST;

  /**
   * Global encoding
   */
  public String ENCODING;

  /**
   * Upload files store folder
   */
  public String UPLOAD_FOLDER;

  /**
   * Pictures store folder
   */
  public String PICTURE_FOLDER;
  /**
   * Judge core's name.
   */
  public String JUDGE_CORE;

  /**
   * Data path name
   */
  public String DATA_PATH;

  /**
   * Work path name
   */
  public String WORK_PATH;

  /**
   * Judge list
   */
  public List<JudgeSetting> JUDGES;

  /**
   * Email setting
   */
  public EmailSetting EMAIL;

  /**
   * Record count in list.
   */
  public Long RECORD_PER_PAGE;

  /**
   * Initialize settings
   */
  @PostConstruct
  public void init() {
    try {
      HOST = getStringValueSettingByName(SettingsID.HOST);
      ENCODING = getStringValueSettingByName(SettingsID.ENCODING);
      UPLOAD_FOLDER = getStringValueSettingByName(SettingsID.UPLOAD_FOLDER);
      PICTURE_FOLDER = getStringValueSettingByName(SettingsID.PICTURE_FOLDER);
      JUDGE_CORE = getStringValueSettingByName(SettingsID.JUDGE_CORE);
      DATA_PATH = getStringValueSettingByName(SettingsID.DATA_PATH);
      WORK_PATH = getStringValueSettingByName(SettingsID.WORK_PATH);
      RECORD_PER_PAGE = getLongValueSettingByName(SettingsID.RECORD_PER_PAGE);

      JUDGES = JSON.parseArray(getStringValueSettingByName(SettingsID.JUDGES), JudgeSetting.class);
      EMAIL = JSON.parseObject(getStringValueSettingByName(SettingsID.EMAIL), EmailSetting.class);
    } catch (AppException e) {
      e.printStackTrace();
    }
  }

  private String getStringValueSettingByName(SettingsID settingsID) throws AppException {
    SettingDTO settingDTO = settingService.getSettingDTO(settingsID.getId());
    return settingDTO.getValue();
  }

  private Long getLongValueSettingByName(SettingsID settingsID) throws AppException {
    SettingDTO settingDTO = settingService.getSettingDTO(settingsID.getId());
    return Long.parseLong(settingDTO.getValue());
  }

}
