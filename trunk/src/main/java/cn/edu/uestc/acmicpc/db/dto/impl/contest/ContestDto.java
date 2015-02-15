package cn.edu.uestc.acmicpc.db.dto.impl.contest;

import cn.edu.uestc.acmicpc.db.dto.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

/**
 * Dto for contest entity. <br/>
 * <code>@Fields({ "contestId", "title", "description", "time", "length", "type",
 * "parentId"})</code>
 */
@Fields({ "contestId", "title", "description", "time", "length", "type",
    "isVisible", "password", "parentId", "frozenTime" })
public class ContestDto implements BaseDto<Contest> {

  private Integer contestId;
  private String title;
  private String description;
  private Timestamp time;
  private Integer length;
  private Byte type;
  private Boolean isVisible;
  private String password;
  private Integer parentId;
  private Integer frozenTime;

  public ContestDto() {
  }

  public ContestDto(Integer contestId, String title, String description, Timestamp time,
      Integer length, Byte type, Boolean isVisible, String password,
      Integer parentId, Integer frozenTime) {
    this.contestId = contestId;
    this.title = title;
    this.description = description;
    this.length = length;
    this.time = time;
    this.type = type;
    this.isVisible = isVisible;
    this.password = password;
    this.parentId = parentId;
    this.frozenTime = frozenTime;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public Byte getType() {
    return type;
  }

  public void setType(Byte type) {
    this.type = type;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
  }

  public Integer getFrozenTime() {
    return frozenTime;
  }

  public void setFrozenTime(Integer frozenTime) {
    this.frozenTime = frozenTime;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ContestDto)) {
      return false;
    }

    ContestDto that = (ContestDto) o;
    return Objects.equals(this.contestId, that.contestId)
        && Objects.equals(this.description, that.description)
        && Objects.equals(this.isVisible, that.isVisible)
        && Objects.equals(this.length, that.length)
        && Objects.equals(this.time, that.time)
        && Objects.equals(this.title, that.title)
        && Objects.equals(this.type, that.type)
        && Objects.equals(this.password, that.password)
        && Objects.equals(this.parentId, that.parentId)
        && Objects.equals(this.frozenTime, that.frozenTime);
  }

  @Override
  public int hashCode() {
    int result = contestId != null ? contestId.hashCode() : 0;
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (time != null ? time.hashCode() : 0);
    result = 31 * result + (length != null ? length.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (isVisible != null ? isVisible.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
    result = 31 * result + (frozenTime != null ? frozenTime.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<ContestDto> {

    private Integer contestId;
    private String title;
    private String description;
    private Timestamp time;
    private Integer length;
    private Byte type;
    private Boolean isVisible;
    private String password;
    private Integer parentId;
    private Integer frozenTime;

    @Override
    public ContestDto build() {
      return new ContestDto(contestId, title, description, time, length, type,
          isVisible, password, parentId, frozenTime);
    }

    @Override
    public ContestDto build(Map<String, Object> properties) {
      contestId = (Integer) properties.get("contestId");
      title = (String) properties.get("title");
      description = (String) properties.get("description");
      length = (Integer) properties.get("length") * 1000;
      time = (Timestamp) properties.get("time");
      type = (Byte) properties.get("type");
      isVisible = (Boolean) properties.get("isVisible");
      password = (String) properties.get("password");
      parentId = (Integer) properties.get("parentId");
      if (properties.get("frozenTime") != null) {
        frozenTime = (Integer) properties.get("frozenTime") * 1000;
      }
      return build();
    }

    public Builder setParentId(Integer parentId) {
      this.parentId = parentId;
      return this;
    }

    public Builder setPassword(String password) {
      this.password = password;
      return this;
    }

    public Builder setContestId(Integer contestId) {
      this.contestId = contestId;
      return this;
    }

    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder setDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder setTime(Timestamp time) {
      this.time = time;
      return this;
    }

    public Builder setLength(Integer length) {
      this.length = length;
      return this;
    }

    public Builder setType(Byte type) {
      this.type = type;
      return this;
    }

    public Builder setIsVisible(Boolean isVisible) {
      this.isVisible = isVisible;
      return this;
    }

    public Builder setFrozenTime(Integer frozenTime) {
      this.frozenTime = frozenTime;
      return this;
    }
  }
}