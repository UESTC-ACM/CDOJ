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

import cn.edu.uestc.acmicpc.db.dao.iface.*;
import cn.edu.uestc.acmicpc.db.dto.impl.CodeDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.StatusDTO;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.db.entity.Language;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.ioc.dao.*;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * action for submit code.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@SuppressWarnings("UnusedDeclaration")
@LoginPermit(NeedLogin = true)
public class SubmitAction extends BaseAction
        implements StatusDAOAware, CodeDAOAware, ContestDAOAware, LanguageDAOAware, ProblemDAOAware {

    /**
     * Code
     */
    private String codeContent;

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
    private IStatusDAO statusDAO;
    private ICodeDAO codeDAO;
    private IContestDAO contestDAO;
    private ILanguageDAO languageDAO;
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
    public String toSubmit() {
        try {
            StatusDTO statusDTO = new StatusDTO();
            statusDTO.setContest(contestDAO.get(contestId));
            Language language = languageDAO.get(languageId);
            if (language == null)
                throw new AppException("No such language");
            statusDTO.setLanguage(language);
            Problem problem = problemDAO.get(problemId);
            if (problem == null)
                throw new AppException("No such problem");
            problem.setTried(problem.getTried() + 1);
            problemDAO.update(problem);
            statusDTO.setProblem(problem);
            statusDTO.setUser(getCurrentUser());
            CodeDTO codeDTO = new CodeDTO();
            codeDTO.setContent(codeContent);
            Code code = codeDTO.getEntity();
            codeDAO.add(code);
            statusDTO.setCode(code);
            statusDTO.setLength(codeContent.length());
            statusDAO.add(statusDTO.getEntity());
        } catch (AppException e) {
            json.put("result", "error");
            json.put("error_msg", e.getMessage());
        } catch (Exception e) {
            json.put("result", "error");
            json.put("error_msg", "Unknown exception occurred.");
        }
        json.put("result", "ok");
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
}
