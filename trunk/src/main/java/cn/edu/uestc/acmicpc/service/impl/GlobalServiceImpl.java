package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.util.dto.AuthenticationTypeDTO;
import cn.edu.uestc.acmicpc.util.dto.ContestRegistryStatusDTO;
import cn.edu.uestc.acmicpc.util.dto.ContestTypeDTO;
import cn.edu.uestc.acmicpc.util.dto.GenderTypeDTO;
import cn.edu.uestc.acmicpc.util.dto.GradeTypeDTO;
import cn.edu.uestc.acmicpc.util.dto.OnlineJudgeResultTypeDTO;
import cn.edu.uestc.acmicpc.util.dto.TShirtsSizeTypeDTO;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.ContestRegistryStatusType;
import cn.edu.uestc.acmicpc.util.enums.ContestType;
import cn.edu.uestc.acmicpc.util.enums.GenderType;
import cn.edu.uestc.acmicpc.util.enums.GradeType;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeResultType;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.enums.TShirtsSizeType;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation for {@link GlobalService}.
 */
@Service
public class GlobalServiceImpl extends AbstractService implements GlobalService {

  @Override
  public List<AuthenticationTypeDTO> getAuthenticationTypeList() {
    List<AuthenticationTypeDTO> result = new LinkedList<>();
    for (AuthenticationType authenticationType: AuthenticationType.values()) {
      result.add(new AuthenticationTypeDTO(authenticationType.ordinal(), authenticationType.getDescription()));
    }
    return result;
  }

  @Override
  public List<OnlineJudgeResultTypeDTO> getOnlineJudgeResultTypeList() {
    List<OnlineJudgeResultTypeDTO> result = new LinkedList<>();
    for (OnlineJudgeResultType onlineJudgeResultType: OnlineJudgeResultType.values()) {
      result.add(new OnlineJudgeResultTypeDTO(onlineJudgeResultType.ordinal(), onlineJudgeResultType.getDescription()));
    }
    return result;
  }

  @Override
  public List<GenderTypeDTO> getGenderTypeList() {
    List<GenderTypeDTO> result = new LinkedList<>();
    for (GenderType gender: GenderType.values()) {
      result.add(new GenderTypeDTO(gender.ordinal(), gender.getDescription()));
    }
    return result;
  }

  @Override
  public List<GradeTypeDTO> getGradeTypeList() {
    List<GradeTypeDTO> result = new LinkedList<>();
    for (GradeType grade: GradeType.values()) {
      result.add(new GradeTypeDTO(grade.ordinal(), grade.getDescription()));
    }
    return result;
  }

  @Override
  public List<TShirtsSizeTypeDTO> getTShirtsSizeTypeList() {
    List<TShirtsSizeTypeDTO> result = new LinkedList<>();
    for (TShirtsSizeType tShirtsSize: TShirtsSizeType.values()) {
      result.add(new TShirtsSizeTypeDTO(tShirtsSize.ordinal(), tShirtsSize.getDescription()));
    }
    return result;
  }

  @Override
  public List<ContestTypeDTO> getContestTypeList() {
    List<ContestTypeDTO> result = new LinkedList<>();
    for (ContestType contestType: ContestType.values()) {
      result.add(new ContestTypeDTO(contestType.ordinal(), contestType.getDescription()));
    }
    return result;
  }

  @Override
  public List<ContestRegistryStatusDTO> getContestRegistryStatusList() {
    List<ContestRegistryStatusDTO> result = new LinkedList<>();
    for (ContestRegistryStatusType contestRegistryStatus: ContestRegistryStatusType.values()) {
      result.add(new ContestRegistryStatusDTO(contestRegistryStatus.ordinal(),
          contestRegistryStatus.getDescription()));
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
