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
import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * // TODO(mzry1992) Description
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingStatusDTO extends BaseDTO<TrainingStatus> {

  @Override
  public TrainingStatus getEntity() throws AppException {
    TrainingStatus trainingStatus = super.getEntity();
    trainingStatus.setPenalty(0);
    trainingStatus.setRank(0);
    trainingStatus.setRating(0.0);
    trainingStatus.setRatingVary(0.0);
    trainingStatus.setSolve(0);
    trainingStatus.setSummary("");
    trainingStatus.setVolatility(0.0);
    trainingStatus.setVolatilityVary(0.0);
    return trainingStatus;
  }

  @Override
  protected Class<TrainingStatus> getReferenceClass() {
    return TrainingStatus.class;
  }
}
