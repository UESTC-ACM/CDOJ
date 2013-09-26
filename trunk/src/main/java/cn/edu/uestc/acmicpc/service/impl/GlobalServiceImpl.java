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
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@Service
@Primary
public class GlobalServiceImpl extends AbstractService implements GlobalService {

  /**
   * TODO(mzry1992): please get rid of global's bean property.
   */
  @Autowired
  private Global global;

  @PostConstruct
  public void init() {
  }

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

  @Override
  public List<Department> getDepartmentList() {
    return global.getDepartmentList();
  }

  @Override
  public List<Global.AuthenticationType> getAuthenticationTypeList() {
    return global.getAuthenticationTypeList();
  }

}
