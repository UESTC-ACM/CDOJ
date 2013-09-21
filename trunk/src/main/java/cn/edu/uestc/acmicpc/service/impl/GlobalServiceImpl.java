package cn.edu.uestc.acmicpc.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.util.Global;

/**
 * Implementation for {@link GlobalService}.
 */
@Service
@Primary
public class GlobalServiceImpl extends AbstractService implements GlobalService {

  private Global global;

  @Autowired
  public GlobalServiceImpl(Global global) {
    this.global = global;
  }

  @PostConstruct
  public void init() {
  }

  /**
   * Return global entity
   *
   * @return global entity
   */
  @Override
  public Global getGlobal() {
    return global;
  }

  @Override
  public Department getDepartmentById(Integer departmentId) {
    for (Department department : global.getDepartmentList()) {
      if (department.getDepartmentId().equals(departmentId)) {
        return department;
      }
    }
    return null;
  }

  /**
   * Get department list
   *
   * @return department list
   */
  @Override
  public List<Department> getDepartmentList() {
    return global.getDepartmentList();
  }
}
