package cn.edu.uestc.acmicpc.service.iface;

/**
 * Email service
 */
public interface EmailService {

  /**
   * Send email
   * @param emailAddress recipient address
   * @param title email title
   * @param content email content
   * @return true if this operation success
   */
  public Boolean send(String emailAddress, String title, String content);
}
