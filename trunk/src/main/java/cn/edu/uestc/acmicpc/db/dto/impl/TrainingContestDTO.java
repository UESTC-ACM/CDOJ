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
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Data transfer object for {@link TrainingContest}.
 */
public class TrainingContestDTO extends BaseDTO<TrainingContest> {

  private Integer trainingContestId;
  private Boolean isPersonal;
  private String title;
  private Integer type;

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getTrainingContestId() {
    return trainingContestId;
  }

  public void setTrainingContestId(Integer trainingContestId) {
    this.trainingContestId = trainingContestId;
  }

  public Boolean getIsPersonal() {
    return isPersonal;
  }

  public void setIsPersonal(Boolean personal) {
    isPersonal = personal;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  protected Class<TrainingContest> getReferenceClass() {
    return TrainingContest.class;
  }

  @Override
  public TrainingContest getEntity() throws AppException {
    TrainingContest trainingContest = super.getEntity();
    trainingContest.setIsPersonal(true);
    return trainingContest;
  }

  @Override
  public void updateEntity(TrainingContest trainingContest) throws AppException {
    super.updateEntity(trainingContest);
  }
}
