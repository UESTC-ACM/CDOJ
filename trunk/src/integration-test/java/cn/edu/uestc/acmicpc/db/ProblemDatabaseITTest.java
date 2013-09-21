package cn.edu.uestc.acmicpc.db;

import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.ioc.condition.ProblemConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Test cases for {@link Problem}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class ProblemDatabaseITTest implements ProblemConditionAware, ProblemDAOAware {

  @Autowired
  private IProblemDAO problemDAO;

  @Autowired
  private ProblemCondition problemCondition;

  @Before
  public void init() {
    problemCondition.clear();
  }

  @Test
  public void testStartIdAndEndId() throws AppException {
    problemCondition.setStartId(1);
    problemCondition.setEndId(5);
    Assert.assertEquals(Long.valueOf(5), problemDAO.count(problemCondition.getCondition()));
  }

  @Test
  public void testStartIdAndEndId_invalidParameter() throws AppException {
    problemCondition.setStartId(2);
    problemCondition.setEndId(1);
    Assert.assertEquals(Long.valueOf(0), problemDAO.count(problemCondition.getCondition()));
  }

  @Test
  public void testIsSpjQuery_notSpj() throws AppException {
    problemCondition.setStartId(1);
    problemCondition.setEndId(5);
    problemCondition.setIsSpj(false);
    Assert.assertEquals(Long.valueOf(3), problemDAO.count(problemCondition.getCondition()));
  }

  @Test
  public void testIsSpjQuery_spj() throws AppException {
    problemCondition.setStartId(1);
    problemCondition.setEndId(5);
    problemCondition.setIsSpj(true);
    Assert.assertEquals(Long.valueOf(2), problemDAO.count(problemCondition.getCondition()));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testProblemCondition_emptyTitle() throws AppException {
    problemCondition.setIsTitleEmpty(true);
    List<Problem> problems = (List<Problem>) problemDAO.findAll(problemCondition.getCondition());
    Assert.assertEquals(1, problems.size());
    Assert.assertEquals(Integer.valueOf(5), problems.get(0).getProblemId());
  }

  @Test
  public void testAddProblem() throws AppException {
    Problem problem = new Problem();
    Integer randomId = new Random().nextInt();
    problem.setTitle("Problem " + randomId.toString());
    problem.setDescription("Description " + randomId.toString());
    problem.setInput("Input " + randomId.toString());
    problem.setOutput("Output " + randomId.toString());
    problem.setSampleInput("Sample input " + randomId.toString());
    problem.setSampleOutput("Sample output " + randomId.toString());
    problem.setHint("Hint " + randomId.toString());
    problem.setSource("Source " + randomId.toString());
    problem.setIsSpj(new Random().nextBoolean());
    problem.setIsVisible(new Random().nextBoolean());
    problemDAO.add(problem);
    Assert.assertNotNull(problem.getProblemId());
  }

  @Override
  public void setProblemCondition(ProblemCondition problemCondition) {
    this.problemCondition = problemCondition;
  }

  @Override
  public ProblemCondition getProblemCondition() {
    return problemCondition;
  }

  @Override
  public void setProblemDAO(IProblemDAO problemDAO) {
    this.problemDAO = problemDAO;
  }
}
