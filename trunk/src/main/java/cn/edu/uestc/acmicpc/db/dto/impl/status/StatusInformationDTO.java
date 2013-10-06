package cn.edu.uestc.acmicpc.db.dto.impl.status;

import java.util.*;
import java.sql.*;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

@Fields({ "statusId", "userId", "codeByCodeId.content", "compileInfoByCompileInfoId.content" })
public class StatusInformationDTO implements BaseDTO<Status> {

  public StatusInformationDTO() {
  }

  private StatusInformationDTO(Integer statusId, Integer userId, String codeContent,
                               String compileInfoContent) {
    this.statusId = statusId;
    this.userId = userId;
    this.codeContent = codeContent;
    this.compileInfoContent = compileInfoContent;
  }

  private Integer statusId;
  private Integer userId;
  private String codeContent;
  private String compileInfoContent;

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

  public String getCompileInfoContent() {
    return compileInfoContent;
  }

  public void setCompileInfoContent(String compileInfoContent) {
    this.compileInfoContent = compileInfoContent;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<StatusInformationDTO> {

    private Builder() {
    }

    @Override
    public StatusInformationDTO build() {
      return new StatusInformationDTO(statusId, userId, codeContent, compileInfoContent);
    }

    @Override
    public StatusInformationDTO build(Map<String, Object> properties) {
      statusId = (Integer) properties.get("statusId");
      userId = (Integer) properties.get("userId");
      codeContent = (String) properties.get("codeByCodeId.content");
      compileInfoContent = (String) properties.get("compileInfoByCompileInfoId.content");
      return build();

    }

    private Integer statusId;
    private Integer userId;
    private String codeContent;
    private String compileInfoContent;

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

    public String getCompileInfoContent() {
      return compileInfoContent;
    }

    public Builder setCompileInfoContent(String compileInfoContent) {
      this.compileInfoContent = compileInfoContent;
      return this;
    }
  }
}
