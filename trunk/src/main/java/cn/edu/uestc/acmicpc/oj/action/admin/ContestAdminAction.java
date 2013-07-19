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
import cn.edu.uestc.acmicpc.db.condition.impl.ContestCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.db.view.impl.ContestListView;
import cn.edu.uestc.acmicpc.db.view.impl.ContestView;
import cn.edu.uestc.acmicpc.ioc.condition.ContestConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.ContestDAOAware;
import cn.edu.uestc.acmicpc.ioc.dto.ContestDTOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.util.ArrayUtil;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.ReflectionUtil;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Contest admin main page
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */
@LoginPermit(value = Global.AuthenticationType.ADMIN)
public class ContestAdminAction extends BaseAction implements ContestDAOAware,
    ContestConditionAware, ContestDTOAware {

  /**
	 * 
	 */
  private static final long serialVersionUID = -8303828012061242917L;

  @Autowired
  private IContestDAO contestDAO;

  @Autowired
  private ContestCondition contestCondition;

  @Autowired
  private ContestDTO contestDTO;

  public String toContestList() {
    return SUCCESS;
  }

  private String editorFlag;

  public String getEditorFlag() {
    return editorFlag;
  }

  public void setEditorFlag(String editorFlag) {
    this.editorFlag = editorFlag;
  }

  public Integer getTargetContestId() {
    return targetContestId;
  }

  public void setTargetContestId(Integer targetContestId) {
    this.targetContestId = targetContestId;
  }

  public ContestView getTargetContest() {
    return targetContest;
  }

  public void setTargetContest(ContestView targetContest) {
    this.targetContest = targetContest;
  }

  private Integer targetContestId;
  private ContestView targetContest;

  /**
   * Go to contest editor view!
   * 
   * @return <strong>SUCCESS</strong> signal
   */
  @SuppressWarnings("unchecked")
  public String toContestEditor() {
    try {
      if (targetContestId == null) {
        contestCondition.clear();
        contestCondition.setIsTitleEmpty(true);
        Condition condition = contestCondition.getCondition();
        Long count = contestDAO.count(condition);

        if (count == 0) {
          Contest contest = contestDTO.getEntity();
          contestDAO.add(contest);
          targetContestId = contest.getContestId();
        } else {
          List<Contest> result =
              (List<Contest>) contestDAO.findAll(contestCondition.getCondition());
          if (result == null || result.size() == 0)
            throw new AppException("Add new contest error!");
          Contest contest = result.get(0);
          contest.setTime(new Timestamp(new Date().getTime()));
          contestDAO.update(contest);
          targetContestId = contest.getContestId();
        }

        if (targetContestId == null)
          throw new AppException("Add new contest error!");

        return redirect(getActionURL("/admin", "contest/editor/" + targetContestId));
      } else {
        targetContest = new ContestView(contestDAO.get(targetContestId));
        if (targetContest.getContestId() == null)
          throw new AppException("Wrong contest ID!");
        editorFlag = "edit";
      }
    } catch (AppException e) {
      return redirect(getActionURL("/admin", "index"), e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return redirect(getActionURL("/admin", "index"), "Unknown exception occurred.");
    }
    return SUCCESS;
  }

  /**
   * Search action.
   * <p/>
   * Find all records by conditions and return them as a list in JSON, and the condition set will
   * set in JSON named "condition".
   * <p/>
   * <strong>JSON output</strong>:
   * <ul>
   * <li>
   * For success: {"result":"ok", "pageInfo":<strong>PageInfo object</strong>, "condition",
   * <strong>ContestCondition entity</strong>, "contestList":<strong>query result</strong>}</li>
   * <li>
   * For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   * </ul>
   * 
   * @return <strong>JSON</strong> signal
   */
  @SuppressWarnings("unchecked")
  @SkipValidation
  public String toSearch() {
    try {
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

  /**
   * Action to operate multiple contests.
   * <p/>
   * <strong>JSON output</strong>:
   * <ul>
   * <li>
   * For success: {"result":"ok", "msg":<strong>successful message</strong>}</li>
   * <li>
   * For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   * </ul>
   * 
   * @return <strong>JSON</strong> signal.
   */
  @SkipValidation
  public String toOperatorContest() {
    try {
      int count = 0, total = 0;
      Integer[] ids = ArrayUtil.parseIntArray(get("id"));
      String method = get("method");
      for (Integer id : ids)
        if (id != null) {
          ++total;
          try {
            Contest contest = contestDAO.get(id);
            if ("delete".equals(method)) {
              contestDAO.delete(contest);
            } else if ("edit".equals(method)) {
              String field = get("field");
              String value = get("value");
              Method[] methods = contest.getClass().getMethods();
              for (Method getter : methods) {
                Column column = getter.getAnnotation(Column.class);
                if (column != null && column.name().equals(field)) {
                  String setterName =
                      StringUtil.getGetterOrSetter(StringUtil.MethodType.SETTER, getter.getName()
                          .substring(3));
                  Method setter = contest.getClass().getMethod(setterName, getter.getReturnType());
                  setter.invoke(contest, ReflectionUtil.valueOf(value, getter.getReturnType()));
                }
              }
              contestDAO.update(contest);
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

  @Override
  public void setContestDAO(IContestDAO contestDAO) {
    this.contestDAO = contestDAO;
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
  public void setContestDTO(ContestDTO contestDTO) {
    this.contestDTO = contestDTO;
  }

  @Override
  public ContestDTO getContestDTO() {
    return contestDTO;
  }
}
