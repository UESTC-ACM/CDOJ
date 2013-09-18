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

package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Data transfer object for {@link TrainingUser}.
 */
public class TrainingUserDTO extends BaseDTO<TrainingUser> {

  private Integer trainingUserId;
  private String name;
  private String member;
  private Integer type;

  public String getMember() {
    return member;
  }

  public void setMember(String member) {
    this.member = member;
  }

  public Integer getTrainingUserId() {
    return trainingUserId;
  }

  public void setTrainingUserId(Integer trainingUserId) {
    this.trainingUserId = trainingUserId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  @Override
  public TrainingUser getEntity() throws AppException {
    TrainingUser trainingUser = super.getEntity();
    trainingUser.setRating(1200.0);
    trainingUser.setVolatility(550.0);
    trainingUser.setAllow(false);
    trainingUser.setCompetitions(0);
    return trainingUser;
  }

  @Override
  protected Class<TrainingUser> getReferenceClass() {
    return TrainingUser.class;
  }
}
