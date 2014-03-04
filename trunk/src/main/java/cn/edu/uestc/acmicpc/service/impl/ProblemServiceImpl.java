package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemListDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Implementation for {@link ProblemService}.
 */
@Service
public class ProblemServiceImpl extends AbstractService implements
    ProblemService {

  private final IProblemDAO problemDAO;

  @Autowired
  public ProblemServiceImpl(IProblemDAO problemDAO) {
    this.problemDAO = problemDAO;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Integer> getAllVisibleProblemIds() throws AppException {
    ProblemCondition problemCondition = new ProblemCondition();
    problemCondition.isVisible = true;
    return (List<Integer>) problemDAO.findAll("problemId",
        problemCondition.getCondition());
  }

  @Override
  public IProblemDAO getDAO() {
    return problemDAO;
  }

  @Override
  public ProblemDTO getProblemDTOByProblemId(Integer problemId)
      throws AppException {
    AppExceptionUtil.assertNotNull(problemId);
    return problemDAO.getDTOByUniqueField(ProblemDTO.class,
        ProblemDTO.builder(), "problemId",
        problemId);
  }

  @Override
  public Long count(ProblemCondition condition) throws AppException {
    return problemDAO.count(condition.getCondition());
  }

  @Override
  public List<ProblemListDTO> getProblemListDTOList(
      ProblemCondition problemCondition,
      PageInfo pageInfo) throws AppException {
    Condition condition = problemCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return problemDAO.findAll(ProblemListDTO.class, ProblemListDTO.builder(),
        condition);
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
    problemDAO.updateEntitiesByField(field, value, "problemId", ids);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object> query(String field, String ids) throws AppException {
    ProblemCondition problemCondition = new ProblemCondition();
    Condition condition = problemCondition.getCondition();
    condition.addEntry("problemId", Condition.ConditionType.IN, ids);
    return (List<Object>) problemDAO.findAll(field, condition);
  }

  @Override
  public Integer createNewProblem() throws AppException {
    Problem problem = new Problem();
    problem.setProblemId(null);
    problem.setTitle("");
    problem.setDescription("");
    problem.setInput("");
    problem.setOutput("");
    problem.setSampleInput("");
    problem.setSampleOutput("");
    problem.setHint("");
    problem.setSource("");
    problem.setTimeLimit(1000);
    problem.setMemoryLimit(65535);
    problem.setSolved(0);
    problem.setTried(0);
    problem.setIsSpj(false);
    problem.setIsVisible(false);
    problem.setOutputLimit(8192);
    problem.setJavaTimeLimit(3000);
    problem.setJavaMemoryLimit(65535);
    problem.setDataCount(0);
    problem.setDifficulty(1);
    problemDAO.add(problem);
    return problem.getProblemId();
  }

  private void updateProblemByProblemDTO(Problem problem, ProblemDTO problemDTO) {
    if (problemDTO.getTitle() != null)
      problem.setTitle(problemDTO.getTitle());
    if (problemDTO.getDescription() != null)
      problem.setDescription(problemDTO.getDescription());
    if (problemDTO.getInput() != null)
      problem.setInput(problemDTO.getInput());
    if (problemDTO.getOutput() != null)
      problem.setOutput(problemDTO.getOutput());
    if (problemDTO.getSampleInput() != null)
      problem.setSampleInput(problemDTO.getSampleInput());
    if (problemDTO.getSampleOutput() != null)
      problem.setSampleOutput(problemDTO.getSampleOutput());
    if (problemDTO.getHint() != null)
      problem.setHint(problemDTO.getHint());
    if (problemDTO.getSource() != null)
      problem.setSource(problemDTO.getSource());
    if (problemDTO.getTimeLimit() != null)
      problem.setTimeLimit(problemDTO.getTimeLimit());
    if (problemDTO.getMemoryLimit() != null)
      problem.setMemoryLimit(problemDTO.getMemoryLimit());
    if (problemDTO.getSolved() != null)
      problem.setSolved(problemDTO.getSolved());
    if (problemDTO.getTried() != null)
      problem.setTried(problemDTO.getTried());
    if (problemDTO.getIsSpj() != null)
      problem.setIsSpj(problemDTO.getIsSpj());
    if (problemDTO.getIsVisible() != null)
      problem.setIsVisible(problemDTO.getIsVisible());
    if (problemDTO.getOutputLimit() != null)
      problem.setOutputLimit(problemDTO.getOutputLimit());
    if (problemDTO.getJavaTimeLimit() != null)
      problem.setJavaTimeLimit(problemDTO.getJavaTimeLimit());
    if (problemDTO.getJavaMemoryLimit() != null)
      problem.setJavaMemoryLimit(problemDTO.getJavaMemoryLimit());
    if (problemDTO.getDataCount() != null)
      problem.setDataCount(problemDTO.getDataCount());
    if (problemDTO.getDifficulty() != null)
      problem.setDifficulty(problemDTO.getDifficulty());
  }

  @Override
  public void updateProblem(ProblemDTO problemDTO) throws AppException {
    Problem problem = problemDAO.get(problemDTO.getProblemId());
    AppExceptionUtil.assertNotNull(problem);
    AppExceptionUtil.assertNotNull(problem.getProblemId());
    updateProblemByProblemDTO(problem, problemDTO);
    problemDAO.update(problem);
  }

  @Override
  public ArrayList<ProblemDTO> createProblems(ArrayList<ProblemDTO> problemDTOs) throws AppException {
    for (ProblemDTO problemDTO : problemDTOs) {
      Integer problemId = problemDTO.getProblemId();
      if (problemId != null) {
        if (!checkProblemExists(problemId)) {
          throw new AppException("No problem #" + problemId + ".");
        }
      } else {
        problemId = createNewProblem();
        problemDTO.setProblemId(problemId);
        updateProblem(problemDTO);
      }
    }
    return problemDTOs;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Boolean checkProblemExists(Integer problemId) throws AppException {
    AppExceptionUtil.assertNotNull(problemId);
    ProblemCondition problemCondition = new ProblemCondition();
    problemCondition.startId = problemId;
    problemCondition.endId = problemId;
    return problemDAO.count(problemCondition.getCondition()) == 1;
  }

  @Override
  public void updateProblemByProblemId(Map<String, Object> properties,
                                       Integer problemId) throws AppException {
    problemDAO.updateEntitiesByField(properties, "problemId",
        problemId.toString());
  }
}
