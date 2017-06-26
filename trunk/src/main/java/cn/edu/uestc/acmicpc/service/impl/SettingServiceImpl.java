package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.SettingCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.SettingDao;
import cn.edu.uestc.acmicpc.db.dto.field.SettingFields;
import cn.edu.uestc.acmicpc.db.dto.impl.SettingDto;
import cn.edu.uestc.acmicpc.db.entity.Setting;
import cn.edu.uestc.acmicpc.service.iface.SettingService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for {@link cn.edu.uestc.acmicpc.service.iface.SettingService}.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SettingServiceImpl extends AbstractService implements SettingService {

  private final SettingDao settingDao;

  @Autowired
  public SettingServiceImpl(SettingDao settingDao) {
    this.settingDao = settingDao;
  }

  @Override
  public SettingDto getSettingDto(Integer settingId) throws AppException {
    SettingCriteria criteria = new SettingCriteria();
    criteria.settingId = settingId;
    return settingDao.getUniqueDto(criteria, SettingFields.ALL_FIELDS);
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
    settingDao.addOrUpdate(setting);
  }
}
