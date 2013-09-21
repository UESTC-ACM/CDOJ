package cn.edu.uestc.acmicpc.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.ioc.util.GlobalAware;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.util.Global;

/**
 * Global service.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@Service
public class GlobalServiceImpl extends AbstractService implements GlobalService, GlobalAware {

  @Autowired
  private Global global;

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

  /**
   * Get department by department id
   *
   * @param departmentId department id
   * @return department entity
   */
  @Override
  public Department getDepartment(Integer departmentId) {
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

  @Override
  public void setGlobal(Global global) {
    this.global = global;
  }
}
