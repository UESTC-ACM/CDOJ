package cn.edu.uestc.acmicpc.training.action.index;

import cn.edu.uestc.acmicpc.db.condition.impl.TrainingUserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingUserDAO;
import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.db.view.impl.TrainingStatusView;
import cn.edu.uestc.acmicpc.db.view.impl.TrainingUserView;
import cn.edu.uestc.acmicpc.ioc.condition.TrainingUserConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingUserDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

/**
 * Description
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@Controller
@LoginPermit(NeedLogin = false)
public class TrainingSystemIndexAction extends BaseAction implements TrainingUserDAOAware,
    TrainingUserConditionAware {

  /**
	 * 
	 */
  private static final long serialVersionUID = 8112513177334421456L;

  public String toIndex() {
    return SUCCESS;
  }

  private Integer userType;

  public Integer getUserType() {
    return userType;
  }

  public void setUserType(Integer userType) {
    this.userType = userType;
  }

  @SuppressWarnings("unchecked")
  public String toAllMemberHistory() {
    try {
      if (userType == null)
        userType = 0;
      trainingUserCondition.clear();
      trainingUserCondition.setAllow(true);
      trainingUserCondition.setType(userType);
      trainingUserCondition.setOrderFields("rating,volatility,name");
      trainingUserCondition.setOrderAsc("false,true,true");
      List<TrainingUser> trainingUserList =
          (List<TrainingUser>) trainingUserDAO.findAll(trainingUserCondition.getCondition());

      List<List<TrainingStatusView>> teamHistory = new LinkedList<>();
      List<TrainingUserView> trainingUserViewList = new LinkedList<>();
      Integer rank = 0;
      for (TrainingUser trainingUser : trainingUserList) {
        List<TrainingStatus> trainingStatusList =
            (List<TrainingStatus>) trainingUser.getTrainingStatusesByTrainingUserId();
        Collections.sort(trainingStatusList, new Comparator<TrainingStatus>() {

          @Override
          public int compare(TrainingStatus a, TrainingStatus b) {
            return a.getTrainingContestByTrainingContestId().getTrainingContestId()
                .compareTo(b.getTrainingContestByTrainingContestId().getTrainingContestId());
          }
        });

        List<TrainingStatusView> trainingStatusViewList = new LinkedList<>();
        TrainingStatusView trainingStatusViewEmpty = new TrainingStatusView();
        trainingStatusViewEmpty.setName(trainingUser.getName());
        trainingStatusViewList.add(trainingStatusViewEmpty);
        for (TrainingStatus trainingStatus : trainingStatusList) {
          TrainingStatusView trainingStatusView = new TrainingStatusView(trainingStatus);
          trainingStatusViewList.add(trainingStatusView);
        }

        teamHistory.add(trainingStatusViewList);

        TrainingUserView trainingUserView = new TrainingUserView(trainingUser);
        rank++;
        trainingUserView.setRank(rank);
        trainingUserViewList.add(trainingUserView);
      }

      json.put("result", "ok");
      json.put("teamHistory", teamHistory);
      json.put("teamSummary", trainingUserViewList);
    } catch (AppException e) {
      json.put("result", "error");
      json.put("err_msg", e.getMessage());
    } catch (Exception e) {
      json.put("result", "error");
      e.printStackTrace();
      json.put("error_msg", "Unknown exception occurred.");
    }
    return JSON;
  }

  @Autowired
  private ITrainingUserDAO trainingUserDAO;

  @Override
  public void setTrainingUserDAO(ITrainingUserDAO trainingUserDAO) {
    this.trainingUserDAO = trainingUserDAO;
  }

  @Autowired
  private TrainingUserCondition trainingUserCondition;

  @Override
  public void setTrainingUserCondition(TrainingUserCondition trainingUserCondition) {
    this.trainingUserCondition = trainingUserCondition;
  }

  @Override
  public TrainingUserCondition getTrainingUserCondition() {
    return trainingUserCondition;
  }
}
