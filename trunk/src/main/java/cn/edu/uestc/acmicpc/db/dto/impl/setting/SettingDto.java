package cn.edu.uestc.acmicpc.db.dto.impl.setting;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.Setting;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;
import java.util.Objects;

@Fields({ "settingId", "name", "description", "value" })
public class SettingDto implements BaseDto<Setting> {

  public SettingDto() {
  }

  private SettingDto(Integer settingId, String name, String description, String value) {
    this.settingId = settingId;
    this.name = name;
    this.description = description;
    this.value = value;
  }

  private Integer settingId;
  private String name;
  private String description;
  private String value;

  public Integer getSettingId() {
    return settingId;
  }

  public void setSettingId(Integer settingId) {
    this.settingId = settingId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SettingDto)) {
      return false;
    }

    SettingDto that = (SettingDto) o;
    return Objects.equals(this.description, that.description)
        && Objects.equals(this.name, that.name)
        && Objects.equals(this.settingId, that.settingId)
        && Objects.equals(this.value, that.value);
  }

  @Override
  public int hashCode() {
    int result = settingId != null ? settingId.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (value != null ? value.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<SettingDto> {

    private Builder() {
    }

    @Override
    public SettingDto build() {
      return new SettingDto(settingId, name, description, value);
    }

    @Override
    public SettingDto build(Map<String, Object> properties) {
      settingId = (Integer) properties.get("settingId");
      name = (String) properties.get("name");
      description = (String) properties.get("description");
      value = (String) properties.get("value");
      return build();

    }

    private Integer settingId;
    private String name;
    private String description;
    private String value;

    public Integer getSettingId() {
      return settingId;
    }

    public Builder setSettingId(Integer settingId) {
      this.settingId = settingId;
      return this;
    }

    public String getName() {
      return name;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public String getDescription() {
      return description;
    }

    public Builder setDescription(String description) {
      this.description = description;
      return this;
    }

    public String getValue() {
      return value;
    }

    public Builder setValue(String value) {
      this.value = value;
      return this;
    }
  }
}