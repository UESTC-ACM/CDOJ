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

package cn.edu.uestc.acmicpc.oj.action.admin;

import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.ioc.dao.ContestDAOAware;
import cn.edu.uestc.acmicpc.ioc.dto.ContestDTOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * Use for edit contest info.
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */
@Controller
public class AdminContestStatementAction extends BaseAction implements ContestDAOAware,
    ContestDTOAware {

  /**
	 * 
	 */
  private static final long serialVersionUID = 7615764585918581076L;

  /**
   * Use for update contest info
   */
  @Autowired
  private IContestDAO contestDAO;

  @Autowired
  private ContestDTO contestDTO;

  @Override
  public ContestDTO getContestDTO() {
    return contestDTO;
  }

  @Override
  public void setContestDTO(ContestDTO contestDTO) {
    this.contestDTO = contestDTO;
  }

  /**
   * To add or edit contest entity.
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
  @Validations(requiredStrings = { @RequiredStringValidator(fieldName = "contestDTO.title",
      key = "error.contestTitle.validation", trim = true) })
  public String toEdit() {
    try {
      Contest contest = contestDAO.get(contestDTO.getContestId());

      if (contest == null)
        throw new AppException("No such contest!");

      contestDTO.updateEntity(contest);

      contestDAO.update(contest);

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

  @Override
  public void setContestDAO(IContestDAO contestDAO) {
    this.contestDAO = contestDAO;
  }

}
