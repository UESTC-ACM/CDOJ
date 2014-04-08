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
   * @param settingId setting's id
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.setting.SettingDTO} entity.
   */
  public SettingDTO getSettingDTO(Integer settingId) throws AppException;

  /**
   * Update setting by content of {@link cn.edu.uestc.acmicpc.db.dto.impl.setting.SettingDTO} entity.
   *
   * @param settingDTO {@link cn.edu.uestc.acmicpc.db.dto.impl.setting.SettingDTO} entity.
   */
  public void updateSettingBySettingDTO(SettingDTO settingDTO) throws AppException;
}
