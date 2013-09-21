package cn.edu.uestc.acmicpc.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import jxl.read.biff.BiffException;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingStatusDAO;
import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingContestDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingStatusDAOAware;
import cn.edu.uestc.acmicpc.ioc.util.TrainingRankListParserAware;
import cn.edu.uestc.acmicpc.training.entity.TrainingContestRankList;
import cn.edu.uestc.acmicpc.training.parser.TrainingRankListParser;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;
import cn.edu.uestc.acmicpc.util.exception.ParserException;

/**
 * Test cases for {@link TrainingRankListParser}.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class RankListParserITTest implements TrainingRankListParserAware, TrainingContestDAOAware,
    TrainingStatusDAOAware {

  @SuppressWarnings("unused")
  @Test
  @Ignore
  public void testXlsParser() throws IOException, BiffException {
    try {
      File file = new File("/Users/mzry1992/Downloads/4.xls");
      List<String[]> result = trainingRankListParser.parseXls(file);
      for (String[] strings : result) {
        System.out.print("Size = " + strings.length + " --> |");
        for (String grid : strings)
          System.out.print(grid + "|");
        System.out.println();
      }
      TrainingContestRankList trainingContestRankList = trainingRankListParser.parse(file, true, 1);
    } catch (ParserException e) {
      System.out.println(e.getMessage());
    } catch (FieldNotUniqueException | AppException e) {
      e.printStackTrace();
    }
  }

  @Test
  @Ignore
  public void testDatabaseParser() throws AppException, ParserException {
    TrainingStatus trainingStatus = trainingStatusDAO.get(11);
    String[] strings = trainingRankListParser.parseTrainingUserSummary(trainingStatus.getSummary());
    System.out.print("Size = " + strings.length + " --> |");
    for (String grid : strings)
      System.out.print(grid + "|");
    System.out.println();
  }

  @Autowired
  private TrainingRankListParser trainingRankListParser;

  @Override
  public void setTrainingRankListParserAware(TrainingRankListParser trainingRankListParser) {
    this.trainingRankListParser = trainingRankListParser;
  }

  @Autowired
  private ITrainingContestDAO trainingContestDAO;

  @Override
  public void setTrainingContestDAO(ITrainingContestDAO trainingContestDAO) {
    this.trainingContestDAO = trainingContestDAO;
  }

  @Autowired
  private ITrainingStatusDAO trainingStatusDAO;

  @Override
  public void setTrainingStatusDAO(ITrainingStatusDAO trainingStatusDAO) {
    this.trainingStatusDAO = trainingStatusDAO;
  }
}
