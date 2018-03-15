package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ProblemDao;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDto;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemListDto;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.util.enums.ProblemType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for {@link ProblemService}.
 */
@SuppressWarnings("deprecation")
@Service
@Transactional(rollbackFor = Exception.class)
public class ProblemServiceImpl extends AbstractService implements ProblemService {

  private final ProblemDao problemDao;

  @Autowired
  public ProblemServiceImpl(ProblemDao problemDao) {
    this.problemDao = problemDao;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Integer> getAllProblemIds(boolean isVisible, ProblemType problemType) throws AppException {
    ProblemCondition problemCondition = new ProblemCondition();
    problemCondition.isVisible = isVisible;
    problemCondition.type = problemType;
    return (List<Integer>) problemDao.findAll("problemId",
        problemCondition.getCondition());
  }

  @Override
  public ProblemDto getProblemDtoByProblemId(Integer problemId)
      throws AppException {
    AppExceptionUtil.assertNotNull(problemId);
    return problemDao.getDtoByUniqueField(ProblemDto.class,
        ProblemDto.builder(), "problemId",
        problemId);
  }

  @Override
  public Long count(ProblemCondition condition) throws AppException {
    return problemDao.count(condition.getCondition());
  }

  @Override
  public List<ProblemListDto> getProblemListDtoList(
      ProblemCondition problemCondition,
      PageInfo pageInfo) throws AppException {
    Condition condition = problemCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return problemDao.findAll(ProblemListDto.class, ProblemListDto.builder(),
        condition);
  }

  @Override
  public void operator(String field, String ids, String sValue)
      throws AppException {
    Object value;
    if (field.equals("isVisible")) {
      value = Boolean.valueOf(sValue);
    } else if (field.equals("type")) {
      if (sValue.equals(ProblemType.INTERNAL.name())) {
        value = ProblemType.INTERNAL.ordinal();
      } else {
        value = ProblemType.NORMAL.ordinal();
      }
    } else {
      value = sValue;
    }
    problemDao.updateEntitiesByField(field, value, "problemId", ids);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Object> query(String field, String ids) throws AppException {
    ProblemCondition problemCondition = new ProblemCondition();
    Condition condition = problemCondition.getCondition();
    condition.addEntry("problemId", Condition.ConditionType.IN, ids);
    return (List<Object>) problemDao.findAll(field, condition);
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
    problem.setMemoryLimit(64);
    problem.setSolved(0);
    problem.setTried(0);
    problem.setIsSpj(false);
    problem.setIsVisible(false);
    problem.setOutputLimit(64);
    problem.setDataCount(0);
    problem.setDifficulty(1);
    problemDao.addOrUpdate(problem);
    return problem.getProblemId();
  }

  private void updateProblemByProblemDto(Problem problem, ProblemDto problemDto) {
    if (problemDto.getTitle() != null) {
      problem.setTitle(problemDto.getTitle());
    }
    if (problemDto.getDescription() != null) {
      problem.setDescription(problemDto.getDescription());
    }
    if (problemDto.getInput() != null) {
      problem.setInput(problemDto.getInput());
    }
    if (problemDto.getOutput() != null) {
      problem.setOutput(problemDto.getOutput());
    }
    if (problemDto.getSampleInput() != null) {
      problem.setSampleInput(problemDto.getSampleInput());
    }
    if (problemDto.getSampleOutput() != null) {
      problem.setSampleOutput(problemDto.getSampleOutput());
    }
    if (problemDto.getHint() != null) {
      problem.setHint(problemDto.getHint());
    }
    if (problemDto.getSource() != null) {
      problem.setSource(problemDto.getSource());
    }
    if (problemDto.getTimeLimit() != null) {
      problem.setTimeLimit(problemDto.getTimeLimit());
    }
    if (problemDto.getMemoryLimit() != null) {
      problem.setMemoryLimit(problemDto.getMemoryLimit());
    }
    if (problemDto.getSolved() != null) {
      problem.setSolved(problemDto.getSolved());
    }
    if (problemDto.getTried() != null) {
      problem.setTried(problemDto.getTried());
    }
    if (problemDto.getIsSpj() != null) {
      problem.setIsSpj(problemDto.getIsSpj());
    }
    if (problemDto.getIsVisible() != null) {
      problem.setIsVisible(problemDto.getIsVisible());
    }
    if (problemDto.getOutputLimit() != null) {
      problem.setOutputLimit(problemDto.getOutputLimit());
    }
    if (problemDto.getDataCount() != null) {
      problem.setDataCount(problemDto.getDataCount());
    }
    if (problemDto.getDifficulty() != null) {
      problem.setDifficulty(problemDto.getDifficulty());
    }
  }

  @Override
  public void updateProblem(ProblemDto problemDto) throws AppException {
    Problem problem = problemDao.get(problemDto.getProblemId());
    AppExceptionUtil.assertNotNull(problem);
    AppExceptionUtil.assertNotNull(problem.getProblemId());
    updateProblemByProblemDto(problem, problemDto);
    problemDao.addOrUpdate(problem);
  }

  @Override
  public ArrayList<ProblemDto> createProblems(ArrayList<ProblemDto> problemDtos)
      throws AppException {
    for (ProblemDto problemDto : problemDtos) {
      Integer problemId = problemDto.getProblemId();
      if (problemId != null) {
        if (!checkProblemExists(problemId)) {
          throw new AppException("No problem #" + problemId + ".");
        }
      } else {
        problemId = createNewProblem();
        problemDto.setProblemId(problemId);
        updateProblem(problemDto);
      }
    }
    return problemDtos;
  }

  @Override
  public Boolean checkProblemExists(Integer problemId) throws AppException {
    AppExceptionUtil.assertNotNull(problemId);
    ProblemCondition problemCondition = new ProblemCondition();
    problemCondition.startId = problemId;
    problemCondition.endId = problemId;
    return problemDao.count(problemCondition.getCondition()) == 1;
  }

  @Override
  public void updateProblemByProblemId(Map<String, Object> properties,
      Integer problemId) throws AppException {
    problemDao.updateEntitiesByField(properties, "problemId",
        problemId.toString());
  }
}
