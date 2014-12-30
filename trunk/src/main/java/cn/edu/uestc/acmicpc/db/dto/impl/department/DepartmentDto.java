package cn.edu.uestc.acmicpc.db.dto.impl.department;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;
import java.util.Objects;

/**
 * Dto for department entity. <br/>
 * <code>@Fields({ "departmentId", "name" })</code>
 */
@Fields({ "departmentId", "name" })
public class DepartmentDto implements BaseDto<Department> {

  public DepartmentDto() {
  }

  private DepartmentDto(Integer departmentId, String name) {
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DepartmentDto)) {
      return false;
    }

    DepartmentDto that = (DepartmentDto) o;
    return Objects.equals(this.departmentId, that.departmentId)
        && Objects.equals(this.name, that.name);
  }

  @Override
  public int hashCode() {
    int result = departmentId != null ? departmentId.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<DepartmentDto> {

    private Builder() {
    }

    @Override
    public DepartmentDto build() {
      return new DepartmentDto(departmentId, name);
    }

    @Override
    public DepartmentDto build(Map<String, Object> properties) {
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
