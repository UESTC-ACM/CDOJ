package cn.edu.uestc.acmicpc.judge.core;

import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;

/**
 * Judge result
 */
public class JudgeResult {

  // Result id, see OnlineJudgeReturnType
  private OnlineJudgeReturnType result;
  private Integer memoryCost;
  private Integer timeCost;
  private String compileInfo;

  public OnlineJudgeReturnType getResult() {
    return result;
  }

  public void setResult(OnlineJudgeReturnType result) {
    this.result = result;
  }

  public Integer getMemoryCost() {
    return memoryCost;
  }

  public void setMemoryCost(Integer memoryCost) {
    this.memoryCost = memoryCost;
  }

  public Integer getTimeCost() {
    return timeCost;
  }

  public void setTimeCost(Integer timeCost) {
    this.timeCost = timeCost;
  }

  public String getCompileInfo() {
    return compileInfo;
  }

  public void setCompileInfo(String compileInfo) {
    this.compileInfo = compileInfo;
  }
}
