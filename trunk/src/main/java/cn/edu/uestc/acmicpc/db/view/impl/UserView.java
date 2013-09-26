package cn.edu.uestc.acmicpc.db.view.impl;

import java.sql.Timestamp;

import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.view.base.View;
import cn.edu.uestc.acmicpc.util.Global;

/**
 * Use for return user information with json type.
 */
public class UserView extends View<User> {

  private Integer userId;
  private String userName;
  private String password;
  private String nickName;
  private String email;
  private String school;
  private String department;
  private Integer departmentId;
  private String studentId;
  private Integer tried;
  private Integer solved;
  private String typeName;
  private Integer type;
  private Timestamp lastLogin;

  @Deprecated
  public UserView(User user) {
    setUserId(user.getUserId());
    setUserName(user.getUserName());
    setPassword(user.getPassword());
    setNickName(user.getNickName());
    setEmail(user.getEmail());
    setSchool(user.getSchool());
    setStudentId(user.getStudentId());
    setTried(user.getTried());
    setSolved(user.getSolved());
//    setDepartmentId(user.getDepartmentByDepartmentId().getDepartmentId());
//    setDepartment(user.getDepartmentByDepartmentId().getName());
    setTypeName(Global.AuthenticationType.values()[user.getType()].getDescription());
    setType(user.getType());
    setLastLogin(user.getLastLogin());
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
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

  public Integer getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public Integer getTried() {
    return tried;
  }

  public void setTried(Integer tried) {
    this.tried = tried;
  }

  public Integer getSolved() {
    return solved;
  }

  public void setSolved(Integer solved) {
    this.solved = solved;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Timestamp getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Timestamp lastLogin) {
    this.lastLogin = lastLogin;
  }
}
