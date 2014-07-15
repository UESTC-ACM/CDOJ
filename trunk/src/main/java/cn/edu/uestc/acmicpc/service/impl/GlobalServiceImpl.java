package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.EnumType;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Implementation for {@link GlobalService}.
 */
@Service
public class GlobalServiceImpl extends AbstractService implements GlobalService {

  @Override
  public List<Map<String, Object>> getEnumTypeList(String idAlias, EnumType[] enumTypes) {
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

  @Override
  public String getAuthenticationName(Integer type) {
    for (AuthenticationType authenticationType : AuthenticationType.values())
      if (authenticationType.ordinal() == type)
        return authenticationType.getDescription();
    return null;
  }

  @Override
  public String getReturnDescription(Integer returnTypeId, Integer caseNumber) {
    for (OnlineJudgeReturnType returnType : OnlineJudgeReturnType.values())
      if (returnType.ordinal() == returnTypeId)
        return StringUtil.getStatusDescription(returnType, caseNumber);
    return null;
  }
}
