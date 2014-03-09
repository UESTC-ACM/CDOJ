package cn.edu.uestc.acmicpc.web.oj.controller.base;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpSession;

/**
 * BaseController
 */
@Controller
public class BaseController {

  protected UserDTO getCurrentUser(HttpSession session) throws AppException {
    return (UserDTO) session.getAttribute("currentUser");
  }

  protected Boolean isAdmin(HttpSession session) throws AppException {
    UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
    return userDTO != null && userDTO.getType() == Global.AuthenticationType.ADMIN.ordinal();
  }

  protected Boolean checkPermission(HttpSession session, Integer userId) throws AppException {
    UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
    return userDTO != null && (userDTO.getUserId().equals(userId) || userDTO.getType() == Global.AuthenticationType.ADMIN.ordinal());
  }

  protected Boolean checkPermission(HttpSession session, String userName) throws AppException {
    UserDTO userDTO = (UserDTO) session.getAttribute("currentUser");
    return userDTO != null && (userDTO.getUserName().equals(userName) || userDTO.getType() == Global.AuthenticationType.ADMIN.ordinal());
  }
  /**
   * Put field errors into binding result
   *
   * @param fieldException field exception
   * @param validateResult prev validate result
   */
  protected void putFieldErrorsIntoBindingResult(FieldException fieldException,
                                                 BindingResult validateResult) {
    for (FieldError error : fieldException) {
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
   * @param count           total number of records
   * @param currentPage     current page number
   * @param countPerPage    number of records per page
   * @param displayDistance display distance for page numbers
   * @return return a PageInfo object and put the HTML content into request attribute list.
   */
  protected PageInfo buildPageInfo(Long count, Long currentPage, Long countPerPage,
                                   Integer displayDistance) {
    return PageInfo.create(count, countPerPage, displayDistance == null ? 2 : displayDistance,
        currentPage);
  }

}
