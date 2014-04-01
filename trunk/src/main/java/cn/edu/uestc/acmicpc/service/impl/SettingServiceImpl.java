package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.ISettingDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.setting.SettingDTO;
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

  private final ISettingDAO settingDAO;

  @Autowired
  public SettingServiceImpl(ISettingDAO settingDAO) {
    this.settingDAO = settingDAO;
  }

  @Override
  public ISettingDAO getDAO() {
    return settingDAO;
  }

  @Override
  public SettingDTO getSettingDTO(Integer settingId) throws AppException {
    return settingDAO.getDTOByUniqueField(SettingDTO.class,
        SettingDTO.builder(), "settingId", settingId);
  }

  @Override
  public void updateSettingBySettingDTO(SettingDTO settingDTO) throws AppException {
    Setting setting = settingDAO.get(settingDTO.getSettingId());
    if (settingDTO.getName() != null) {
      setting.setName(settingDTO.getName());
    }
    if (settingDTO.getDescription() != null) {
      setting.setDescription(settingDTO.getDescription());
    }
    if (settingDTO.getValue() != null) {
      setting.setValue(settingDTO.getValue());
    }
    settingDAO.update(setting);
  }
}
