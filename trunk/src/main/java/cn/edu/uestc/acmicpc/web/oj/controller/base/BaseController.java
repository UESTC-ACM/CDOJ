package cn.edu.uestc.acmicpc.web.oj.controller.base;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;

import cn.edu.uestc.acmicpc.db.dto.impl.department.DepartmentDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

/**
 * BaseController
 */
@Controller
public class BaseController {

  protected DepartmentService departmentService;
  protected GlobalService globalService;

  @Autowired
  public void setDepartmentService(DepartmentService departmentService) {
    this.departmentService = departmentService;
  }

  @Autowired
  public void setGlobalService(GlobalService globalService) {
    this.globalService = globalService;
  }

  @ModelAttribute("departmentList")
  protected List<DepartmentDTO> getDepartmentList() {
    return departmentService.getDepartmentList();
  }

  @ModelAttribute("authenticationTypeList")
  protected List<Global.AuthenticationType> getAuthenticationTypeList() {
    return globalService.getAuthenticationTypeList();
  }

  protected Integer getCurrentUserID(HttpSession session) throws AppException {
    UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
    return userDTO.getUserId();
  }

  /**
   * Put field errors into binding result
   *
   * @param fieldException field exception
   * @param validateResult prev validate result
   */
  protected void putFieldErrorsIntoBindingResult(FieldException fieldException,
      BindingResult validateResult) {
    for (FieldError error: fieldException) {
      validateResult.addError(error);
    }
  }

  /**
   * Build a page html content according to number of records, records per page, base URL and
   * display distance.
   * <p/>
   * <strong>Example:</strong> Get total and set it into {@code buildPageInfo} method: <br />
   * {@code PageInfo pageInfo = buildPageInfo(articleDAO.count(), RECORD_PER_PAGE,
   * getContextPath("") + "/Problem", null);}
   *
   * @param count total number of records
   * @param currentPage current page number
   * @param countPerPage number of records per page
   * @param baseURL base URL
   * @param displayDistance display distance for page numbers
   * @return return a PageInfo object and put the HTML content into request attribute list.
   */
  protected PageInfo buildPageInfo(Long count, Long currentPage, Long countPerPage, String baseURL,
                                   Integer displayDistance) {
    return PageInfo.create(count, countPerPage, baseURL,
        displayDistance == null ? 2 : displayDistance, currentPage);
  }

}
