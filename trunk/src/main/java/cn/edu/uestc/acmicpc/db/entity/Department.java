package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Department information.
 */
@Table(name = "department")
@Entity
@KeyField("departmentId")
public class Department implements Serializable {

  private static final long serialVersionUID = -2249534733683595360L;
  private Integer departmentId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "departmentId", nullable = false, insertable = true, updatable = true,
      length = 10, precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
  }

  private String name = "";

  @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private Collection<User> usersByDepartmentId;

  @OneToMany(mappedBy = "departmentByDepartmentId", cascade = CascadeType.ALL)
  public Collection<User> getUsersByDepartmentId() {
    return usersByDepartmentId;
  }

  public void setUsersByDepartmentId(Collection<User> usersByDepartmentId) {
    this.usersByDepartmentId = usersByDepartmentId;
  }
}
