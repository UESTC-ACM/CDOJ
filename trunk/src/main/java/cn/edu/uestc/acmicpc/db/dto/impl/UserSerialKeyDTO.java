package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;

/** Data transfer object for {@link UserSerialKey}. */
public class UserSerialKeyDTO implements BaseDTO<UserSerialKey> {

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }
  }
}
