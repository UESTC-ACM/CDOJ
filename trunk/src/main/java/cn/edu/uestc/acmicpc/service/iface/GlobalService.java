package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.util.Global;

import java.util.List;

/**
 * Global service.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public interface GlobalService {

  /**
   * Return global entity
   *
   * @return global entity
   */
  public Global getGlobal();

  /**
   * Get department by department id
   *
   * @param departmentId department id
   * @return department entity
   */
  public Department getDepartmentById(Integer departmentId);

  /**
   * Get department list
   *
   * @return department list
   */
  public List<Department> getDepartmentList();

  /**
   * Get authentication type list
   *
   * @return authentication type list
   */
  public List<Global.AuthenticationType> getAuthenticationTypeList();
}
