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
import cn.edu.uestc.acmicpc.db.condition.impl.TrainingUserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingUserDAO;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.ioc.condition.TrainingUserConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingUserDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingUserAdminAction extends BaseAction implements TrainingUserConditionAware, TrainingUserDAOAware{

    public String toMemberList() {
        try {
            Condition condition = trainingUserCondition.getCondition();
            Long count = trainingUserDAO.count(trainingUserCondition.getCondition());
            PageInfo pageInfo = buildPageInfo(count, RECORD_PER_PAGE, "", null);
            condition.setCurrentPage(pageInfo.getCurrentPage());
            condition.setCountPerPage(RECORD_PER_PAGE);
            List<TrainingUser> trainingUserList = (List<TrainingUser>) trainingUserDAO.findAll(condition);

            json.put("pageInfo", pageInfo.getHtmlString());
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

    @Autowired
    private TrainingUserCondition trainingUserCondition;

    @Override
    public void setTrainingUserCondition(TrainingUserCondition trainingUserCondition) {
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
}
