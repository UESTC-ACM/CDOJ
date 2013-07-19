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

package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.JoinedProperty;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingContestDAO;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingContestDAOAware;
import cn.edu.uestc.acmicpc.util.annotation.Ignore;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingStatusCondition extends BaseCondition implements TrainingContestDAOAware {

  private Integer startId;
  private Integer endId;
  private Integer trainingContestId;

  @Exp(MapField = "trainingStatusId", Type = ConditionType.ge)
  public Integer getStartId() {
    return startId;
  }

  public void setStartId(Integer startId) {
    this.startId = startId;
  }

  @Exp(MapField = "trainingStatusId", Type = ConditionType.le)
  public Integer getEndId() {
    return endId;
  }

  public void setEndId(Integer endId) {
    this.endId = endId;
  }

  public Integer getTrainingContestId() {
    return trainingContestId;
  }

  public void setTrainingContestId(Integer trainingContestId) {
    this.trainingContestId = trainingContestId;
  }

  @Override
  public void invoke(Condition condition) {
    super.invoke(condition);

    if (trainingContestId != null) {
      try {
        TrainingContest trainingContest = trainingContestDAO.get(trainingContestId);
        if (trainingContest == null)
          return;
        JoinedProperty joinedProperty =
            new JoinedProperty(Restrictions.eq("trainingContestByTrainingContestId",
                trainingContest), trainingContest.getTrainingContestId(), ConditionType.eq);
        condition.addJoinedProperty("trainingContestByTrainingContestId", joinedProperty);
      } catch (AppException e) {
        e.printStackTrace(); // To change body of catch statement use
        // File | Settings | File Templates.
      }
    }
  }

  @Autowired
  private ITrainingContestDAO trainingContestDAO;

  @Override
  @Ignore
  public void setTrainingContestDAO(ITrainingContestDAO trainingContestDAO) {
    this.trainingContestDAO = trainingContestDAO;
  }
}
