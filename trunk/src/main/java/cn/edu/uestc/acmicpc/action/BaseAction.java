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

package cn.edu.uestc.acmicpc.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import java.util.Map;

/**
 * Base action support, add specified common elements in here.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
public class BaseAction extends ActionSupport implements RequestAware, SessionAware, ApplicationAware {
    private static final long serialVersionUID = -3221772654123596229L;

    /**
     * Request attribute map.
     */
    protected Map<String, Object> request;

    /**
     * Session attribute map.
     */
    protected Map<String, Object> session;

    /**
     * Application attribute map.
     */
    protected Map<String, Object> application;

    /**
     * redirect flag.
     */
    protected final String REDIRECT = "redirect";

    /**
     * Implement {@link ApplicationAware} interface, with Ioc.
     *
     * @param application application attribute
     */
    @Override
    public void setApplication(Map<String, Object> application) {
        this.application = application;
    }

    /**
     * Implement {@link RequestAware} interface, with Ioc.
     *
     * @param request request attribute
     */
    @Override
    public void setRequest(Map<String, Object> request) {
        this.request = request;
    }

    /**
     * Implement {@link SessionAware} interface, with Ioc.
     *
     * @param session session attribute
     */
    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }

}
