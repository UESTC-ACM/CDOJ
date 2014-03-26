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
@Fields({"statusId", "userId", "codeByCodeId.content", "compileInfoId", "contestId"})
public class StatusInformationDTO implements BaseDTO<Status> {

  public StatusInformationDTO() {
  }

  private StatusInformationDTO(Integer statusId, Integer userId, String codeContent,
                               Integer compileInfoId, Integer contestId) {
    this.statusId = statusId;
    this.userId = userId;
    this.codeContent = codeContent;
    this.compileInfoId = compileInfoId;
    this.contestId = contestId;
  }

  private Integer statusId;
  private Integer userId;
  private String codeContent;
  private Integer compileInfoId;
  private Integer contestId;

  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    StatusInformationDTO that = (StatusInformationDTO) o;

    if (codeContent != null ? !codeContent.equals(that.codeContent) : that.codeContent != null) {
      return false;
    }
    if (compileInfoId != null ? !compileInfoId.equals(that.compileInfoId) : that.compileInfoId != null) {
      return false;
    }
    if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) {
      return false;
    }
    if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
      return false;
    }
    if (contestId != null ? !contestId.equals(that.contestId) : that.contestId != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = statusId != null ? statusId.hashCode() : 0;
    result = 31 * result + (userId != null ? userId.hashCode() : 0);
    result = 31 * result + (codeContent != null ? codeContent.hashCode() : 0);
    result = 31 * result + (compileInfoId != null ? compileInfoId.hashCode() : 0);
    result = 31 * result + (contestId != null ? contestId.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<StatusInformationDTO> {

    private Builder() {
    }

    @Override
    public StatusInformationDTO build() {
      return new StatusInformationDTO(statusId, userId, codeContent, compileInfoId,
          contestId);
    }

    @Override
    public StatusInformationDTO build(Map<String, Object> properties) {
      statusId = (Integer) properties.get("statusId");
      userId = (Integer) properties.get("userId");
      codeContent = (String) properties.get("codeByCodeId.content");
      compileInfoId = (Integer) properties.get("compileInfoId");
      contestId = (Integer) properties.get("contestId");
      return build();

    }

    private Integer statusId;
    private Integer userId;
    private String codeContent;
    private Integer compileInfoId;
    private Integer contestId;

    public Builder setContestId(Integer contestId) {
      this.contestId = contestId;
      return this;
    }

    public Builder setStatusId(Integer statusId) {
      this.statusId = statusId;
      return this;
    }

    public Builder setUserId(Integer userId) {
      this.userId = userId;
      return this;
    }

    public Builder setCodeContent(String codeContent) {
      this.codeContent = codeContent;
      return this;
    }

    public Builder setCompileInfoId(Integer compileInfoId) {
      this.compileInfoId = compileInfoId;
      return this;
    }
  }
}
