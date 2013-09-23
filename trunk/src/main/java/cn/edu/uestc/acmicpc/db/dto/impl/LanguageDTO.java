package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Language;

/** Data transfer object for {@link Language}. */
public class LanguageDTO implements BaseDTO<Language> {

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }
  }
}
