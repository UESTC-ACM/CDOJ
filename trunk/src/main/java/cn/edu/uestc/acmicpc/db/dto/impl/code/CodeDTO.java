package cn.edu.uestc.acmicpc.db.dto.impl.code;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

/**
 * DTO for code entity.
 * <br/>
 * <code>@Fields({ "codeId", "content", "share" })</code>
 */
@Fields({"codeId", "content", "share"})
public class CodeDTO implements BaseDTO<Code> {

  public CodeDTO() {
  }

  private CodeDTO(Integer codeId, String content, Boolean share) {
    this.codeId = codeId;
    this.content = content;
    this.share = share;
  }

  private Integer codeId;
  private String content;
  private Boolean share;

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

  public Boolean getShare() {
    return share;
  }

  public void setShare(Boolean share) {
    this.share = share;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<CodeDTO> {

    private Builder() {
    }

    @Override
    public CodeDTO build() {
      return new CodeDTO(codeId, content, share);
    }

    @Override
    public CodeDTO build(Map<String, Object> properties) {
      codeId = (Integer) properties.get("codeId");
      content = (String) properties.get("content");
      share = (Boolean) properties.get("share");
      return build();

    }

    private Integer codeId;
    private String content;
    private Boolean share;

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

    public Boolean getShare() {
      return share;
    }

    public Builder setShare(Boolean share) {
      this.share = share;
      return this;
    }
  }
}
