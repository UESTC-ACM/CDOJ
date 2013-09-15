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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.view.impl.UserView;
import cn.edu.uestc.acmicpc.ioc.dto.UserDTOAware;
import cn.edu.uestc.acmicpc.ioc.service.ProblemServiceAware;
import cn.edu.uestc.acmicpc.ioc.service.StatusServiceAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.oj.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.oj.service.iface.StatusService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
import com.opensymphony.xwork2.validator.annotations.ValidationParameter;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action for user center
 */
@Controller
@LoginPermit(NeedLogin = false)
public class UserCenterAction extends BaseAction implements StatusServiceAware, UserDTOAware,
    ProblemServiceAware {

  /**
	 *
	 */
  private static final long serialVersionUID = 3041678335489314037L;

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
  @SkipValidation
  public String toUserCenter() {
    try {
      if (targetUserName == null)
        throw new AppException("User name is empty!");
      User user = userService.getUserByUserName(targetUserName);
      if (user == null) {
        throw new AppException("No such user!");
      }
      targetUser = new UserView(user);
    } catch (AppException e) {
      return redirect(getActionURL("/", "index"), e.getMessage());
    } catch (Exception e) {
      return redirect(getActionURL("/", "index"), "Unknown exception occurred.");
    }
    return SUCCESS;
  }

  /**
   * Get user's problem status list.
   * <p/>
   * Find all problem that target user passed or failed.
   * <p/>
   * <strong>JSON output</strong>:
   * <ul>
   * <li>
   * For success: {"result":"ok", "problemStatus":<strong>ProblemStatus object</strong>,
   * "problemCount":<strong>Tot problems</strong>}</li>
   * <li>
   * For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   * </ul>
   *
   * @return <strong>JSON</strong> signal
   */
  @SkipValidation
  public String toUserProblemState() {
    try {
      if (targetUserName == null) {
        throw new AppException("User name is empty!");
      }
      User currentUser = userService.getUserByUserName(targetUserName);
      if (currentUser == null)
        throw new AppException("No such user!");

      Map<Integer, Global.AuthorStatusType> problemStatus = new HashMap<>();

      List<Integer> results = problemService.getAllVisibleProblemIds();
      for (Integer result : results)
        problemStatus.put(result, Global.AuthorStatusType.NONE);

      results = statusService.findAllUserTriedProblemIds(currentUser.getUserId());
      for (Integer result : results)
        if (problemStatus.containsKey(result))
          problemStatus.put(result, Global.AuthorStatusType.FAIL);

      results = statusService.findAllUserAcceptedProblemIds(currentUser.getUserId());
      for (Integer result : results)
        if (problemStatus.containsKey(result))
          problemStatus.put(result, Global.AuthorStatusType.PASS);

      json.put("result", "ok");
      json.put("problemStatus", problemStatus);
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      json.put("result", "error");
      e.printStackTrace();
      json.put("error_msg", "Unknown exception occurred.");
    }
    return JSON;

  }

  /**
   * To edit user entity.
   * <p/>
   * <strong>JSON output</strong>:
   * <ul>
   * <li>
   * For success: {"result":"ok"}</li>
   * <li>
   * For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   * </ul>
   *
   * @return <strong>JSON</strong> signal
   */
  @Validations(
      requiredStrings = {
          @RequiredStringValidator(fieldName = "userDTO.school", key = "error.school.validation"),
          @RequiredStringValidator(fieldName = "userDTO.studentId",
              key = "error.studentId.validation"),
          @RequiredStringValidator(fieldName = "userDTO.nickName",
              key = "error.nickName.validation"),
          @RequiredStringValidator(fieldName = "userDTO.oldPassword",
              key = "error.password.validation") },
      stringLengthFields = {
          @StringLengthFieldValidator(fieldName = "userDTO.school",
              key = "error.school.validation", minLength = "1", maxLength = "100", trim = false),
          @StringLengthFieldValidator(fieldName = "userDTO.studentId",
              key = "error.studentId.validation", minLength = "1", maxLength = "20", trim = false),
          @StringLengthFieldValidator(fieldName = "userDTO.oldPassword",
              key = "error.password.validation", minLength = "6", maxLength = "20", trim = false) },
      customValidators = { @CustomValidator(type = "regex", fieldName = "userDTO.nickName",
          key = "error.nickName.validation", parameters = {
              @ValidationParameter(name = "expression", value = "\\b^[^\\s]{2,20}$\\b"),
              @ValidationParameter(name = "trim", value = "false") }) },
      fieldExpressions = { @FieldExpressionValidator(fieldName = "userDTO.departmentId",
          expression = "userDTO.departmentId in global.departmentList.{departmentId}",
          key = "error.department.validation") })
  @LoginPermit(NeedLogin = true)
  public
      String toEdit() {
    try {
      if (userDTO.getUserId() == null || !userDTO.getUserId().equals(currentUser.getUserId()))
        throw new AppException("You can only change your profile!");
      User user = userService.getUserByUserId(userDTO.getUserId());
      if (user == null)
        throw new AppException("No such user!");
      if (!StringUtil.encodeSHA1(userDTO.getOldPassword()).equals(user.getPassword())) {
        addFieldError("userDTO.oldPassword", "Your passowrd is wrong, please try again.");
        return INPUT;
      }
      if (userDTO.getPassword() != null) {
        if (userDTO.getPassword().length() < 6 || userDTO.getPassword().length() > 20) {
          addFieldError("userDTO.password", "Please enter 6-20 characters.");
          return INPUT;
        }
        if (!userDTO.getPassword().equals(userDTO.getPasswordRepeat())) {
          addFieldError("userDTO.passwordRepeat", "Password do not match.");
          return INPUT;
        }
      }
      userDTO.setType(null);
      userDTO.setDepartmentId(userDTO.getDepartmentId());
      userDTO.updateEntity(user);
      userService.updateUser(user);
      json.put("result", "ok");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return JSON;
  }

  @Autowired
  private UserDTO userDTO;

  @Override
  public void setUserDTO(UserDTO userDTO) {
    this.userDTO = userDTO;
  }

  @Override
  public UserDTO getUserDTO() {
    return userDTO;
  }

  private ProblemService problemService;

  @Override
  public void setProblemUProblemService(ProblemService problemService) {
    this.problemService = problemService;
  }

  private StatusService statusService;

  @Override
  public void setStatusService(StatusService statusService) {
    this.statusService = statusService;
  }
}
