package cn.edu.uestc.acmicpc.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.util.dto.AuthenticationTypeDTO;
import cn.edu.uestc.acmicpc.util.dto.ContestTypeDTO;
import cn.edu.uestc.acmicpc.util.dto.OnlineJudgeResultTypeDTO;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Global;

/**
 * Implementation for {@link GlobalService}.
 */
@Service
public class GlobalServiceImpl extends AbstractService implements GlobalService {

  @Override
  public List<AuthenticationTypeDTO> getAuthenticationTypeList() {
    List<AuthenticationTypeDTO> result = new LinkedList<>();
    for (Global.AuthenticationType authenticationType: Global.AuthenticationType.values()) {
      result.add(new AuthenticationTypeDTO(authenticationType.ordinal(), authenticationType.getDescription()));
    }
    return result;
  }

  @Override
  public List<OnlineJudgeResultTypeDTO> getOnlineJudgeResultTypeList() {
    List<OnlineJudgeResultTypeDTO> result = new LinkedList<>();
    for (Global.OnlineJudgeResultType onlineJudgeResultType: Global.OnlineJudgeResultType.values()) {
      result.add(new OnlineJudgeResultTypeDTO(onlineJudgeResultType.ordinal(), onlineJudgeResultType.getDescription()));
    }
    return result;
  }

  @Override
  public List<ContestTypeDTO> getContestTypeList() {
    List<ContestTypeDTO> result = new LinkedList<>();
    for (Global.ContestType contestType: Global.ContestType.values()) {
      result.add(new ContestTypeDTO(contestType.ordinal(), contestType.getDescription()));
    }
    return result;
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
}
