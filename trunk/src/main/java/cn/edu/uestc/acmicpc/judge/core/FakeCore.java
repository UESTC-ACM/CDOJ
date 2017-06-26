package cn.edu.uestc.acmicpc.judge.core;

import cn.edu.uestc.acmicpc.judge.entity.JudgeItem;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import java.util.Random;

/**
 * A fake judge core that return fake result randomly.
 */
public class FakeCore implements JudgeCore {

  @Override
  public JudgeResult judge(int currentCase, JudgeItem judgeItem) {
    Random random = new Random(System.currentTimeMillis());
    Integer timeLimit = judgeItem.getStatus().getTimeLimit();
    Integer memoryLimit = judgeItem.getStatus().getMemoryLimit();
    JudgeResult judgeResult = new JudgeResult();
    judgeResult.setTimeCost(random.nextInt(timeLimit));
    judgeResult.setMemoryCost(random.nextInt(memoryLimit));
    if (currentCase < judgeItem.getStatus().getDataCount() || random.nextInt(3) == 0) {
      judgeResult.setResult(OnlineJudgeReturnType.OJ_AC);
    } else {
      judgeResult.setResult(OnlineJudgeReturnType.getReturnType(2 + random.nextInt(14)));
      if (judgeResult.getResult() == OnlineJudgeReturnType.OJ_CE) {
        judgeResult.setCompileInfo("the compiler exits with exit_code: 127");
      }
    }
    return judgeResult;
  }
}
