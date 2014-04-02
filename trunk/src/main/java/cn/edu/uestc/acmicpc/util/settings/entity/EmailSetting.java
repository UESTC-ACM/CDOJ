package cn.edu.uestc.acmicpc.util.settings.entity;

/**
 * Email setting entity
 */
public class EmailSetting {
  private String address;
  private String userName;
  private String password;
  private String smtpServer;

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
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

  public String getSmtpServer() {
    return smtpServer;
  }

  public void setSmtpServer(String smtpServer) {
    this.smtpServer = smtpServer;
  }
}
