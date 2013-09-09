/*
 *
 *  cdoj, UESTC ACMICPC Online Judge
 *  Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  	mzry1992 <@link muziriyun@gmail.com>
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.db.condition.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;

/**
 * // TODO(mzry1992) Description
 */
@Repository
public class TrainingContestCondition extends BaseCondition {

  private Integer startId;
  private Integer endId;
  private String title;
  private Boolean isPersonal;
  private Integer type;

  @Exp(MapField = "type", Type = ConditionType.eq)
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  private Boolean isTitleEmpty;

  public Boolean getIsTitleEmpty() {
    return isTitleEmpty;
  }

  public void setIsTitleEmpty(Boolean isTitleEmpty) {
    this.isTitleEmpty = isTitleEmpty;
  }

  @Exp(MapField = "trainingContestId", Type = ConditionType.ge)
  public Integer getStartId() {
    return startId;
  }

  public void setStartId(Integer startId) {
    this.startId = startId;
  }

  @Exp(MapField = "trainingContestId", Type = ConditionType.le)
  public Integer getEndId() {
    return endId;
  }

  public void setEndId(Integer endId) {
    this.endId = endId;
  }

  @Exp(Type = ConditionType.like)
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Exp(Type = ConditionType.eq)
  public Boolean getIsPersonal() {
    return isPersonal;
  }

  public void setIsPersonal(Boolean personal) {
    isPersonal = personal;
  }

  @Override
  public void invoke(Condition condition) {
    super.invoke(condition);
    if (isTitleEmpty != null) {
      if (isTitleEmpty) {
        condition.addCriterion(Restrictions.like("title", ""));
      } else {
        condition.addCriterion(Restrictions.like("title", "_%"));
      }
    }
  }
}
