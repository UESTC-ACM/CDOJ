package cn.edu.uestc.acmicpc.service.iface;

import java.util.List;

import cn.edu.uestc.acmicpc.util.Global.AuthenticationType;
import cn.edu.uestc.acmicpc.util.Global.ContestType;

/**
 * Global service.
 * TODO(mzry1992)
 */
public interface GlobalService {

  /**
   * Get authentication type list
   *
   * @return authentication type list
   */
  public List<AuthenticationType> getAuthenticationTypeList();

  public String getAuthenticationName(Integer type);

  public String getReturnDescription(Integer returnTypeId, Integer caseNumber);

  public List<ContestType> getContestTypeList();
}
