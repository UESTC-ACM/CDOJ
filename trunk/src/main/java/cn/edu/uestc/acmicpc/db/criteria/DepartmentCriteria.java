package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dto.impl.DepartmentDto;
import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.DetachedCriteria;

/**
 * Department database criteria entity.
 */
public class DepartmentCriteria extends BaseCriteria<Department, DepartmentDto> {

  public DepartmentCriteria() {
    super(Department.class, DepartmentDto.class);
  }

  @Override
  DetachedCriteria updateCriteria(DetachedCriteria criteria) throws AppException {
    return criteria;
  }
}
