package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.DepartmentDTO;
import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * DAO for department entity.
 */
@Repository
public class DepartmentDAO extends DAO<Department, Integer, DepartmentDTO>
    implements IDepartmentDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Department> getReferenceClass() {
    return Department.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public DepartmentDTO persist(DepartmentDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
