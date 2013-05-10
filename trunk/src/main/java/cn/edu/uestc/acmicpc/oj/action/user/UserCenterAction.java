/*
 * cdoj, UESTC ACMICPC Online Judge
 *
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
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

package cn.edu.uestc.acmicpc.oj.action.user;

import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.view.impl.UserView;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Action for user center
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@SuppressWarnings("UnusedDeclaration")
@LoginPermit(NeedLogin = false)
public class UserCenterAction extends BaseAction {

    private String targetUserName;

    private UserView targetUser;

    public UserView getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(UserView targetUser) {
        this.targetUser = targetUser;
    }

    public String getTargetUserName() {
        return targetUserName;
    }

    public void setTargetUserName(String targetUserName) {
        this.targetUserName = targetUserName;
    }

    /**
     * To enter user center.
     *
     * @return {@code SUCCESS} flag
     */
    public String toUserCenter() {
        try {
            if (targetUserName == null)
                throw new AppException("User name is empty!");
            User user = userDAO.getEntityByUniqueField("userName", targetUserName);
            if (user == null)
                throw new AppException("No such user!");
            targetUser = new UserView(user);
        } catch (AppException e) {
            return redirect(getActionURL("/", "index"), e.getMessage());
        } catch (Exception e) {
            return redirect(getActionURL("/", "index"), "Unknown exception occurred.");
        }
        return SUCCESS;
    }

}
