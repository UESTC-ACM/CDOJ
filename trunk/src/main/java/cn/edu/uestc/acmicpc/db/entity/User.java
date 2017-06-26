package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;
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

/**
 * User information.
 */
@Table(name = "user")
@Entity
@KeyField("userId")
public class User implements Serializable {

  private static final long serialVersionUID = -1942419166710527006L;
  private Integer userId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "userId", nullable = false, length = 10, unique = true)
  @Id
  @GeneratedValue
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  private String userName;

  @Column(name = "userName", nullable = false, length = 24, unique = true)
  @Basic
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  private String studentId = "";

  @Column(name = "studentId", nullable = false, length = 50)
  @Basic
  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  private String password;

  @Column(name = "password", nullable = false, length = 40)
  @Basic
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  private String school = "";

  @Column(name = "school", nullable = false, length = 100)
  @Basic
  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  private String nickName;

  @Column(name = "nickName", nullable = false, length = 50)
  @Basic
  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  private String email;

  @Column(name = "email", nullable = false, length = 100, unique = true)
  @Basic
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  private Integer solved = 0;

  @Column(name = "solved", nullable = false, length = 10)
  @Basic
  public Integer getSolved() {
    return solved;
  }

  public void setSolved(Integer solved) {
    this.solved = solved;
  }

  private Integer tried = 0;

  @Column(name = "tried", nullable = false, length = 10)
  @Basic
  public Integer getTried() {
    return tried;
  }

  public void setTried(Integer tried) {
    this.tried = tried;
  }

  private Integer type = 0;

  @Column(name = "type", nullable = false, length = 10)
  @Basic
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  private Timestamp lastLogin = new Timestamp(0L);

  @Column(name = "lastLogin", nullable = false, length = 19)
  @Basic
  public Timestamp getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Timestamp lastLogin) {
    this.lastLogin = lastLogin;
  }

  private String motto = "";

  @Column(name = "motto", nullable = false)
  @Basic
  public String getMotto() {
    return motto;
  }

  public void setMotto(String motto) {
    this.motto = motto;
  }

  private String name = "";

  @Column(name = "name", nullable = false, length = 50)
  @Basic
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private String phone = "";

  @Column(name = "phone", nullable = false, length = 45)
  @Basic
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  private Integer sex = 0;

  @Column(name = "sex", nullable = false, length = 10)
  @Basic
  public Integer getSex() {
    return sex;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

  private Integer grade = 0;

  @Column(name = "grade", nullable = false, length = 10)
  @Basic
  public Integer getGrade() {
    return grade;
  }

  public void setGrade(Integer grade) {
    this.grade = grade;
  }

  private Integer size = 0;

  @Column(name = "size", nullable = false, length = 10)
  @Basic
  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  private Integer departmentId;

  @Column(name = "departmentId", nullable = false, length = 10)
  public Integer getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
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
  @JoinColumn(name = "departmentId", referencedColumnName = "departmentId", nullable = false,
      insertable = false, updatable = false)
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

  private Collection<TeamUser> teamUsersByUserId;

  @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL)
  public Collection<TeamUser> getTeamUsersByUserId() {
    return teamUsersByUserId;
  }

  public void setTeamUsersByUserId(Collection<TeamUser> teamUsersByUserId) {
    this.teamUsersByUserId = teamUsersByUserId;
  }

  private Collection<TrainingUser> trainingUsersByUserId;

  @OneToMany(mappedBy = "userByUserId", cascade = CascadeType.ALL)
  public Collection<TrainingUser> getTrainingUsersByUserId() {
    return trainingUsersByUserId;
  }

  public void setTrainingUsersByUserId(Collection<TrainingUser> trainingUsersByUserId) {
    this.trainingUsersByUserId = trainingUsersByUserId;
  }
}
