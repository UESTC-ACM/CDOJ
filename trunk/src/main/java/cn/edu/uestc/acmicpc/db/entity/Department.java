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

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Department information.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 3
 */
@Table(name = "department", schema = "", catalog = "uestcoj")
@Entity
public class Department implements Serializable {
    private static final long serialVersionUID = -2249534733683595360L;
    private int departmentId;

    @Column(name = "departmentId", nullable = false, insertable = true,
            updatable = true, length = 10, precision = 0, unique = true)
    @Id
    @GeneratedValue
    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    private String name;

    @Column(name = "name", nullable = false, insertable = true, updatable = true,
            length = 50, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Department that = (Department) o;

        if (departmentId != that.departmentId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = departmentId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    private Collection<User> usersByDepartmentId;

    @OneToMany(mappedBy = "departmentByDepartmentId")
    public Collection<User> getUsersByDepartmentId() {
        return usersByDepartmentId;
    }

    public void setUsersByDepartmentId(Collection<User> usersByDepartmentId) {
        this.usersByDepartmentId = usersByDepartmentId;
    }
}
