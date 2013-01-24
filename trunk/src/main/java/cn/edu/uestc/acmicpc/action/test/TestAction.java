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

package cn.edu.uestc.acmicpc.action.test;

import cn.edu.uestc.acmicpc.action.BaseAction;
import cn.edu.uestc.acmicpc.db.dao.TagDAO;
import cn.edu.uestc.acmicpc.db.entity.Tag;
import cn.edu.uestc.acmicpc.ioc.TagDAOAware;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.util.List;

/**
 * Test Action for cdoj.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
public class TestAction extends BaseAction implements TagDAOAware {
    private TagDAO tagDAO = null;

    /**
     * Go to test index page
     *
     * @return success signal
     */
    public String toTest() {
        try {
            List<Tag> tags = tagDAO.findAll();
            request.put("tags", tags);
        } catch (AppException e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    @Override
    public void setTagDAO(TagDAO tagDAO) {
        System.out.println(tagDAO);
        this.tagDAO = tagDAO;
    }
}
