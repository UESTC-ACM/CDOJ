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

import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ProblemDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.ioc.dto.ProblemDTOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * description
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class ProblemStatementAdminAction extends BaseAction implements ProblemDAOAware,
    ProblemDTOAware {

  /**
	 * 
	 */
  private static final long serialVersionUID = 8385029063046939825L;
  /**
   * ProblemDAO for problem search.
   */
  @Autowired
  private IProblemDAO problemDAO;

  /**
   * Setter of ProblemDAO for Ioc.
   * 
   * @param problemDAO newly problemDAO
   */
  public void setProblemDAO(IProblemDAO problemDAO) {
    this.problemDAO = problemDAO;
  }

  @Override
  public ProblemDTO getProblemDTO() {
    return problemDTO;
  }

  @Override
  public void setProblemDTO(ProblemDTO problemDTO) {
    this.problemDTO = problemDTO;
  }

  /**
   * User database transform object entity.
   */
  @Autowired
  private ProblemDTO problemDTO;

  /**
   * To add or edit user entity.
   * <p/>
   * <strong>JSON output</strong>:
   * <ul>
   * <li>
   * For success: {"result":"ok"}</li>
   * <li>
   * For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   * </ul>
   * 
   * @return <strong>JSON</strong> signal
   */
  @Validations(requiredStrings = { @RequiredStringValidator(fieldName = "problemDTO.title",
      key = "error.title.validation", trim = true) })
  public String toEdit() {
    try {
      Problem problem;

      problem = problemDAO.get(problemDTO.getProblemId());
      if (problem == null)
        throw new AppException("No such problem!");

      problemDTO.updateEntity(problem);

      problemDAO.update(problem);
      json.put("result", "ok");
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
