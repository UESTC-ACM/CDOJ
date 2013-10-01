package cn.edu.uestc.acmicpc.db.dto.impl.problem;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.ProblemTag;

/** Data transfer object for {@link ProblemTag}. */
public class ProblemTagDTO implements BaseDTO<ProblemTag> {

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }
  }
}
