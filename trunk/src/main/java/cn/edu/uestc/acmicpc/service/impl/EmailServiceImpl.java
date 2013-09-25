package cn.edu.uestc.acmicpc.service.impl;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.service.iface.EmailService;
import cn.edu.uestc.acmicpc.util.Settings;

/**
 * Implementation for {@link EmailService}
 */
@Service
@Primary
public class EmailServiceImpl extends AbstractService implements EmailService {

  @Autowired
  private Settings settings;

  class AJavaAuthenticator extends Authenticator {

    private final String user;
    private final String pwd;

    public AJavaAuthenticator(String user, String pwd) {
      this.user = user;
      this.pwd = pwd;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(user, pwd);
    }
  }

  @Override
  public Boolean send(String emailAddress, String title, String content) throws AppException {
    Properties properties = new Properties();
    properties.setProperty("mail.transport.protocol", "smtp");
    // properties.setProperty("mail.smtp.starttls.enable", "true");
    properties.setProperty("mail.smtp.host", settings.EMAIL_SMTP_SERVER);
    properties.setProperty("mail.smtp.auth", "true");
    Authenticator auth = new AJavaAuthenticator(settings.EMAIL_USERNAME, settings.EMAIL_PASSWORD);
    Session session = Session.getDefaultInstance(properties, auth);
    try {
      Message message = new MimeMessage(session);
      message.setFrom(new InternetAddress(settings.EMAIL_ADDRESS));
      message.setRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
      message.setSubject(title);
      message.setText(content);
      Transport.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  @Override
  public Boolean sendUserSerialKey(UserSerialKey userSerialKey) throws AppException {
    String url = settings.SETTING_HOST
            + "/user/activate/" + userSerialKey.getUserByUserId().getUserName()
            + "/" + StringUtil.encodeSHA1(userSerialKey.getSerialKey());
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Dear ").append(userSerialKey.getUserByUserId().getUserName()).append(" :\n\n");
    stringBuilder
        .append("To reset your password, simply click on the link below or paste into the url field on your favorite browser:\n\n");
    stringBuilder.append(url).append("\n\n");
    stringBuilder
        .append("The activation link will only be good for 30 minutes, after that you will have to try again from the beginning. When you visit the above page, you'll be able to set your password as you like.\n\n");
    stringBuilder
        .append("If you have any questions about the system, feel free to contact us anytime at acm@uestc.edu.cn.\n\n");
    stringBuilder.append("The UESTC OJ Team.\n");

    return send(userSerialKey.getUserByUserId().getEmail(), "UESTC Online Judge", stringBuilder.toString());
  }
}
