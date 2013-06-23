/*
 * cdoj, UESTC ACMICPC Online Judge
 *
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
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

package cn.edu.uestc.acmicpc.oj.action.contest;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ContestCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.db.view.impl.ContestListView;
import cn.edu.uestc.acmicpc.ioc.condition.ContestConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.ContestDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(NeedLogin = false)
public class ContestListAction extends BaseAction implements ContestDAOAware, ContestConditionAware {

    @Autowired
    private IContestDAO contestDAO;

    @Autowired
    private ContestCondition contestCondition;

    /**
     * Refer to contest list page.
     * @return <strong>SUCCESS</strong> signal
     */
    public String toContestList() {
        return SUCCESS;
    }

    /**
     * Search action.
     * <p/>
     * Find all records by conditions and return them as a list in JSON, and the condition
     * set will set in JSON named "condition".
     * <p/>
     * <strong>JSON output</strong>:
     * <ul>
     * <li>
     * For success: {"result":"ok", "pageInfo":<strong>PageInfo object</strong>,
     * "condition", <strong>ContestCondition entity</strong>,
     * "contestList":<strong>query result</strong>}
     * </li>
     * <li>
     * For error: {"result":"error", "error_msg":<strong>error message</strong>}
     * </li>
     * </ul>
     *
     * @return <strong>JSON</strong> signal
     */
    @SuppressWarnings("unchecked")
    @SkipValidation
    public String toSearch() {
        try {
            if (currentUser == null || currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal())
                contestCondition.setIsVisible(true);
            contestCondition.setIsTitleEmpty(false);
            Condition condition = contestCondition.getCondition();
            Long count = contestDAO.count(contestCondition.getCondition());
            PageInfo pageInfo = buildPageInfo(count, RECORD_PER_PAGE, "", null);
            condition.setCurrentPage(pageInfo.getCurrentPage());
            condition.setCountPerPage(RECORD_PER_PAGE);
            List<Contest> contestList = (List<Contest>) contestDAO.findAll(condition);
            List<ContestListView> contestListViewList = new ArrayList<>();
            for (Contest contest : contestList)
                contestListViewList.add(new ContestListView(contest));
            json.put("pageInfo", pageInfo.getHtmlString());
            json.put("result", "ok");
            json.put("contestList", contestListViewList);
        } catch (AppException e) {
            json.put("result", "error");
            json.put("error_msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", "error");
            json.put("error_msg", "Unknown exception occurred.");
        }
        return JSON;
    }

    @Override
    public void setContestCondition(ContestCondition contestCondition) {
        this.contestCondition = contestCondition;
    }

    @Override
    public ContestCondition getContestCondition() {
        return contestCondition;
    }

    @Override
    public void setContestDAO(IContestDAO contestDAO) {
        this.contestDAO = contestDAO;
    }
}
