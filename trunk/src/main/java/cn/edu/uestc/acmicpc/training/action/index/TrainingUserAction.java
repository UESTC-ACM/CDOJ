/*
 * cdoj, UESTC ACMICPC Online Judge
 *
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 * mzry1992 <@link muziriyun@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package cn.edu.uestc.acmicpc.training.action.index;

import cn.edu.uestc.acmicpc.db.condition.impl.TrainingContestCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TrainingUserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingUserDAO;
import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.db.view.impl.TrainingStatusView;
import cn.edu.uestc.acmicpc.db.view.impl.TrainingUserView;
import cn.edu.uestc.acmicpc.ioc.condition.TrainingContestConditionAware;
import cn.edu.uestc.acmicpc.ioc.condition.TrainingUserConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingContestDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingUserDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Description
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingUserAction extends BaseAction implements
		TrainingUserConditionAware, TrainingUserDAOAware,
		TrainingContestConditionAware, TrainingContestDAOAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1861121195824887104L;

	@SuppressWarnings("unchecked")
	public String toMemberList() {
		try {
			trainingUserCondition.setAllow(true);
			trainingUserCondition.setOrderFields("rating,volatility,name");
			trainingUserCondition.setOrderAsc("false,true,true");
			List<TrainingUser> trainingUserList = (List<TrainingUser>) trainingUserDAO
					.findAll(trainingUserCondition.getCondition());
			List<TrainingUserView> trainingUserViewList = new LinkedList<>();
			Integer teamRank = 0, personalRank = 0;
			for (TrainingUser trainingUser : trainingUserList) {
				TrainingUserView trainingUserView = new TrainingUserView(
						trainingUser);
				if (trainingUserView.getType() == Global.TrainingUserType.PERSONAL
						.ordinal()) {
					personalRank++;
					trainingUserView.setRank(personalRank);
				} else {
					teamRank++;
					trainingUserView.setRank(teamRank);
				}
				trainingUserViewList.add(trainingUserView);
			}

			json.put("trainingUserList", trainingUserViewList);
			json.put("result", "ok");
		} catch (AppException e) {
			json.put("result", "error");
		} catch (Exception e) {
			json.put("result", "error");
			e.printStackTrace();
			json.put("error_msg", "Unknown exception occurred.");
		}
		return JSON;
	}

	private Integer targetTrainingUserId;

	private TrainingUserView targetTrainingUser;

	public TrainingUserView getTargetTrainingUser() {
		return targetTrainingUser;
	}

	public void setTargetTrainingUser(TrainingUserView targetTrainingUser) {
		this.targetTrainingUser = targetTrainingUser;
	}

	public Integer getTargetTrainingUserId() {
		return targetTrainingUserId;
	}

	public void setTargetTrainingUserId(Integer targetTrainingUserId) {
		this.targetTrainingUserId = targetTrainingUserId;
	}

	public String toMemberPage() {
		try {
			if (targetTrainingUserId == null)
				throw new AppException("No such training user!");
			TrainingUser trainingUser = trainingUserDAO
					.get(targetTrainingUserId);
			if (trainingUser == null)
				throw new AppException("No such training user!");
			targetTrainingUser = new TrainingUserView(trainingUser);
		} catch (AppException e) {
			return redirect(getActionURL("/training/", "index"), e.getMessage());
		} catch (Exception e) {
			return redirect(getActionURL("/training/", "index"),
					"Unknown exception occurred.");
		}
		return SUCCESS;
	}

	public String toMemberHistory() {
		try {
			if (targetTrainingUserId == null)
				throw new AppException("No such training user!");
			TrainingUser trainingUser = trainingUserDAO
					.get(targetTrainingUserId);
			if (trainingUser == null)
				throw new AppException("No such training user!");

			List<TrainingStatus> trainingStatusList = (List<TrainingStatus>) trainingUser
					.getTrainingStatusesByTrainingUserId();
			Collections.sort(trainingStatusList,
					new Comparator<TrainingStatus>() {
						@Override
						public int compare(TrainingStatus a, TrainingStatus b) {
							return a.getTrainingContestByTrainingContestId()
									.getTrainingContestId()
									.compareTo(
											b.getTrainingContestByTrainingContestId()
													.getTrainingContestId());
						}
					});

			trainingUserCondition.clear();
			trainingUserCondition.setType(trainingUser.getType());
			Long totUsers = trainingUserDAO.count(trainingUserCondition
					.getCondition());

			Integer[] rankStatus = new Integer[(int) (totUsers + 1)];
			Arrays.fill(rankStatus, 0);
			List<TrainingStatusView> trainingStatusViewList = new LinkedList<>();
			trainingStatusViewList.add(new TrainingStatusView());
			for (TrainingStatus trainingStatus : trainingStatusList) {
				TrainingStatusView trainingStatusView = new TrainingStatusView(
						trainingStatus);
				trainingStatusViewList.add(trainingStatusView);

				rankStatus[((int) Math.max(1,
						Math.min(totUsers, trainingStatusView.getRank())))]++;
			}

			trainingContestCondition.clear();
			if (trainingUser.getType() == Global.TrainingUserType.PERSONAL
					.ordinal())
				trainingContestCondition.setIsPersonal(true);
			else
				trainingContestCondition.setIsPersonal(false);
			Long totContests = trainingContestDAO
					.count(trainingContestCondition.getCondition());

			json.put("result", "ok");
			json.put("rankStatus", rankStatus);
			json.put("totUsers", totUsers);
			json.put("totContests", totContests);
			json.put("teamHistory", trainingStatusViewList);
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
	private TrainingUserCondition trainingUserCondition;

	@Override
	public void setTrainingUserCondition(
			TrainingUserCondition trainingUserCondition) {
		this.trainingUserCondition = trainingUserCondition;
	}

	@Override
	public TrainingUserCondition getTrainingUserCondition() {
		return this.trainingUserCondition;
	}

	@Autowired
	private ITrainingUserDAO trainingUserDAO;

	@Override
	public void setTrainingUserDAO(ITrainingUserDAO trainingUserDAO) {
		this.trainingUserDAO = trainingUserDAO;
	}

	@Autowired
	private TrainingContestCondition trainingContestCondition;
	@Autowired
	private ITrainingContestDAO trainingContestDAO;

	@Override
	public void setTrainingContestCondition(
			TrainingContestCondition trainingContestCondition) {
		this.trainingContestCondition = trainingContestCondition;
	}

	@Override
	public TrainingContestCondition getTrainingContestCondition() {
		return trainingContestCondition;
	}

	@Override
	public void setTrainingContestDAO(ITrainingContestDAO trainingContestDAO) {
		this.trainingContestDAO = trainingContestDAO;
	}
}
