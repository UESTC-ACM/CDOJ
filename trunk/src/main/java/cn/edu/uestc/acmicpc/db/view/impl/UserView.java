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

package cn.edu.uestc.acmicpc.db.view.impl;

import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.view.base.View;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.Ignore;

import java.sql.Timestamp;

/**
 * Use for return user information with json type.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class UserView extends View<User> {

    private Integer userId;
    private String userName;
    private String password;
    private String nickName;
    private String email;
    private String school;
    private Integer departmentId;
    private String studentId;
    private Integer tried;
    private Integer solved;
    private String typeName;
    private Integer type;
    private Timestamp lastLogin;

    public UserView(User user) {
        super(user);
        setDepartmentId(user.getDepartmentByDepartmentId().getDepartmentId());
        setTypeName(Global.AuthenticationType.values()[user.getType()].getDescription());
    }

    @SuppressWarnings("UnusedDeclaration")
    public Integer getUserId() {
        return userId;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getPassword() {
        return password;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setPassword(String password) {
        this.password = password;
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getNickName() {
        return nickName;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getEmail() {
        return email;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setEmail(String email) {
        this.email = email;
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getSchool() {
        return school;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setSchool(String school) {
        this.school = school;
    }

    @SuppressWarnings("UnusedDeclaration")
    public Integer getDepartmentId() {
        return departmentId;
    }

    @Ignore
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getStudentId() {
        return studentId;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @SuppressWarnings("UnusedDeclaration")
    public Integer getTried() {
        return tried;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setTried(Integer tried) {
        this.tried = tried;
    }

    @SuppressWarnings("UnusedDeclaration")
    public Integer getSolved() {
        return solved;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setSolved(Integer solved) {
        this.solved = solved;
    }

    public String getTypeName() {
        return typeName;
    }

    @Ignore
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @SuppressWarnings("UnusedDeclaration")
    public Timestamp getLastLogin() {
        return lastLogin;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }
}
