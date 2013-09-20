package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.ioc.util.GlobalAware;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.util.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@Service
public class GlobalServiceImpl extends AbstractService implements GlobalService, GlobalAware {

  @Autowired
  private Global global;

  @Override
  public Global getGlobal() {
    return global;
  }

  public Department getDepartmentByDepartmentId(Integer departmentId) {
    for (Department department: global.getDepartmentList())
      if (department.getDepartmentId().equals(departmentId))
        return department;
    return null;
  }

  @Override
  public void setGlobal(Global global) {
    this.global = global;
  }
}
