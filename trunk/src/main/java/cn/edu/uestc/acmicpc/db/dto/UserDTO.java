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

package cn.edu.uestc.acmicpc.db.dto;

import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.StringUtil;

import java.sql.Timestamp;
import java.util.Date;

/**
 * collect information from register action and generate a User class.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */
public class UserDTO {

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
    private int departmentId;

    /**
     * Department entity
     */
    private Department department;

    /**
     * Input: student ID
     */
    private String studentId;

    /**
     * Output: A user entity
     */
    private User user;

    /**
     * Val
     *
     * @return
     */
    public User getUser() {
        user = new User();
        user.setUserName(getUserName());
        user.setPassword(StringUtil.encodeSHA1(getPassword()));
        user.setNickName(getNickName());
        user.setEmail(getEmail());
        user.setSchool(getSchool());
        user.setDepartmentByDepartmentId(getDepartment());
        user.setStudentId(getStudentId());
        user.setLastLogin(new Timestamp(new Date().getTime()));
        user.setSolved(0);
        user.setTried(0);
        user.setType(0);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
