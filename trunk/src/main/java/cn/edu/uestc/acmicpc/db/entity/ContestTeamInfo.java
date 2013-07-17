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
 * Contest team information, for school programming contest.
 * 
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@Table(name = "contestTeamInfo", schema = "", catalog = "uestcoj")
@Entity
@KeyField("teamId")
public class ContestTeamInfo implements Serializable {
	private static final long serialVersionUID = -5816811480409208296L;
	private Integer teamId;

	private Integer version;

	@Version
	@Column(name = "OPTLOCK")
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name = "teamId", nullable = false, insertable = true, updatable = true, length = 10, precision = 0, unique = true)
	@Id
	@GeneratedValue
	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	private Integer userId;

	@Column(name = "userId", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
	@Basic
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	private String name;

	@Column(name = "name", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
	@Basic
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String coderName;

	@Column(name = "coderName", nullable = false, insertable = true, updatable = true, length = 150, precision = 0)
	@Basic
	public String getCoderName() {
		return coderName;
	}

	public void setCoderName(String coderName) {
		this.coderName = coderName;
	}

	private String sex;

	@Column(name = "sex", nullable = false, insertable = true, updatable = true, length = 3, precision = 0)
	@Basic
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	private String department;

	@Column(name = "department", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
	@Basic
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	private String grade;

	@Column(name = "grade", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
	@Basic
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	private String phone;

	@Column(name = "phone", nullable = false, insertable = true, updatable = true, length = 100, precision = 0)
	@Basic
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	private String size;

	@Column(name = "size", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
	@Basic
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	private String email;

	@Column(name = "email", nullable = false, insertable = true, updatable = true, length = 300, precision = 0)
	@Basic
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String school;

	@Column(name = "school", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
	@Basic
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	private Byte state;

	@Column(name = "state", nullable = false, insertable = true, updatable = true, length = 3, precision = 0)
	@Basic
	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ContestTeamInfo that = (ContestTeamInfo) o;

		if (!state.equals(that.state))
			return false;
		if (!teamId.equals(that.teamId))
			return false;
		if (!userId.equals(that.userId))
			return false;
		if (coderName != null ? !coderName.equals(that.coderName)
				: that.coderName != null)
			return false;
		if (department != null ? !department.equals(that.department)
				: that.department != null)
			return false;
		if (email != null ? !email.equals(that.email) : that.email != null)
			return false;
		if (grade != null ? !grade.equals(that.grade) : that.grade != null)
			return false;
		if (name != null ? !name.equals(that.name) : that.name != null)
			return false;
		if (phone != null ? !phone.equals(that.phone) : that.phone != null)
			return false;
		if (school != null ? !school.equals(that.school) : that.school != null)
			return false;
		if (sex != null ? !sex.equals(that.sex) : that.sex != null)
			return false;
		if (size != null ? !size.equals(that.size) : that.size != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = teamId;
		result = 31 * result + userId;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (coderName != null ? coderName.hashCode() : 0);
		result = 31 * result + (sex != null ? sex.hashCode() : 0);
		result = 31 * result + (department != null ? department.hashCode() : 0);
		result = 31 * result + (grade != null ? grade.hashCode() : 0);
		result = 31 * result + (phone != null ? phone.hashCode() : 0);
		result = 31 * result + (size != null ? size.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (school != null ? school.hashCode() : 0);
		result = 31 * result + (int) state;
		return result;
	}
}
