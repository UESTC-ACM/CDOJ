package cn.edu.uestc.acmicpc.db.dto.impl.userSerialKey;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.sql.Timestamp;
import java.util.Map;

/**
 * DTO for user serial key entity.
 * <br/>
 * <code>@Fields({ "userSerialKeyId", "time", "serialKey", "userId" })</code>
 */
@Fields({"userSerialKeyId", "time", "serialKey", "userId"})
public class UserSerialKeyDTO implements BaseDTO<UserSerialKey> {

  public UserSerialKeyDTO() {
  }

  private UserSerialKeyDTO(Integer userSerialKeyId, Timestamp time, String serialKey, Integer userId) {
    this.userSerialKeyId = userSerialKeyId;
    this.time = time;
    this.serialKey = serialKey;
    this.userId = userId;
  }

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserSerialKeyDTO that = (UserSerialKeyDTO) o;

    if (serialKey != null ? !serialKey.equals(that.serialKey) : that.serialKey != null) {
      return false;
    }
    if (time != null ? !time.equals(that.time) : that.time != null) {
      return false;
    }
    if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
      return false;
    }
    if (userSerialKeyId != null ? !userSerialKeyId.equals(that.userSerialKeyId) : that.userSerialKeyId != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = userSerialKeyId != null ? userSerialKeyId.hashCode() : 0;
    result = 31 * result + (time != null ? time.hashCode() : 0);
    result = 31 * result + (serialKey != null ? serialKey.hashCode() : 0);
    result = 31 * result + (userId != null ? userId.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<UserSerialKeyDTO> {

    private Builder() {
    }

    @Override
    public UserSerialKeyDTO build() {
      return new UserSerialKeyDTO(userSerialKeyId, time, serialKey, userId);
    }

    @Override
    public UserSerialKeyDTO build(Map<String, Object> properties) {
      userSerialKeyId = (Integer) properties.get("userSerialKeyId");
      time = (Timestamp) properties.get("time");
      serialKey = (String) properties.get("serialKey");
      userId = (Integer) properties.get("userId");
      return build();

    }

    private Integer userSerialKeyId;
    private Timestamp time;
    private String serialKey;
    private Integer userId;

    public Integer getUserSerialKeyId() {
      return userSerialKeyId;
    }

    public Builder setUserSerialKeyId(Integer userSerialKeyId) {
      this.userSerialKeyId = userSerialKeyId;
      return this;
    }

    public Timestamp getTime() {
      return time;
    }

    public Builder setTime(Timestamp time) {
      this.time = time;
      return this;
    }

    public String getSerialKey() {
      return serialKey;
    }

    public Builder setSerialKey(String serialKey) {
      this.serialKey = serialKey;
      return this;
    }

    public Integer getUserId() {
      return userId;
    }

    public Builder setUserId(Integer userId) {
      this.userId = userId;
      return this;
    }
  }
}
