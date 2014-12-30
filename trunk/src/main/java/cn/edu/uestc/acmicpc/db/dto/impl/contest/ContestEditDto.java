package cn.edu.uestc.acmicpc.db.dto.impl.contest;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Dto post from contest editor.
 */
public class ContestEditDto {

  public ContestEditDto() {
  }

  public ContestEditDto(String action, Integer contestId, String description,
      Integer lengthDays, Integer lengthHours,
      Integer lengthMinutes, String problemList,
      Timestamp time, String title, Byte type,
      String password, String passwordRepeat,
      Integer parentId, Boolean needFrozen,
      Integer frozenLengthMinutes,
      Integer frozenLengthHours,
      Integer frozenLengthDays) {
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
    this.password = password;
    this.passwordRepeat = passwordRepeat;
    this.parentId = parentId;
    this.needFrozen = needFrozen;
    this.frozenLengthMinutes = frozenLengthMinutes;
    this.frozenLengthHours = frozenLengthHours;
    this.frozenLengthDays = frozenLengthDays;
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
  private String password;
  private String passwordRepeat;
  private Integer parentId;
  private List<UserDto> userList;
  private Boolean needFrozen;
  private Integer frozenLengthDays;
  private Integer frozenLengthHours;
  private Integer frozenLengthMinutes;

  public List<UserDto> getUserList() {
    return userList;
  }

  public void setUserList(List<UserDto> userList) {
    this.userList = userList;
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

  public String getPasswordRepeat() {
    return passwordRepeat;
  }

  public void setPasswordRepeat(String passwordRepeat) {
    this.passwordRepeat = passwordRepeat;
  }

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

  public Boolean getNeedFrozen() {
    return needFrozen;
  }

  public void setNeedFrozen(Boolean needFrozen) {
    this.needFrozen = needFrozen;
  }

  public Integer getFrozenLengthDays() {
    return frozenLengthDays;
  }

  public void setFrozenLengthDays(Integer frozenLengthDays) {
    this.frozenLengthDays = frozenLengthDays;
  }

  public Integer getFrozenLengthHours() {
    return frozenLengthHours;
  }

  public void setFrozenLengthHours(Integer frozenLengthHours) {
    this.frozenLengthHours = frozenLengthHours;
  }

  public Integer getFrozenLengthMinutes() {
    return frozenLengthMinutes;
  }

  public void setFrozenLengthMinutes(Integer frozenLengthMinutes) {
    this.frozenLengthMinutes = frozenLengthMinutes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ContestEditDto)) {
      return false;
    }

    ContestEditDto that = (ContestEditDto) o;
    return Objects.equals(this.action, that.action)
        && Objects.equals(this.contestId, that.contestId)
        && Objects.equals(this.description, that.description)
        && Objects.equals(this.frozenLengthDays, that.frozenLengthDays)
        && Objects.equals(this.frozenLengthHours, that.frozenLengthHours)
        && Objects.equals(this.frozenLengthMinutes, that.frozenLengthMinutes)
        && Objects.equals(this.lengthDays, that.lengthDays)
        && Objects.equals(this.lengthHours, that.lengthHours)
        && Objects.equals(this.lengthMinutes, that.lengthMinutes)
        && Objects.equals(this.needFrozen, that.needFrozen)
        && Objects.equals(this.parentId, that.parentId)
        && Objects.equals(this.password, that.password)
        && Objects.equals(this.passwordRepeat, that.passwordRepeat)
        && Objects.equals(this.problemList, that.problemList)
        && Objects.equals(this.time, that.time)
        && Objects.equals(this.title, that.title)
        && Objects.equals(this.type, that.type)
        && Objects.equals(this.userList, that.userList);
  }

  @Override
  public int hashCode() {
    int result = action != null ? action.hashCode() : 0;
    result = 31 * result + (contestId != null ? contestId.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (lengthDays != null ? lengthDays.hashCode() : 0);
    result = 31 * result + (lengthHours != null ? lengthHours.hashCode() : 0);
    result = 31 * result + (lengthMinutes != null ? lengthMinutes.hashCode() : 0);
    result = 31 * result + (problemList != null ? problemList.hashCode() : 0);
    result = 31 * result + (time != null ? time.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (passwordRepeat != null ? passwordRepeat.hashCode() : 0);
    result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
    result = 31 * result + (userList != null ? userList.hashCode() : 0);
    result = 31 * result + (needFrozen != null ? needFrozen.hashCode() : 0);
    result = 31 * result + (frozenLengthDays != null ? frozenLengthDays.hashCode() : 0);
    result = 31 * result + (frozenLengthHours != null ? frozenLengthHours.hashCode() : 0);
    result = 31 * result + (frozenLengthMinutes != null ? frozenLengthMinutes.hashCode() : 0);
    return result;
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
    private String password;
    private String passwordRepeat;
    private Integer parentId;
    private Boolean needFrozen;
    private Integer frozenLengthDays;
    private Integer frozenLengthHours;
    private Integer frozenLengthMinutes;

    public ContestEditDto build() {
      return new ContestEditDto(action, contestId, description, lengthDays, lengthHours,
          lengthMinutes,
          problemList, time, title, type, password, passwordRepeat, parentId, needFrozen,
          frozenLengthMinutes, frozenLengthHours, frozenLengthDays);
    }

    public Builder setParentId(Integer parentId) {
      this.parentId = parentId;
      return this;
    }

    public Builder setPassword(String password) {
      this.password = password;
      return this;
    }

    public Builder setPasswordRepeat(String passwordRepeat) {
      this.passwordRepeat = passwordRepeat;
      return this;
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

    public Builder setNeedFrozen(Boolean needFrozen) {
      this.needFrozen = needFrozen;
      return this;
    }

    public Builder setFrozenLengthDays(Integer frozenLengthDays) {
      this.frozenLengthDays = frozenLengthDays;
      return this;
    }

    public Builder setFrozenLengthHours(Integer frozenLengthHours) {
      this.frozenLengthHours = frozenLengthHours;
      return this;
    }

    public Builder setFrozenLengthMinutes(Integer frozenLengthMinutes) {
      this.frozenLengthMinutes = frozenLengthMinutes;
      return this;
    }
  }
}
