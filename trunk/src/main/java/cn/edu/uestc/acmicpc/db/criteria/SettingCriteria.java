package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dto.impl.SettingDto;
import cn.edu.uestc.acmicpc.db.entity.Setting;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Setting database criteria entity.
 */
public class SettingCriteria extends BaseCriteria<Setting, SettingDto> {

  public SettingCriteria() {
    super(Setting.class, SettingDto.class);
  }

  public Integer settingId;

  @Override
  DetachedCriteria updateCriteria(DetachedCriteria criteria) throws AppException {
    if (settingId != null) {
      criteria.add(Restrictions.eq("settingId", settingId));
    }
    return criteria;
  }
}
