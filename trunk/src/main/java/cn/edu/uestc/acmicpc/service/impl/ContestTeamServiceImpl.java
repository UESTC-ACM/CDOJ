package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ContestTeamCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestTeamDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamReportDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestTeam;
import cn.edu.uestc.acmicpc.service.iface.ContestTeamService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.type.ContestRegistryStatusType;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description
 */
@Service
@Primary
public class ContestTeamServiceImpl extends AbstractService implements ContestTeamService {

  private IContestTeamDAO contestTeamDAO;

  @Autowired
  public ContestTeamServiceImpl(IContestTeamDAO contestTeamDAO) {
    this.contestTeamDAO = contestTeamDAO;
  }

  @Override
  public IContestTeamDAO getDAO() {
    return contestTeamDAO;
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
    Long count = contestTeamDAO.customCount("contestTeam.contestTeamId", hqlBuilder.toString());
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
    contestTeamDAO.add(contestTeam);
    return contestTeam.getContestTeamId();
  }

  @Override
  public Long count(ContestTeamCondition contestTeamCondition) throws AppException {
    return contestTeamDAO.count(contestTeamCondition.getCondition());
  }

  @Override
  public List<ContestTeamListDTO> getContestTeamList(ContestTeamCondition contestTeamCondition,
                                                     PageInfo pageInfo) throws AppException {
    Condition condition = contestTeamCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return contestTeamDAO.findAll(ContestTeamListDTO.class, ContestTeamListDTO.builder(),
        condition);
  }

  @Override
  public ContestTeamDTO getContestTeamDTO(Integer contestTeamId) throws AppException {
    AppExceptionUtil.assertNotNull(contestTeamId);
    return contestTeamDAO.getDTOByUniqueField(ContestTeamDTO.class, ContestTeamDTO.builder(),
        "contestTeamId", contestTeamId);
  }

  @Override
  public void updateContestTeam(ContestTeamDTO contestTeamDTO) throws AppException {
    AppExceptionUtil.assertNotNull(contestTeamDTO);
    AppExceptionUtil.assertNotNull(contestTeamDTO.getContestTeamId());
    ContestTeam contestTeam = contestTeamDAO.get(contestTeamDTO.getContestTeamId());
    AppExceptionUtil.assertNotNull(contestTeam.getContestTeamId());
    if (contestTeamDTO.getComment() != null) {
      contestTeam.setComment(contestTeamDTO.getComment());
    }
    if (contestTeamDTO.getStatus() != null) {
      contestTeam.setStatus(contestTeamDTO.getStatus());
    }
    contestTeamDAO.update(contestTeam);
  }

  @Override
  public List<ContestTeamReportDTO> exportContestTeamReport(Integer contestId) throws AppException {
    ContestTeamCondition contestTeamCondition = new ContestTeamCondition();
    contestTeamCondition.contestId = contestId;
    return contestTeamDAO.findAll(ContestTeamReportDTO.class,
        ContestTeamReportDTO.builder(), contestTeamCondition.getCondition());
  }

  @Override
  public Integer getTeamIdByUserIdAndContestId(Integer userId, Integer contestId) throws AppException {
    StringBuilder hqlBuilder = new StringBuilder();
    hqlBuilder.append("select contestTeam.teamId from ContestTeam contestTeam, TeamUser teamUser where")
        // Contest id
        .append(" contestTeam.contestId = ").append(contestId)
        // Team should be accepted
        .append(" and contestTeam.status = ").append(ContestRegistryStatusType.ACCEPTED.ordinal())
        .append(" and contestTeam.teamId = teamUser.teamId")
            // User id
        .append(" and teamUser.userId = ").append(userId)
        // User should be allowed
        .append(" and teamUser.allow = true");
    List<Integer> result = (List<Integer>) contestTeamDAO.findAll(hqlBuilder.toString());
    return result.size() > 0 ? result.get(0) : null;
  }
}
