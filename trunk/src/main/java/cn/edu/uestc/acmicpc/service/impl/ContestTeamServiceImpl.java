package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ContestTeamCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ContestTeamDao;
import cn.edu.uestc.acmicpc.db.dto.impl.contestteam.ContestTeamDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contestteam.ContestTeamListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contestteam.ContestTeamReportDto;
import cn.edu.uestc.acmicpc.db.entity.ContestTeam;
import cn.edu.uestc.acmicpc.service.iface.ContestTeamService;
import cn.edu.uestc.acmicpc.util.enums.ContestRegistryStatusType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description
 */
@SuppressWarnings("deprecation")
@Service
@Primary
@Transactional(rollbackFor = Exception.class)
public class ContestTeamServiceImpl extends AbstractService implements ContestTeamService {

  private final ContestTeamDao contestTeamDao;

  @Autowired
  public ContestTeamServiceImpl(ContestTeamDao contestTeamDao) {
    this.contestTeamDao = contestTeamDao;
  }

  @Override
  public Boolean checkUserCanRegisterInContest(Integer userId,
      Integer contestId) throws AppException {
    StringBuilder hqlBuilder = new StringBuilder();
    hqlBuilder.append("from ContestTeam contestTeam, TeamUser teamUser where")
        .append(" contestTeam.teamId = teamUser.teamId")
        .append(" and contestTeam.contestId = ").append(contestId)
        .append(" and contestTeam.status != ").append(ContestRegistryStatusType.REFUSED.ordinal())
        .append(" and teamUser.userId = ").append(userId)
        .append(" and teamUser.allow = 1");
    Long count = contestTeamDao.customCount("contestTeam.contestTeamId", hqlBuilder.toString());
    return count == 0;
  }

  @Override
  public Integer createNewContestTeam(Integer contestId, Integer teamId) throws AppException {
    AppExceptionUtil.assertNotNull(contestId);
    AppExceptionUtil.assertNotNull(teamId);
    ContestTeam contestTeam = new ContestTeam();
    contestTeam.setTeamId(teamId);
    contestTeam.setContestId(contestId);
    contestTeam.setStatus(ContestRegistryStatusType.PENDING.ordinal());
    contestTeam.setComment("");
    contestTeamDao.addOrUpdate(contestTeam);
    return contestTeam.getContestTeamId();
  }

  @Override
  public Long count(ContestTeamCondition contestTeamCondition) throws AppException {
    return contestTeamDao.count(contestTeamCondition.getCondition());
  }

  @Override
  public List<ContestTeamListDto> getContestTeamList(ContestTeamCondition contestTeamCondition,
      PageInfo pageInfo) throws AppException {
    Condition condition = contestTeamCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return contestTeamDao.findAll(ContestTeamListDto.class, ContestTeamListDto.builder(),
        condition);
  }

  @Override
  public ContestTeamDto getContestTeamDto(Integer contestTeamId) throws AppException {
    AppExceptionUtil.assertNotNull(contestTeamId);
    return contestTeamDao.getDtoByUniqueField(ContestTeamDto.class, ContestTeamDto.builder(),
        "contestTeamId", contestTeamId);
  }

  @Override
  public void updateContestTeam(ContestTeamDto contestTeamDto) throws AppException {
    AppExceptionUtil.assertNotNull(contestTeamDto);
    AppExceptionUtil.assertNotNull(contestTeamDto.getContestTeamId());
    ContestTeam contestTeam = contestTeamDao.get(contestTeamDto.getContestTeamId());
    AppExceptionUtil.assertNotNull(contestTeam.getContestTeamId());
    if (contestTeamDto.getComment() != null) {
      contestTeam.setComment(contestTeamDto.getComment());
    }
    if (contestTeamDto.getStatus() != null) {
      contestTeam.setStatus(contestTeamDto.getStatus());
    }
    contestTeamDao.addOrUpdate(contestTeam);
  }

  @Override
  public List<ContestTeamReportDto> exportContestTeamReport(Integer contestId) throws AppException {
    ContestTeamCondition contestTeamCondition = new ContestTeamCondition();
    contestTeamCondition.contestId = contestId;
    return contestTeamDao.findAll(ContestTeamReportDto.class,
        ContestTeamReportDto.builder(), contestTeamCondition.getCondition());
  }

  @SuppressWarnings("unchecked")
  @Override
  public Integer getTeamIdByUserIdAndContestId(Integer userId, Integer contestId)
      throws AppException {
    StringBuilder hqlBuilder = new StringBuilder();
    hqlBuilder
        .append("select contestTeam.teamId from ContestTeam contestTeam, TeamUser teamUser where")
            // Contest id
        .append(" contestTeam.contestId = ").append(contestId)
        // Team should be accepted
        .append(" and contestTeam.status = ").append(ContestRegistryStatusType.ACCEPTED.ordinal())
        .append(" and contestTeam.teamId = teamUser.teamId")
            // User id
        .append(" and teamUser.userId = ").append(userId)
        // User should be allowed
        .append(" and teamUser.allow = true");
    List<Integer> result = (List<Integer>) contestTeamDao.findAll(hqlBuilder.toString());
    return result.size() > 0 ? result.get(0) : null;
  }
}
