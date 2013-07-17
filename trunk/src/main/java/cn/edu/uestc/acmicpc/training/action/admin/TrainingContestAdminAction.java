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

package cn.edu.uestc.acmicpc.training.action.admin;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.TrainingContestCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingContestDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingContestDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.db.view.impl.TrainingContestListView;
import cn.edu.uestc.acmicpc.db.view.impl.TrainingContestView;
import cn.edu.uestc.acmicpc.ioc.condition.TrainingContestConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingContestDAOAware;
import cn.edu.uestc.acmicpc.ioc.dto.TrainingContestDTOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingContestAdminAction extends BaseAction implements TrainingContestConditionAware,
        TrainingContestDAOAware, TrainingContestDTOAware {

    public String toIndex() {
        return SUCCESS;
    }

    public String toSearchTrainingContest() {
        try {
            trainingContestCondition.setIsTitleEmpty(false);
            Condition condition = trainingContestCondition.getCondition();
            Long count = trainingContestDAO.count(trainingContestCondition.getCondition());
            PageInfo pageInfo = buildPageInfo(count, RECORD_PER_PAGE, "", null);
            condition.setCurrentPage(pageInfo.getCurrentPage());
            condition.setCountPerPage(RECORD_PER_PAGE);
            List<TrainingContest> trainingContestList = (List<TrainingContest>) trainingContestDAO.findAll(condition);
            List<TrainingContestListView> trainingContestListViewList = new LinkedList<>();
            for (TrainingContest trainingContest: trainingContestList)
                trainingContestListViewList.add(new TrainingContestListView(trainingContest));
            json.put("pageInfo", pageInfo.getHtmlString());
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

    public String toContestEditor() {
        try {
            if (targetTrainingContestId == null) {
                trainingContestCondition.clear();
                trainingContestCondition.setIsTitleEmpty(true);
                Condition condition = trainingContestCondition.getCondition();
                Long count = trainingContestDAO.count(condition);

                if (count == 0) {
                    TrainingContest trainingContest = trainingContestDTO.getEntity();
                    trainingContestDAO.add(trainingContest);
                    targetTrainingContestId = trainingContest.getTrainingContestId();
                } else {
                    List<TrainingContest> result = (List<TrainingContest>) trainingContestDAO.findAll(trainingContestCondition.getCondition());
                    if (result == null || result.size() == 0)
                        throw new AppException("Add new contest error!");
                    TrainingContest trainingContest = result.get(0);
                    targetTrainingContestId = trainingContest.getTrainingContestId();
                }

                if (targetTrainingContestId == null)
                    throw new AppException("Add new training contest error!");

                return redirect(getActionURL("/training/admin", "contest/editor/" + targetTrainingContestId));
            } else {
                targetTrainingContest = new TrainingContestView(trainingContestDAO.get(targetTrainingContestId));
                File targetFile = new File(getTrainingRankFileName());
                if (targetFile.exists() && !targetFile.delete())
                    throw new AppException("Internal exception: target file exists and can not be deleted.");
                if (targetTrainingContest.getTrainingContestId() == null)
                    throw new AppException("Wrong training contest ID!");
            }
        } catch (AppException e) {
            return redirect(getActionURL("/training/admin", "index"), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return redirect(getActionURL("/training/admin", "index"), "Unknown exception occurred.");
        }
        return SUCCESS;
    }

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
}
