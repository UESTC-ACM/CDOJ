package cn.edu.uestc.acmicpc.judge.entity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.db.dao.iface.ICompileInfoDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.CompileInfo;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;

/**
 * Judge item for single problem.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JudgeItem {

  public Status status;
  public CompileInfo compileInfo;

  @Autowired
  private ICompileInfoDAO compileinfoDAO;
  @Autowired
  private IStatusDAO statusDAO;
  @Autowired
  private IProblemDAO problemDAO;
  @Autowired
  private IUserDAO userDAO;
  @Autowired
  private LanguageService languageService;

  public int parseLanguage() {
    String extension = languageService.getExtension(status.getLanguageId());
    switch (extension) {
    case "cc":
      return 0;
    case "c":
      return 1;
    case "java":
      return 2;
    default:
      return 3;
    }
  }

  public String getSourceName() throws AppException {
    AppExceptionUtil.assertNotNull(languageService);
    return "Main" + languageService.getExtension(status.getLanguageId());
  }

  /**
   * Update database for item.
   *
   * @param updateStatus
   *          if set {@code true}, update status' information.
   */
  @Transactional
  public void update(boolean updateStatus) {
    if (compileInfo != null) {
      if (compileInfo.getContent().length() > 65535)
        compileInfo.setContent(compileInfo.getContent().substring(0, 65534));
      try {
        compileinfoDAO.addOrUpdate(compileInfo);
        status.setCompileInfoId(compileInfo.getCompileInfoId());
      } catch (AppException ignored) {
      }
    }
    try {
      statusDAO.update(status);
    } catch (AppException ignored) {
    }

    if (updateStatus) {
      try {
        Integer userId = status.getUserId();
        Integer problemId = status.getProblemId();
        Condition condition = new Condition();
        condition.addEntry("userId", ConditionType.EQUALS, userId);
        condition.addEntry("result", ConditionType.EQUALS,
            Global.OnlineJudgeReturnType.OJ_AC.ordinal());
        Map<String, Object> properties = new HashMap<>();
        properties.put("solved", statusDAO.customCount("distinct problemId", condition));
        condition = new Condition();
        condition.addEntry("userId", ConditionType.EQUALS, userId);
        userDAO.updateEntitiesByCondition(properties, condition);
        condition = new Condition();
        condition.addEntry("problemId", ConditionType.EQUALS, problemId);
        condition.addEntry("result", ConditionType.EQUALS,
            Global.OnlineJudgeReturnType.OJ_AC.ordinal());
        properties.clear();
        properties.put("solved", statusDAO.customCount("distinct userId", condition));
        condition = new Condition();
        condition.addEntry("problemId", ConditionType.EQUALS, problemId);
        problemDAO.updateEntitiesByCondition(properties, condition);

      } catch (Exception e) {
      }
    }
  }
}
