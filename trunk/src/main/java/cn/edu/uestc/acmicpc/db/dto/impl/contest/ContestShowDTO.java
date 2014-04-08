package cn.edu.uestc.acmicpc.db.dto.impl.contest;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.util.annotation.Fields;
import cn.edu.uestc.acmicpc.util.enums.ContestType;

import java.sql.Timestamp;
import java.util.Map;

/**
 * DTO for contest entity.
 * <br/>
 * <code>@Fields({ "contestId", "title", "description", "time", "length", "type" })</code>
 */
@Fields({"contestId", "title", "description", "time", "length", "type",
    "isVisible", "parentId"})
public class ContestShowDTO implements BaseDTO<Contest> {

  private Integer contestId;
  private String title;
  private String description;
  private String status;
  private Timestamp startTime;
  private Timestamp endTime;
  private Timestamp currentTime;
  private Long timeLeft;
  private Integer length;
  private Byte type;
  private String typeName;
  private Boolean isVisible;
  private Integer parentId;

  public ContestShowDTO() {
  }

  public ContestShowDTO(Integer contestId, String title,
                        String description,
                        String status, Timestamp startTime, Timestamp endTime,
                        Timestamp currentTime, Long timeLeft, Integer length, Byte type,
                        String typeName, Boolean isVisible, Integer parentId) {
    this.contestId = contestId;
    this.title = title;
    this.description = description;
    this.status = status;
    this.startTime = startTime;
    this.endTime = endTime;
    this.currentTime = currentTime;
    this.timeLeft = timeLeft;
    this.length = length;
    this.type = type;
    this.typeName = typeName;
    this.isVisible = isVisible;
    this.parentId = parentId;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Long getTimeLeft() {
    return timeLeft;
  }

  public void setTimeLeft(Long timeLeft) {
    this.timeLeft = timeLeft;
  }

  public Timestamp getStartTime() {
    return startTime;
  }

  public void setStartTime(Timestamp startTime) {
    this.startTime = startTime;
  }

  public Timestamp getEndTime() {
    return endTime;
  }

  public void setEndTime(Timestamp endTime) {
    this.endTime = endTime;
  }

  public Timestamp getCurrentTime() {
    return currentTime;
  }

  public void setCurrentTime(Timestamp currentTime) {
    this.currentTime = currentTime;
  }

  public Byte getType() {
    return type;
  }

  public void setType(Byte type) {
    this.type = type;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ContestShowDTO that = (ContestShowDTO) o;

    if (contestId != null ? !contestId.equals(that.contestId) : that.contestId != null) {
      return false;
    }
    if (currentTime != null ? !currentTime.equals(that.currentTime) : that.currentTime != null) {
      return false;
    }
    if (description != null ? !description.equals(that.description) : that.description != null) {
      return false;
    }
    if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) {
      return false;
    }
    if (isVisible != null ? !isVisible.equals(that.isVisible) : that.isVisible != null) {
      return false;
    }
    if (length != null ? !length.equals(that.length) : that.length != null) {
      return false;
    }
    if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) {
      return false;
    }
    if (status != null ? !status.equals(that.status) : that.status != null) {
      return false;
    }
    if (timeLeft != null ? !timeLeft.equals(that.timeLeft) : that.timeLeft != null) {
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

    return true;
  }

  @Override
  public int hashCode() {
    int result = contestId != null ? contestId.hashCode() : 0;
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
    result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
    result = 31 * result + (currentTime != null ? currentTime.hashCode() : 0);
    result = 31 * result + (timeLeft != null ? timeLeft.hashCode() : 0);
    result = 31 * result + (length != null ? length.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
    result = 31 * result + (isVisible != null ? isVisible.hashCode() : 0);
    result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<ContestShowDTO> {

    private Integer contestId;
    private String title;
    private String description;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp currentTime;
    private Long timeLeft;
    private Integer length;
    private String status;
    private Byte type;
    private String typeName;
    private Boolean isVisible;
    private Integer parentId;

    @Override
    public ContestShowDTO build() {
      return new ContestShowDTO(contestId, title, description,
          status, startTime, endTime, currentTime, timeLeft, length, type,
          typeName, isVisible, parentId);
    }

    @Override
    public ContestShowDTO build(Map<String, Object> properties) {
      contestId = (Integer) properties.get("contestId");
      title = (String) properties.get("title");
      description = (String) properties.get("description");
      length = (Integer) properties.get("length") * 1000;
      startTime = (Timestamp) properties.get("time");
      type = (Byte) properties.get("type");
      typeName = ContestType.values()[type].getDescription();
      isVisible = (Boolean) properties.get("isVisible");
      parentId = (Integer) properties.get("parentId");

      endTime = new Timestamp(startTime.getTime() + length);
      currentTime = new Timestamp(System.currentTimeMillis());
      timeLeft = Math.max(endTime.getTime() - currentTime.getTime(), 0L);
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

    public Builder setTimeLeft(Long timeLeft) {
      this.timeLeft = timeLeft;
      return this;
    }

    public Builder setLength(Integer length) {
      this.length = length;
      return this;
    }

    public Builder setStatus(String status) {
      this.status = status;
      return this;
    }

    public Builder setStartTime(Timestamp startTime) {
      this.startTime = startTime;
      return this;
    }

    public Builder setEndTime(Timestamp endTime) {
      this.endTime = endTime;
      return this;
    }

    public Builder setCurrentTime(Timestamp currentTime) {
      this.currentTime = currentTime;
      return this;
    }

    public Builder setType(Byte type) {
      this.type = type;
      return this;
    }

    public Builder setTypeName(String typeName) {
      this.typeName = typeName;
      return this;
    }

    public Builder setIsVisible(Boolean isVisible) {
      this.isVisible = isVisible;
      return this;
    }
  }
}