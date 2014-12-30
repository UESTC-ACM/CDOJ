package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.SettingDao;
import cn.edu.uestc.acmicpc.db.dto.impl.setting.SettingDto;
import cn.edu.uestc.acmicpc.db.entity.Setting;
import cn.edu.uestc.acmicpc.service.iface.SettingService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for {@link cn.edu.uestc.acmicpc.service.iface.SettingService}.
 */
@Service
public class SettingServiceImpl extends AbstractService implements SettingService {

  private final SettingDao settingDao;

  @Autowired
  public SettingServiceImpl(SettingDao settingDao) {
    this.settingDao = settingDao;
  }

  @Override
  public SettingDao getDao() {
    return settingDao;
  }

  @Override
  public SettingDto getSettingDto(Integer settingId) throws AppException {
    return settingDao.getDtoByUniqueField(SettingDto.class,
        SettingDto.builder(), "settingId", settingId);
  }

  @Override
  public void updateSettingBySettingDto(SettingDto settingDto) throws AppException {
    Setting setting = settingDao.get(settingDto.getSettingId());
    if (settingDto.getName() != null) {
      setting.setName(settingDto.getName());
    }
    if (settingDto.getDescription() != null) {
      setting.setDescription(settingDto.getDescription());
    }
    if (settingDto.getValue() != null) {
      setting.setValue(settingDto.getValue());
    }
    settingDao.update(setting);
  }
}
