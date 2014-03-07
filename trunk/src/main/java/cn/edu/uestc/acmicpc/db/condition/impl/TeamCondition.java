package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;

/**
 * Team database condition entity.
 */
public class TeamCondition extends BaseCondition {

  public TeamCondition() {
    super("teamId");
  }

  /**
   * Submit user name.
   */
  public String teamName;

}
