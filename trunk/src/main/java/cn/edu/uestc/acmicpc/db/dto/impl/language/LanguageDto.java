package cn.edu.uestc.acmicpc.db.dto.impl.language;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.Language;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;
import java.util.Objects;

/**
 * Dto for language entity. <br/>
 * <code>@Fields({ "languageId", "name", "extension", "param" })</code>
 */
@Fields({ "languageId", "name", "extension", "param" })
public class LanguageDto implements BaseDto<Language> {

  public LanguageDto() {
  }

  private LanguageDto(Integer languageId, String name, String extension, String param) {
    this.languageId = languageId;
    this.name = name;
    this.extension = extension;
    this.param = param;
  }

  private Integer languageId;
  private String name;
  private String extension;
  private String param;

  public Integer getLanguageId() {
    return languageId;
  }

  public void setLanguageId(Integer languageId) {
    this.languageId = languageId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  public String getParam() {
    return param;
  }

  public void setParam(String param) {
    this.param = param;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof LanguageDto)) {
      return false;
    }

    LanguageDto that = (LanguageDto) o;
    return Objects.equals(this.extension, that.extension)
        && Objects.equals(this.languageId, that.languageId)
        && Objects.equals(this.name, that.name)
        && Objects.equals(this.param, that.param);
  }

  @Override
  public int hashCode() {
    int result = languageId != null ? languageId.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (extension != null ? extension.hashCode() : 0);
    result = 31 * result + (param != null ? param.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<LanguageDto> {

    private Builder() {
    }

    @Override
    public LanguageDto build() {
      return new LanguageDto(languageId, name, extension, param);
    }

    @Override
    public LanguageDto build(Map<String, Object> properties) {
      languageId = (Integer) properties.get("languageId");
      name = (String) properties.get("name");
      extension = (String) properties.get("extension");
      param = (String) properties.get("parma");
      return build();
    }

    private Integer languageId;
    private String name;
    private String extension;
    private String param;

    public Integer getLanguageId() {
      return languageId;
    }

    public Builder setLanguageId(Integer languageId) {
      this.languageId = languageId;
      return this;
    }

    public String getName() {
      return name;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public String getExtension() {
      return extension;
    }

    public Builder setExtension(String extension) {
      this.extension = extension;
      return this;
    }

    public String getParam() {
      return param;
    }

    public Builder setParam(String param) {
      this.param = param;
      return this;
    }
  }
}
