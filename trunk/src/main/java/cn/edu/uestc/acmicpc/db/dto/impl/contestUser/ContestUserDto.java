package cn.edu.uestc.acmicpc.db.dto.impl.contestUser;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.ContestUser;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;
import java.util.Objects;

/**
 * Dto used in contest user entity.
 */

@Fields({ "contestUserId", "contestId", "userId", "status", "comment" })
public class ContestUserDto implements BaseDto<ContestUser> {
  private Integer contestUserId;
  private Integer contestId;
  private Integer userId;
  private Byte status;
  private String comment;

  public ContestUserDto() {
  }

  public ContestUserDto(Integer contestUserId, Integer contestId, Integer userId,
      Byte status, String comment) {
    this.contestUserId = contestUserId;
    this.contestId = contestId;
    this.userId = userId;
    this.status = status;
    this.comment = comment;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Integer getContestUserId() {
    return contestUserId;
  }

  public void setContestUserId(Integer contestUserId) {
    this.contestUserId = contestUserId;
  }

  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Byte getStatus() {
    return status;
  }

  public void setStatus(Byte status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ContestUserDto)) {
      return false;
    }

    ContestUserDto that = (ContestUserDto) o;
    return Objects.equals(this.contestId, that.contestId)
        && Objects.equals(this.contestUserId, that.contestUserId)
        && Objects.equals(this.status, that.status)
        && Objects.equals(this.userId, that.userId)
        && Objects.equals(this.comment, that.comment);
  }

  @Override
  public int hashCode() {
    int result = contestUserId != null ? contestUserId.hashCode() : 0;
    result = 31 * result + (contestId != null ? contestId.hashCode() : 0);
    result = 31 * result + (userId != null ? userId.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    result = 31 * result + (comment != null ? comment.hashCode() : 0);
    return result;
  }

  public static Builder Builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<ContestUserDto> {

    private Integer contestUserId;
    private Integer contestId;
    private Integer userId;
    private Byte status;
    private String comment;

    @Override
    public ContestUserDto build() {
      return new ContestUserDto(contestUserId, contestId, userId, status, comment);
    }

    @Override
    public ContestUserDto build(Map<String, Object> properties) {
      contestUserId = (Integer) properties.get("contestUserId");
      contestId = (Integer) properties.get("contestId");
      userId = (Integer) properties.get("userId");
      status = (Byte) properties.get("status");
      comment = (String) properties.get("comment");
      return build();
    }

    public Builder setComment(String comment) {
      this.comment = comment;
      return this;
    }

    public Builder setContestUserId(Integer contestUserId) {
      this.contestUserId = contestUserId;
      return this;
    }

    public Builder setContestId(Integer contestId) {
      this.contestId = contestId;
      return this;
    }

    public Builder setUserId(Integer userId) {
      this.userId = userId;
      return this;
    }

    public Builder setStatus(Byte status) {
      this.status = status;
      return this;
    }
  }
}
