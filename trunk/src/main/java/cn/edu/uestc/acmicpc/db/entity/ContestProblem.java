/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Mappings between contest and problems.
 * 
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@Table(name = "contestProblem", schema = "", catalog = "uestcoj")
@Entity
@KeyField("contestProblemId")
public class ContestProblem implements Serializable {
	private static final long serialVersionUID = -9079259357297937419L;
	private Integer contestProblemId;

	private Integer version;

	@Version
	@Column(name = "OPTLOCK")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "contestProblemId", nullable = false, insertable = true, updatable = true, length = 10, precision = 0, unique = true)
	@Id
	@GeneratedValue
	public Integer getContestProblemId() {
		return contestProblemId;
	}

	public void setContestProblemId(Integer contestProblemId) {
		this.contestProblemId = contestProblemId;
	}

	private Integer order;

	@Column(name = "`order`", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
	@Basic
	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ContestProblem that = (ContestProblem) o;

		if (!contestProblemId.equals(that.contestProblemId))
			return false;
		if (!order.equals(that.order))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = contestProblemId;
		result = 31 * result + order;
		return result;
	}

	private Contest contestByContestId;

	@ManyToOne
	@JoinColumn(name = "contestId", referencedColumnName = "contestId", nullable = false)
	public Contest getContestByContestId() {
		return contestByContestId;
	}

	public void setContestByContestId(Contest contestByContestId) {
		this.contestByContestId = contestByContestId;
	}

	private Problem problemByProblemId;

	@ManyToOne
	@JoinColumn(name = "problemId", referencedColumnName = "problemId", nullable = false)
	public Problem getProblemByProblemId() {
		return problemByProblemId;
	}

	public void setProblemByProblemId(Problem problemByProblemId) {
		this.problemByProblemId = problemByProblemId;
	}
}
