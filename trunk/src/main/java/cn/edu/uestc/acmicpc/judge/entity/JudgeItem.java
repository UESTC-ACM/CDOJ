package cn.edu.uestc.acmicpc.judge.entity;

import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusForJudgeDTO;
import cn.edu.uestc.acmicpc.service.iface.CompileInfoService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Judge item for single problem.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JudgeItem {

  private StatusForJudgeDTO status;
  private String compileInfo;
  private final CompileInfoService compileInfoService;
  private final StatusService statusService;
  private final UserService userService;
  private final ProblemService problemService;

  @Autowired
  public JudgeItem(
      CompileInfoService compileInfoService,
      StatusService statusService,
      UserService userService,
      ProblemService problemService) {
    this.compileInfoService = compileInfoService;
    this.statusService = statusService;
    this.userService = userService;
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
   * @param updateStatus if set {@code true}, update status' information.
   */
  public void update(boolean updateStatus) {
    try {
      if (compileInfo != null) {
        // Compile error!
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
            statusService.countProblemsUserTried(userId));
        userService.updateUserByUserId(properties, userId);

        properties.clear();
        properties.put("solved",
            statusService.countUsersAcceptedProblem(problemId));
        properties.put("tried",
            statusService.countUsersTriedProblem(problemId));
        problemService.updateProblemByProblemId(properties, problemId);
      } catch (Exception e) {
      }
    }
  }
}
