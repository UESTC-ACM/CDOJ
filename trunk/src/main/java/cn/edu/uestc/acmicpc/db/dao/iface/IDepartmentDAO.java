package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.DepartmentDTO;
import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * DepartmentDAO AOP interface.
 */
public interface IDepartmentDAO extends IDAO<Department, Integer, DepartmentDTO> {

  @Override
  public DepartmentDTO persist(DepartmentDTO dto) throws AppException;
}
