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

package cn.edu.uestc.acmicpc.oj.test.util;

import cn.edu.uestc.acmicpc.ioc.util.EMailSenderAware;
import cn.edu.uestc.acmicpc.util.EMailSender;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-test.xml"})
public class EMailTest implements EMailSenderAware {
    @Autowired
    private EMailSender sender;

    @Test
    public void testEmailSending() {
        sender.send("lyhypacm@gmail.com", "title", "content");
    }

    @Test
    public void testMultiRecipientsSending() {
        sender.send("muziriyun@qq.com;muziriyun@gmail.com", "title", "content");
    }

    @Override
    @Ignore
    public void setEMailSender(EMailSender sender) {
        try {
            this.sender = sender;
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
