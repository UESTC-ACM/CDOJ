package cn.edu.uestc.acmicpc.db.dto.impl.contest;

import java.sql.Timestamp;
import java.util.Map;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

@Fields({"contestId", "title", "type", "time", "length"})
public class ContestListDTO implements BaseDTO<Contest>{

  private Integer contestId;
  private String title;
  private String description;
  private Byte type;
  private Timestamp time;
  private Integer length;

  public ContestListDTO() {
  }

  public ContestListDTO(Integer contestId, String title, String description,
      Byte type, Timestamp time, Integer length) {
    this.contestId = contestId;
    this.title = title;
    this.description = description;
    this.type = type;
    this.time = time;
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
  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<ContestListDTO>{

    private Integer contestId;
    private String title;
    private String description;
    private Byte type;
    private Timestamp time;
    private Integer length;

    @Override
    public ContestListDTO build() {
      return new ContestListDTO(contestId, title, description, type, time, length);
    }

    @Override
    public ContestListDTO build(Map<String, Object> properties) {
      contestId = (Integer) properties.get("contestId");
      title = (String) properties.get("title");
      description = (String) properties.get("description");
      type = (Byte) properties.get("type");
      time = (Timestamp) properties.get("time");
      length = (Integer) properties.get("length");
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
  }
}
