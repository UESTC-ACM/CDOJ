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

package cn.edu.uestc.acmicpc.oj.action.admin;

import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.oj.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.oj.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.oj.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.oj.db.dto.UserDTO;
import cn.edu.uestc.acmicpc.oj.db.entity.User;
import cn.edu.uestc.acmicpc.oj.db.view.UserView;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.util.ArrayUtil;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.util.ArrayList;
import java.util.List;

/**
 * action for edit user information.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 4
 */
@LoginPermit(value = Global.AuthenticationType.ADMIN)
public class UserAdminAction extends BaseAction {

    public String toUserList() {
        return SUCCESS;
    }

    /**
     * Conditions for user search.
     */
    public UserCondition userCondition;

    /**
     * Setter of userCondition for Ioc.
     *
     * @param userCondition newly userCondition
     */
    @Deprecated
    public void setUserCondition(UserCondition userCondition) {
        this.userCondition = userCondition;
    }

    /**
     * Search action.
     * <p/>
     * Find all records by conditions and return them as a list in JSON, and the condition
     * set will set in JSON named "condition".
     * <p/>
     * <strong>JSON output</strong>:
     * <ul>
     * <li>
     * For success: {"result":"ok", "pageInfo":<strong>PageInfo object</strong>,
     * "condition", <strong>UserCondition entity</strong>,
     * "userList":<strong>query result</strong>}
     * </li>
     * <li>
     * For error: {"result":"error", "error_msg":<strong>error message</strong>}
     * </li>
     * </ul>
     *
     * @return <strong>JSON</strong> signal
     */
    public String toSearch() {
        try {
            Condition condition = userCondition.getCondition();
            Long count = userDAO.count(userCondition.getCondition());
            PageInfo pageInfo = buildPageInfo(count, RECORD_PER_PAGE, "", null);
            condition.currentPage = pageInfo.getCurrentPage();
            condition.countPerPage = RECORD_PER_PAGE;
            List<User> userList = userDAO.findAll(condition);
            List<UserView> userViewList = new ArrayList<UserView>();
            for (User user : userList)
                userViewList.add(new UserView(user));
            json.put("pageInfo", pageInfo.getHtmlString());
            json.put("result", "ok");
            json.put("condition", userCondition);
            json.put("userList", userViewList);
        } catch (AppException e) {
            json.put("result", "error");
        } catch (Exception e) {
            json.put("result", "error");
            json.put("error_msg", "Unknown exception occurred.");
        }
        return JSON;
    }

    /**
     * User database transform object entity.
     * <p/>
     * <strong>JSON output</strong>:
     * <ul>
     * <li>
     * For success: {"result":"ok"}
     * </li>
     * <li>
     * For error: {"result":"error", "error_msg":<strong>error message</strong>}
     * </li>
     * </ul>
     */
    private UserDTO userDTO;

    public String toEdit() {
        User user = userDTO.getUser();
        try {
            userDAO.update(user);
            json.put("result", "ok");
        } catch (AppException e) {
            json.put("result", "error");
            json.put("error_msg", e.getMessage());
        } catch (Exception e) {
            json.put("result", "error");
            json.put("error_msg", "Unknown exception occurred.");
        }
        return JSON;
    }

    /**
     * Action to operate multiple users.
     * <p/>
     * <strong>JSON output</strong>:
     * <ul>
     * <li>
     * For success: {"result":"ok", "msg":<strong>successful message</strong>}
     * </li>
     * <li>
     * For error: {"result":"error", "error_msg":<strong>error message</strong>}
     * </li>
     * </ul>
     *
     * @return <strong>JSON</strong> signal.
     */
    public String toOperatorUser() {
        try {
            int count = 0, total = 0;
            System.out.println(get("id")+" "+get("method"));
            Integer[] ids = ArrayUtil.parseIntArray(get("id"));
            String method = get("method");
            for (int i = 0; i < ids.length; ++i)
                if (ids[i] != null) {
                    ++total;
                    try {
                        if ("delete".equals(method)) {
                            userDAO.delete(userDAO.get(ids[i]));
                        }
                        ++count;
                    } catch (AppException e) {
                    }
                }
            json.put("result", "ok");
            String message = "";
            if ("delete".equals(message))
                message = String.format("%d total, %d deleted.", total, count);
            json.put("msg", message);
        } catch (Exception e) {
            json.put("result", "error");
            json.put("error_msg", "Unknown exception occurred.");
        }
        return JSON;
    }

    /**
     * User DTO setter for IoC.
     *
     * @param userDTO user data transform object
     */
    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
