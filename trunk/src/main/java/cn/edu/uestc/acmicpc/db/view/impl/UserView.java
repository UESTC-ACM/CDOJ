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
import cn.edu.uestc.acmicpc.util.annotation.Ignored;

import java.sql.Timestamp;

/**
 * Use for return user information with json type.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 5
 */
public class UserView extends View<User> {

    private int userId;
    private String userName;
    private String password;
    private String nickName;
    private String email;
    private String school;
    private int departmentId;
    private String studentId;
    private int tried;
    private int solved;
    private String type;
    private Timestamp lastLogin;

    public UserView(User user) {
        super(user);
        setDepartmentId(user.getDepartmentByDepartmentId().getDepartmentId());
        setType(Global.AuthenticationType.values()[user.getType()].getDescription());
    }

    @SuppressWarnings("UnusedDeclaration")
    public int getUserId() {
        return userId;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setUserId(int userId) {
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
    public int getDepartmentId() {
        return departmentId;
    }

    @Ignored
    public void setDepartmentId(int departmentId) {
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
    public int getTried() {
        return tried;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setTried(int tried) {
        this.tried = tried;
    }

    @SuppressWarnings("UnusedDeclaration")
    public int getSolved() {
        return solved;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setSolved(int solved) {
        this.solved = solved;
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getType() {
        return type;
    }

    @Ignored
    public void setType(String type) {
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
