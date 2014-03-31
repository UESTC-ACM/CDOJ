package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.ISettingDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.setting.SettingDTO;
import cn.edu.uestc.acmicpc.service.iface.SettingsService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for {@link SettingsService}.
 */
@Service
public class SettingsServiceImpl extends AbstractService implements SettingsService {

  private final ISettingDAO settingsDAO;

  @Autowired
  public SettingsServiceImpl(ISettingDAO settingsDAO) {
    this.settingsDAO = settingsDAO;
  }

  @Override
  public ISettingDAO getDAO() {
    return settingsDAO;
  }


  @Override
  public SettingDTO getSettingsDTOByName(String name) throws AppException {
    return settingsDAO.getDTOByUniqueField(SettingDTO.class,
        SettingDTO.builder(), "name", name);
  }

  @Override
  public void updateSettingsBySettingsDTO(SettingDTO settingsDTO) {
  }
}
