package cn.edu.uestc.acmicpc.judge.entity;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusForJudgeDTO;
import cn.edu.uestc.acmicpc.service.iface.CompileInfoService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Judge item for single problem.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JudgeItem {

  private StatusForJudgeDTO status;
  private String compileInfo;
  private CompileInfoService compileInfoService;
  private StatusService statusService;
  private UserService userService;
  private ProblemService problemService;

  @Autowired
  public void setCompileInfoService(CompileInfoService compileInfoService) {
    this.compileInfoService = compileInfoService;
  }

  @Autowired
  public void setStatusService(StatusService statusService) {
    this.statusService = statusService;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setProblemService(ProblemService problemService) {
    this.problemService = problemService;
  }

  public void setStatusForJudgeDTO(StatusForJudgeDTO status) {
    this.status = status;
  }

  public StatusForJudgeDTO getStatusForJudgeDTO() {
    return status;
  }

  public void setCompileInfo(String compileInfo) {
    this.compileInfo = compileInfo;
  }

  public String getCompileInfo() {
    return compileInfo;
  }

  public String getSourceName() {
    return "Main" + status.getLanguageExtension();
  }

  /**
   * Update database for item.
   *
   * @param updateStatus
   *          if set {@code true}, update status' information.
   */
  public void update(boolean updateStatus) {
    try {
      if (compileInfo != null) {
        // Compile error!
        // FIXME(mzry1992) Why 30000?
        if (compileInfo.length() > 30000)
          compileInfo = compileInfo.substring(0, 30000);
        if (status.getCompileInfoId() != null) {
          // Update old compile info (if exists)
          compileInfoService.updateCompileInfoContent(
              status.getCompileInfoId(),
              compileInfo);
        } else {
          // Create new compile info
          Integer newCompileInfoId = compileInfoService
              .createCompileInfo(compileInfo);
          status.setCompileInfoId(newCompileInfoId);
        }
      }
      statusService.updateStatusByStatusForJudgeDTO(status);
    } catch (AppException ignored) {
      // TODO(fish) Why not set result as OJ_REJUDGING or something else
      // to rejudge it.
    }

    if (updateStatus) {
      try {
        Integer userId = status.getUserId();
        Integer problemId = status.getProblemId();

        Map<String, Object> properties = new HashMap<>();
        properties.put("solved",
            statusService.countProblemsUserAccepted(userId));
        properties.put("tried",
            statusService.countProblemsUserTired(userId));
        userService.updateUserByUserId(properties, userId);

        properties.clear();
        properties.put("solved",
            statusService.countUsersAcceptedProblem(problemId));
        properties.put("tried",
            statusService.countUsersTiredProblem(problemId));
        problemService.updateProblemByProblemId(properties, problemId);
      } catch (Exception e) {
      }
    }
  }
}
