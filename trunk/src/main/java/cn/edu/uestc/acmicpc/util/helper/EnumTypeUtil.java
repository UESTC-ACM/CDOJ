package cn.edu.uestc.acmicpc.util.helper;

import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.EnumType;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Utility in OJ enum type.
 */
public class EnumTypeUtil {

  public static List<Map<String, Object>> getEnumTypeList(String idAlias, EnumType[] enumTypes) {
    List<Map<String, Object>> result = new LinkedList<>();
    Integer ordinal = 0;
    for (EnumType enumType : enumTypes) {
      Map<String, Object> data = new HashMap<>();
      data.put(idAlias, ordinal);
      data.put("description", enumType.getDescription());

      ordinal = ordinal + 1;
      result.add(data);
    }
    return result;
  }

  public static String getAuthenticationName(Integer type) {
    for (AuthenticationType authenticationType : AuthenticationType.values()) {
      if (authenticationType.ordinal() == type) {
        return authenticationType.getDescription();
      }
    }
    return null;
  }

  public static String getReturnDescription(Integer returnTypeId, Integer caseNumber) {
    for (OnlineJudgeReturnType returnType : OnlineJudgeReturnType.values()) {
      if (returnType.ordinal() == returnTypeId) {
        return StringUtil.getStatusDescription(returnType, caseNumber);
      }
    }
    return null;
  }
}
