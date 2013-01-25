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

package cn.edu.uestc.acmicpc.oj.action.test;

import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.oj.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.oj.db.dao.TagDAO;
import cn.edu.uestc.acmicpc.oj.db.entity.Tag;
import cn.edu.uestc.acmicpc.oj.ioc.TagDAOAware;
import cn.edu.uestc.acmicpc.oj.util.Global;
import cn.edu.uestc.acmicpc.oj.util.exception.AppException;

import java.util.List;

/**
 * Test Action for cdoj.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 2
 */
@LoginPermit(value = Global.AuthenticationType.ADMIN)
public class TestAction extends BaseAction implements TagDAOAware {
    private TagDAO tagDAO = null;

    /**
     * Go to test index page
     *
     * @return <strong>SUCCESS</strong> signal
     */
    public String toTest() {
        try {
            List<Tag> tags = tagDAO.findAll();
            request.put("tags", tags);
        } catch (AppException e) {
        }
        return SUCCESS;
    }

    @Override
    public void setTagDAO(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
    }
}
