package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;

import java.sql.Timestamp;

/**
 * Data transfer object for {@link UserSerialKey}.
 * TODO(mzry1992)
 */
public class UserSerialKeyDTO implements BaseDTO<UserSerialKey> {

  private Integer userSerialKeyId;

  private Timestamp time;

  private String serialKey;

  private Integer userId;

  public Integer getUserSerialKeyId() {
    return userSerialKeyId;
  }

  public void setUserSerialKeyId(Integer userSerialKeyId) {
    this.userSerialKeyId = userSerialKeyId;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public String getSerialKey() {
    return serialKey;
  }

  public void setSerialKey(String serialKey) {
    this.serialKey = serialKey;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public UserSerialKeyDTO(Integer userSerialKeyId, Timestamp time, String serialKey, Integer userId) {
    this.userSerialKeyId = userSerialKeyId;
    this.time = time;
    this.serialKey = serialKey;
    this.userId = userId;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }

    private Integer userSerialKeyId;

    private Timestamp time;

    private String serialKey;

    private Integer userId;

    public Builder setUserSerialKeyId(Integer userSerialKeyId) {
      this.userSerialKeyId = userSerialKeyId;
      return this;
    }

    public Builder setTime(Timestamp time) {
      this.time = time;
      return this;
    }

    public Builder setSerialKey(String serialKey) {
      this.serialKey = serialKey;
      return this;
    }

    public Builder setUserId(Integer userId) {
      this.userId = userId;
      return this;
    }

    public UserSerialKeyDTO build() {
      return new UserSerialKeyDTO(userSerialKeyId, time, serialKey, userId);
    }
  }
}
