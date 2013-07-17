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

package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.util.DateUtil;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Contest database condition entity.
 * 
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
public class ContestCondition extends BaseCondition {
	private Integer startId;

	private Integer endId;

	private String title;

	private String description;

	private Byte type;

	private Boolean isTitleEmpty;

	public Boolean getIsTitleEmpty() {
		return isTitleEmpty;
	}

	public void setIsTitleEmpty(Boolean isTitleEmpty) {
		this.isTitleEmpty = isTitleEmpty;
	}

	@Exp(MapField = "contestId", Type = ConditionType.ge)
	public Integer getStartId() {
		return startId;
	}

	public void setStartId(Integer startId) {
		this.startId = startId;
	}

	@Exp(MapField = "contestId", Type = ConditionType.le)
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

	@Exp(Type = ConditionType.like)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Exp(Type = ConditionType.eq)
	public Byte getType() {
		return type;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	@Exp(MapField = "time", Type = ConditionType.ge)
	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	@Exp(Type = ConditionType.eq)
	public Boolean getIsVisible() {
		return isVisible;
	}

	public void setIsVisible(Boolean visible) {
		isVisible = visible;
	}

	private Timestamp startTime;

	private Timestamp endTime;

	private Boolean isVisible;

	/**
	 * Keyword for {@code description}, {@code title}
	 */
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public void invoke(Condition condition) {
		super.invoke(condition);
		if (keyword != null) {
			String[] fields = new String[] { "description", "title" };
			Junction junction = Restrictions.disjunction();
			for (String field : fields) {
				junction.add(Restrictions.like(field,
						String.format("%%%s%%", keyword)));
			}
			condition.addCriterion(junction);
		}
		if (endTime != null) {
			condition.addCriterion(Restrictions.lt("time",
					DateUtil.add(endTime, Calendar.DATE, 1)));
		}
		if (isTitleEmpty != null) {
			if (isTitleEmpty) {
				condition.addCriterion(Restrictions.like("title", ""));
			} else {
				condition.addCriterion(Restrictions.like("title", "_%"));
			}
		}
	}
}
