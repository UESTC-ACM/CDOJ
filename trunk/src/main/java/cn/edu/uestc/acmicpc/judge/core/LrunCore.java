
package cn.edu.uestc.acmicpc.judge.core;
import cn.edu.uestc.acmicpc.judge.entity.JudgeItem;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
/**
 * judge core adapter using lrun as judge core.
 */
public class LrunCore extends AbstractJudgeCore {

  private static final Logger logger = Logger.getLogger(LrunCore.class);

  private static final String COMMAND_LINE_PATTERN =
      "py-lrun-core.py -l %s -w %s -d %s -s %s -t %d -m %d -f %s -o %d";

  public LrunCore(String workPath, String tempPath, Settings settings) {
    super(workPath, tempPath, settings);
  }

  @Override
  protected String buildJudgeShellCommand(int currentTestCase, JudgeItem judgeItem) {
    String language = normalizeLanguage(judgeItem.getStatus().getLanguageId() );
    String dataDir = settings.DATA_PATH + "/" + judgeItem.getStatus().getProblemId() + "/";
    int timeLimit = judgeItem.getStatus().getTimeLimit();
    int memory = judgeItem.getStatus().getMemoryLimit() * 1024; // change mb to kb
    int outputlimit = judgeItem.getStatus().getOutputLimit() * 1024; // change mb to kb
    Boolean SpeicalJudgeFlag = judgeItem.getStatus().getIsSpj();
    String command = String.format(COMMAND_LINE_PATTERN,
        language, tempPath, dataDir,
        judgeItem.getSourceNameWithoutExtension(),
        timeLimit, memory,
        Integer.toString(currentTestCase) /* data file */,outputlimit);
    if (currentTestCase == 1 && language != "python2" && language != "python3" ) {
      command = command + " -c";
    }
    if( SpeicalJudgeFlag == true )
      command = command + " -spj";
    return command;
  }

  private String normalizeLanguage(int languageid) {
    if( languageid == 1 )
      return "gnu-gcc";
    else if( languageid == 2 )
      return "gnu-g++11";
    else if( languageid == 3 )
      return "java";
    else if( languageid == 4 )
      return "python3";
    else if( languageid == 5 )
      return "python2";
    else
      throw new AppException("unreognize language: " + languageid );
  }

  private String parseJudgeResult(String output) {
    return output.substring(0, output.indexOf('\n'));
  }

  @Override
  protected JudgeResult execute(JudgeItem judgeItem, String output) {
    JudgeResult result = new JudgeResult();
    String judgeResult = parseJudgeResult(output);
    JSONObject jsonObject = new JSONObject();
    int index = judgeResult.length();
    // TODO(ruinshe): change to a string to enum mapping.
    if ("CE".equals(judgeResult)) {
      result.setResult(OnlineJudgeReturnType.OJ_CE);
      result.setCompileInfo(output.substring(index + 1).replace(tempPath, ""));
    } else if ("AC".equals(judgeResult)) {
      jsonObject = JSON.parseObject(output.substring(index + 1));
      result.setResult(OnlineJudgeReturnType.OJ_AC);
      if (jsonObject.containsKey("MEMORY")) {
        result.setMemoryCost((int) (Long.parseLong(
            jsonObject.getString("MEMORY")) / 1024));
      } else {
        result.setMemoryCost(null);
      }
      if (jsonObject.containsKey("CPUTIME")) {
        result.setTimeCost((int) (Double.parseDouble(
            jsonObject.getString("CPUTIME")) * 1000.0));
      } else {
        result.setTimeCost(null);
      }
    } else if ("TLE".equals(judgeResult)) {
      result.setResult(OnlineJudgeReturnType.OJ_TLE);
    } else if ("RE".equals(judgeResult)) {
      result.setResult(OnlineJudgeReturnType.OJ_RE_SEGV);
    } else if ( "MLE".equals( judgeResult ) ){
      result.setResult(OnlineJudgeReturnType.OJ_MLE);
    }else if ( "OLE".equals( judgeResult ) ){
      result.setResult(OnlineJudgeReturnType.OJ_OLE);
    }else if ( "WA".equals( judgeResult ) ){
      result.setResult(OnlineJudgeReturnType.OJ_WA);
    }else {
      result.setResult(OnlineJudgeReturnType.OJ_SE);
    }
    return result;
  }
}
