package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.department.DepartmentDTO;
import cn.edu.uestc.acmicpc.db.entity.Department;

import java.util.List;

/**
 * Department service interface.
 */
public interface DepartmentService extends DatabaseService<Department, Integer> {

  /**
   * Get department name by department id.
   *
   * @param departmentId department's id.
   * @return department name.
   */
  public String getDepartmentName(Integer departmentId);

  /**
   * Get {@link DepartmentDTO} of all departments.
   *
   * @return list of {@link DepartmentDTO}.
   */
  public List<DepartmentDTO> getDepartmentList();
}
