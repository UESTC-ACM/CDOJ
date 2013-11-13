package cn.edu.uestc.acmicpc.service;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.List;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cn.edu.uestc.acmicpc.config.TestContext;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.Entry;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.JoinedType;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDataEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDataShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemEditorShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemShowDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.ObjectUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.view.PageInfo;

/** Test cases for {@link ProblemService}. */
@WebAppConfiguration
@ContextConfiguration(classes = { TestContext.class })
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

}
