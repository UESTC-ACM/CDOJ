package cn.edu.uestc.acmicpc.service.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ICompileInfoDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.CompileInfo;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Judge item for single problem.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JudgeItem {

  public Status status;
  public CompileInfo compileInfo;

  @Autowired
  public JudgeItem(ICompileInfoDAO compileInfoDAO,
      IStatusDAO statusDAO,
      IUserDAO userDAO,
      IProblemDAO problemDAO) {
    this.compileinfoDAO = compileInfoDAO;
    this.statusDAO = statusDAO;
    this.problemDAO = problemDAO;
  }

  /**
   * CompileInfo DAO for database query.
   */
  private final ICompileInfoDAO compileinfoDAO;
  /**
   * Status DAO for database query.
   */
  private final IStatusDAO statusDAO;
  /**
   * Problem DAO for database query.
   */
  @SuppressWarnings("unused")
  private final IProblemDAO problemDAO;

  public int parseLanguage() {
    // TODO(fish): use new API.
//    String extension = status.getLanguageByLanguageId().getExtension();
//    switch (extension) {
//      case "cc":
//        return 0;
//      case "c":
//        return 1;
//      case "java":
//        return 2;
//      default:
//        return 3;
//    }
    return 3;
  }

  public String getSourceName() {
    // TODO(fish): use new API.
//    return "Main" + status.getLanguageByLanguageId().getExtension();
    return null;
  }

  /**
   * Update database for item.
   *
   * @param updateStatus if set {@code true}, update status' information.
   */
  public void update(boolean updateStatus) {
    // TODO(fish): use new API.
    if (compileInfo != null) {
      if (compileInfo.getContent().length() > 65535)
        compileInfo.setContent(compileInfo.getContent().substring(0, 65534));
      try {
        compileinfoDAO.addOrUpdate(compileInfo);
//        status.setCompileInfoByCompileInfoId(compileInfo);
      } catch (AppException ignored) {
      }
    }
    try {
      statusDAO.update(status);
    } catch (AppException ignored) {
    }

//    if (updateStatus) {
//      try {
//        User user = status.getUserByUserId();
//        Problem problem = status.getProblemByProblemId();
//        String hql =
//            "update User set solved = (select count(distinct problemByProblemId.problemId)"
//                + " from Status where userByUserId.userId = " + user.getUserId() + " and result = "
//                + Global.OnlineJudgeReturnType.OJ_AC.ordinal() + ") where userId = "
//                + user.getUserId();
//        problemDAO.executeHQL(hql);
//        hql =
//            "update Problem set solved = (select count(distinct userByUserId.userId)"
//                + " from Status where problemByProblemId.problemId = " + problem.getProblemId()
//                + " and result=" + Global.OnlineJudgeReturnType.OJ_AC.ordinal()
//                + ") where problemId = " + problem.getProblemId();
//        problemDAO.executeHQL(hql);
//
//      } catch (Exception e) {
//      }
//    }
  }
}
