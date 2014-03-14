package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestTeamCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamListDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestTeam;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import java.util.List;

/**
 * Description
 */
public interface ContestTeamService extends DatabaseService<ContestTeam, Integer> {

  public Boolean whetherUserHasBeenRegistered(Integer userId, Integer contestId) throws AppException;

  public Integer createNewContestTeam(Integer contestId, Integer teamId) throws AppException;

  public Long count(ContestTeamCondition contestTeamCondition) throws AppException;
  public List<ContestTeamListDTO> getContestTeamList(ContestTeamCondition contestTeamCondition,
                                                     PageInfo pageInfo) throws AppException;

  public ContestTeamDTO getContestTeamDTO(Integer contestTeamId) throws AppException;

  public void updateContestTeam(ContestTeamDTO contestTeamDTO) throws AppException;
}
