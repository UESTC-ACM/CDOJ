package cn.edu.uestc.acmicpc.testing;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.service.testing.ArticleProvider;
import cn.edu.uestc.acmicpc.service.testing.CodeProvider;
import cn.edu.uestc.acmicpc.service.testing.ContestProvider;
import cn.edu.uestc.acmicpc.service.testing.ProblemProvider;
import cn.edu.uestc.acmicpc.service.testing.StatusProvider;
import cn.edu.uestc.acmicpc.service.testing.TeamProvider;
import cn.edu.uestc.acmicpc.service.testing.TeamUserProvider;
import cn.edu.uestc.acmicpc.service.testing.TrainingPlatformInfoProvider;
import cn.edu.uestc.acmicpc.service.testing.TrainingProvider;
import cn.edu.uestc.acmicpc.service.testing.TrainingUserProvider;
import cn.edu.uestc.acmicpc.service.testing.UserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.BeforeMethod;

/**
 * Basic integration test using real java beans and real database.
 */
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class PersistenceITTest extends AbstractTransactionalTestNGSpringContextTests {

  @Autowired
  protected UserProvider userProvider;
  @Autowired
  protected ArticleProvider articleProvider;
  @Autowired
  protected ContestProvider contestProvider;
  @Autowired
  protected TeamUserProvider teamUserProvider;
  @Autowired
  protected TeamProvider teamProvider;
  @Autowired
  protected ProblemProvider problemProvider;
  @Autowired
  protected StatusProvider statusProvider;
  @Autowired
  protected CodeProvider codeProvider;
  @Autowired
  protected TrainingProvider trainingProvider;
  @Autowired
  protected TrainingUserProvider trainingUserProvider;
  @Autowired
  protected TrainingPlatformInfoProvider trainingPlatformInfoProvider;

  protected Integer testUserId;

  @BeforeMethod
  protected void beforeMethod() throws Exception {
    setUpDefaultUser();
    setUp();
  }

  public void setUp() throws Exception {
  }

  public void setUpDefaultUser() throws Exception {
    testUserId = userProvider.createUser().getUserId();
  }
}
