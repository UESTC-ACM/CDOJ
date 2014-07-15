package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.util.enums.EnumType;

import java.util.List;
import java.util.Map;

/**
 * Global service interface.
 */
public interface GlobalService {

  /**
   * Package {@link cn.edu.uestc.acmicpc.util.enums.EnumType} into JSON data.
   *
   * @param idAlias   alias for ordinal id.
   * @param enumTypes Enum type list
   * @return List of packaged data, specified by a Map.
   */
  public List<Map<String, Object>> getEnumTypeList(String idAlias, EnumType[] enumTypes);

  /**
   * Get authentication name by authentication type.
   *
   * @param type authentication type.
   * @return authentication name.
   * @see cn.edu.uestc.acmicpc.util.enums.AuthenticationType
   */
  public String getAuthenticationName(Integer type);

  /**
   * Get return type description and replace <code>$case</code> by case number.
   *
   * @param returnTypeId return type's id.
   * @param caseNumber   current processed case number.
   * @return return type description.
   * @see cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType
   */
  public String getReturnDescription(Integer returnTypeId, Integer caseNumber);

}
