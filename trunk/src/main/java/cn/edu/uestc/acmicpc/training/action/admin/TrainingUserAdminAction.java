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

package cn.edu.uestc.acmicpc.training.action.admin;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.TrainingUserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingUserDAO;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.db.view.impl.TrainingUserView;
import cn.edu.uestc.acmicpc.ioc.condition.TrainingUserConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingUserDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.util.ArrayUtil;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.ReflectionUtil;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Column;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * Description
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(value = Global.AuthenticationType.ADMIN)
public class TrainingUserAdminAction extends BaseAction implements TrainingUserConditionAware,
    TrainingUserDAOAware {

  /**
	 * 
	 */
  private static final long serialVersionUID = -2720723992092063656L;

  @SuppressWarnings("unchecked")
  public String toMemberList() {
    try {
      Condition condition = trainingUserCondition.getCondition();
      Long count = trainingUserDAO.count(trainingUserCondition.getCondition());
      PageInfo pageInfo = buildPageInfo(count, RECORD_PER_PAGE, "", null);
      condition.setCurrentPage(pageInfo.getCurrentPage());
      condition.setCountPerPage(RECORD_PER_PAGE);
      List<TrainingUser> trainingUserList = (List<TrainingUser>) trainingUserDAO.findAll(condition);
      List<TrainingUserView> trainingUserViewList = new LinkedList<>();
      for (TrainingUser trainingUser : trainingUserList)
        trainingUserViewList.add(new TrainingUserView(trainingUser));

      json.put("pageInfo", pageInfo.getHtmlString());
      json.put("trainingUserList", trainingUserViewList);
      json.put("result", "ok");
    } catch (AppException e) {
      json.put("result", "error");
    } catch (Exception e) {
      json.put("result", "error");
      e.printStackTrace();
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
   * For success: {"result":"ok", "msg":<strong>successful message</strong>}</li>
   * <li>
   * For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   * </ul>
   * 
   * @return <strong>JSON</strong> signal.
   */
  public String toOperatorTrainingUser() {
    try {
      int count = 0, total = 0;
      Integer[] ids = ArrayUtil.parseIntArray(get("id"));
      String method = get("method");
      for (Integer id : ids)
        if (id != null) {
          ++total;
          try {
            TrainingUser trainingUser = trainingUserDAO.get(id);
            if ("delete".equals(method)) {
              trainingUserDAO.delete(trainingUser);
            } else if ("edit".equals(method)) {
              String field = get("field");
              String value = get("value");
              Method[] methods = trainingUser.getClass().getMethods();
              for (Method getter : methods) {
                Column column = getter.getAnnotation(Column.class);
                if (column != null && column.name().equals(field)) {
                  String setterName =
                      StringUtil.getGetterOrSetter(StringUtil.MethodType.SETTER, getter.getName()
                          .substring(3));
                  Method setter =
                      trainingUser.getClass().getMethod(setterName, getter.getReturnType());
                  setter
                      .invoke(trainingUser, ReflectionUtil.valueOf(value, getter.getReturnType()));
                }
              }
              trainingUserDAO.update(trainingUser);
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

  @Autowired
  private TrainingUserCondition trainingUserCondition;

  @Override
  public void setTrainingUserCondition(TrainingUserCondition trainingUserCondition) {
    this.trainingUserCondition = trainingUserCondition;
  }

  @Override
  public TrainingUserCondition getTrainingUserCondition() {
    return this.trainingUserCondition;
  }

  @Autowired
  private ITrainingUserDAO trainingUserDAO;

  @Override
  public void setTrainingUserDAO(ITrainingUserDAO trainingUserDAO) {
    this.trainingUserDAO = trainingUserDAO;
  }
}
