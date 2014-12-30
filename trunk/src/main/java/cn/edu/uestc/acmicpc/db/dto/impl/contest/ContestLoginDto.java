package cn.edu.uestc.acmicpc.db.dto.impl.contest;

import org.hibernate.validator.constraints.Length;

import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * Dto send from contest login modal
 */
public class ContestLoginDto {

  private Integer contestId;

  @NotNull(message = "Please enter your password.")
  @Length(min = 40, max = 40, message = "Please enter your password.")
  private String password;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ContestLoginDto)) {
      return false;
    }

    ContestLoginDto that = (ContestLoginDto) o;
    return Objects.equals(this.contestId, that.contestId)
        && Objects.equals(this.password, that.password);
  }

  public ContestLoginDto() {
  }

  @Override
  public int hashCode() {
    int result = contestId != null ? contestId.hashCode() : 0;
    result = 31 * result + (password != null ? password.hashCode() : 0);
    return result;
  }

  public ContestLoginDto(Integer contestId, String password) {

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

    public ContestLoginDto build() {
      return new ContestLoginDto(contestId, password);
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
