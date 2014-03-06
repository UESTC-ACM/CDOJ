package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.Entry;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

/**
 * Test cases for {@link Problem}.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class ProblemDatabaseITTest extends AbstractTestNGSpringContextTests {

  // TODO(fish): use problem service to query.

  @Autowired
  private IProblemDAO problemDAO;

  @Test
  public void testStartIdAndEndId() throws AppException {
    Condition condition = new Condition();
    condition.addEntry(Entry.of("problemId", ConditionType.GREATER_OR_EQUALS, 1));
    condition.addEntry(Entry.of("problemId", ConditionType.LESS_OR_EQUALS, 5));
    Assert.assertEquals(problemDAO.count(condition), Long.valueOf(5));
  }

  @Test
  public void testStartIdAndEndId_invalidParameter() throws AppException {
    Condition condition = new Condition();
    condition.addEntry(Entry.of("problemId", ConditionType.GREATER_OR_EQUALS, 2));
    condition.addEntry(Entry.of("problemId", ConditionType.LESS_OR_EQUALS, 1));
    Assert.assertEquals(problemDAO.count(condition), Long.valueOf(0));
  }

  @Test
  public void testIsSpjQuery_notSpj() throws AppException {
    Condition condition = new Condition();
    condition.addEntry(Entry.of("problemId", ConditionType.GREATER_OR_EQUALS, 1));
    condition.addEntry(Entry.of("problemId", ConditionType.LESS_OR_EQUALS, 5));
    condition.addEntry(Entry.of("isSpj", ConditionType.EQUALS, false));
    Assert.assertEquals(problemDAO.count(condition), Long.valueOf(3));
  }

  @Test
  public void testIsSpjQuery_spj() throws AppException {
    ProblemCondition condition = new ProblemCondition();
    condition.startId = 1;
    condition.endId = 5;
    condition.isSpj = true;
    Assert.assertEquals(problemDAO.count(condition.getCondition()), Long.valueOf(2));
  }

  @SuppressWarnings({"unchecked", "deprecation"})
  @Test
  public void testProblemCondition_emptyTitle() throws AppException {
    Condition condition = new Condition();
    condition.addEntry(Entry.of("title", ConditionType.STRING_EQUALS, ""));
    List<ProblemDTO> problems =
        problemDAO.findAll(ProblemDTO.class, ProblemDTO.builder(), condition);
    Assert.assertEquals(problems.size(), 1);
    Assert.assertEquals(problems.get(0).getProblemId(), Integer.valueOf(5));
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
