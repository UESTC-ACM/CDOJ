package cn.edu.uestc.acmicpc.db.dao;

import cn.edu.uestc.acmicpc.db.dao.iface.ContestProblemDao;
import cn.edu.uestc.acmicpc.db.dao.impl.ContestProblemDaoImpl;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Test case for {@link ContestProblemDaoImpl}.
 */
public class ContestProblemDAOITTest extends PersistenceITTest {

  @Autowired
  private ContestProblemDao contestProblemDao;

  @Test
  public void testAddContestProblem() throws AppException {
    ContestProblem contestProblem = new ContestProblem();
    contestProblem.setContestId(1);
    contestProblem.setProblemId(1);
    contestProblem.setOrder(0);
    contestProblemDao.addOrUpdate(contestProblem);
  }
}
