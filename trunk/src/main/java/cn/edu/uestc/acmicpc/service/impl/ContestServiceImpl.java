package cn.edu.uestc.acmicpc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestListDTO;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.view.PageInfo;

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
  public ContestDTO getContestDTO(
      Integer contestId)
      throws AppException {
    AppExceptionUtil.assertNotNull(contestId);
    return contestDAO.getDTOByUniqueField(ContestDTO.class,
        ContestDTO.builder(), "contestId", contestId);
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

}
