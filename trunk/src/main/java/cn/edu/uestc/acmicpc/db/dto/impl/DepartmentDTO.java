package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Department;

/**
 * Data transfer object for {@link Department}.
 * TODO(mzry1992)
 */
public class DepartmentDTO implements BaseDTO<Department> {

  private Integer departmentId;

  private String name;

  public Integer getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public DepartmentDTO() {
  }

  public DepartmentDTO(Integer departmentId, String name) {
    this.departmentId = departmentId;
    this.name = name;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }

    private Integer departmentId;

    private String name;

    public Builder setDepartmentId(Integer departmentId) {
      this.departmentId = departmentId;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public DepartmentDTO build() {
      return new DepartmentDTO(departmentId, name);
    }
  }
}
