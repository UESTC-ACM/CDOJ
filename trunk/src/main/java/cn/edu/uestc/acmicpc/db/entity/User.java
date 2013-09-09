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

package cn.edu.uestc.acmicpc.db.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;

/**
 * User information.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@Table(name = "user")
@Entity
@KeyField("userId")
public class User implements Serializable {

  private static final long serialVersionUID = -1942419166710527006L;
  private Integer userId;

  private Integer version;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "userId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  private String userName;

  @Column(name = "userName", nullable = false, insertable = true, updatable = true, length = 24,
      precision = 0, unique = true)
  @Basic
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  private String studentId;

  @Column(name = "studentId", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  private String password;

  @Column(name = "password", nullable = false, insertable = true, updatable = true, length = 40,
      precision = 0)
  @Basic
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  private String school;

  @Column(name = "school", nullable = false, insertable = true, updatable = true, length = 100,
      precision = 0)
  @Basic
  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  private String nickName;

  @Column(name = "nickName", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  private String email;

  @Column(name = "email", nullable = false, insertable = true, updatable = true, length = 100,
      precision = 0, unique = true)
  @Basic
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  private Integer solved;

  @Override
  public String toString() {
    return "User{" + "userId=" + userId + ", userName='" + userName + '\'' + ", studentId='"
        + studentId + '\'' + ", password='" + password + '\'' + ", school='" + school + '\''
        + ", nickName='" + nickName + '\'' + ", email='" + email + '\'' + ", solved=" + solved
        + ", tried=" + tried + ", type=" + type + ", lastLogin=" + lastLogin + '}';
  }

  @Column(name = "solved", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getSolved() {
    return solved;
  }

  public void setSolved(Integer solved) {
    this.solved = solved;
  }

  private Integer tried;

  @Column(name = "tried", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getTried() {
    return tried;
  }

  public void setTried(Integer tried) {
    this.tried = tried;
  }

  private Integer type;

  @Column(name = "type", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  private Timestamp lastLogin;

  @Column(name = "lastLogin", nullable = false, insertable = true, updatable = true, length = 19,
      precision = 0)
  @Basic
  public Timestamp getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Timestamp lastLogin) {
    this.lastLogin = lastLogin;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    User user = (User) o;

    if (!solved.equals(user.solved))
      return false;
    if (!tried.equals(user.tried))
      return false;
    if (!type.equals(user.type))
      return false;
    if (!userId.equals(user.userId))
      return false;
    if (email != null ? !email.equals(user.email) : user.email != null)
      return false;
    if (lastLogin != null ? !lastLogin.equals(user.lastLogin) : user.lastLogin != null)
      return false;
    if (nickName != null ? !nickName.equals(user.nickName) : user.nickName != null)
      return false;
    if (password != null ? !password.equals(user.password) : user.password != null)
      return false;
    if (school != null ? !school.equals(user.school) : user.school != null)
      return false;
    if (studentId != null ? !studentId.equals(user.studentId) : user.studentId != null)
      return false;
    if (!(userName != null ? !userName.equals(user.userName) : user.userName != null))
      return true;
    else
      return false;

  }

  @Override
  public int hashCode() {
    int result = userId;
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (school != null ? school.hashCode() : 0);
    result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + solved;
    result = 31 * result + tried;
    result = 31 * result + type;
    result = 31 * result + (lastLogin != null ? lastLogin.hashCode() : 0);
    return result;
  }

  private Collection<ContestUser> contestUsersByUserId;

  @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL)
  public Collection<ContestUser> getContestUsersByUserId() {
    return contestUsersByUserId;
  }

  public void setContestUsersByUserId(Collection<ContestUser> contestUsersByUserId) {
    this.contestUsersByUserId = contestUsersByUserId;
  }

  private Collection<Message> messagesByUserId;

  @OneToMany(mappedBy = "userByReceiverId", cascade = CascadeType.ALL)
  public Collection<Message> getMessagesByUserId() {
    return messagesByUserId;
  }

  public void setMessagesByUserId(Collection<Message> messagesByUserId) {
    this.messagesByUserId = messagesByUserId;
  }

  private Collection<Message> messagesByUserId_0;

  @OneToMany(mappedBy = "userBySenderId", cascade = CascadeType.ALL)
  public Collection<Message> getMessagesByUserId_0() {
    return messagesByUserId_0;
  }

  public void setMessagesByUserId_0(Collection<Message> messagesByUserId_0) {
    this.messagesByUserId_0 = messagesByUserId_0;
  }

  private Collection<Status> statusesByUserId;

  @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL)
  public Collection<Status> getStatusesByUserId() {
    return statusesByUserId;
  }

  public void setStatusesByUserId(Collection<Status> statusesByUserId) {
    this.statusesByUserId = statusesByUserId;
  }

  private Collection<UserSerialKey> userSerialKeysByUserId;

  @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL)
  public Collection<UserSerialKey> getUserSerialKeysByUserId() {
    return userSerialKeysByUserId;
  }

  public void setUserSerialKeysByUserId(Collection<UserSerialKey> userSerialKeysByUserId) {
    this.userSerialKeysByUserId = userSerialKeysByUserId;
  }

  private Department departmentByDepartmentId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "departmentId", referencedColumnName = "departmentId", nullable = false)
  public Department getDepartmentByDepartmentId() {
    return departmentByDepartmentId;
  }

  public void setDepartmentByDepartmentId(Department departmentByDepartmentId) {
    this.departmentByDepartmentId = departmentByDepartmentId;
  }

  private Collection<Article> articlesByUserId;

  @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL)
  public Collection<Article> getArticlesByUserId() {
    return articlesByUserId;
  }

  public void setArticlesByUserId(Collection<Article> articlesByUserId) {
    this.articlesByUserId = articlesByUserId;
  }
}
