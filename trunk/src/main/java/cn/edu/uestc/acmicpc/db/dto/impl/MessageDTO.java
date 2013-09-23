package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Message;

/** Data transfer object for {@link Message}. */
public class MessageDTO implements BaseDTO<Message> {

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }
  }
}
