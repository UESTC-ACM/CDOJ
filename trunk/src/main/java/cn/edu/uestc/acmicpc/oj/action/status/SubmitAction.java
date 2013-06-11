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

package cn.edu.uestc.acmicpc.oj.action.status;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.*;
import cn.edu.uestc.acmicpc.db.dto.impl.CodeDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.StatusDTO;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.db.entity.Language;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.ioc.condition.StatusConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.*;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * action for submit code.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@SuppressWarnings("UnusedDeclaration")
@LoginPermit(NeedLogin = true)
public class SubmitAction extends BaseAction
        implements StatusDAOAware, CodeDAOAware,
        ContestDAOAware, LanguageDAOAware, ProblemDAOAware, StatusConditionAware {

    /**
     * Code
     */
    private String codeContent;

    @Autowired
    private StatusCondition statusCondition;

    /**
     * Language ID
     */
    private Integer languageId;

    /**
     * Contest ID, null if it's not submit in contest
     */
    private Integer contestId;

    /**
     * Problem ID
     */
    private Integer problemId;

    /**
     * DAO for database
     */
    @Autowired
    private IStatusDAO statusDAO;
    @Autowired
    private ICodeDAO codeDAO;
    @Autowired
    private IContestDAO contestDAO;
    @Autowired
    private ILanguageDAO languageDAO;
    @Autowired
    private IProblemDAO problemDAO;

    /**
     * Submit code to judge core.
     * <p/>
     * <strong>JSON output</strong>:
     * <ul>
     * <li>
     * For success: {"result":"ok"}
     * </li>
     * <li>
     * For error: {"result":"error", "error_msg":<strong>error message</strong>}
     * </li>
     * </ul>
     *
     * @return JSON
     */
    @SuppressWarnings("unchecked")
    public String toSubmit() {
        try {
            if (codeContent == null || codeContent.length() < 50 || codeContent.length() > 65536)
                throw new AppException("Code length should at least 50B and at most 65536B");
            StatusDTO statusDTO = applicationContext.getBean("statusDTO", StatusDTO.class);
            if (contestId != null) {
                Contest contest = contestDAO.get(contestId);
                if (contest == null)
                    throw new AppException("No such contest");
                statusDTO.setContest(contestDAO.get(contestId));
            }
            Language language = languageDAO.get(languageId);
            if (language == null)
                throw new AppException("No such language");
            statusDTO.setLanguage(language);
            Problem problem = problemDAO.get(problemId);
            if (problem == null)
                throw new AppException("No such problem");
            statusDTO.setProblem(problem);
            statusDTO.setUser(getCurrentUser());
            CodeDTO codeDTO = applicationContext.getBean("codeDTO", CodeDTO.class);
            codeDTO.setContent(codeContent);
            Code code = codeDTO.getEntity();
            codeDAO.add(code);
            statusDTO.setCode(code);
            statusDTO.setLength(codeContent.length());
            statusDAO.add(statusDTO.getEntity());
            statusCondition.clear();
            statusCondition.setUserId(currentUser.getUserId());
            Condition condition = statusCondition.getCondition();
            Long count = statusDAO.count(condition);
            currentUser.setTried((int) count.longValue());
            userDAO.update(currentUser);
            statusCondition.clear();
            statusCondition.setProblemId(problem.getProblemId());
            condition = statusCondition.getCondition();
            count = statusDAO.count(condition);
            problem.setTried((int) count.longValue());
            problemDAO.update(problem);
            json.put("result", "ok");
        } catch (AppException e) {
            json.put("result", "error");
            json.put("error_msg", e.getMessage());
        } catch (Exception e) {
            json.put("result", "error");
            json.put("error_msg", "Unknown exception occurred.");
        }
        return JSON;
    }

    public String getCodeContent() {
        return codeContent;
    }

    public void setCodeContent(String codeContent) {
        this.codeContent = codeContent;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    @Override
    public void setCodeDAO(ICodeDAO codeDAO) {
        this.codeDAO = codeDAO;
    }

    @Override
    public void setStatusDAO(IStatusDAO statusDAO) {
        this.statusDAO = statusDAO;
    }

    @Override
    public void setContestDAO(IContestDAO contestDAO) {
        this.contestDAO = contestDAO;
    }

    @Override
    public void setProblemDAO(IProblemDAO problemDAO) {
        this.problemDAO = problemDAO;
    }

    @Override
    public void setLanguageDAO(ILanguageDAO languageDAO) {
        this.languageDAO = languageDAO;
    }

    @Override
    public void setStatusCondition(StatusCondition statusCondition) {
        this.statusCondition = statusCondition;
    }

    @Override
    public StatusCondition getStatusCondition() {
        return statusCondition;
    }
}
