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
import cn.edu.uestc.acmicpc.oj.util.Global;
import cn.edu.uestc.acmicpc.oj.util.exception.AppException;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * action for edit user information.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 3
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
        }
        return JSON;
    }

    /**
     * User database transform object entity.
     */
    private UserDTO userDTO;

    public String toEdit() {
        User user = userDTO.getUser();
        try {
            userDAO.update(user);
            json.put("result", "error");
        } catch (AppException e) {
            json.put("result", "error");
            json.put("error_msg", e.getMessage());
        }
        return JSON;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
