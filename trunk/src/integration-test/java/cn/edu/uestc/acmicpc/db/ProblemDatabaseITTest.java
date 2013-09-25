package cn.edu.uestc.acmicpc.db;

import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.Entry;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Test cases for {@link Problem}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class ProblemDatabaseITTest {

  // TODO use problem service to query.

  @Autowired
  private IProblemDAO problemDAO;

  @Test
  public void testStartIdAndEndId() throws AppException {
    Condition condition = new Condition();
    condition.addEntry(Entry.of("problemId", ConditionType.GREATER_OR_EQUALS, 1));
    condition.addEntry(Entry.of("problemId", ConditionType.LESS_OR_EQUALS, 5));
    Assert.assertEquals(Long.valueOf(5), problemDAO.count(condition));
  }

  @Test
  public void testStartIdAndEndId_invalidParameter() throws AppException {
    Condition condition = new Condition();
    condition.addEntry(Entry.of("problemId", ConditionType.GREATER_OR_EQUALS, 2));
    condition.addEntry(Entry.of("problemId", ConditionType.LESS_OR_EQUALS, 1));
    Assert.assertEquals(Long.valueOf(0), problemDAO.count(condition));
  }

  @Test
  public void testIsSpjQuery_notSpj() throws AppException {
    Condition condition = new Condition();
    condition.addEntry(Entry.of("problemId", ConditionType.GREATER_OR_EQUALS, 1));
    condition.addEntry(Entry.of("problemId", ConditionType.LESS_OR_EQUALS, 5));
    condition.addEntry(Entry.of("isSpj", ConditionType.EQUALS, false));
    Assert.assertEquals(Long.valueOf(3), problemDAO.count(condition));
  }

  @Test
  public void testIsSpjQuery_spj() throws AppException {
    // TODO use problem service to query.
    Condition condition = new Condition();
    condition.addEntry(Entry.of("problemId", ConditionType.GREATER_OR_EQUALS, 1));
    condition.addEntry(Entry.of("problemId", ConditionType.LESS_OR_EQUALS, 5));
    condition.addEntry(Entry.of("isSpj", ConditionType.EQUALS, 1));
    Assert.assertEquals(Long.valueOf(2), problemDAO.count(condition));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testProblemCondition_emptyTitle() throws AppException {
    Condition condition = new Condition();
    condition.addEntry(Entry.of("title", ConditionType.STRING_EQUALS, ""));
    List<Problem> problems = (List<Problem>) problemDAO.findAll(condition);
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
}
