/*
 * cdoj, UESTC ACMICPC Online Judge
 * Copyright (c) 2012  fish <@link lyhypacm@gmail.com>,
 * mzry1992 <@link muziriyun@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package cn.edu.uestc.acmicpc.util;

import cn.edu.uestc.acmicpc.ioc.util.SettingsAware;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
public class EMailSender implements SettingsAware {
    @Override
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

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

    private Settings settings;

    public boolean send(String emailAddress, String title, String content) {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.smtp.host", settings.EMAIL_STMP_SERVER);
        properties.setProperty("mail.smtp.auth", "true");
        Authenticator auth = new AJavaAuthenticator(settings.EMAIL_USERNAME, settings.EMAIL_PASSWORD);
        Session session = Session.getDefaultInstance(properties, auth);
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(settings.EMAIL_ADDRESS));
            message.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(emailAddress));
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
