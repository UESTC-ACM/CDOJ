package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ISettingDAO;
import cn.edu.uestc.acmicpc.db.entity.Setting;

import org.springframework.stereotype.Repository;

/**
 * DAO for setting.
 */
@Repository
public class SettingDAO extends DAO<Setting, Integer> implements ISettingDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Setting> getReferenceClass() {
    return Setting.class;
  }
}
