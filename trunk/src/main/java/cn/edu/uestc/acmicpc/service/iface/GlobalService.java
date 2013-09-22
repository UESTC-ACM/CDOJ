package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.util.Global;

import java.util.List;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public interface GlobalService {

  public Global getGlobal();

  /**
   * Get department by department id
   *
   * @param departmentId department id
   * @return department entity
   */
  public Department getDepartmentById(Integer departmentId);

  public List<Department> getDepartmentList();

  public List<Global.AuthenticationType> getAuthenticationTypeList();
}
