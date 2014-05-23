package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.ContestProblemDao;
import cn.edu.uestc.acmicpc.db.dao.impl.ContestProblemDaoImpl;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

/**
 * Test case for {@link ContestProblemDaoImpl}.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class ContestProblemDAOITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private ContestProblemDao contestProblemDao;

  @Test
  public void testAddContestProblem() throws AppException {
    ContestProblem contestProblem = new ContestProblem();
    contestProblem.setContestId(1);
    contestProblem.setProblemId(1);
    contestProblem.setOrder(0);
    contestProblemDao.add(contestProblem);
  }
}
