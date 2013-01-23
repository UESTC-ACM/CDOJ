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

package cn.edu.uestc.acmicpc.util;

import cn.edu.uestc.acmicpc.db.dao.DepartmentDAO;
import cn.edu.uestc.acmicpc.db.entity.Department;

import java.util.List;

/**
 * Global enumerates and constants inside project.
 * <p/>
 * <strong>WARN:</strong> this file may be rewritten carefully.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 2
 */
public class Global {

    public enum AuthenticationType {
        NORMAL("normal user"), ADMIN("administrator"), CONSTANT("constant user");
        private String description;

        private AuthenticationType(String description) {
            this.description = description;
        }

        /**
         * Get enumerate value's description.
         *
         * @return description string for specific authentication type
         */
        public String getDescription() {
            return description;
        }
    }

    /**
     * Department DAO using for get all departments.
     */
    private static DepartmentDAO departmentDAO;

    /**
     * Department list.
     */
    private static List<Department> departmentList;

    /**
     * Static constructor.
     */
    static {
        try {
            setDepartmentList(departmentDAO.findAll());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setDepartmentDAO(DepartmentDAO departmentDAO) {
        Global.departmentDAO = departmentDAO;
    }

    /**
     * Get all departments in database.
     * @return All departments
     */
    public static List<Department> getDepartmentList() {
        return departmentList;
    }

    public static void setDepartmentList(List<Department> departmentList) {
        Global.departmentList = departmentList;
    }

}
