package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestShowDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Primary
public class ContestServiceImpl extends AbstractService implements
    ContestService {

  private final IContestDAO contestDAO;

  @Autowired
  public ContestServiceImpl(IContestDAO contestDAO) {
    this.contestDAO = contestDAO;
  }

  @Override
  public IContestDAO getDAO() {
    return contestDAO;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Integer> getAllVisibleContestIds() throws AppException {
    ContestCondition contestCondition = new ContestCondition();
    contestCondition.isVisible = true;
    return (List<Integer>) contestDAO.findAll("contestId",
        contestCondition.getCondition());
  }

  @Override
  public ContestDTO getContestDTOByContestId(
      Integer contestId)
      throws AppException {
    AppExceptionUtil.assertNotNull(contestId);
    return contestDAO.getDTOByUniqueField(ContestDTO.class,
        ContestDTO.builder(), "contestId", contestId);
  }

  @Override
  public ContestShowDTO getContestShowDTOByContestId(Integer contestId) throws AppException {
    AppExceptionUtil.assertNotNull(contestId);
    return contestDAO.getDTOByUniqueField(ContestShowDTO.class,
        ContestShowDTO.builder(), "contestId", contestId);
  }

  @Override
  public Boolean checkContestExists(Integer contestId) throws AppException {
    ContestCondition contestCondition = new ContestCondition();
    contestCondition.startId = contestId;
    contestCondition.endId = contestId;
    return contestDAO.count(contestCondition.getCondition()) == 1;
  }

  private void updateContestByContestDTO(Contest contest, ContestDTO contestDTO) {
    if (contestDTO.getDescription() != null) {
      contest.setDescription(contestDTO.getDescription());
    }
    if (contestDTO.getIsVisible() != null) {
      contest.setIsVisible(contestDTO.getIsVisible());
    }
    if (contestDTO.getLength() != null) {
      contest.setLength(contestDTO.getLength());
    }
    if (contestDTO.getTime() != null) {
      contest.setTime(contestDTO.getTime());
    }
    if (contestDTO.getTitle() != null) {
      contest.setTitle(contestDTO.getTitle());
    }
    if (contestDTO.getType() != null) {
      contest.setType(contestDTO.getType());
    }
    if (contestDTO.getPassword() != null) {
      contest.setPassword(contestDTO.getPassword());
    }
    if (contestDTO.getParentId() != null) {
      contest.setParentId(contestDTO.getParentId());
    }
  }

  @Override
  public void updateContest(ContestDTO contestDTO) throws AppException {
    Contest contest = contestDAO.get(contestDTO.getContestId());
    AppExceptionUtil.assertNotNull(contest);
    AppExceptionUtil.assertNotNull(contest.getContestId());
    updateContestByContestDTO(contest, contestDTO);
    contestDAO.update(contest);
  }

  @Override
  public Long count(ContestCondition contestCondition) throws AppException {
    return contestDAO.count(contestCondition.getCondition());
  }

  @Override
  public List<ContestListDTO> getContestListDTOList(
      ContestCondition contestCondition,
      PageInfo pageInfo) throws AppException {
    contestCondition.currentPage = pageInfo.getCurrentPage();
    contestCondition.countPerPage = Global.RECORD_PER_PAGE;
    return contestDAO.findAll(ContestListDTO.class, ContestListDTO.builder(),
        contestCondition.getCondition());
  }

  @Override
  public void operator(String field, String ids, String sValue)
      throws AppException {
    Object value;
    if (field.equals("isVisible")) {
      value = Boolean.valueOf(sValue);
    } else {
      value = sValue;
    }
    contestDAO.updateEntitiesByField(field, value, "contestId", ids);
  }

  @Override
  public Integer createNewContest() throws AppException {
    Contest contest = new Contest();
    contestDAO.add(contest);
    return contest.getContestId();
  }

}
