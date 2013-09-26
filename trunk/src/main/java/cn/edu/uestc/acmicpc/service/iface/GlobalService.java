package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.util.Global;

import java.util.List;

/**
 * Global service.
 * TODO(mzry1992)
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public interface GlobalService {

  /**
   * Get authentication type list
   *
   * @return authentication type list
   */
  public List<Global.AuthenticationType> getAuthenticationTypeList();

  public String getAuthenticationName(Integer type);
}
