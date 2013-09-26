package cn.edu.uestc.acmicpc.db.dto.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;

/**
 * Data transfer object for {@link Contest}.
 */
public class ContestDTO implements BaseDTO<Contest> {

  private Integer contestId;
  private String title;
  private String description;
  private Byte type;
  private Timestamp time;
  private Integer length;
  private Boolean isVisible;
  private List<Integer> problemList;

  public ContestDTO() {
  }

  private ContestDTO(Integer contestId, String title, String description, Byte type,
      Timestamp time, Integer length, Boolean isVisible, List<Integer> problemList) {
    this.contestId = contestId;
    this.title = title;
    this.description = description;
    this.type = type;
    this.time = time;
    this.length = length;
    this.isVisible = isVisible;
    this.problemList = problemList;
  }

  public Boolean getVisible() {
    return isVisible;
  }

  public void setVisible(Boolean visible) {
    isVisible = visible;
  }

  public List<Integer> getProblemList() {
    return problemList;
  }

  public void setProblemList(List<Integer> problemList) {
    this.problemList = problemList;
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

  public Byte getType() {
    return type;
  }

  public void setType(Byte type) {
    this.type = type;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = new Timestamp(time);
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

  public void setIsVisible(Boolean visible) {
    isVisible = visible;
  }

  public static Builder builder() {
    return new Builder();
  }

  /** Builder for {@link ContestDTO}. */
  public static class Builder {

    private Builder() {
    }

    private Integer contestId;
    private String title = "";
    private String description = "";
    private Byte type = 0;
    private Timestamp time = new Timestamp(new Date().getTime());
    private Integer length = 300;
    private Boolean isVisible = false;
    private List<Integer> problemList = new ArrayList<>();

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

    public Builder setProblemList(List<Integer> problemList) {
      this.problemList = problemList;
      return this;
    }

    public Builder addProblem(Integer problemId) {
      this.problemList.add(problemId);
      return this;
    }

    public ContestDTO build() {
      return new ContestDTO(contestId, title, description, type, time, length, isVisible,
          problemList);
    }
  }
}
