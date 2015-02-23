package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.SettingDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Setting service interface.
 */
public interface SettingService {

  /**
   * Get settings detail by settings name.
   *
   * @param settingId setting's id
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.SettingDto} entity.
   * @throws AppException
   */
  public SettingDto getSettingDto(Integer settingId) throws AppException;

  /**
   * Update setting by content of
   * {@link cn.edu.uestc.acmicpc.db.dto.impl.SettingDto} entity.
   *
   * @param settingDto {@link cn.edu.uestc.acmicpc.db.dto.impl.SettingDto} entity.
   * @throws AppException
   */
  public void updateSettingBySettingDto(SettingDto settingDto) throws AppException;
}
