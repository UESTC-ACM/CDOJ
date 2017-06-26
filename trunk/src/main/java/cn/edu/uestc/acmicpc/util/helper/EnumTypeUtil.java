package cn.edu.uestc.acmicpc.util.helper;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.EnumType;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Utility in OJ enum type.
 */
@SuppressWarnings("deprecation")
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

  @Deprecated
  public static Condition toOrdinalCondition(Condition preCondition) throws AppException {
    Condition condition = new Condition();
    List<Condition.Entry> entries = preCondition.getEntries();
    for (int i = 0; i < entries.size(); i++) {
      Condition.Entry entry = entries.get(i);
      if (entry.getValue() instanceof Enum) {
        Enum<?> tmpValue = (Enum<?>) entry.getValue();
        condition.addEntry(entry.getFieldName(), entry.getConditionType(), tmpValue.ordinal());
      } else {
        condition.addEntry(entry);
      }
    }
    return condition;
  }
}
