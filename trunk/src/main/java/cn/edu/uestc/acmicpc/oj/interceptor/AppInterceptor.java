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

package cn.edu.uestc.acmicpc.oj.interceptor;

import cn.edu.uestc.acmicpc.oj.interceptor.iface.IActionInterceptor;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * Application global interceptor, to invoke
 * {@link cn.edu.uestc.acmicpc.oj.interceptor.iface.IActionInterceptor} interface.
 * 
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @see cn.edu.uestc.acmicpc.oj.interceptor.iface.IActionInterceptor
 */
public class AppInterceptor extends AbstractInterceptor {

  /**
	 * 
	 */
  private static final long serialVersionUID = -4194530694931169483L;

  @Override
  public String intercept(ActionInvocation action) throws Exception {
    IActionInterceptor ia = (IActionInterceptor) action.getAction();
    ActionInfo ai = new ActionInfo(ia.getClass(), action.getInvocationContext());
    ia.onActionExecuting(ai);
    String result = ai.cancel ? ai.getActionResult() : action.invoke();
    ia.onActionExecuted();
    return result;
  }

  /**
   * Basic action information entity.
   * 
   * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
   */
  public class ActionInfo {

    /**
     * Controller class
     */
    private final Class<?> controller;
    /**
     * Application context.
     */
    private final ActionContext action;
    /**
     * The action process will be stopped or not.
     */
    private boolean cancel = false;
    /**
     * Action result.
     */
    private String result = Action.ERROR;

    public ActionInfo(Class<?> controller, ActionContext action) {
      this.controller = controller;
      this.action = action;
    }

    public Class<?> getController() {
      return controller;
    }

    public ActionContext getAction() {
      return action;
    }

    public boolean getCancel() {
      return cancel;
    }

    public void setCancel(boolean cancel) {
      this.cancel = cancel;
    }

    public String getActionResult() {
      return this.result;
    }

    public void setActionResult(String result) {
      this.result = result;
    }
  }
}
