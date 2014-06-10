package cn.edu.uestc.acmicpc.db.dto.impl.contest;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.sql.Timestamp;
import java.util.Map;

/**
 * DTO for contest entity.
 * <br/>
 * <code>@Fields({ "contestId", "title", "description", "time", "length", "type",
 * "parentId"})</code>
 */
@Fields({"contestId", "title", "description", "time", "length", "type",
    "isVisible", "password", "parentId", "frozenTime"})
public class ContestDTO implements BaseDTO<Contest> {

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

  public ContestDTO() {
  }

  public ContestDTO(Integer contestId, String title, String description, Timestamp time,
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
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ContestDTO that = (ContestDTO) o;

    if (contestId != null ? !contestId.equals(that.contestId) : that.contestId != null) {
      return false;
    }
    if (description != null ? !description.equals(that.description) : that.description != null) {
      return false;
    }
    if (isVisible != null ? !isVisible.equals(that.isVisible) : that.isVisible != null) {
      return false;
    }
    if (length != null ? !length.equals(that.length) : that.length != null) {
      return false;
    }
    if (time != null ? !time.equals(that.time) : that.time != null) {
      return false;
    }
    if (title != null ? !title.equals(that.title) : that.title != null) {
      return false;
    }
    if (type != null ? !type.equals(that.type) : that.type != null) {
      return false;
    }
    if (password != null ? !password.equals(that.password) : that.password != null) {
      return false;
    }
    if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) {
      return false;
    }
    if (frozenTime != null ? !frozenTime.equals(that.frozenTime) : that.frozenTime != null) {
      return false;
    }

    return true;
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

  public static class Builder implements BaseBuilder<ContestDTO> {

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
    public ContestDTO build() {
      return new ContestDTO(contestId, title, description, time, length, type,
          isVisible, password, parentId, frozenTime);
    }

    @Override
    public ContestDTO build(Map<String, Object> properties) {
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