package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.IContestUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestUser.ContestUserDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestUser;
import cn.edu.uestc.acmicpc.service.iface.ContestUserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Description
 */
@Service
@Primary
public class ContestUserServiceImpl extends AbstractService implements ContestUserService {
  private final IContestUserDAO contestUserDAO;

  @Autowired
  public ContestUserServiceImpl(IContestUserDAO contestUserDAO) {
    this.contestUserDAO = contestUserDAO;
  }

  @Override
  public IContestUserDAO getDAO() {
    return contestUserDAO;
  }

  @Override
  public Integer createNewContestUser(ContestUserDTO contestUserDTO) throws AppException {
    ContestUser contestUser = new ContestUser();
    if (contestUserDTO.getUserId() != null) {
      contestUser.setUserId(contestUserDTO.getUserId());
    }
    if (contestUserDTO.getStatus() != null) {
      contestUser.setStatus(contestUserDTO.getStatus());
    }
    if (contestUserDTO.getContestId() != null) {
      contestUser.setContestId(contestUserDTO.getContestId());
    }
    if (contestUserDTO.getComment() != null) {
      contestUser.setComment(contestUserDTO.getComment());
    }
    contestUserDAO.add(contestUser);
    return contestUser.getUserId();
  }

  @Override
  public void removeContestUsersByContestId(Integer contestId) throws AppException {
    StringBuilder hqlBuilder = new StringBuilder();
    hqlBuilder
        .append("delete from User where")
        .append(" userId in (")
        .append("   select userId from ContestUser where contestId = ")
        .append(contestId)
        .append(" )")
        .append(")");
    contestUserDAO.executeHQL(hqlBuilder.toString());
    contestUserDAO.deleteEntitiesByField("contestId", contestId.toString());
  }
}
