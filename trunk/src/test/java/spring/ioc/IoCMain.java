/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package spring.ioc;

import junit.framework.TestCase;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * Created with IntelliJ IDEA.
 * User: mzry1992
 * Date: 13-1-1
 * Time: 上午12:00
 * To change this template use File | Settings | File Templates.
 */
public class IoCMain extends TestCase {

    ClassPathResource resource;
    BeanFactory factory;

    protected void setUp() throws Exception {
        resource = new ClassPathResource("beans.xml");
        factory = new XmlBeanFactory(resource);
    }

    protected void tearDown() throws Exception {
    }

    public void testIoc() {
        IoCService service = (IoCService)factory.getBean("iocService");
        service.display();
    }
}
