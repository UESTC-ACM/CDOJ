package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.setting.SettingDto;
import cn.edu.uestc.acmicpc.db.entity.Setting;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Setting service interface.
 */
public interface SettingService extends DatabaseService<Setting, Integer> {

  /**
   * Get settings detail by settings name.
   *
   * @param settingId
   *          setting's id
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.setting.SettingDto} entity.
   * @throws AppException
   */
  public SettingDto getSettingDto(Integer settingId) throws AppException;

  /**
   * Update setting by content of
   * {@link cn.edu.uestc.acmicpc.db.dto.impl.setting.SettingDto} entity.
   *
   * @param settingDto
   *          {@link cn.edu.uestc.acmicpc.db.dto.impl.setting.SettingDto}
   *          entity.
   * @throws AppException
   */
  public void updateSettingBySettingDto(SettingDto settingDto) throws AppException;
}
