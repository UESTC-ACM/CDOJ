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
import cn.edu.uestc.acmicpc.oj.db.condition.UserCondition;
import cn.edu.uestc.acmicpc.oj.db.entity.User;
import cn.edu.uestc.acmicpc.oj.util.DatabaseUtil;
import cn.edu.uestc.acmicpc.oj.util.Global;
import cn.edu.uestc.acmicpc.oj.util.exception.AppException;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import org.hibernate.Criteria;

import java.util.List;

/**
 * action for edit user information.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 2
 */
@LoginPermit(value = Global.AuthenticationType.ADMIN)
public class UserAdminAction extends BaseAction {

    public String toUserList() {
        return SUCCESS;
    }

    /**
     * Conditions for user search.
     */
    private UserCondition userCondition;

    /**
     * Setter of userCondition for Ioc.
     *
     * @param userCondition newly userCondition
     */
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
            Criteria criteria = userDAO.createCriteria();
            DatabaseUtil.putCriterionIntoCriteria(criteria, userCondition.getCriterionList());
            Long count = userDAO.count(criteria);
            criteria = userDAO.createCriteria();
            DatabaseUtil.putCriterionIntoCriteria(criteria, userCondition.getCriterionList());
            PageInfo pageInfo = buildPageInfo(count, RECORD_PER_PAGE, "", null);
            List<User> userList = userDAO.findAll(criteria, pageInfo.getCurrentPage(),
                    RECORD_PER_PAGE, null, null);
            request.put("pageInfo", pageInfo);
            json.put("result", "ok");
            json.put("condition", userCondition);
            json.put("userList", userList);
        } catch (AppException e) {
            json.put("result", "error");
        }
        return JSON;
    }

    public String toEdit() {
        return JSON;
    }

}
