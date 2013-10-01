package cn.edu.uestc.acmicpc.db.dto.impl.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Description
 * TODO(mzry1992)
 */
public class UserActivateDTO {

  @NotNull(message = "Please enter your user name.")
  @Pattern(regexp = "\\b^[a-zA-Z0-9_]{4,24}$\\b",
      message = "Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")
  private String userName;

  private String serialKey;

  /**
   * Input: password
   */
  @Length(min = 6, max = 20, message = "Please enter 6-20 characters.")
  private String password;

  /**
   * Input: repeat password
   */
  @Length(min = 6, max = 20, message = "Please enter 6-20 characters.")
  private String passwordRepeat;

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
}
