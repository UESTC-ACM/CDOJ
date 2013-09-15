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

package cn.edu.uestc.acmicpc.oj.action.user;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.ioc.condition.StatusConditionAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidationParameter;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Login action for user toLogin.
 */
@Controller
@LoginPermit(NeedLogin = false)
public class LoginAction extends BaseAction implements StatusConditionAware {

  private static final long serialVersionUID = 2034049134718450987L;

  private String userName;
  private String password;
  @Autowired
  private StatusCondition statusCondition;

  /**
   * toLogin with {@code userName} and {@code password}. <strong>JSON output</strong>:
   * <ul>
   * <li>
   * For success: {"result":"ok"}</li>
   * <li>
   * For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   * </ul>
   *
   * @return action signal
   */
  @Validations(requiredStrings = {
      @RequiredStringValidator(fieldName = "userName", key = "error.userName.validation"),
      @RequiredStringValidator(fieldName = "password", key = "error.password.validation") },
      stringLengthFields = { @StringLengthFieldValidator(fieldName = "password",
          key = "error.password.validation", minLength = "6", maxLength = "20", trim = false) },
      customValidators = { @CustomValidator(type = "regex", fieldName = "userName",
          key = "error.userName.validation", parameters = {
              @ValidationParameter(name = "expression", value = "\\b^[a-zA-Z0-9_]{4,24}$\\b"),
              @ValidationParameter(name = "trim", value = "false") }) })
  public String toLogin() {
    try {
      User user = userService.getUserByUserName(getUserName());
      if (user == null || !StringUtil.encodeSHA1(getPassword()).equals(user.getPassword())) {
        addFieldError("password", "User or password is wrong, please try again.");
        return INPUT;
      }

      user.setLastLogin(new Timestamp(new Date().getTime() / 1000 * 1000));
      userService.updateUser(user);

      session.put("userName", user.getUserName());
      session.put("password", user.getPassword());
      session.put("lastLogin", user.getLastLogin());
      session.put("userType", user.getType());
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", setError(e));
      return JSON;
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", setError("Unknown exception occurred."));
      return JSON;
    }
    json.put("result", "ok");
    return JSON;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public void setStatusCondition(StatusCondition statusCondition) {
    this.statusCondition = statusCondition;
  }

  @Override
  public StatusCondition getStatusCondition() {
    return statusCondition;
  }
}
