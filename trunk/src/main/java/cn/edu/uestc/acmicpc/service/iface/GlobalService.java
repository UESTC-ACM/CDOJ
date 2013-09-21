package cn.edu.uestc.acmicpc.service.iface;

import java.util.List;

import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.util.Global;

/**
 * Global service interface to handle operations about {@link Global}.
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
   * @return department list
   */
  public List<Department> getDepartmentList();
}
