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

package cn.edu.uestc.acmicpc.action.user;

import cn.edu.uestc.acmicpc.action.BaseAction;
import cn.edu.uestc.acmicpc.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.AppException;
import cn.edu.uestc.acmicpc.util.StringUtil;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Login action for user login.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 2
 */
@LoginPermit(NeedLogin = false)
public class LoginAction extends BaseAction {

    private static final long serialVersionUID = 2034049134718450987L;

    /**
     * login with {@code userName} and {@code password}.
     *
     * @return action signal
     */
    public String login() {
        try {
            User user = userDAO.getUserByName(get("userName"));
            if (user == null || !StringUtil.encodeSHA1(get("password")).equals(user.getPassword()))
                throw new AppException("User or password is wrong, please try again.");
            try {
                user.setLastLogin(new Timestamp(new Date().getTime()));
                userDAO.update(user);
            } catch (AppException e) {
                return setError(e);
            } catch (Exception e) {
                return setError("Unknown exception occurred.");
            }
            user = userDAO.get(user.getUserId());
            session.put("userName", user.getUserName());
            session.put("password", user.getPassword());
            session.put("lastLogin", user.getLastLogin());
            session.put("userType", user.getType());
        } catch (AppException e) {
            return setError(e);
        } catch (Exception e) {
            return setError("Unknown exception occurred.");
        }
        return redirectToRefer();
    }
}
