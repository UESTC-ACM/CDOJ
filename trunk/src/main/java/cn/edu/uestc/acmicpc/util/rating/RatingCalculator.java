package cn.edu.uestc.acmicpc.util.rating;

import cn.edu.uestc.acmicpc.db.dto.impl.TrainingContestDto;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingUserDto;
import cn.edu.uestc.acmicpc.util.enums.TrainingContestType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.TrainingRating;
import cn.edu.uestc.acmicpc.web.rank.TrainingRankList;
import cn.edu.uestc.acmicpc.web.rank.TrainingRankListUser;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RatingCalculator {

  private final List<TrainingUserDto> userList;

  public RatingCalculator(List<TrainingUserDto> userList) {
    this.userList = userList;
  }

  public void calculate(TrainingContestDto contest, TrainingRankList rankList) throws AppException {
    if (contest.getType() == TrainingContestType.ADJUST.ordinal()) {
      // TODO
    } else {
      calculateByRank(contest, rankList);
    }
  }

  private TrainingUserDto getTrainingUserDto(Integer trainingUserId) throws AppException {
    for (TrainingUserDto trainingUserDto : userList) {
      if (trainingUserDto.getTrainingUserId().equals(trainingUserId)) {
        return trainingUserDto;
      }
    }
    throw new AppException("Training user not found!");
  }

  /**
   * Calculate rating by user's rank.
   * @param contest
   *
   * @param rankList
   * @throws AppException
   */
  public void calculateByRank(TrainingContestDto contest, TrainingRankList rankList)
      throws AppException {
    HashMap<Integer, TrainingRankListUser> userToRankListMap = new HashMap<>();
    for (TrainingRankListUser rankListUser : rankList.users) {
      for (TrainingUserDto usersInRankList : rankListUser.users) {
        userToRankListMap.put(usersInRankList.getTrainingUserId(), rankListUser);
      }
    }

    List<TrainingUserDto> activeUsers = new LinkedList<>();
    List<TrainingRankListUser> trainingRankListUser = new LinkedList<>();

    for (Map.Entry<Integer, TrainingRankListUser> entry : userToRankListMap.entrySet()) {
      activeUsers.add(getTrainingUserDto(entry.getKey()));
      trainingRankListUser.add(entry.getValue());
    }

    if (activeUsers.size() <= 1) {
      for (int i = 0; i < activeUsers.size(); i++) {
        TrainingUserDto user = activeUsers.get(i);
        List<TrainingRating> ratingHistory = user.getRatingHistoryList();
        ratingHistory.add(new TrainingRating(
            user.getCurrentRating(),
            user.getCurrentVolatility(),
            trainingRankListUser.get(i).rank,
            0.0,
            0.0,
            contest.getTrainingContestId()
            ));
        user.setCompetitions(ratingHistory.size());
      }
    } else {
      Double aveRating = 0.0;
      for (TrainingUserDto trainingUserDto : activeUsers) {
        aveRating += trainingUserDto.getCurrentRating();
      }
      aveRating /= activeUsers.size();

      Double a = 0.0, b = 0.0;
      for (TrainingUserDto trainingUserDto : activeUsers) {
        a += square(trainingUserDto.getCurrentVolatility());
        b += square(trainingUserDto.getCurrentRating() - aveRating);
      }

      Double cf = Math.sqrt(a / activeUsers.size() + b / (activeUsers.size() - 1));

      Double[] aRank = new Double[activeUsers.size()];
      for (int i = 0; i < activeUsers.size(); i++) {
        int lose = 0, tie = 0;
        for (int j = 0; j < activeUsers.size(); j++) {
          if (trainingRankListUser.get(i).rank > trainingRankListUser.get(j).rank) {
            lose++;
          } else if (trainingRankListUser.get(i).rank.equals(trainingRankListUser.get(j).rank)) {
            tie++;
          }
        }
        aRank[i] = 1.0 * lose + (1.0 + tie) / 2.0;
      }

      Double[] eRank = new Double[activeUsers.size()];
      for (int i = 0; i < activeUsers.size(); i++) {
        eRank[i] = 0.5;
        TrainingUserDto userA = activeUsers.get(i);
        for (TrainingUserDto userB : activeUsers) {
          eRank[i] += 0.5 * erf(
              (userB.getCurrentRating() - userA.getCurrentRating())
                  / Math.sqrt(2.0 * (square(userA.getCurrentVolatility()) + square(userB
                      .getCurrentVolatility())))
              ) + 0.5;
        }
      }

      Double[] aPref = new Double[activeUsers.size()];
      Double[] ePref = new Double[activeUsers.size()];
      Double[] perfAs = new Double[activeUsers.size()];
      for (int i = 0; i < activeUsers.size(); i++) {
        aPref[i] = -inv_norm((aRank[i] - 0.5) / activeUsers.size());
        ePref[i] = -inv_norm((eRank[i] - 0.5) / activeUsers.size());
      }

      // Amount number of deducted rating
      Integer totalDeductedRating = 0;
      Integer totalUnDeductedUser = 0;
      for (int i = 0; i < activeUsers.size(); i++) {
        if (trainingRankListUser.get(i).deductRating != null) {
          totalDeductedRating += trainingRankListUser.get(i).deductRating;
        } else {
          totalUnDeductedUser++;
        }
      }

      for (int i = 0; i < activeUsers.size(); i++) {
        TrainingUserDto user = activeUsers.get(i);
        List<TrainingRating> ratingList = user.getRatingHistoryList();

        perfAs[i] = user.getCurrentRating() + cf * (aPref[i] - ePref[i]);
        Double weight = 1.0 / (1 - (0.42 / (ratingList.size() + 1) + 0.18)) - 1.0;
        if (user.getCurrentRating() >= 2000 && user.getCurrentRating() <= 2500) {
          weight *= 0.9;
        } else if (user.getCurrentRating() > 2500) {
          weight *= 0.8;
        }

        Double cap = 150.0 + 1500.0 / (ratingList.size() + 2.0);
        Double newRating = (user.getCurrentRating() + weight * perfAs[i]) / (1.0 + weight);
        newRating = Math.min(newRating, user.getCurrentRating() + cap);
        newRating = Math.max(newRating, user.getCurrentRating() - cap);
        Double newVolatility = Math.sqrt(
            square(newRating - user.getCurrentRating()) / weight +
                square(user.getCurrentVolatility()) / (weight + 1.0)
            );

        if (trainingRankListUser.get(i).deductRating != null) {
          // Deduct rating manually
          newRating -= trainingRankListUser.get(i).deductRating;
        } else {
          // Add to other users
          newRating += totalDeductedRating / totalUnDeductedUser;
        }

        newRating = Math.max(newRating, 1.0);
        newVolatility = Math.max(newVolatility, 101.0);

        ratingList.add(new TrainingRating(
            newRating,
            newVolatility,
            trainingRankListUser.get(i).rank,
            newRating - user.getCurrentRating(),
            newVolatility - user.getCurrentVolatility(),
            contest.getTrainingContestId()
            ));
        user.setCurrentRating(newRating);
        user.setCurrentVolatility(newVolatility);
        user.setCompetitions(ratingList.size());
        user.setMostRecentEventId(contest.getTrainingContestId());
        user.setMostRecentEventName(contest.getTitle());
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
   * @param value
   *          the double value to be evaluated
   * @return double signal number of value
   */
  public static int sgn(double value) {
    return value < -EPS ? -1 : value > EPS ? 1 : 0;
  }

  private static double p_gamma(double a, double x, double loggamma_a) {
    if (sgn(x - 1 - a) >= 0) {
      return 1 - q_gamma(a, x, loggamma_a);
    }
    if (sgn(x) == 0) {
      return 0;
    }
    double result, term, pre;
    result = term = Math.exp(a * Math.log(x) - x - loggamma_a) / a;
    for (int k = 1; k < 1000; k++) {
      term *= x / (a + k);
      pre = result;
      result += term;
      if (sgn(result - pre) == 0) {
        return result;
      }
    }
    return result;
  }

  private static double q_gamma(double a, double x, double loggamma_a) {
    double la, lb, result, w, temp, pre;
    la = 1;
    lb = 1 + x - a;
    if (sgn(x - 1 - a) < 0) {
      return 1 - p_gamma(a, x, loggamma_a);
    }
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
      if (sgn(result - pre) == 0) {
        return result;
      }
    }
    return result;
  }

  /**
   * standard error function of x
   *
   * @param x
   *          the variable's value
   * @return the function value of x
   */
  public static double erf(double x) {
    if (Double.isInfinite(x)) {
      if (Double.isNaN(x)) {
        return x;
      }
      return (x > 0 ? 1.0 : -1.0);
    }
    if (sgn(x) >= 0) {
      return p_gamma(0.5, x * x, LOG_PI_OVER_2);
    } else {
      return -p_gamma(0.5, x * x, LOG_PI_OVER_2);
    }
  }

  /**
   * the inversion of normal function of x
   *
   * @param x
   *          the variable's value
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
