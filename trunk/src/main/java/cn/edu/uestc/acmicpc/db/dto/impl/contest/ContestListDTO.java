package cn.edu.uestc.acmicpc.db.dto.impl.contest;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.util.annotation.Fields;
import cn.edu.uestc.acmicpc.util.enums.ContestType;

import java.sql.Timestamp;
import java.util.Map;

/**
 * DTO used in contest list.
 * <br/>
 * <code>@Fields({ "contestId", "title", "type", "time", "length", "isVisible" })</code>
 */
@Fields({"contestId", "title", "type", "time", "length", "isVisible", "parentId"})
public class ContestListDTO implements BaseDTO<Contest> {

  private Integer contestId;
  private String title;
  private String description;
  private Byte type;
  private Timestamp time;
  private Integer length;
  private Boolean isVisible;
  private String typeName;
  private String status;
  private Integer parentId;
  private Byte parentType;
  private String parentTypeName;

  public ContestListDTO() {
  }

  public ContestListDTO(Integer contestId, String title, String description, Byte type,
                        Timestamp time, Integer length, Boolean isVisible, String typeName,
                        String status, Integer parentId) {
    this.contestId = contestId;
    this.title = title;
    this.description = description;
    this.type = type;
    this.time = time;
    this.length = length;
    this.isVisible = isVisible;
    this.typeName = typeName;
    this.status = status;
    this.parentId = parentId;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public Byte getParentType() {
    return parentType;
  }

  public void setParentType(Byte parentType) {
    this.parentType = parentType;
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

  public String getParentTypeName() {
    return parentTypeName;
  }

  public void setParentTypeName(String parentTypeName) {
    this.parentTypeName = parentTypeName;
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

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ContestListDTO that = (ContestListDTO) o;

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
    if (status != null ? !status.equals(that.status) : that.status != null) {
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
    if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) {
      return false;
    }
    if (parentId != null ? !parentId.equals(that.parentId) : that.parentId != null) {
      return false;
    }
    if (parentType != null ? !parentType.equals(that.parentType) : that.parentType != null) {
      return false;
    }
    if (parentTypeName != null ? !parentTypeName.equals(that.parentTypeName) : that.parentTypeName != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = contestId != null ? contestId.hashCode() : 0;
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (time != null ? time.hashCode() : 0);
    result = 31 * result + (length != null ? length.hashCode() : 0);
    result = 31 * result + (isVisible != null ? isVisible.hashCode() : 0);
    result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
    result = 31 * result + (parentType != null ? parentType.hashCode() : 0);
    result = 31 * result + (parentTypeName != null ? parentTypeName.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<ContestListDTO> {

    private Integer contestId;
    private String title;
    private String description;
    private Byte type;
    private Timestamp time;
    private Integer length;
    private Boolean isVisible;
    private String typeName;
    private String status;
    private Integer parentId;

    @Override
    public ContestListDTO build() {
      return new ContestListDTO(contestId, title, description, type, time,
          length, isVisible, typeName, status, parentId);
    }

    @Override
    public ContestListDTO build(Map<String, Object> properties) {
      contestId = (Integer) properties.get("contestId");
      title = (String) properties.get("title");
      description = (String) properties.get("description");
      type = (Byte) properties.get("type");
      time = (Timestamp) properties.get("time");
      length = (Integer) properties.get("length") * 1000;
      isVisible = (Boolean) properties.get("isVisible");
      typeName = ContestType.values()[type].getDescription();
      parentId = (Integer) properties.get("parentId");

      Timestamp endTime = new Timestamp(time.getTime() + length);
      Timestamp currentTime = new Timestamp(System.currentTimeMillis());
      Long timeLeft = Math.max(endTime.getTime() - currentTime.getTime(), 0L);
      if (timeLeft > length) {
        status = "Pending";
      } else if (timeLeft > 0) {
        status = "Running";
      } else {
        status = "Ended";
      }
      return build();
    }

    public Builder setParentId(Integer parentId) {
      this.parentId = parentId;
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

    public Builder setType(Byte type) {
      this.type = type;
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

    public Builder setIsVisible(Boolean isVisible) {
      this.isVisible = isVisible;
      return this;
    }

    public Builder setTypeName(String typeName) {
      this.typeName = typeName;
      return this;
    }

    public Builder setStatus(String status) {
      this.status = status;
      return this;
    }
  }
}
