package cn.edu.uestc.acmicpc.service.iface;

import java.util.List;

import cn.edu.uestc.acmicpc.db.dto.impl.department.DepartmentDTO;
import cn.edu.uestc.acmicpc.db.entity.Department;

/**
 * Description
 * TODO(mzry1992)
 */
public interface DepartmentService extends DatabaseService<Department, Integer> {

  public String getDepartmentName(Integer departmentId);

  public List<DepartmentDTO> getDepartmentList();
}
