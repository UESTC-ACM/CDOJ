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

import java.sql.Timestamp;

/**
 * Use for return user information with json type.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 4
 */
@SuppressWarnings("UnusedDeclaration")
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

    @SuppressWarnings("unused")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @SuppressWarnings("unused")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @SuppressWarnings("unused")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SuppressWarnings("unused")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @SuppressWarnings("unused")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @SuppressWarnings("unused")
    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    @Ignored
    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @SuppressWarnings("unused")
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    @SuppressWarnings("unused")
    public int getTried() {
        return tried;
    }

    public void setTried(int tried) {
        this.tried = tried;
    }

    @SuppressWarnings("unused")
    public int getSolved() {
        return solved;
    }

    public void setSolved(int solved) {
        this.solved = solved;
    }

    @SuppressWarnings("unused")
    public String getType() {
        return type;
    }

    @Ignored
    public void setType(String type) {
        this.type = type;
    }

    @SuppressWarnings("unused")
    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }
}
