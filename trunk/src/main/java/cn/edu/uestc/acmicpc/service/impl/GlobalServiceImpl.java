package cn.edu.uestc.acmicpc.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.util.settings.Global.ContestType;

/**
 * Implementation for {@link GlobalService}.
 */
@Service
public class GlobalServiceImpl extends AbstractService implements GlobalService {

  @Override
  public List<Global.AuthenticationType> getAuthenticationTypeList() {
    return Arrays.asList(Global.AuthenticationType.values());
  }

  @Override
  public String getAuthenticationName(Integer type) {
    for (Global.AuthenticationType authenticationType : Global.AuthenticationType.values())
      if (authenticationType.ordinal() == type)
        return authenticationType.getDescription();
    return null;
  }

  @Override
  public String getReturnDescription(Integer returnTypeId, Integer caseNumber) {
    for (Global.OnlineJudgeReturnType returnType : Global.OnlineJudgeReturnType.values())
      if (returnType.ordinal() == returnTypeId)
        return StringUtil.getStatusDescription(returnType, caseNumber);
    return null;
  }

  @Override
  public List<ContestType> getContestTypeList() {
    return Arrays.asList(Global.ContestType.values());
  }
}
