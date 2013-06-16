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
 * Code information.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
@Table(name = "code", schema = "", catalog = "uestcoj")
@Entity
public class Code implements Serializable {
    private static final long serialVersionUID = 6092881044668152921L;
    private Integer codeId;

    private Integer version;

    @Version
    @Column(name = "OPTLOCK")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "codeId", nullable = false, insertable = true, updatable = true, length = 10,
            precision = 0, unique = true)
    @Id
    @GeneratedValue
    public Integer getCodeId() {
        return codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    private String content;

    @Column(name = "content", nullable = false, insertable = true, updatable = true, length = 65535,
            precision = 0)
    @Basic
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Code code = (Code) o;

        if (!codeId.equals(code.codeId)) return false;
        if (content != null ? !content.equals(code.content) : code.content != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codeId;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }

    private Collection<Status> statusesByCodeId;

    @OneToMany(mappedBy = "codeByCodeId")
    public Collection<Status> getStatusesByCodeId() {
        return statusesByCodeId;
    }

    public void setStatusesByCodeId(Collection<Status> statusesByCodeId) {
        this.statusesByCodeId = statusesByCodeId;
    }
}
