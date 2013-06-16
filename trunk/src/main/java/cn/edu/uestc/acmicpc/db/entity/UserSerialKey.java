/*
 * cdoj, UESTC ACMICPC Online Judge
 * Copyright (c) 2012  fish <@link lyhypacm@gmail.com>,
 * mzry1992 <@link muziriyun@gmail.com>
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

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * User serial keys for password.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
@Table(name = "userSerialKey", schema = "", catalog = "uestcoj")
@Entity
public class UserSerialKey implements Serializable {
    private static final long serialVersionUID = -129312932189312L;

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
    public String toString() {
        return "UserSerialKey{" +
                "serialKey='" + serialKey + '\'' +
                ", userByUserId=" + userByUserId +
                ", time=" + time +
                ", userSerialKeyId=" + userSerialKeyId +
                '}';
    }

    private Integer userSerialKeyId;

    private Timestamp time;
    private String serialKey;

    @Column(name = "serialKey", nullable = false, insertable = true, updatable = true,
            length = 128, precision = 0, unique = false)
    @Basic
    public String getSerialKey() {
        return serialKey;
    }

    public void setSerialKey(String serialKey) {
        this.serialKey = serialKey;
    }

    @Column(name = "userSerialKeyId", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0, unique = true)
    @Id
    @GeneratedValue
    public Integer getUserSerialKeyId() {
        return userSerialKeyId;
    }

    public void setUserSerialKeyId(Integer userSerialKeyId) {
        this.userSerialKeyId = userSerialKeyId;
    }

    @Column(name = "time", nullable = false, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public Timestamp getTime() {
        return time;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSerialKey that = (UserSerialKey) o;

        if (!serialKey.equals(that.serialKey)) return false;
        if (!time.equals(that.time)) return false;
        if (!userByUserId.equals(that.userByUserId)) return false;
        if (!userSerialKeyId.equals(that.userSerialKeyId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userSerialKeyId.hashCode();
        result = 31 * result + time.hashCode();
        result = 31 * result + serialKey.hashCode();
        result = 31 * result + userByUserId.hashCode();
        return result;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    private User userByUserId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }
}
