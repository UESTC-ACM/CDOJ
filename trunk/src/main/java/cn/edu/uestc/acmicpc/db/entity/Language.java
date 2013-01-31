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

import cn.edu.uestc.acmicpc.oj.annotation.IdSetter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Languages for compiler.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 2
 */
@Table(name = "language", schema = "", catalog = "uestcoj")
@Entity
public class Language implements Serializable {
    private static final long serialVersionUID = 6622284482431851438L;
    private int languageId;

    @Column(name = "languageId", nullable = false, insertable = true,
            updatable = true, length = 10, precision = 0, unique = true)
    @Id
    @GeneratedValue
    public int getLanguageId() {
        return languageId;
    }

    @IdSetter
    public void setLanguageId(int languageId) {
        this.languageId = languageId;
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

    private String extension;

    @Column(name = "extension", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    private String param;

    @Column(name = "param", nullable = false, insertable = true, updatable = true,
            length = 65535, precision = 0)
    @Basic
    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Language language = (Language) o;

        if (languageId != language.languageId) return false;
        if (extension != null ? !extension.equals(language.extension) : language.extension != null) return false;
        if (name != null ? !name.equals(language.name) : language.name != null) return false;
        if (param != null ? !param.equals(language.param) : language.param != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = languageId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (extension != null ? extension.hashCode() : 0);
        result = 31 * result + (param != null ? param.hashCode() : 0);
        return result;
    }

    private Collection<Status> statusesByLanguageId;

    @OneToMany(mappedBy = "languageByLanguageId")
    public Collection<Status> getStatusesByLanguageId() {
        return statusesByLanguageId;
    }

    public void setStatusesByLanguageId(Collection<Status> statusesByLanguageId) {
        this.statusesByLanguageId = statusesByLanguageId;
    }
}
