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
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingContestDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingContestDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.db.view.impl.TrainingContestListView;
import cn.edu.uestc.acmicpc.db.view.impl.TrainingContestView;
import cn.edu.uestc.acmicpc.ioc.condition.TrainingContestConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingContestDAOAware;
import cn.edu.uestc.acmicpc.ioc.dto.TrainingContestDTOAware;
import cn.edu.uestc.acmicpc.ioc.util.TrainingRankListParserAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.training.entity.TrainingContestRankList;
import cn.edu.uestc.acmicpc.util.TrainingRankListParser;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

/**
 * Description
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingContestAction extends BaseAction implements TrainingContestConditionAware,
    TrainingContestDAOAware, TrainingContestDTOAware, TrainingRankListParserAware {

  /**
	 * 
	 */
  private static final long serialVersionUID = 2230337792377511101L;

  @SuppressWarnings("unchecked")
  public String toSearchTrainingContest() {
    try {
      trainingContestCondition.setIsTitleEmpty(false);
      trainingContestCondition.setOrderFields("id");
      trainingContestCondition.setOrderAsc("false");
      List<TrainingContest> trainingContestList =
          (List<TrainingContest>) trainingContestDAO.findAll(trainingContestCondition
              .getCondition());
      List<TrainingContestListView> trainingContestListViewList = new LinkedList<>();
      for (TrainingContest trainingContest : trainingContestList)
        trainingContestListViewList.add(new TrainingContestListView(trainingContest));
      json.put("result", "ok");
      json.put("trainingContestList", trainingContestListViewList);
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return JSON;
  }

  public String toTrainingContest() {
    try {
      if (targetTrainingContestId == null)
        throw new AppException("No such training contest!");
      TrainingContest trainingContest = trainingContestDAO.get(targetTrainingContestId);
      if (trainingContest == null)
        throw new AppException("No such training contest!");
      targetTrainingContest = new TrainingContestView(trainingContest);
      if (trainingContest.getTrainingStatusesByTrainingContestId().size() > 0) {
        targetTrainingContestRankList = trainingRankListParser.parse(trainingContest);
      }
    } catch (AppException e) {
      e.printStackTrace();
      return redirect(getActionURL("/training/", "index"), e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return redirect(getActionURL("/training/", "index"), "Unknown exception occurred.");
    }
    return SUCCESS;
  }

  private TrainingContestRankList targetTrainingContestRankList;

  public TrainingContestRankList getTargetTrainingContestRankList() {
    return targetTrainingContestRankList;
  }

  public void
      setTargetTrainingContestRankList(TrainingContestRankList targetTrainingContestRankList) {
    this.targetTrainingContestRankList = targetTrainingContestRankList;
  }

  private Integer targetTrainingContestId;

  public Integer getTargetTrainingContestId() {
    return targetTrainingContestId;
  }

  public void setTargetTrainingContestId(Integer targetTrainingContestId) {
    this.targetTrainingContestId = targetTrainingContestId;
  }

  private TrainingContestView targetTrainingContest;

  public TrainingContestView getTargetTrainingContest() {
    return targetTrainingContest;
  }

  public void setTargetTrainingContest(TrainingContestView targetTrainingContest) {
    this.targetTrainingContest = targetTrainingContest;
  }

  @SuppressWarnings("unused")
  private String getTrainingRankFileName() {
    return settings.SETTING_UPLOAD_FOLDER + "/training_contest_" + targetTrainingContestId + ".xls";
  }

  @Autowired
  private TrainingContestCondition trainingContestCondition;

  @Override
  public void setTrainingContestCondition(TrainingContestCondition trainingContestCondition) {
    this.trainingContestCondition = trainingContestCondition;
  }

  @Override
  public TrainingContestCondition getTrainingContestCondition() {
    return trainingContestCondition;
  }

  @Autowired
  private ITrainingContestDAO trainingContestDAO;

  @Override
  public void setTrainingContestDAO(ITrainingContestDAO trainingContestDAO) {
    this.trainingContestDAO = trainingContestDAO;
  }

  @Autowired
  private TrainingContestDTO trainingContestDTO;

  @Override
  public void setTrainingContestDTO(TrainingContestDTO trainingContestDTO) {
    this.trainingContestDTO = trainingContestDTO;
  }

  @Override
  public TrainingContestDTO getTrainingContestDTO() {
    return trainingContestDTO;
  }

  @Autowired
  private TrainingRankListParser trainingRankListParser;

  @Override
  public void setTrainingRankListParserAware(TrainingRankListParser trainingRankListParser) {
    this.trainingRankListParser = trainingRankListParser;
  }
}
