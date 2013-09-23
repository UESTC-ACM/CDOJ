package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Department;

/** Data transfer object for {@link Department}. */
public class DepartmentDTO implements BaseDTO<Department> {

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }
  }
}
