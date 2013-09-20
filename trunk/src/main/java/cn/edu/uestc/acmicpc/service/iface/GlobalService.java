package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.util.Global;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public interface GlobalService {

  public Global getGlobal();

  public Department getDepartmentByDepartmentId(Integer departmentId);
}
