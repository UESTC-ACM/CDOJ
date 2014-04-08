package cn.edu.uestc.acmicpc.db.dto.impl.setting;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Setting;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

@Fields({"settingId", "name", "description", "value"})
public class SettingDTO implements BaseDTO<Setting> {

  public SettingDTO() {
  }

  private SettingDTO(Integer settingId, String name, String description, String value) {
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
    if (!(o instanceof SettingDTO)) {
      return false;
    }

    SettingDTO that = (SettingDTO) o;

    if (description != null ? !description.equals(that.description) : that.description != null) {
      return false;
    }
    if (name != null ? !name.equals(that.name) : that.name != null) {
      return false;
    }
    if (settingId != null ? !settingId.equals(that.settingId) : that.settingId != null) {
      return false;
    }
    if (value != null ? !value.equals(that.value) : that.value != null) {
      return false;
    }

    return true;
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

  public static class Builder implements BaseBuilder<SettingDTO> {

    private Builder() {
    }

    @Override
    public SettingDTO build() {
      return new SettingDTO(settingId, name, description, value);
    }

    @Override
    public SettingDTO build(Map<String, Object> properties) {
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
