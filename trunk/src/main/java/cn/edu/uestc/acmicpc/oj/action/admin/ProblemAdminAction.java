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

package cn.edu.uestc.acmicpc.oj.action.admin;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ProblemDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.view.impl.ProblemListView;
import cn.edu.uestc.acmicpc.db.view.impl.ProblemView;
import cn.edu.uestc.acmicpc.ioc.condition.ProblemConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.util.ArrayUtil;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.ReflectionUtil;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.apache.struts2.interceptor.validation.SkipValidation;

import javax.persistence.Column;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * action for list, search, edit, add problem.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(value = Global.AuthenticationType.ADMIN)
public class ProblemAdminAction extends BaseAction
        implements ProblemConditionAware, ProblemDAOAware {

    /**
     * ProblemDAO for problem search.
     */
    private IProblemDAO problemDAO;

    private ProblemCondition problemCondition;

    public ProblemAdminAction() {
    }

    @Override
    public void setProblemCondition(ProblemCondition problemCondition) {
        this.problemCondition = problemCondition;
    }

    public ProblemCondition getProblemCondition() {
        return problemCondition;
    }

    /**
     * Setter of ProblemDAO for Ioc.
     *
     * @param problemDAO newly problemDAO
     */
    public void setProblemDAO(IProblemDAO problemDAO) {
        this.problemDAO = problemDAO;
    }

    /**
     * return the problem.jsp for base view
     *
     * @return <strong>SUCCESS</strong> signal
     */
    @SuppressWarnings("SameReturnValue")
    @SkipValidation
    public String toProblemList() {
        return SUCCESS;
    }

    /**
     * editor mode flag
     */
    private String editorFlag;

    /**
     * save target problem id
     */
    private Integer targetProblemId;

    /**
     * save problem to edit
     */
    private ProblemView targetProblem;

    @SuppressWarnings("UnusedDeclaration")
    public String getEditorFlag() {
        return editorFlag;
    }

    @SuppressWarnings("UnusedDeclaration")
    public Integer getTargetProblemId() {
        return targetProblemId;
    }

    public void setTargetProblemId(Integer targetProblemId) {
        this.targetProblemId = targetProblemId;
    }

    @SuppressWarnings("UnusedDeclaration")
    public ProblemView getTargetProblem() {
        return targetProblem;
    }

    /**
     * Go to problem editor view!
     *
     * @return <strong>SUCCESS</strong> signal
     */
    @SuppressWarnings("SameReturnValue")
    @SkipValidation
    public String toProblemEditor() {
        if (targetProblemId == null) {
            try {
                ProblemDTO problemDTO = new ProblemDTO();
                Problem problem = problemDTO.getEntity();
                problemDAO.add(problem);
                targetProblemId = problem.getProblemId();
                if (targetProblemId == null)
                    throw new AppException("Add new problem error!");
            } catch (AppException e) {
                return redirect(getActionURL("/admin", "index"), e.getMessage());
            }
            return redirect(getActionURL("/admin", "problem/editor/" + targetProblemId));
        } else {
            try {
                targetProblem = new ProblemView(problemDAO.get(targetProblemId));
                if (targetProblem.getProblemId() == null)
                    throw new AppException("Wrong problem ID!");
                editorFlag = "edit";
            } catch (AppException e) {
                return redirect(getActionURL("/admin", "index"), e.getMessage());
            }
        }
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
     * "condition", <strong>ProblemCondition entity</strong>,
     * "problemList":<strong>query result</strong>}
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
            Condition condition = problemCondition.getCondition();
            Long count = problemDAO.count(problemCondition.getCondition());
            PageInfo pageInfo = buildPageInfo(count, RECORD_PER_PAGE, "", null);
            condition.currentPage = pageInfo.getCurrentPage();
            condition.countPerPage = RECORD_PER_PAGE;
            List<Problem> problemList = (List<Problem>) problemDAO.findAll(condition);
            List<ProblemListView> problemListViewList = new ArrayList<>();
            for (Problem problem : problemList)
                problemListViewList.add(new ProblemListView(problem, null,
                        problemStatus.get(problem.getProblemId())));
            json.put("pageInfo", pageInfo.getHtmlString());
            json.put("result", "ok");
            json.put("condition", problemCondition);
            json.put("problemList", problemListViewList);
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

    /**
     * Action to operate multiple problems.
     * <p/>
     * <strong>JSON output</strong>:
     * <ul>
     * <li>
     * For success: {"result":"ok", "msg":<strong>successful message</strong>}
     * </li>
     * <li>
     * For error: {"result":"error", "error_msg":<strong>error message</strong>}
     * </li>
     * </ul>
     *
     * @return <strong>JSON</strong> signal.
     */
    @SkipValidation
    public String toOperatorProblem() {
        try {
            int count = 0, total = 0;
            Integer[] ids = ArrayUtil.parseIntArray(get("id"));
            String method = get("method");
            for (Integer id : ids)
                if (id != null) {
                    ++total;
                    try {
                        Problem problem = problemDAO.get(id);
                        if ("delete".equals(method)) {
                            problemDAO.delete(problem);
                        } else if ("edit".equals(method)) {
                            String field = get("field");
                            String value = get("value");
                            Method[] methods = problem.getClass().getMethods();
                            for (Method getter : methods) {
                                Column column = getter.getAnnotation(Column.class);
                                if (column != null && column.name().equals(field)) {
                                    String setterName = StringUtil.getGetterOrSetter(StringUtil.MethodType.SETTER,
                                            getter.getName().substring(3));
                                    Method setter = problem.getClass().getMethod(setterName, getter.getReturnType());
                                    setter.invoke(problem, ReflectionUtil.valueOf(value, getter.getReturnType()));
                                }
                            }
                            problemDAO.update(problem);
                        }
                        ++count;
                    } catch (AppException ignored) {
                    }
                }
            json.put("result", "ok");
            String message = "";
            if ("delete".equals(method))
                message = String.format("%d total, %d deleted.", total, count);
            else if ("edit".equals(method))
                message = String.format("%d total, %d changed.", total, count);
            json.put("msg", message);
        } catch (Exception e) {
            json.put("result", "error");
            json.put("error_msg", "Unknown exception occurred.");
        }
        return JSON;
    }
}
