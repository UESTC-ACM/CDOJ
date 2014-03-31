package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ISettingsDAO;
import cn.edu.uestc.acmicpc.db.entity.Settings;

import org.springframework.stereotype.Repository;

/**
 * DAO for settings.
 */
@Repository
public class SettingsDAO extends DAO<Settings, Integer> implements ISettingsDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Settings> getReferenceClass() {
    return Settings.class;
  }
}
