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

package cn.edu.uestc.acmicpc.action.user;

import cn.edu.uestc.acmicpc.action.BaseAction;
import cn.edu.uestc.acmicpc.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.db.dao.DepartmentDAO;
import cn.edu.uestc.acmicpc.db.dto.UserDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.exception.ValidatorException;
import com.alibaba.fastjson.JSON;
import org.apache.struts2.convention.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Action for register
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 7
 */
@ParentPackage("default")
@Namespace("/user")
@Results({@Result(name="json",type="json",params = {"root","json"})})
@LoginPermit(NeedLogin = false)
public class RegisterAction extends BaseAction {

    private static final long serialVersionUID = -2854303130010851540L;

    /**
     * user dto...
     */
    private UserDTO userDTO;

    /**
     * department dao, use for get a department entity by id.
     */
    private DepartmentDAO departmentDAO;

    /**
     * return message
     */
    private Map<String, Object> json;

    /**
     * Register action! with so many validators! ha ha...
     *
     * @return <strong>NONE</strong> signal, process in front
     */
    @Action("register")
    public String toRegister() {
        json = new HashMap<String, Object>();
        try {
            if (userDAO.getEntityByUniqueField("userName", userDTO.getUserName()) != null)
                throw new ValidatorException("userDTO.userName", "User name has been used!");
            if (userDAO.getEntityByUniqueField("email", userDTO.getEmail()) != null)
                throw new ValidatorException("userDTO.email", "Email has benn used!");
            userDTO.setDepartment(departmentDAO.get(userDTO.getDepartmentId()));
            User user = userDTO.getUser();
            userDAO.add(userDTO.getUser());
            session.put("userName", user.getUserName());
            session.put("password", user.getPassword());
            session.put("lastLogin", user.getLastLogin());
            session.put("userType", user.getType());
            json.put("result", "ok");
        } catch (ValidatorException e) {
            json.put("result", "error");
            for (String key : e.getJson().keySet())
                json.put(key, e.getJson().get(key));
        } catch (Exception e) {
            // TODO fix the error msg
            json.put("result", "error");
            json.put("error_msg", "error_msg");
        }
        return JSON;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public DepartmentDAO getDepartmentDAO() {
        return departmentDAO;
    }

    public void setDepartmentDAO(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    public Map<String, Object> getJson() {
        return json;
    }

    public void setJson(Map<String, Object> json) {
        this.json = json;
    }
}
