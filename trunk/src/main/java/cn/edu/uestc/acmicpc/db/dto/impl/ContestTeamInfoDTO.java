package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestTeamInfo;

/** Data transfer object for {@link ContestTeamInfo}. */
public class ContestTeamInfoDTO implements BaseDTO<ContestTeamInfo> {

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }
  }
}
