package cn.edu.uestc.acmicpc.db.criteria.impl;

import cn.edu.uestc.acmicpc.db.criteria.base.BaseCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.Fields;
import cn.edu.uestc.acmicpc.db.dto.impl.DepartmentDto;
import cn.edu.uestc.acmicpc.db.entity.Department;

/**
 * Article database criteria entity.
 */
public class DepartmentCriteria extends BaseCriteria<Department, DepartmentDto> {

  public DepartmentCriteria(Fields resultFields) {
    super(Department.class, DepartmentDto.class, resultFields);
  }

  public DepartmentCriteria() {
    this(null);
  }
}
