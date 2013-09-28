package cn.edu.uestc.acmicpc.oj.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.DepartmentDTO;
import cn.edu.uestc.acmicpc.db.entity.Department;

import java.util.List;

/**
 * Description
 * TODO(mzry1992)
 */
public interface DepartmentService extends OnlineJudgeService<Department, Integer> {

  public String getDepartmentName(Integer departmentId);

  public List<DepartmentDTO> getDepartmentList();
}
