package cn.edu.uestc.acmicpc.db.dto.impl.department;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

/**
 * DTO for department entity.
 * <br/>
 * <code>@Fields({ "departmentId", "name" })</code>
 */
@Fields({"departmentId", "name"})
public class DepartmentDTO implements BaseDTO<Department> {

  public DepartmentDTO() {
  }

  private DepartmentDTO(Integer departmentId, String name) {
    this.departmentId = departmentId;
    this.name = name;
  }

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

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<DepartmentDTO> {

    private Builder() {
    }

    @Override
    public DepartmentDTO build() {
      return new DepartmentDTO(departmentId, name);
    }

    @Override
    public DepartmentDTO build(Map<String, Object> properties) {
      departmentId = (Integer) properties.get("departmentId");
      name = (String) properties.get("name");
      return build();

    }

    private Integer departmentId;
    private String name;

    public Integer getDepartmentId() {
      return departmentId;
    }

    public Builder setDepartmentId(Integer departmentId) {
      this.departmentId = departmentId;
      return this;
    }

    public String getName() {
      return name;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }
  }
}
