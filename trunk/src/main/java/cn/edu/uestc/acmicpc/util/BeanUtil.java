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

package cn.edu.uestc.acmicpc.util;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * Manage methods for Spring Beans.
 * 
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
public class BeanUtil {

  /**
   * Get specific bean by bean name and servletContext.
   * 
   * @param beanName bean's name
   * @param servletContext servlet application
   * @return specific bean
   */
  public static Object getBeanByServletContext(String beanName, ServletContext servletContext) {
    WebApplicationContext wc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
    return wc.getBean(beanName);
  }
}
