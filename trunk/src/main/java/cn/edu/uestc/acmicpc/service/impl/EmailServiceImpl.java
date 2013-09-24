package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.service.iface.EmailService;
import cn.edu.uestc.acmicpc.util.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Implementation for {@link EmailService}
 */
@Service
public class EmailServiceImpl extends AbstractService implements EmailService {

  @Autowired
  private Settings settings;

  class AJavaAuthenticator extends Authenticator {

    private String user;
    private String pwd;

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
  public Boolean send(String emailAddress, String title, String content) {
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
}
