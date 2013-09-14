package cn.edu.uestc.acmicpc.training.entity;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingUserDAO;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingUserDAOAware;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;
import cn.edu.uestc.acmicpc.util.exception.ParserException;

/**
 * Parser string style rank list into OJ format.
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TrainingContestRankList implements TrainingUserDAOAware {

  private List<TrainingContestProblemSummaryView> problemSummary;
  private List<TrainingUserRankSummary> trainingUserRankSummaryList;
  private Integer problemCount;
  private Boolean isPersonal;
  private Integer type;

  private static final Logger LOGGER = LogManager.getLogger(TrainingContestRankList.class);

  //TODO Fix me! SCOPE error(maybe)
  public TrainingContestRankList() {
    LOGGER.info("Clear old data");
    problemSummary = new LinkedList<>();
    trainingUserRankSummaryList = new LinkedList<>();
  }

  /**
   * Generate training contest rank list by string values.
   *
   * @param rankList   original rank list
   * @param isPersonal contest category
   * @param type       contest type
   * @throws FieldNotUniqueException
   * @throws AppException
   * @throws ParserException
   */
  public void setRankList(List<String[]> rankList, Boolean isPersonal, Integer type)
      throws FieldNotUniqueException, AppException, ParserException {
    problemSummary = new LinkedList<>();
    trainingUserRankSummaryList = new LinkedList<>();
    this.isPersonal = isPersonal;
    this.type = type;

    //Get problem number
    problemCount = rankList.get(0).length - 1;
    //Special cases
    if (type != Global.TrainingContestType.TEAM.ordinal() && type != Global.TrainingContestType.NORMAL.ordinal())
      problemCount = 0;
    for (int i = 0; i < problemCount; i++) {
      TrainingContestProblemSummaryView trainingContestProblemSummaryView =
          new TrainingContestProblemSummaryView(i);
      problemSummary.add(trainingContestProblemSummaryView);
    }

    //Get user rank summary list
    for (String[] userInfo : rankList) {
      trainingUserRankSummaryList.addAll(getTrainingUserRankSummaryListByUserInfo(userInfo));
    }

    //Sort and calc problem summary
    if (type == Global.TrainingContestType.TEAM.ordinal()
        || type == Global.TrainingContestType.NORMAL.ordinal()
        || type == Global.TrainingContestType.UNRATED.ordinal()) {
      calcProblemSummary();
      sortRankList();
    } else if (type == Global.TrainingContestType.ADJUST.ordinal() ||
        type == Global.TrainingContestType.ABSENT.ordinal()) {
      sortRankList();
    } else if (type == Global.TrainingContestType.TC.ordinal()
        || type == Global.TrainingContestType.CF.ordinal()) {
      calcProblemSummary();
      sortRankListByScore();
    } else {
      sortRankList();
    }

    //Rank
    for (int i = 0; i < trainingUserRankSummaryList.size(); i++) {
      if (i > 0
          && samePosition(trainingUserRankSummaryList.get(i),
          trainingUserRankSummaryList.get(i - 1)))
        trainingUserRankSummaryList.get(i)
            .setRank(trainingUserRankSummaryList.get(i - 1).getRank());
      else
        trainingUserRankSummaryList.get(i).setRank(i + 1);
    }
  }

  /**
   * Get training user rank summary by a row of records
   * If this contest is team contest, use ',' to split team member's name
   * Team members in the same team share same rank summary
   *
   * @param userInfo a row of records from data source
   * @return parsed training user rank summary list
   * @throws FieldNotUniqueException
   * @throws AppException
   * @throws ParserException
   */
  public List<TrainingUserRankSummary> getTrainingUserRankSummaryListByUserInfo(String[] userInfo)
      throws FieldNotUniqueException, AppException, ParserException {
    List<TrainingUserRankSummary> trainingUserRankSummaryList = new LinkedList<>();
    String names[] = userInfo[0].split(",");
    for (String name : names) {
      TrainingUser trainingUser = trainingUserDAO.getEntityByUniqueField("name", name);
      // If there are no such user or it's not allowed, just
      // continue
      if (trainingUser == null || !trainingUser.getAllow())
        continue;
      // If user type is wrong
      if (trainingUser.getType() == Global.TrainingUserType.PERSONAL.ordinal() && !isPersonal)
        continue;
      if (trainingUser.getType() == Global.TrainingUserType.TEAM.ordinal() && isPersonal)
        continue;

      TrainingUserRankSummary trainingUserRankSummary =
          new TrainingUserRankSummary(trainingUser, userInfo, type);
      trainingUserRankSummaryList.add(trainingUserRankSummary);
    }
    return trainingUserRankSummaryList;
  }

  /**
   * Calc problem summary.
   * <p/>
   * TODO Fix problem tried and solved under team type contest.
   */
  public void calcProblemSummary() {
    for (int i = 0; i < problemCount; i++) {
      // Get basic information
      Integer totTried = 0;
      Integer totSolved = 0;
      Integer firstSolvedTime = Integer.MAX_VALUE;
      for (TrainingUserRankSummary anTrainingUserRankSummaryList1 : trainingUserRankSummaryList) {
        TrainingProblemSummaryInfo trainingProblemSummaryInfo =
            anTrainingUserRankSummaryList1.getTrainingProblemSummaryInfoList()[i];
        totTried += trainingProblemSummaryInfo.getTried();
        if (trainingProblemSummaryInfo.getSolved()) {
          totSolved++;
          firstSolvedTime = Math.min(firstSolvedTime, trainingProblemSummaryInfo.getSolutionTime());
        }
      }
      problemSummary.get(i).setTried(totTried);
      problemSummary.get(i).setSolved(totSolved);
      // Set first solved
      for (TrainingUserRankSummary anTrainingUserRankSummaryList : trainingUserRankSummaryList)
        if (anTrainingUserRankSummaryList.getTrainingProblemSummaryInfoList()[i].getSolved()
            && anTrainingUserRankSummaryList.getTrainingProblemSummaryInfoList()[i]
            .getSolutionTime().equals(firstSolvedTime))
          anTrainingUserRankSummaryList.getTrainingProblemSummaryInfoList()[i].setFirstSolved(true);
    }
  }

  /**
   * Check weather two users have same rank position
   *
   * @param userA user A
   * @param userB user B
   * @return weather A and B have same rank position
   */
  public Boolean samePosition(TrainingUserRankSummary userA, TrainingUserRankSummary userB) {
    return userA.getSolved().equals(userB.getSolved())
        && userA.getPenalty().equals(userB.getPenalty());
  }

  /**
   * Sort rank list by penalty(score)
   */
  public void sortRankListByScore() {
    // Sort
    Collections.sort(trainingUserRankSummaryList, new Comparator<TrainingUserRankSummary>() {

      @Override
      public int compare(TrainingUserRankSummary a, TrainingUserRankSummary b) {
        if (a.getPenalty().equals(b.getPenalty())) {
          return a.getNickName().compareTo(b.getNickName());
        }
        return b.getPenalty().compareTo(a.getPenalty());
      }
    });
  }

  /**
   * Sort rank list by solved, if two user have same solved number, who have smaller penalty(score) goes first.
   */
  public void sortRankList() {
    // Sort
    Collections.sort(trainingUserRankSummaryList, new Comparator<TrainingUserRankSummary>() {

      @Override
      public int compare(TrainingUserRankSummary a, TrainingUserRankSummary b) {
        if (a.getSolved().equals(b.getSolved())) {
          if (a.getPenalty().equals(b.getPenalty())) {
            return a.getNickName().compareTo(b.getNickName());
          }
          return a.getPenalty().compareTo(b.getPenalty());
        }
        return (b.getSolved().compareTo(a.getSolved()));
      }
    });
  }

  public Integer getProblemCount() {
    return problemCount;
  }

  public void setProblemCount(Integer problemCount) {
    this.problemCount = problemCount;
  }

  public Boolean getPersonal() {
    return isPersonal;
  }

  public void setPersonal(Boolean personal) {
    isPersonal = personal;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public List<TrainingContestProblemSummaryView> getProblemSummary() {
    return problemSummary;
  }

  public void setProblemSummary(List<TrainingContestProblemSummaryView> problemSummary) {
    this.problemSummary = problemSummary;
  }

  public List<TrainingUserRankSummary> getTrainingUserRankSummaryList() {
    return trainingUserRankSummaryList;
  }

  public void setTrainingUserRankSummaryList(
      List<TrainingUserRankSummary> trainingUserRankSummaryList) {
    this.trainingUserRankSummaryList = trainingUserRankSummaryList;
  }

  @Autowired
  private ITrainingUserDAO trainingUserDAO;

  @Override
  public void setTrainingUserDAO(ITrainingUserDAO trainingUserDAO) {
    this.trainingUserDAO = trainingUserDAO;
  }
}
