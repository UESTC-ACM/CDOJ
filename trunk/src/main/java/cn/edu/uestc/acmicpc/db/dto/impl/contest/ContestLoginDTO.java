package cn.edu.uestc.acmicpc.db.dto.impl.contest;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * DTO send from contest login modal
 */
public class ContestLoginDTO {

  private Integer contestId;

  @NotNull(message = "Please enter your password.")
  @Length(min = 40, max = 40, message = "Please enter your password.")
  private String password;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ContestLoginDTO)) return false;

    ContestLoginDTO that = (ContestLoginDTO) o;

    if (contestId != null ? !contestId.equals(that.contestId) : that.contestId != null) {
      return false;
    }
    if (password != null ? !password.equals(that.password) : that.password != null) {
      return false;
    }

    return true;
  }

  public ContestLoginDTO() {
  }

  @Override
  public int hashCode() {
    int result = contestId != null ? contestId.hashCode() : 0;
    result = 31 * result + (password != null ? password.hashCode() : 0);
    return result;
  }

  public ContestLoginDTO(Integer contestId, String password) {

    this.contestId = contestId;
    this.password = password;
  }

  public Integer getContestId() {

    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    public ContestLoginDTO build() {
      return new ContestLoginDTO(contestId, password);
    }

    private Integer contestId;
    private String password;

    public Builder setContestId(Integer contestId) {
      this.contestId = contestId;
      return this;
    }

    public Builder setPassword(String password) {
      this.password = password;
      return this;
    }
  }

}
