package cn.edu.uestc.acmicpc.util.helper;

import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingUserDAO;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RatingUtil {

  @SuppressWarnings("unused")
  private final ITrainingStatusDAO trainingStatusDAO;
  @SuppressWarnings("unused")
  private final ITrainingUserDAO trainingUserDAO;

  @Autowired
  public RatingUtil(ITrainingUserDAO trainingUserDAO,
                    ITrainingStatusDAO trainingStatusDAO) {
    this.trainingUserDAO = trainingUserDAO;
    this.trainingStatusDAO = trainingStatusDAO;
  }

  // TODO(mzry1992): refactor.
  public void updateRating(TrainingContest trainingContest) throws AppException {
//    if (trainingContest.getType() == Global.TrainingContestType.UNRATED.ordinal()) {
//      for (TrainingStatus trainingStatus : trainingContest.getTrainingStatusesByTrainingContestId()) {
//        TrainingUser trainingUser = trainingStatus.getTrainingUserByTrainingUserId();
//        if (!trainingUser.getAllow())
//          continue;
//
//        trainingUser.setRating(trainingUser.getRating());
//        trainingUser.setRatingVary(0.0);
//        trainingUser.setVolatility(trainingUser.getVolatility());
//        trainingUser.setVolatilityVary(0.0);
//        trainingUser.setCompetitions(trainingUser.getCompetitions() + 1);
//
//        trainingStatus.setRating(trainingUser.getRating());
//        trainingStatus.setRatingVary(trainingUser.getRatingVary());
//        trainingStatus.setVolatility(trainingUser.getVolatility());
//        trainingStatus.setVolatilityVary(trainingUser.getVolatilityVary());
//
//        trainingUserDAO.update(trainingUser);
//        trainingStatusDAO.update(trainingStatus);
//      }
//    } else if (trainingContest.getType() == Global.TrainingContestType.ADJUST.ordinal() ||
//        trainingContest.getType() == Global.TrainingContestType.ABSENT.ordinal()) {
//      for (TrainingStatus trainingStatus : trainingContest.getTrainingStatusesByTrainingContestId()) {
//        TrainingUser trainingUser = trainingStatus.getTrainingUserByTrainingUserId();
//        if (!trainingUser.getAllow())
//          continue;
//
//        trainingUser.setRating(trainingUser.getRating() - trainingStatus.getPenalty());
//        trainingUser.setRatingVary(-1.0 * trainingStatus.getPenalty());
//        if (trainingContest.getType() == Global.TrainingContestType.ADJUST.ordinal()) {
//          trainingUser.setVolatilityVary(trainingUser.getVolatility() - 550.0);
//          trainingUser.setVolatility(550.0);
//        } else {
//          trainingUser.setVolatilityVary(0.0);
//        }
//        trainingUser.setCompetitions(trainingUser.getCompetitions() + 1);
//
//        trainingStatus.setRating(trainingUser.getRating());
//        trainingStatus.setRatingVary(trainingUser.getRatingVary());
//        trainingStatus.setVolatility(trainingUser.getVolatility());
//        trainingStatus.setVolatilityVary(trainingUser.getVolatilityVary());
//
//        trainingUserDAO.update(trainingUser);
//        trainingStatusDAO.update(trainingStatus);
//      }
//    } else {
//      List<TrainingUser> trainingUsers = new LinkedList<>();
//      List<Integer> trainingUserRank = new LinkedList<>();
//      for (TrainingStatus trainingStatus : trainingContest.getTrainingStatusesByTrainingContestId()) {
//        TrainingUser trainingUser = trainingStatus.getTrainingUserByTrainingUserId();
//        if (!trainingUser.getAllow())
//          continue;
//        trainingUsers.add(trainingUser);
//        trainingUserRank.add(trainingStatus.getRank());
//      }
//
//      if (trainingContest.getType() == Global.TrainingContestType.TEAM.ordinal()) {
//        updateTrainingUser(trainingUsers, trainingUserRank, 0.5);
//      } else {
//        updateTrainingUser(trainingUsers, trainingUserRank, 1.0);
//      }
//      for (TrainingStatus trainingStatus : trainingContest.getTrainingStatusesByTrainingContestId()) {
//        TrainingUser trainingUser = trainingStatus.getTrainingUserByTrainingUserId();
//        if (!trainingUser.getAllow())
//          continue;
//
//        trainingStatus.setRating(trainingUser.getRating());
//        trainingStatus.setRatingVary(trainingUser.getRatingVary());
//        trainingStatus.setVolatility(trainingUser.getVolatility());
//        trainingStatus.setVolatilityVary(trainingUser.getVolatilityVary());
//
//        trainingUserDAO.update(trainingUser);
//        trainingStatusDAO.update(trainingStatus);
//      }
//    }
  }

  @SuppressWarnings("unused")
  private void updateTrainingUser(List<TrainingUser> trainingUsers, List<Integer> rank,
                                  Double contestWeight) {
    if (trainingUsers.size() <= 1) {
      for (TrainingUser trainingUser : trainingUsers) {
        trainingUser.setRatingVary(0.0);
        trainingUser.setVolatilityVary(0.0);
        trainingUser.setCompetitions(trainingUser.getCompetitions() + 1);
      }
    } else {
      Double aveRating = 0.0;
      for (TrainingUser trainingUser : trainingUsers)
        aveRating += trainingUser.getRating();
      aveRating /= trainingUsers.size();
      Double a = 0.0, b = 0.0;
      for (TrainingUser trainingUser : trainingUsers) {
        a += square(trainingUser.getVolatility());
        b += square(trainingUser.getRating() - aveRating);
      }
      Double cf = Math.sqrt(a / trainingUsers.size() + b / (trainingUsers.size() - 1));
      Double[] aRank = new Double[trainingUsers.size()];
      for (int i = 0; i < trainingUsers.size(); i++) {
        int lose = 0, tie = 0;
        for (int j = 0; j < trainingUsers.size(); j++)
          if (rank.get(i) > rank.get(j))
            lose++;
          else if (rank.get(i).equals(rank.get(j)))
            tie++;
        aRank[i] = lose + (1.0 + tie) / 2.0;
      }
      Double[] eRank = new Double[trainingUsers.size()];
      for (int i = 0; i < trainingUsers.size(); i++) {
        eRank[i] = 0.5;
        for (TrainingUser trainingUser : trainingUsers) {
          TrainingUser trainingUserA = trainingUsers.get(i);
          eRank[i] +=
              0.5 * erf((trainingUser.getRating() - trainingUserA.getRating())
                  / Math.sqrt(2.0 * (square(trainingUserA.getVolatility()) + square(trainingUser
                  .getVolatility())))) + 0.5;
        }
      }
      Double[] aPerf = new Double[trainingUsers.size()];
      Double[] ePerf = new Double[trainingUsers.size()];
      Double[] perfAs = new Double[trainingUsers.size()];
      for (int i = 0; i < trainingUsers.size(); i++) {
        aPerf[i] = -inv_norm((aRank[i] - 0.5) / trainingUsers.size());
        ePerf[i] = -inv_norm((eRank[i] - 0.5) / trainingUsers.size());
      }
      for (int i = 0; i < trainingUsers.size(); i++) {
        TrainingUser trainingUser = trainingUsers.get(i);
        perfAs[i] = trainingUser.getRating() + cf * (aPerf[i] - ePerf[i]);
        Double weight = 1.0 / (1 - (0.42 / (trainingUser.getCompetitions() + 1) + 0.18)) - 1.0;
        if (trainingUser.getRating() >= 2000 && trainingUser.getRating() <= 2500)
          weight *= 0.9;
        else if (trainingUser.getRating() > 2500)
          weight *= 0.8;
        weight *= contestWeight;
        Double cap = 150.0 + 1500.0 / (trainingUser.getCompetitions() + 2.0);
        Double newRating = (trainingUser.getRating() + weight * perfAs[i]) / (1.0 + weight);
        newRating = Math.min(newRating, trainingUser.getRating() + cap);
        newRating = Math.max(newRating, trainingUser.getRating() - cap);
        Double newVolatility =
            Math.sqrt(square(newRating - trainingUser.getRating()) / weight
                + square(trainingUser.getVolatility()) / (weight + 1.0));
        newRating = Math.max(newRating, 1.0);
        newVolatility = Math.max(newVolatility, 101.0);
        trainingUser.setRatingVary(newRating - trainingUser.getRating());
        trainingUser.setVolatilityVary(newVolatility - trainingUser.getVolatility());
        trainingUser.setRating(newRating);
        trainingUser.setVolatility(newVolatility);
        trainingUser.setCompetitions(trainingUser.getCompetitions() + 1);
      }
    }
  }

  /**
   * signal number delta
   */
  public static final double EPS = 1e-8;
  private static final double LOG_PI_OVER_2 = Math.log(Math.PI) / 2.0;

  public static Double square(Double a) {
    return a * a;
  }

  /**
   * double signal number with Common.EPS
   *
   * @param value the double value to be evaluated
   * @return double signal number of value
   */
  public static int sgn(double value) {
    return value < -EPS ? -1 : value > EPS ? 1 : 0;
  }

  private static double p_gamma(double a, double x, double loggamma_a) {
    if (sgn(x - 1 - a) >= 0)
      return 1 - q_gamma(a, x, loggamma_a);
    if (sgn(x) == 0)
      return 0;
    double result, term, pre;
    result = term = Math.exp(a * Math.log(x) - x - loggamma_a) / a;
    for (int k = 1; k < 1000; k++) {
      term *= x / (a + k);
      pre = result;
      result += term;
      if (sgn(result - pre) == 0)
        return result;
    }
    return result;
  }

  private static double q_gamma(double a, double x, double loggamma_a) {
    double la, lb, result, w, temp, pre;
    la = 1;
    lb = 1 + x - a;
    if (sgn(x - 1 - a) < 0)
      return 1 - p_gamma(a, x, loggamma_a);
    w = Math.exp(a * Math.log(x) - x - loggamma_a);
    result = w / lb;
    for (int k = 2; k < 1000; k++) {
      temp = ((k - 1 - a) * (lb - la) + (k + x) * lb) / k;
      la = lb;
      lb = temp;
      w *= (k - 1 - a) / k;
      temp = w / (la * lb);
      pre = result;
      result += temp;
      if (sgn(result - pre) == 0)
        return result;
    }
    return result;
  }

  /**
   * standard error function of x
   *
   * @param x the variable's value
   * @return the function value of x
   */
  public static double erf(double x) {
    if (Double.isInfinite(x)) {
      if (Double.isNaN(x))
        return x;
      return (x > 0 ? 1.0 : -1.0);
    }
    if (sgn(x) >= 0)
      return p_gamma(0.5, x * x, LOG_PI_OVER_2);
    else
      return -p_gamma(0.5, x * x, LOG_PI_OVER_2);
  }

  /**
   * the inversion of normal function of x
   *
   * @param x the variable's value
   * @return the function value of x
   */
  public static double inv_norm(double x) {
    double r, s;
    r = 0.0;
    s = 0.0;
    if (x < 0.02425) {
      s = Math.sqrt(-2.0 * Math.log(x));
      return +(2.938163982698783 + (4.374664141464968 - (2.549732539343734 + (2.400758277161838 + (0.3223964580411365 + 0.007784894002430293 * s)
          * s)
          * s)
          * s)
          * s)
          / (1.0 + (3.754408661907416 + (2.445134137142996 + (0.3224671290700398 + 0.007784695709041462 * s)
          * s)
          * s)
          * s);
    } else if (x > 0.97575) {
      s = Math.sqrt(-2.0 * Math.log(1.0 - x));
      return -(2.938163982698783 + (4.374664141464968 - (2.549732539343734 + (2.400758277161838 + (0.3223964580411365 + 0.007784894002430293 * s)
          * s)
          * s)
          * s)
          * s)
          / (1.0 + (3.754408661907416 + (2.445134137142996 + (0.3224671290700398 + 0.007784695709041462 * s)
          * s)
          * s)
          * s);
    } else {
      s = x - 0.5;
      r = s * s;
      return s
          * (2.506628277459239 - (30.66479806614716 - (138.3577518672690 - (275.9285104469687 - (220.9460984245205 - 39.69683028665376 * r)
          * r)
          * r)
          * r)
          * r)
          / (1.0 - (13.28068155288572 - (66.80131188771972 - (155.6989798598866 - (161.5858368580409 - 54.47609879822406 * r)
          * r)
          * r)
          * r)
          * r);
    }
  }
}
