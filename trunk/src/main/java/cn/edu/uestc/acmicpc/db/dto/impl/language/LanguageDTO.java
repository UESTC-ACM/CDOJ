package cn.edu.uestc.acmicpc.db.dto.impl.language;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Language;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

/**
 * DTO for language entity.
 * <br/>
 * <code>@Fields({ "languageId", "name", "extension", "param" })</code>
 */
@Fields({"languageId", "name", "extension", "param"})
public class LanguageDTO implements BaseDTO<Language> {

  public LanguageDTO() {
  }

  private LanguageDTO(Integer languageId, String name, String extension, String param) {
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

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<LanguageDTO> {

    private Builder() {
    }

    @Override
    public LanguageDTO build() {
      return new LanguageDTO(languageId, name, extension, parma);
    }

    @Override
    public LanguageDTO build(Map<String, Object> properties) {
      languageId = (Integer) properties.get("languageId");
      name = (String) properties.get("name");
      extension = (String) properties.get("extension");
      parma = (String) properties.get("parma");
      return build();
    }

    private Integer languageId;
    private String name;
    private String extension;
    private String parma;

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

    public String getParma() {
      return parma;
    }

    public Builder setParma(String parma) {
      this.parma = parma;
      return this;
    }
  }
}
