package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.IContestTeamInfoDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeamInfo.ContestTeamInfoDTO;
import cn.edu.uestc.acmicpc.service.iface.ContestTeamInfoService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ContestTeamInfoServiceImpl extends AbstractService implements ContestTeamInfoService {

  private final IContestTeamInfoDAO contestTeamInfoDAO;

  @Autowired
  public ContestTeamInfoServiceImpl(IContestTeamInfoDAO contestTeamInfoDAO) {
    this.contestTeamInfoDAO =  contestTeamInfoDAO;
  }

  @Override
  public IContestTeamInfoDAO getDAO() {
    return contestTeamInfoDAO;
  }

  @Override
  public ContestTeamInfoDTO getContestTeamInfoDTOByTeamId(Integer teamId) throws AppException {
    AppExceptionUtil.assertNotNull(teamId);
    return contestTeamInfoDAO.getDTOByUniqueField(ContestTeamInfoDTO.class,
        ContestTeamInfoDTO.builder(), "teamId", teamId);
  }
}
