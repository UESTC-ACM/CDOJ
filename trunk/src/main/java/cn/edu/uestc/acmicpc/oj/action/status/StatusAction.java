/*
 * cdoj, UESTC ACMICPC Online Judge
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,mzry1992 <@link muziriyun@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package cn.edu.uestc.acmicpc.oj.action.status;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.db.entity.CompileInfo;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.db.view.impl.CodeView;
import cn.edu.uestc.acmicpc.db.view.impl.StatusView;
import cn.edu.uestc.acmicpc.ioc.condition.StatusConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.StatusDAOAware;
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
 * Action for list and search all submit status
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(NeedLogin = false)
public class StatusAction extends BaseAction implements StatusConditionAware, StatusDAOAware {

  /**
	 * 
	 */
  private static final long serialVersionUID = 8258180610533072271L;

  @SkipValidation
  public String toStatusList() {
    return SUCCESS;
  }

  /**
   * StatusDAO for status queries.
   */
  @Autowired
  private IStatusDAO statusDAO;
  @Autowired
  private StatusCondition statusCondition;

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
   * <strong>ProblemCondition entity</strong>, "problemList":<strong>query result</strong>}</li>
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
      statusCondition.setContestId(-1);
      Condition condition = statusCondition.getCondition();
      Long count = statusDAO.count(statusCondition.getCondition());
      PageInfo pageInfo = buildPageInfo(count, RECORD_PER_PAGE, "", null);
      condition.setCurrentPage(pageInfo.getCurrentPage());
      condition.setCountPerPage(RECORD_PER_PAGE);
      condition.addOrder("statusId", false);
      List<Status> statusList = (List<Status>) statusDAO.findAll(condition);
      List<StatusView> statusViewList = new ArrayList<>();
      for (Status status : statusList)
        if (status.getProblemByProblemId().getIsVisible() || (currentUser != null && currentUser.getType() == Global.AuthenticationType.ADMIN.ordinal()))
          statusViewList.add(new StatusView(status));
      json.put("pageInfo", pageInfo.getHtmlString());
      json.put("result", "ok");
      json.put("statusList", statusViewList);
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
  public void setStatusCondition(StatusCondition statusCondition) {
    this.statusCondition = statusCondition;
  }

  @Override
  public StatusCondition getStatusCondition() {
    return statusCondition;
  }

  @Override
  public void setStatusDAO(IStatusDAO statusDAO) {
    this.statusDAO = statusDAO;
  }

  private Integer statusId;

  public Integer getStatusId() {
    return statusId;
  }

  public void setStatusId(Integer statusId) {
    this.statusId = statusId;
  }

  @LoginPermit(NeedLogin = true)
  public String toCode() {
    try {
      if (statusId == null)
        throw new AppException("Empty status id!");
      Status status = statusDAO.get(statusId);
      if (status == null)
        throw new AppException("No such code!");
      if (currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal()
          && status.getUserByUserId() != currentUser)
        throw new AppException("You can't view others' code!");
      Code code = status.getCodeByCodeId();
      json.put("result", "success");
      json.put("code", new CodeView(code));
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

  @LoginPermit(NeedLogin = true)
  public String toCEInformation() {
    try {
      if (statusId == null)
        throw new AppException("Empty status id!");
      Status status = statusDAO.get(statusId);
      if (status == null)
        throw new AppException("No such code!");
      if (currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal()
          && status.getUserByUserId() != currentUser)
        throw new AppException("You can't view others' CE information!");
      CompileInfo compileInfo = status.getCompileInfoByCompileInfoId();
      if (compileInfo == null)
        throw new AppException("No CE information!");
      json.put("result", "success");
      json.put("CEInformation", compileInfo.getContent());
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
}
