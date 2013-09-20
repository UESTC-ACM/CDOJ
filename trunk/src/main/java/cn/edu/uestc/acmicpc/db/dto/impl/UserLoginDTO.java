package cn.edu.uestc.acmicpc.db.dto.impl;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class UserLoginDTO {

  /**
   * Input: user name
   */
  @NotNull(message = "Please enter your user name.")
  @Pattern(regexp = "\\b^[a-zA-Z0-9_]{4,24}$\\b",
      message = "Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")
  private String userName;

  /**
   * Input: password
   */
  @NotNull(message = "Please enter your password.")
  @Length(min = 6, max = 20, message = "Please enter 6-20 characters.")
  private String password;

  public UserLoginDTO() {
  }

  private UserLoginDTO(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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

  /** Builder for {@link UserLoginDTO}. */
  public static class Builder {

    private Builder() {
    }

    private String userName = "";
    private String password = "";

    public Builder setUserName(String userName) {
      this.userName = userName;
      return this;
    }

    public Builder setPassword(String password) {
      this.password = password;
      return this;
    }

    public UserLoginDTO build() {
      return new UserLoginDTO(userName, password);
    }
  }
}
