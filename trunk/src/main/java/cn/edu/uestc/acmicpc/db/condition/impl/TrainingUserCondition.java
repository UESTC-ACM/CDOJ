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

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.JoinedProperty;
import cn.edu.uestc.acmicpc.db.dao.impl.UserDAO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * // TODO(mzry1992) Description
 */
@Repository
public class TrainingUserCondition extends BaseCondition {

  @Autowired
  public TrainingUserCondition(UserCondition userCondition) {
    this.userCondition = userCondition;
  }

  private Integer startId;
  private Integer endId;
  private String name;
  private String userName;
  private Integer type;
  private Boolean allow;

  @Exp(MapField = "allow", Type = ConditionType.eq)
  public Boolean getAllow() {
    return allow;
  }

  public void setAllow(Boolean allow) {
    this.allow = allow;
  }

  @Exp(MapField = "trainingUserId", Type = ConditionType.ge)
  public Integer getStartId() {
    return startId;
  }

  public void setStartId(Integer startId) {
    this.startId = startId;
  }

  @Exp(MapField = "trainingUserId", Type = ConditionType.le)
  public Integer getEndId() {
    return endId;
  }

  public void setEndId(Integer endId) {
    this.endId = endId;
  }

  @Exp(Type = ConditionType.like)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  @Exp(Type = ConditionType.eq)
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void invoke(Condition condition) {
    super.invoke(condition);

    if (userName != null) {
      UserDAO userDAO = applicationContext.getBean("userDAO", UserDAO.class);
      userCondition.clear();
      userCondition.setUserName(userName);
      try {
        List<User> users = (List<User>) userDAO.findAll(userCondition.getCondition());
        if (users != null && !users.isEmpty()) {
          JoinedProperty joinedProperty =
              new JoinedProperty(Restrictions.eq("userByUserId", users.get(0)), users.get(0)
                  .getUserId(), ConditionType.eq);
          condition.addJoinedProperty("userByUserId", joinedProperty);
        }
      } catch (AppException ignored) {
      }
    }
  }

  private UserCondition userCondition;
}
