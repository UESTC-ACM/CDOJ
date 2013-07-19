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

import cn.edu.uestc.acmicpc.db.dao.iface.ITagDAO;
import cn.edu.uestc.acmicpc.db.entity.Tag;
import cn.edu.uestc.acmicpc.ioc.dao.TagDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Test Action for cdoj.
 * 
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@LoginPermit(value = Global.AuthenticationType.NORMAL)
public class TestAction extends BaseAction implements TagDAOAware {

  private static final long serialVersionUID = 3513904272112800586L;
  /**
   * TagDAO from IoC.
   */
  @Autowired
  private ITagDAO tagDAO = null;

  /**
   * Go to test index page
   * 
   * @return <strong>SUCCESS</strong> signal
   */
  @SuppressWarnings("unchecked")
  public String toTest() {
    try {
      List<Tag> tags = (List<Tag>) tagDAO.findAll();
      request.put("tags", tags);
    } catch (AppException ignored) {
    }
    return SUCCESS;
  }

  @Override
  public void setTagDAO(ITagDAO tagDAO) {
    this.tagDAO = tagDAO;
  }
}
