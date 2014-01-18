package cn.edu.uestc.acmicpc.db.dto.impl.contest;

import java.sql.Timestamp;

/**
 * DTO post from contest editor.
 */
public class ContestEditDTO {

  public ContestEditDTO() {
  }

  public ContestEditDTO(String action, Integer contestId, String description, Integer lengthDays,
                        Integer lengthHours, Integer lengthMinutes, String problemList,
                        Timestamp time, String title, Byte type) {
    this.action = action;
    this.contestId = contestId;
    this.description = description;
    this.lengthDays = lengthDays;
    this.lengthHours = lengthHours;
    this.lengthMinutes = lengthMinutes;
    this.problemList = problemList;
    this.time = time;
    this.title = title;
    this.type = type;
  }

  private String action;
  private Integer contestId;
  private String description;
  private Integer lengthDays;
  private Integer lengthHours;
  private Integer lengthMinutes;
  private String problemList;
  private Timestamp time;
  private String title;
  private Byte type;

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

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

  public String getProblemList() {
    return problemList;
  }

  public void setProblemList(String problemList) {
    this.problemList = problemList;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Byte getType() {
    return type;
  }

  public void setType(Byte type) {
    this.type = type;
  }


  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }

    private String action;
    private Integer contestId;
    private String description;
    private Integer lengthDays;
    private Integer lengthHours;
    private Integer lengthMinutes;
    private String problemList;
    private Timestamp time;
    private String title;
    private Byte type;

    public ContestEditDTO build() {
      return new ContestEditDTO(action, contestId, description, lengthDays, lengthHours, lengthMinutes,
          problemList, time, title, type);
    }

    public Builder setAction(String action) {
      this.action = action;
      return this;
    }

    public Builder setContestId(Integer contestId) {
      this.contestId = contestId;
      return this;
    }

    public Builder setDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder setLengthDays(Integer lengthDays) {
      this.lengthDays = lengthDays;
      return this;
    }

    public Builder setLengthHours(Integer lengthHours) {
      this.lengthHours = lengthHours;
      return this;
    }

    public Builder setLengthMinutes(Integer lengthMinutes) {
      this.lengthMinutes = lengthMinutes;
      return this;
    }

    public Builder setProblemList(String problemList) {
      this.problemList = problemList;
      return this;
    }

    public Builder setTime(Timestamp time) {
      this.time = time;
      return this;
    }

    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder setType(Byte type) {
      this.type = type;
      return this;
    }
  }
}
