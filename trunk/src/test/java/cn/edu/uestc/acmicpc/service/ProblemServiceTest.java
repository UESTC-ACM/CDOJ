package cn.edu.uestc.acmicpc.service;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cn.edu.uestc.acmicpc.config.TestContext;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.Entry;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.JoinedType;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemListDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.ObjectUtil;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test cases for {@link ProblemService}.
 */
@WebAppConfiguration
@ContextConfiguration(classes = {TestContext.class})
public class ProblemServiceTest extends AbstractTestNGSpringContextTests {

  @Autowired
  @Qualifier("realProblemService")
  private ProblemService problemService;

  @Autowired
  @Qualifier("mockProblemDAO")
  private IProblemDAO problemDAO;

  @Autowired
  @Qualifier("mockGlobalService")
  private GlobalService globalService;

  @BeforeMethod
  public void init() {
    Mockito.reset(problemDAO, globalService);
  }

  @Test
  public void testGetProblemDTOByProblemId() throws AppException {
    ProblemDTO problemDTO = ProblemDTO.builder().build();
    when(problemDAO.getDTOByUniqueField(eq(ProblemDTO.class), Mockito.<ProblemDTO.Builder>any(),
        eq("problemId"), eq(problemDTO.getProblemId()))).thenReturn(problemDTO);
    Assert.assertEquals(problemService.getProblemDTOByProblemId(problemDTO.getProblemId()), problemDTO);
    verify(problemDAO).getDTOByUniqueField(eq(ProblemDTO.class), Mockito.<ProblemDTO.Builder>any(),
        eq("problemId"), eq(problemDTO.getProblemId()));
  }

  @Test
  public void testCount() throws AppException {
    ProblemCondition problemCondition = mock(ProblemCondition.class);
    Condition condition = mock(Condition.class);
    when(problemCondition.getCondition()).thenReturn(condition);
    problemService.count(problemCondition);
    verify(problemDAO).count(condition);
  }

  @Test
  public void testUpdateProblem() throws AppException {
    ProblemDTO problemDTO = ProblemDTO.builder().build();
    Problem problem = new Problem();
    problem.setProblemId(problemDTO.getProblemId());
    when(problemDAO.get(problemDTO.getProblemId())).thenReturn(problem);
    problemService.updateProblem(problemDTO);
    ArgumentCaptor<Problem> captor = ArgumentCaptor.forClass(Problem.class);
    verify(problemDAO).update(captor.capture());
    Assert.assertTrue(ObjectUtil.entityEquals(problemDTO, captor.getValue()));
    verify(problemDAO).get(problemDTO.getProblemId());
  }

  @Test(expectedExceptions = AppException.class)
  public void testUpdateProblem_problemNotFound() throws AppException {
    ProblemDTO problemDTO = ProblemDTO.builder().build();
    when(problemDAO.get(problemDTO.getProblemId())).thenReturn(null);

    problemService.updateProblem(problemDTO);
    Assert.fail();
  }

  @Test(expectedExceptions = AppException.class)
  public void testUpdateProblem_problemFoundWithNullId() throws AppException {
    ProblemDTO problemDTO = ProblemDTO.builder().build();
    Problem problem = mock(Problem.class);
    when(problemDAO.get(problemDTO.getProblemId())).thenReturn(problem);
    when(problem.getProblemId()).thenReturn(null);
    problemService.updateProblem(problemDTO);
    Assert.fail();
  }

  @Test
  public void testCreateNewProblem() throws AppException {
    ArgumentCaptor<Problem> captor = ArgumentCaptor.forClass(Problem.class);
    when(problemDAO.add(captor.capture())).thenAnswer(new Answer<Problem>() {
      @Override
      public Problem answer(InvocationOnMock invocation) {
        Object[] args = invocation.getArguments();
        Problem problem = (Problem) args[0];
        problem.setProblemId(1015);
        return null;
      }
    });
    Integer problemId = problemService.createNewProblem();
    Assert.assertEquals(problemId, Integer.valueOf(1015));
  }

  @Test
  public void testGetProblemListDTOList() throws AppException {
    ArgumentCaptor<Condition> captor = ArgumentCaptor.forClass(Condition.class);
    PageInfo pageInfo = PageInfo.create(300L, Global.RECORD_PER_PAGE, 10, 2L);
    problemService.getProblemListDTOList(new ProblemCondition(), pageInfo);
    verify(problemDAO).findAll(eq(ProblemListDTO.class),
        isA(ProblemListDTO.Builder.class), captor.capture());
    Condition condition = captor.getValue();
    Assert.assertEquals(condition.getJoinedType(), JoinedType.AND);
    Assert.assertEquals(condition.getPageInfo(), pageInfo);
  }

  @Test
  public void testGetAllVisibleProblemIds() throws AppException {
    ArgumentCaptor<Condition> captor = ArgumentCaptor.forClass(Condition.class);
    problemService.getAllVisibleProblemIds();
    verify(problemDAO).findAll(eq("problemId"), captor.capture());
    Condition condition = captor.getValue();
    List<Entry> entries = condition.getEntries();
    Assert.assertEquals(Entry.of("isVisible",
        ConditionType.STRING_EQUALS, "1"), entries.get(0));
  }

}
