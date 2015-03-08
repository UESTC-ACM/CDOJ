package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.UserSerialKeyDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Email service interface.
 */
public interface EmailService {

  /**
   * Send email.
   *
   * @param emailAddress recipient address.
   * @param title        email title.
   * @param content      email content.
   * @return true if this operation success.
   * @throws AppException
   */
  public Boolean send(String emailAddress, String title, String content) throws AppException;

  /**
   * Send user serial key by email.
   *
   * @param userSerialKeyDto Entity.
   * @return true if this operation success.
   * @throws AppException
   */
  public Boolean sendUserSerialKey(UserSerialKeyDto userSerialKeyDto) throws AppException;
}
