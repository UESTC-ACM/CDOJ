package cn.edu.uestc.acmicpc.judge.entity;

import cn.edu.uestc.acmicpc.db.dto.impl.StatusDto;
import cn.edu.uestc.acmicpc.service.iface.CompileInfoService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * Judge item for single problem.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JudgeItem {

  private StatusDto status;
  private String compileInfo;
  private String SourceNameWithoutExtension;
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
    this.SourceNameWithoutExtension = UUID.randomUUID().toString();
  }

  public void setStatus(StatusDto status) {
    this.status = status;
  }

  public StatusDto getStatus() {
    return status;
  }

  public void setCompileInfo(String compileInfo) {
    this.compileInfo = compileInfo;
  }

  public String getCompileInfo() {
    return compileInfo;
  }

  public void setSourceNameWithoutExtension(String SourceNameWithoutExtension){
     this.SourceNameWithoutExtension = SourceNameWithoutExtension;
  }

  public String getSourceNameWithoutExtension() {
    return SourceNameWithoutExtension;
  }

  public String getSourceName() {
    return getSourceNameWithoutExtension() + status.getExtension();
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
        if (compileInfo.length() > 30000) {
          compileInfo = compileInfo.substring(0, 30000);
        }
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
      statusService.updateStatus(status);
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
            statusService.countProblemsThatUserSolved(userId, false));
        properties.put("tried",
            statusService.countProblemsThatUserTried(userId, false));
        userService.updateUserByUserId(properties, userId);

        properties.clear();
        properties.put("solved",
            statusService.countUsersThatSolvedThisProblem(problemId));
        properties.put("tried",
            statusService.countUsersThatTriedThisProblem(problemId));
        problemService.updateProblemByProblemId(properties, problemId);
      } catch (Exception e) {
      }
    }
  }
}
