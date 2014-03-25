package cn.edu.uestc.acmicpc.db.dto.impl.contest;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.sql.Timestamp;
import java.util.Map;

/**
 * DTO for contest editor view.
 * <br/>
 * <code>@Fields({ "contestId", "title", "description", "time", "length", "type" })</code>
 */
@Fields({"contestId", "title", "description", "time", "length", "type"})
public class ContestEditorShowDTO implements BaseDTO<Contest> {

  public ContestEditorShowDTO() {
  }

  private ContestEditorShowDTO(Integer contestId, String title,
                               String description, Timestamp time, Integer length, Byte type,
                               Integer lengthDays, Integer lengthHours, Integer lengthMinutes) {
    this.contestId = contestId;
    this.title = title;
    this.description = description;
    this.time = time;
    this.length = length;
    this.type = type;
    this.lengthDays = lengthDays;
    this.lengthHours = lengthHours;
    this.lengthMinutes = lengthMinutes;
  }

  private Integer contestId;
  private String title;
  private String description;
  private Timestamp time;
  private Integer length;
  private Integer lengthDays;
  private Integer lengthHours;
  private Integer lengthMinutes;
  private Byte type;

  public Integer getLengthDays() {
    return lengthDays;
  }

  public void setLengthDays(Integer lengthDays) {
    this.lengthDays = lengthDays;
  }

  public Integer getLengthHours() {
    return lengthHours;
  }

  public void setLengthHours(Integer lengthHours) {
    this.lengthHours = lengthHours;
  }

  public Integer getLengthMinutes() {
    return lengthMinutes;
  }

  public void setLengthMinutes(Integer lengthMinutes) {
    this.lengthMinutes = lengthMinutes;
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

  public Byte getType() {
    return type;
  }

  public void setType(Byte type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ContestEditorShowDTO that = (ContestEditorShowDTO) o;

    if (contestId != null ? !contestId.equals(that.contestId) : that.contestId != null) {
      return false;
    }
    if (description != null ? !description.equals(that.description) : that.description != null) {
      return false;
    }
    if (length != null ? !length.equals(that.length) : that.length != null) {
      return false;
    }
    if (lengthDays != null ? !lengthDays.equals(that.lengthDays) : that.lengthDays != null) {
      return false;
    }
    if (lengthHours != null ? !lengthHours.equals(that.lengthHours) : that.lengthHours != null) {
      return false;
    }
    if (lengthMinutes != null ? !lengthMinutes.equals(that.lengthMinutes) : that.lengthMinutes != null) {
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

    return true;
  }

  @Override
  public int hashCode() {
    int result = contestId != null ? contestId.hashCode() : 0;
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (time != null ? time.hashCode() : 0);
    result = 31 * result + (length != null ? length.hashCode() : 0);
    result = 31 * result + (lengthDays != null ? lengthDays.hashCode() : 0);
    result = 31 * result + (lengthHours != null ? lengthHours.hashCode() : 0);
    result = 31 * result + (lengthMinutes != null ? lengthMinutes.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<ContestEditorShowDTO> {

    private Builder() {
    }

    @Override
    public ContestEditorShowDTO build() {
      return new ContestEditorShowDTO(contestId, title, description, time,
          length, type, lengthDays, lengthHours, lengthMinutes);
    }

    @Override
    public ContestEditorShowDTO build(Map<String, Object> properties) {
      contestId = (Integer) properties.get("contestId");
      title = (String) properties.get("title");
      description = (String) properties.get("description");
      time = (Timestamp) properties.get("time");
      length = (Integer) properties.get("length");
      type = (Byte) properties.get("type");
      lengthDays = length / (24 * 60 * 60);
      lengthHours = (length - lengthDays * 24 * 60 * 60) / (60 * 60);
      lengthMinutes = (length - lengthDays * 24 * 60 * 60 - lengthHours * 60 * 60) / 60;
      return build();
    }

    private Integer contestId;
    private String title;
    private String description;
    private Timestamp time;
    private Integer length;
    private Byte type;
    private Integer lengthDays;
    private Integer lengthHours;
    private Integer lengthMinutes;

    public Integer getLengthDays() {
      return lengthDays;
    }

    public Builder setLengthDays(Integer lengthDays) {
      this.lengthDays = lengthDays;
      return this;
    }

    public Integer getLengthHours() {
      return lengthHours;
    }

    public Builder setLengthHours(Integer lengthHours) {
      this.lengthHours = lengthHours;
      return this;
    }

    public Integer getLengthMinutes() {
      return lengthMinutes;
    }

    public Builder setLengthMinutes(Integer lengthMinutes) {
      this.lengthMinutes = lengthMinutes;
      return this;
    }

    public Integer getContestId() {
      return contestId;
    }

    public Builder setContestId(Integer contestId) {
      this.contestId = contestId;
      return this;
    }

    public String getTitle() {
      return title;
    }

    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    public String getDescription() {
      return description;
    }

    public Builder setDescription(String description) {
      this.description = description;
      return this;
    }

    public Timestamp getTime() {
      return time;
    }

    public Builder setTime(Timestamp time) {
      this.time = time;
      return this;
    }

    public Integer getLength() {
      return length;
    }

    public Builder setLength(Integer length) {
      this.length = length;
      return this;
    }

    public Byte getType() {
      return type;
    }

    public Builder setType(Byte type) {
      this.type = type;
      return this;
    }
  }
}
