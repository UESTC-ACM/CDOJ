package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.db.entity.Department;

import org.springframework.stereotype.Repository;

/**
 * DAO for department entity.
 */
@Repository
public class DepartmentDAO extends DAO<Department, Integer> implements IDepartmentDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Department> getReferenceClass() {
    return Department.class;
  }
}
