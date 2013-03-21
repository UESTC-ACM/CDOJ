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

package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.annotation.Ignore;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.sql.Timestamp;
import java.util.Date;

/**
 * collect information from register action and generate a User class.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@SuppressWarnings("UnusedDeclaration")
public class UserDTO extends BaseDTO<User> {

    /**
     * Input: user id, set null for new user
     */
    private Integer userId;

    /**
     * Input: user name
     */
    private String userName;

    /**
     * Input: password
     */
    private String password;

    /**
     * Input: repeat password
     */
    private String passwordRepeat;

    /**
     * Input: nick name
     */
    private String nickName;

    /**
     * Input: email
     */
    private String email;

    /**
     * Input: school
     */
    private String school;

    /**
     * Input: departmentId
     */
    private Integer departmentId;

    /**
     * Department entity
     */
    private Department department;

    /**
     * Input: student ID
     */
    private String studentId;

    public UserDTO() {
        super();
    }

    public Integer getUserId() {
        return userId;

    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * Input: number of problems the user has solved
     */
    private Integer solved;

    @Ignore
    public Integer getSolved() {
        return solved;
    }

    public void setSolved(Integer solved) {
        this.solved = solved;
    }

    @Ignore
    public Integer getTried() {
        return tried;
    }

    public void setTried(Integer tried) {
        this.tried = tried;
    }

    @Ignore
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * Input: number the problems the user has tried
     */
    private Integer tried;

    /**
     * Input: User type
     */
    private Integer type;

    /**
     * Output: A user entity
     */
    private User user;

    @Override
    public User getEntity() throws AppException {
        user = super.getEntity();
        user.setPassword(StringUtil.encodeSHA1(getPassword()));
        user.setDepartmentByDepartmentId(department);
        user.setLastLogin(new Timestamp(new Date().getTime() / 1000 * 1000));
        user.setSolved(getSolved() == null ? 0 : getSolved());
        user.setTried(getTried() == null ? 0 : getTried());
        user.setType(getType() == null ? 0 : getType());
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Ignore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Ignore
    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @Ignore
    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @Ignore
    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    /**
     * User is very special, the user name and password etc can not be changed in this method.
     *
     * @param user user entity to be updated
     */
    @Override
    public void updateEntity(User user) {
        if (school != null)
            user.setSchool(school);
        if (department != null)
            user.setDepartmentByDepartmentId(department);
        if (studentId != null)
            user.setStudentId(studentId);
        if (type != null)
            user.setType(type);
    }

    @Override
    protected Class<User> getReferenceClass() {
        return User.class;
    }
}
