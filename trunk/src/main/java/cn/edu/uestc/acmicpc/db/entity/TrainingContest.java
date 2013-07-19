/*
 * cdoj, UESTC ACMICPC Online Judge
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 * 	mzry1992 <@link muziriyun@gmail.com>
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

package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@Table(name = "trainingContest", schema = "", catalog = "uestcoj")
@Entity
@KeyField("trainingContestId")
public class TrainingContest implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -3563641835123515967L;
  private Integer trainingContestId;

  @Column(name = "trainingContestId", nullable = false, insertable = true, updatable = true,
      length = 10, precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getTrainingContestId() {
    return trainingContestId;
  }

  public void setTrainingContestId(Integer trainingContestId) {
    this.trainingContestId = trainingContestId;
  }

  private Boolean isPersonal;

  @Column(name = "isPersonal", nullable = false, insertable = true, updatable = true, length = 0,
      precision = 0)
  @Basic
  public Boolean getIsPersonal() {
    return isPersonal;
  }

  public void setIsPersonal(Boolean isPersonal) {
    this.isPersonal = isPersonal;
  }

  private String title;

  @Column(name = "title", nullable = false, insertable = true, updatable = true, length = 150,
      precision = 0)
  @Basic
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  private Integer type;

  @Column(name = "type", nullable = false, insertable = true, updatable = true, length = 11,
      precision = 0)
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  private Integer version;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (!(o instanceof TrainingContest))
      return false;

    TrainingContest that = (TrainingContest) o;

    if (isPersonal != null ? !isPersonal.equals(that.isPersonal) : that.isPersonal != null)
      return false;
    if (title != null ? !title.equals(that.title) : that.title != null)
      return false;
    if (trainingContestId != null ? !trainingContestId.equals(that.trainingContestId)
        : that.trainingContestId != null)
      return false;
    if (version != null ? !version.equals(that.version) : that.version != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = trainingContestId != null ? trainingContestId.hashCode() : 0;
    result = 31 * result + (isPersonal != null ? isPersonal.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (version != null ? version.hashCode() : 0);
    return result;
  }

  private Collection<TrainingStatus> trainingStatusesByTrainingContestId;

  @OneToMany(mappedBy = "trainingContestByTrainingContestId")
  public Collection<TrainingStatus> getTrainingStatusesByTrainingContestId() {
    return trainingStatusesByTrainingContestId;
  }

  public void setTrainingStatusesByTrainingContestId(
      Collection<TrainingStatus> trainingStatusesByTrainingContestId) {
    this.trainingStatusesByTrainingContestId = trainingStatusesByTrainingContestId;
  }
}
