package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.CompileInfo;

/** Data transfer object for {@link CompileInfo}. */
public class CompileInfoDTO implements BaseDTO<CompileInfo> {

  private Integer compileInfoId;
  private String content;

  public CompileInfoDTO() {
  }

  public CompileInfoDTO(Integer compileInfoId, String content) {
    this.compileInfoId = compileInfoId;
    this.content = content;
  }

  public Integer getCompileInfoId() {
    return compileInfoId;
  }

  public void setCompileInfoId(Integer compileInfoId) {
    this.compileInfoId = compileInfoId;
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

  public static class Builder {

    private Builder() {
    }

    private Integer compileInfoId;
    private String content;

    public Integer getCompileInfoId() {
      return compileInfoId;
    }
    public void setCompileInfoId(Integer compileInfoId) {
      this.compileInfoId = compileInfoId;
    }
    public String getContent() {
      return content;
    }
    public void setContent(String content) {
      this.content = content;
    }

    public CompileInfoDTO build() {
      return new CompileInfoDTO(compileInfoId, content);
    }
  }
}
