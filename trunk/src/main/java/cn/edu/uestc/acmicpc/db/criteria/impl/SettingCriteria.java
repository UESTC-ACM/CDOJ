package cn.edu.uestc.acmicpc.db.criteria.impl;

import cn.edu.uestc.acmicpc.db.criteria.base.BaseCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.Fields;
import cn.edu.uestc.acmicpc.db.dto.impl.SettingDto;
import cn.edu.uestc.acmicpc.db.entity.Setting;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Setting database criteria entity.
 */
public class SettingCriteria extends BaseCriteria<Setting, SettingDto> {

  public SettingCriteria(Fields resultFields) {
    super(Setting.class, SettingDto.class, resultFields);
  }

  public SettingCriteria() {
    this(null);
  }

  public Integer settingId;

  @Override
  public DetachedCriteria getCriteria() throws AppException {
    DetachedCriteria detachedCriteria = super.getCriteria();
    if (settingId != null) {
      detachedCriteria.add(Restrictions.eq("settingId", settingId));
    }
    return detachedCriteria;
  }
}
