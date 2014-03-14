package cn.edu.uestc.acmicpc.db.dto.impl.status;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

/**
 * DTO for code and compile information query.
 * <br/>
 * <code>@Fields({ "statusId", "userId", "codeByCodeId.content", "compileInfoId" })</code>
 */
@Fields({"statusId", "userId", "codeByCodeId.content", "compileInfoId"})
public class StatusInformationDTO implements BaseDTO<Status> {

  public StatusInformationDTO() {
  }

  private StatusInformationDTO(Integer statusId, Integer userId, String codeContent,
                               Integer compileInfoId) {
    this.statusId = statusId;
    this.userId = userId;
    this.codeContent = codeContent;
    this.compileInfoId = compileInfoId;
  }

  private Integer statusId;
  private Integer userId;
  private String codeContent;
  private Integer compileInfoId;

  public Integer getStatusId() {
    return statusId;
  }

  public void setStatusId(Integer statusId) {
    this.statusId = statusId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getCodeContent() {
    return codeContent;
  }

  public void setCodeContent(String codeContent) {
    this.codeContent = codeContent;
  }

  public Integer getCompileInfoId() {
    return compileInfoId;
  }

  public void setCompileInfoId(Integer compileInfoId) {
    this.compileInfoId = compileInfoId;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<StatusInformationDTO> {

    private Builder() {
    }

    @Override
    public StatusInformationDTO build() {
      return new StatusInformationDTO(statusId, userId, codeContent, compileInfoId);
    }

    @Override
    public StatusInformationDTO build(Map<String, Object> properties) {
      statusId = (Integer) properties.get("statusId");
      userId = (Integer) properties.get("userId");
      codeContent = (String) properties.get("codeByCodeId.content");
      compileInfoId = (Integer) properties.get("compileInfoId");
      return build();

    }

    private Integer statusId;
    private Integer userId;
    private String codeContent;
    private Integer compileInfoId;

    public Integer getStatusId() {
      return statusId;
    }

    public Builder setStatusId(Integer statusId) {
      this.statusId = statusId;
      return this;
    }

    public Integer getUserId() {
      return userId;
    }

    public Builder setUserId(Integer userId) {
      this.userId = userId;
      return this;
    }

    public String getCodeContent() {
      return codeContent;
    }

    public Builder setCodeContent(String codeContent) {
      this.codeContent = codeContent;
      return this;
    }

    public Integer getCompileInfoId() {
      return compileInfoId;
    }

    public Builder setCompileInfoId(Integer compileInfoId) {
      this.compileInfoId = compileInfoId;
      return this;
    }
  }
}
