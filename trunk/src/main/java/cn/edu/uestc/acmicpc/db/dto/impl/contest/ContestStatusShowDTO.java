package cn.edu.uestc.acmicpc.db.dto.impl.contest;

import java.sql.Timestamp;
import java.util.Map;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

@Fields({"contestId", "title", "description", "time", "length"})
public class ContestStatusShowDTO implements BaseDTO<Contest>{

  private Integer contestId;
  private String title;
  private String description;
  private String status;
  private Long timeLeft;
  private Integer length;



  public ContestStatusShowDTO() {
  }

  public ContestStatusShowDTO(Integer contestId, String title, String description,
      String status, Long timeLeft, Integer length) {
    this.contestId = contestId;
    this.title = title;
    this.description = description;
    this.status = status;
    this.timeLeft = timeLeft;
    this.length = length;
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
  public static Builder builder() {
    return new Builder();
  }


  public static class Builder implements BaseBuilder<ContestStatusShowDTO>{

    private Integer contestId;
    private String title;
    private String description;
    private Long timeLeft;
    private Integer length;
    private String status;

    @Override
    public ContestStatusShowDTO build() {
      return new ContestStatusShowDTO(contestId, title, description,
          status, timeLeft, length);
    }

    @Override
    public ContestStatusShowDTO build(Map<String, Object> properties) {
      contestId = (Integer) properties.get("contestId");
      title = (String) properties.get("title");
      description = (String) properties.get("description");
      length = (Integer) properties.get("length");
      Timestamp time = (Timestamp) properties.get("time");
      timeLeft = Math.max(time.getTime() + length - System.currentTimeMillis(), 0L);
      if(timeLeft > length){
        status = "Pending";
      } else if(timeLeft >0){
        status = "Running";
      } else {
        status = "Ended";
      }
      return build();
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
  }
}