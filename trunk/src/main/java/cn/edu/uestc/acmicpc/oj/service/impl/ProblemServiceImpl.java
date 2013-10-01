package cn.edu.uestc.acmicpc.oj.service.impl;

import java.util.ArrayList;
import java.util.List;

import javassist.compiler.ast.NewExpr;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.opensymphony.module.sitemesh.Page;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.impl.ProblemDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemListDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.oj.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.service.impl.AbstractService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;

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
    Problem problem  = problemDAO.get(problemId);
    AppExceptionUtil.assertNotNull(problem);
    return getProblemDTOByProblem(problem);
  }

  @Override
  public ProblemDTO getProblemDTOByProblem(Problem problem) throws AppException {
    AppExceptionUtil.assertNotNull(problem);
    return ProblemDTO.builder()
           .setProblemId(problem.getProblemId())
           .setTitle(problem.getTitle())
           .setDescription(problem.getDescription())
           .setInput(problem.getInput())
           .setOutput(problem.getOutput())
           .setSampleInput(problem.getSampleInput())
           .setSampleOutput(problem.getSampleOutput())
           .setHint(problem.getHint())  
           .setSource(problem.getSource())
           .setTimeLimit(problem.getTimeLimit())
           .setMemoryLimit(problem.getMemoryLimit())
           .setSolved(problem.getSolved())
           .setTried(problem.getTried())
           .setIsSpj(problem.getIsSpj())  
           .setJavaTimeLimit(problem.getJavaTimeLimit())
           .setJavaMemoryLimit(problem.getJavaMemoryLimit())
           .build();
            
  }

  @Override
  public ProblemListDTO getProblemListDTOByProblem(Problem problem) 
      throws AppException {
    AppExceptionUtil.assertNotNull(problem);
    return ProblemListDTO.builder()
           .setProblemId(problem.getProblemId())
           .setTitle(problem.getTitle())
           .setSource(problem.getSource())
           .setSolved(problem.getSolved())
           .setTried(problem.getTried())
           .setIsSpj(problem.getIsSpj())
           .setIsVisible(problem.getIsVisible())
           .setDifficulty(problem.getDifficulty())
           .build();
    
  }
  
  @Override
  public Long count(Condition condition) throws AppException {
    return problemDAO.count(condition);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ProblemListDTO> GetProblemListDTOList(ProblemCondition problemCondition, 
      PageInfo pageInfo) throws AppException{
    problemCondition.currentPage = pageInfo.getCurrentPage();
    problemCondition.countPerPage = Global.RECORD_PER_PAGE;
    return problemDAO.findAll(ProblemListDTO.class, ProblemListDTO.builder(), 
                              problemCondition.getCondition());
  }
}
