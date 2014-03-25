package cn.edu.uestc.acmicpc.db.dto.impl.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * DTO post from user activate form.
 */
public class UserActivateDTO {

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
  @Length(min = 40, max = 40, message = "Please enter your password.")
  private String password;

  /**
   * Input: repeat password
   */
  @Length(min = 40, max = 40, message = "Please repeat your password.")
  private String passwordRepeat;

  private String serialKey;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getSerialKey() {
    return serialKey;
  }

  public void setSerialKey(String serialKey) {
    this.serialKey = serialKey;
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

  public UserActivateDTO() {
  }

  private UserActivateDTO(String userName, String serialKey, String password, String passwordRepeat) {
    this.userName = userName;
    this.serialKey = serialKey;
    this.password = password;
    this.passwordRepeat = passwordRepeat;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserActivateDTO that = (UserActivateDTO) o;

    if (password != null ? !password.equals(that.password) : that.password != null) {
      return false;
    }
    if (passwordRepeat != null ? !passwordRepeat.equals(that.passwordRepeat) : that.passwordRepeat != null) {
      return false;
    }
    if (serialKey != null ? !serialKey.equals(that.serialKey) : that.serialKey != null) {
      return false;
    }
    if (userName != null ? !userName.equals(that.userName) : that.userName != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = userName != null ? userName.hashCode() : 0;
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (passwordRepeat != null ? passwordRepeat.hashCode() : 0);
    result = 31 * result + (serialKey != null ? serialKey.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }

    public UserActivateDTO build() {
      return new UserActivateDTO(userName, serialKey, password, passwordRepeat);
    }

    private String userName;
    private String serialKey;
    private String password;
    private String passwordRepeat;

    public String getUserName() {
      return userName;
    }

    public Builder setUserName(String userName) {
      this.userName = userName;
      return this;
    }

    public String getSerialKey() {
      return serialKey;
    }

    public Builder setSerialKey(String serialKey) {
      this.serialKey = serialKey;
      return this;
    }

    public String getPassword() {
      return password;
    }

    public Builder setPassword(String password) {
      this.password = password;
      return this;
    }

    public String getPasswordRepeat() {
      return passwordRepeat;
    }

    public Builder setPasswordRepeat(String passwordRepeat) {
      this.passwordRepeat = passwordRepeat;
      return this;
    }
  }

}
