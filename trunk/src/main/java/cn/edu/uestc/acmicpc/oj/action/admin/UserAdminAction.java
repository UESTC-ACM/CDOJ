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
import cn.edu.uestc.acmicpc.oj.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.oj.db.dao.impl.DepartmentDAO;
import cn.edu.uestc.acmicpc.oj.db.dto.UserDTO;
import cn.edu.uestc.acmicpc.oj.db.entity.User;
import cn.edu.uestc.acmicpc.oj.db.view.UserView;
import cn.edu.uestc.acmicpc.oj.ioc.DepartmentDAOAware;
import cn.edu.uestc.acmicpc.oj.ioc.UserDAOAware;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.util.ArrayUtil;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import com.opensymphony.xwork2.validator.annotations.*;
import org.apache.struts2.interceptor.validation.SkipValidation;

import java.util.ArrayList;
import java.util.List;

/**
 * action for edit user information.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 5
 */
@SuppressWarnings("UnusedDeclaration")
@LoginPermit(value = Global.AuthenticationType.ADMIN)
public class UserAdminAction extends BaseAction implements DepartmentDAOAware {

    /**
     * return the user.jsp for base view
     *
     * @return SUCCESS
     */
    @SkipValidation
    public String toUserList() {
        return SUCCESS;
    }

    /**
     * department dao, use for get a department entity by id.
     */
    private IDepartmentDAO departmentDAO;

    /**
     * Conditions for user search.
     */
    public UserCondition userCondition;

    /**
     * Setter of userCondition for Ioc.
     *
     * @param userCondition newly userCondition
     */
    @SuppressWarnings("UnusedDeclaration")
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
    @SkipValidation
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

    private UserDTO userDTO;

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
    @Validations(
            requiredStrings = {
                    @RequiredStringValidator(
                            fieldName = "userDTO.school",
                            key = "error.school.validation"
                    ),
                    @RequiredStringValidator(
                            fieldName = "userDTO.studentId",
                            key = "error.studentId.validation"
                    )
            },
            stringLengthFields = {
                    @StringLengthFieldValidator(
                            fieldName = "userDTO.school",
                            key = "error.school.validation",
                            minLength = "1",
                            maxLength = "50",
                            trim = false
                    ),
                    @StringLengthFieldValidator(
                            fieldName = "userDTO.studentId",
                            key = "error.studentId.validation",
                            minLength = "1",
                            maxLength = "20",
                            trim = false
                    )
            },
            fieldExpressions = {
                    @FieldExpressionValidator(
                            fieldName = "userDTO.departmentId",
                            expression = "userDTO.departmentId in global.departmentList.{departmentId}",
                            key = "error.department.validation"
                    ),
                    @FieldExpressionValidator(
                            fieldName = "userDTO.type",
                            expression = "userDTO.type in global.authenticationTypeList.{ordinal()}",
                            key = "error.type.validation"
                    )
            }
    )

    /**
     * To edit user entity.
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
    public String toEdit() {
        try {
            User user = userDAO.get(userDTO.getUserId());
            if (user == null)
                throw new AppException("No such user!");
            userDTO.setDepartment(departmentDAO.get(userDTO.getDepartmentId()));
            userDTO.updateUser(user);
            userDAO.update(user);
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
            Integer[] ids = ArrayUtil.parseIntArray(get("id"));
            String method = get("method");
            for (Integer id : ids)
                if (id != null) {
                    ++total;
                    try {
                        if ("delete".equals(method)) {
                            userDAO.delete(userDAO.get(id));
                        }
                        ++count;
                    } catch (AppException ignored) {
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

    public UserDTO getUserDTO() {
        return userDTO;
    }

    /**
     * User DTO setter for IoC.
     *
     * @param userDTO user data transform object
     */
    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public IDepartmentDAO getDepartmentDAO() {
        return departmentDAO;
    }

    public void setDepartmentDAO(IDepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }
}
