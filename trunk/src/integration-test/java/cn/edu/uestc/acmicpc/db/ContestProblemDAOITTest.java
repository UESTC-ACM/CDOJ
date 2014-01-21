package cn.edu.uestc.acmicpc.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.impl.ContestProblemDAO;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Test case for {@link ContestProblemDAO}.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class ContestProblemDAOITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private IProblemDAO problemDAO;

  @Autowired
  private IContestDAO contestDAO;

  @Autowired
  private IContestProblemDAO contestProblemDAO;

  @Test
  public void testAddContestProblem() throws AppException {
    ContestProblem contestProblem = new ContestProblem();
    contestProblem.setContestId(1);
    contestProblem.setProblemId(1);
    contestProblem.setOrder(0);
    contestProblemDAO.add(contestProblem);
  }
}
