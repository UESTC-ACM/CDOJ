package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.setting.SettingDTO;
import cn.edu.uestc.acmicpc.db.entity.Setting;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Setting service interface.
 */
public interface SettingService extends DatabaseService<Setting, Integer> {

  /**
   * Get settings detail by settings name.
   *
   * @param name settings name
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.setting.SettingDTO} entity.
   */
  public SettingDTO getSettingsDTOByName(String name) throws AppException;

  /**
   * Update settings by content of {@link cn.edu.uestc.acmicpc.db.dto.impl.setting.SettingDTO} entity.
   *
   * @param settingsDTO {@link cn.edu.uestc.acmicpc.db.dto.impl.setting.SettingDTO} entity.
   */
  public void updateSettingsBySettingsDTO(SettingDTO settingsDTO) throws AppException;
}
