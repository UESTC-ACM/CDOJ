package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Code;

/**
 * Data transfer object for {@link Code}.
 */
public class CodeDTO implements BaseDTO<Code> {

  private Integer codeId;
  private String content;

  public CodeDTO() {
  }

  private CodeDTO(Integer codeId, String content) {
    this.codeId = codeId;
    this.content = content;
  }

  public Integer getCodeId() {
    return codeId;
  }

  public void setCodeId(Integer codeId) {
    this.codeId = codeId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public static Builder builder() {
    return new Builder();
  }

  /** Builder for {@link CodeDTO}. */
  public static class Builder {

    private Builder() {
    }

    private Integer codeId;
    private String content = "";

    public Builder setCodeId(Integer codeId) {
      this.codeId = codeId;
      return this;
    }

    public Builder setContent(String content) {
      this.content = content;
      return this;
    }

    public CodeDTO build() {
      return new CodeDTO(codeId, content);
    }
  }
}
