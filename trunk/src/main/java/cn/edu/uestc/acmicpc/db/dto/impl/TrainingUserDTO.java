package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.util.Global;

/**
 * Data transfer object for {@link TrainingUser}.
 */
public class TrainingUserDTO implements BaseDTO<TrainingUser> {

  private Integer trainingUserId;
  private String name;
  private String member;
  private Integer type;

  public TrainingUserDTO() {
  }

  private TrainingUserDTO(Integer trainingUserId, String name, String member, Integer type) {
    this.trainingUserId = trainingUserId;
    this.name = name;
    this.member = member;
    this.type = type;
  }

  public String getMember() {
    return member;
  }

  public void setMember(String member) {
    this.member = member;
  }

  public Integer getTrainingUserId() {
    return trainingUserId;
  }

  public void setTrainingUserId(Integer trainingUserId) {
    this.trainingUserId = trainingUserId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public static Builder builder() {
    return new Builder();
  }

  /** Builder for {@link TrainingUserDTO}. */
  public static class Builder {

    private Builder() {
    }

    private Integer trainingUserId;
    private String name = "";
    private String member = "";
    private Integer type = Global.TrainingUserType.PERSONAL.ordinal();

    public Builder setTrainingUserId(Integer trainingUserId) {
      this.trainingUserId = trainingUserId;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setMember(String member) {
      this.member = member;
      return this;
    }

    public Builder setType(Integer type) {
      this.type = type;
      return this;
    }

    public TrainingUserDTO build() {
      return new TrainingUserDTO(trainingUserId, name, member, type);
    }
  }
}
