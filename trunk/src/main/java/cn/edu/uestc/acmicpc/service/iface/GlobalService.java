package cn.edu.uestc.acmicpc.service.iface;

import java.util.List;

import cn.edu.uestc.acmicpc.util.settings.Global.AuthenticationType;
import cn.edu.uestc.acmicpc.util.settings.Global.ContestType;
import cn.edu.uestc.acmicpc.util.settings.Global.OnlineJudgeReturnType;

/**
 * Global service interface.
 */
public interface GlobalService {

  /**
   * Get all {@link AuthenticationType} entities.
   *
   * @return list of all {@link AuthenticationType} entities.
   */
  public List<AuthenticationType> getAuthenticationTypeList();

  /**
   * Get authentication name by authentication type.
   *
   * @param type authentication type.
   * @return authentication name.
   * @see AuthenticationType
   */
  public String getAuthenticationName(Integer type);

  /**
   * Get return type description and replace <code>$case</code> by case number.
   *
   * @param returnTypeId return type's id.
   * @param caseNumber   current processed case number.
   * @return return type description.
   * @see OnlineJudgeReturnType
   */
  public String getReturnDescription(Integer returnTypeId, Integer caseNumber);

  /**
   * Get all {@link ContestType} entities.
   *
   * @return list of all {@link ContestType} entities.
   */
  public List<ContestType> getContestTypeList();
}
