package cn.edu.uestc.acmicpc.judge.core;

import cn.edu.uestc.acmicpc.judge.entity.JudgeItem;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;

/**
 * Judge core interface
 */
public interface JudgeCore {

  public JudgeResult judge(int currentCase, JudgeItem judgeItem);
}
