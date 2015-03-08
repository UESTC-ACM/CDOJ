package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.Entry;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ProblemDao;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

/**
 * Test cases for {@link Problem}.
 */
@SuppressWarnings("deprecation")
public class ProblemDatabaseITTest extends PersistenceITTest {

  // TODO(fish): use problem service to query.

  @Autowired
  private ProblemDao problemDao;

  @Test
  public void testStartIdAndEndId() throws AppException {
    Condition condition = new Condition();
    condition.addEntry(Entry.of("problemId", ConditionType.GREATER_OR_EQUALS, 1));
    condition.addEntry(Entry.of("problemId", ConditionType.LESS_OR_EQUALS, 5));
    Assert.assertEquals(problemDao.count(condition), Long.valueOf(5));
  }

  @Test
  public void testStartIdAndEndId_invalidParameter() throws AppException {
    Condition condition = new Condition();
    condition.addEntry(Entry.of("problemId", ConditionType.GREATER_OR_EQUALS, 2));
    condition.addEntry(Entry.of("problemId", ConditionType.LESS_OR_EQUALS, 1));
    Assert.assertEquals(problemDao.count(condition), Long.valueOf(0));
  }

  @Test
  public void testIsSpjQuery_notSpj() throws AppException {
    Condition condition = new Condition();
    condition.addEntry(Entry.of("problemId", ConditionType.GREATER_OR_EQUALS, 1));
    condition.addEntry(Entry.of("problemId", ConditionType.LESS_OR_EQUALS, 5));
    condition.addEntry(Entry.of("isSpj", ConditionType.EQUALS, false));
    Assert.assertEquals(problemDao.count(condition), Long.valueOf(3));
  }

  @Test
  public void testIsSpjQuery_spj() throws AppException {
    ProblemCondition condition = new ProblemCondition();
    condition.startId = 1;
    condition.endId = 5;
    condition.isSpj = true;
    Assert.assertEquals(problemDao.count(condition.getCondition()), Long.valueOf(2));
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
    problemDao.addOrUpdate(problem);
    Assert.assertNotNull(problem.getProblemId());
  }
}
