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

import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.ioc.dao.DepartmentDAOAware;
import com.opensymphony.xwork2.validator.annotations.*;

/**
 * Action for register
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(NeedLogin = false)
public class RegisterAction extends BaseAction implements DepartmentDAOAware {

    private static final long serialVersionUID = -2854303130010851540L;

    /**
     * user dto...
     */
    private UserDTO userDTO;

    /**
     * department dao, use for get a department entity by id.
     */
    private IDepartmentDAO departmentDAO;

    /**
     * Register action.
     * <p/>
     * Check register information and pass then to validator, if the information is correct, write
     * the user's information into database.
     *
     * @return <strong>JSON</strong> signal, process in front
     */
    @Validations(
            requiredStrings = {
                    @RequiredStringValidator(
                            fieldName = "userDTO.userName",
                            key = "error.userName.validation"
                    ),
                    @RequiredStringValidator(
                            fieldName = "userDTO.password",
                            key = "error.password.validation"
                    ),
                    @RequiredStringValidator(
                            fieldName = "userDTO.nickName",
                            key = "error.nickName.validation"
                    ),
                    @RequiredStringValidator(
                            fieldName = "userDTO.email",
                            key = "error.email.validation"
                    ),
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
                            fieldName = "userDTO.password",
                            key = "error.password.validation",
                            minLength = "6",
                            maxLength = "20",
                            trim = false
                    ),
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
            customValidators = {
                    @CustomValidator(
                            type = "regex",
                            fieldName = "userDTO.userName",
                            key = "error.userName.validation",
                            parameters = {
                                    @ValidationParameter(
                                            name = "expression",
                                            value = "\\b^[a-zA-Z0-9_]{4,24}$\\b"
                                    ),
                                    @ValidationParameter(
                                            name = "trim",
                                            value = "false"
                                    )
                            }
                    ),
                    @CustomValidator(
                            type = "regex",
                            fieldName = "userDTO.nickName",
                            key = "error.nickName.validation",
                            parameters = {
                                    @ValidationParameter(
                                            name = "expression",
                                            value = "\\b^[^\\s]{2,20}$\\b"
                                    ),
                                    @ValidationParameter(
                                            name = "trim",
                                            value = "false"
                                    )
                            }
                    )
            },
            fieldExpressions = {
                    @FieldExpressionValidator(
                            fieldName = "userDTO.passwordRepeat",
                            expression = "userDTO.password == userDTO.passwordRepeat",
                            key = "error.passwordRepeat.validation"
                    ),
                    @FieldExpressionValidator(
                            fieldName = "userDTO.departmentId",
                            expression = "userDTO.departmentId in global.departmentList.{departmentId}",
                            key = "error.department.validation"
                    )
            },
            emails = {
                    @EmailValidator(
                            fieldName = "userDTO.email",
                            key = "error.email.validation"
                    )
            }
    )
    public String toRegister() {
        try {
            if (userDAO.getEntityByUniqueField("userName", userDTO.getUserName()) != null) {
                addFieldError("userDTO.userName", "User name has been used!");
                return INPUT;
            }
            if (userDAO.getEntityByUniqueField("email", userDTO.getEmail()) != null) {
                addFieldError("userDTO.email", "Email has benn used!");
                return INPUT;
            }
            userDTO.setDepartment(departmentDAO.get(userDTO.getDepartmentId()));
            User user = userDTO.getEntity();
            userDAO.add(user);
            session.put("userName", user.getUserName());
            session.put("password", user.getPassword());
            session.put("lastLogin", user.getLastLogin());
            session.put("userType", user.getType());
            json.put("result", "ok");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", "error");
            json.put("error_msg", "Unknown exception occurred.");
        }
        return JSON;
    }

    @SuppressWarnings("UnusedDeclaration")
    public UserDTO getUserDTO() {
        return userDTO;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    @SuppressWarnings("UnusedDeclaration")
    public IDepartmentDAO getDepartmentDAO() {
        return departmentDAO;
    }

    public void setDepartmentDAO(IDepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

}
