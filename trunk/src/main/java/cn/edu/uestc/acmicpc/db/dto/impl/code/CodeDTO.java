package cn.edu.uestc.acmicpc.db.dto.impl.code;

import java.util.*;
import java.sql.*;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

@Fields({ "codeId", "content" })
public class CodeDTO implements BaseDTO<Code> {

  public CodeDTO() {
  }

  private CodeDTO(Integer codeId, String content) {
    this.codeId = codeId;
    this.content = content;
  }

  private Integer codeId;
  private String content;

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

  public static class Builder implements BaseBuilder<CodeDTO> {

    private Builder() {
    }

    @Override
    public CodeDTO build() {
      return new CodeDTO(codeId, content);
    }

    @Override
    public CodeDTO build(Map<String, Object> properties) {
      codeId = (Integer) properties.get("codeId");
      content = (String) properties.get("content");
      return build();

    }

    private Integer codeId;
    private String content;

    public Integer getCodeId() {
      return codeId;
    }

    public Builder setCodeId(Integer codeId) {
      this.codeId = codeId;
      return this;
    }

    public String getContent() {
      return content;
    }

    public Builder setContent(String content) {
      this.content = content;
      return this;
    }
  }
}
