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
import cn.edu.uestc.acmicpc.db.dto.UserDTO;
import com.opensymphony.xwork2.validator.annotations.*;

/**
 * Action for register
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

public class RegisterAction extends BaseAction {

    private static final long serialVersionUID = -2854303130010851540L;

    /**
     * user dto...
     */
    private UserDTO userDTO;

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
                            fieldName = "userDTO.department",
                            key = "error.department.validation"
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
        return SUCCESS;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }
}
