package cn.edu.uestc.acmicpc.web.oj.controller.base;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;

/**
 * BaseController
 */
@Controller
public class BaseController {

  protected UserDto getCurrentUser(HttpSession session) throws AppException {
    return (UserDto) session.getAttribute("currentUser");
  }

  protected Boolean isAdmin(HttpSession session) throws AppException {
    UserDto userDto = (UserDto) session.getAttribute("currentUser");
    return userDto != null && userDto.getType() == AuthenticationType.ADMIN.ordinal();
  }

  protected Boolean checkPermission(HttpSession session, Integer userId) throws AppException {
    UserDto userDto = (UserDto) session.getAttribute("currentUser");
    return userDto != null
        && (userDto.getUserId().equals(userId) || userDto.getType() == AuthenticationType.ADMIN
            .ordinal());
  }

  protected Boolean checkPermission(HttpSession session, String userName) throws AppException {
    UserDto userDto = (UserDto) session.getAttribute("currentUser");
    return userDto != null
        && (userDto.getUserName().equals(userName) || userDto.getType() == AuthenticationType.ADMIN
            .ordinal());
  }

  protected void checkContestPermission(HttpSession session, Integer contestId) throws AppException {
    String attributeName = "ContestPermission#" + contestId;
    if (session.getAttribute(attributeName) == null) {
      throw new AppException("Permission denied");
    }
  }

  @SuppressWarnings("unchecked")
  protected List<Integer> getContestTeamMembers(HttpSession session, Integer contestId) {
    String attributeName = "ContestPermission#" + contestId + "#members";
    return new ArrayList<Integer>((Set<Integer>) session.getAttribute(attributeName));
  }

  protected Byte getContestType(HttpSession session, Integer contestId) {
    String attributeName = "ContestPermission#" + contestId + "#type";
    return (Byte) session.getAttribute(attributeName);
  }

  protected void setContestPermission(HttpSession session, Integer contestId) {
    String attributeName = "ContestPermission#" + contestId;
    session.setAttribute(attributeName, true);
  }

  protected void setContestType(HttpSession session, Integer contestId, Byte type) {
    String attributeName = "ContestPermission#" + contestId + "#type";
    session.setAttribute(attributeName, type);
  }

  protected void setContestTeamMembers(HttpSession session, Integer contestId,
      Set<Integer> members) {
    String attributeName = "ContestPermission#" + contestId + "#members";
    session.setAttribute(attributeName, members);
  }

  /**
   * Put field errors into binding result
   *
   * @param fieldException
   *          field exception
   * @param validateResult
   *          prev validate result
   */
  protected void putFieldErrorsIntoBindingResult(FieldException fieldException,
      BindingResult validateResult) {
    for (FieldError error : fieldException) {
      validateResult.addError(error);
    }
  }

  /**
   * Build a page html content according to number of records, records per page,
   * base URL and display distance.
   * <p>
   * <strong>Example:</strong> Get total and set it into {@code buildPageInfo}
   * method: <br>
   * {@code PageInfo pageInfo = buildPageInfo(articleDAO.count(), RECORD_PER_PAGE,
   * getContextPath("") + "/Problem", null);}
   *
   * @param count
   *          total number of records
   * @param currentPage
   *          current page number
   * @param countPerPage
   *          number of records per page
   * @param displayDistance
   *          display distance for page numbers
   * @return return a PageInfo object and put the HTML content into request
   *         attribute list.
   */
  protected PageInfo buildPageInfo(Long count, Long currentPage, Long countPerPage,
      Integer displayDistance) {
    return PageInfo.create(count, countPerPage, displayDistance == null ? 2 : displayDistance,
        currentPage);
  }

}
