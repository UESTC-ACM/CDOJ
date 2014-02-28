package cn.edu.uestc.acmicpc.db.dto.impl.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * DTO post from user login form.
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
  @Length(min = 40, max = 40, message = "Please enter your password.")
  private String password;

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

  public UserLoginDTO() {
  }

  public UserLoginDTO(String userName, String password) {
    this.userName = userName;
    this.password = password;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }

    public UserLoginDTO build() {
      return new UserLoginDTO(userName, password);
    }

    private String userName = "admin";
    private String password = "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8";

    public String getUserName() {
      return userName;
    }

    public Builder setUserName(String userName) {
      this.userName = userName;
      return this;
    }

    public String getPassword() {
      return password;
    }

    public Builder setPassword(String password) {
      this.password = password;
      return this;
    }
  }
}
