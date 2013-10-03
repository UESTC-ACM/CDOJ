package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemListDTO;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.view.PageInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation for {@link ProblemService}.
 */
@Service
@Primary
public class ProblemServiceImpl extends AbstractService implements ProblemService {

  private static final Logger log = Logger.getLogger(ProblemServiceImpl.class);
  private final IProblemDAO problemDAO;

  @Autowired
  public ProblemServiceImpl(IProblemDAO problemDAO) {
    this.problemDAO = problemDAO;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Integer> getAllVisibleProblemIds() throws AppException {
    ProblemCondition problemCondition = new ProblemCondition();
    // TODO(mzry1992): set this is problem condition.
    problemCondition.isVisible = true;
    // TODO(mzry1992): please test for this statement.
    return (List<Integer>) problemDAO.findAll("problemId", problemCondition.getCondition());
  }

  @Override
  public IProblemDAO getDAO() {
    return problemDAO;
  }

  @Override
  public ProblemDTO getProblemDTOByProblemId(Integer problemId) throws AppException {
    AppExceptionUtil.assertNotNull(problemId);
    return problemDAO.getDTOByUniqueField(ProblemDTO.class, ProblemDTO.builder(), "problemId",
        problemId);
  }

  @Override
  public Long count(Condition condition) throws AppException {
    return problemDAO.count(condition);
  }

  
  @Override
  public List<ProblemListDTO> GetProblemListDTOList(ProblemCondition problemCondition, 
      PageInfo pageInfo) throws AppException{
    problemCondition.currentPage = pageInfo.getCurrentPage();
    problemCondition.countPerPage = Global.RECORD_PER_PAGE;
    return problemDAO.findAll(ProblemListDTO.class, ProblemListDTO.builder(), 
                              problemCondition.getCondition());
  }
  
}
