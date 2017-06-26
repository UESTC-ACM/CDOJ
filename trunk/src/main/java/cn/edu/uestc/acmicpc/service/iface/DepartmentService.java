package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.DepartmentDto;
import java.util.List;

/**
 * Department service interface.
 */
public interface DepartmentService {

  /**
   * Get department name by department id.
   *
   * @param departmentId department's id.
   * @return department name.
   */
  public String getDepartmentName(Integer departmentId);

  /**
   * Get {@link DepartmentDto} of all departments.
   *
   * @return list of {@link DepartmentDto}.
   */
  public List<DepartmentDto> getDepartmentList();
}
