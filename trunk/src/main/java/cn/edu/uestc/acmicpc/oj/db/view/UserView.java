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

package cn.edu.uestc.acmicpc.oj.db.view;

import cn.edu.uestc.acmicpc.oj.db.entity.User;
import cn.edu.uestc.acmicpc.util.Global;

import java.sql.Timestamp;

/**
 * use for return user information with json type.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 3
 */
public class UserView {

    private int userId;
    private String userName;
    private String password;
    private String nickName;
    private String email;
    private String school;
    private String department;
    private String studentId;
    private int tried;
    private int solved;
    private String type;
    private Timestamp lastLogin;

    /**
     * construct UserView entity by User entity.
     *
     * @param user specific user entity
     */
    public UserView(User user) {
        setUserId(user.getUserId());
        setUserName(user.getUserName());
        setPassword(user.getPassword());
        setNickName(user.getNickName());
        setEmail(user.getEmail());
        setSchool(user.getSchool());
        setDepartment(user.getDepartmentByDepartmentId().getName());
        setStudentId(user.getStudentId());
        setTried(user.getTried());
        setSolved(user.getSolved());
        setType(Global.AuthenticationType.values()[user.getType()].getDescription());
        setLastLogin(user.getLastLogin());
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

    @SuppressWarnings("unused")
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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
