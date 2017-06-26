package cn.edu.uestc.acmicpc.web.oj.controller.base;

import cn.edu.uestc.acmicpc.db.dto.Fields;
import cn.edu.uestc.acmicpc.db.dto.FieldsUtil;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDto;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
    return new ArrayList<>((Set<Integer>) session.getAttribute(attributeName));
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
   * @param fieldException field exception
   * @param validateResult prev validate result
   * @deprecated please use {@code filteredFieldErrors#addAll}.
   */
  @Deprecated
  protected void putFieldErrorsIntoBindingResult(
      FieldException fieldException, BindingResult validateResult) {
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
   * @param count           total number of records
   * @param currentPage     current page number
   * @param countPerPage    number of records per page
   * @param displayDistance display distance for page numbers
   * @return return a PageInfo object and put the HTML content into request
   * attribute list.
   */
  protected PageInfo buildPageInfo(
      Long count, Long currentPage, Long countPerPage, Integer displayDistance) {
    return PageInfo.create(count, countPerPage, displayDistance == null ? 2 : displayDistance,
        currentPage);
  }

  protected List<FieldError> filterFieldErrorsByFields(
      BindingResult validateResult, Set<? extends Fields> fields, String overriddenObjectName) {
    if (!validateResult.hasErrors()) {
      return ImmutableList.of();
    }
    List<FieldError> errors = validateResult.getFieldErrors();
    List<FieldError> results = new ArrayList<>();
    Set<String> neededFilters = FieldsUtil.getFieldNamesByFieldSet(fields);
    errors.stream().filter(error -> neededFilters.contains(error.getField()))
        .forEach(error -> results.add(new FieldError(
            overriddenObjectName,
            error.getField(),
            error.getRejectedValue(),
            error.isBindingFailure(),
            error.getCodes(),
            error.getArguments(),
            error.getDefaultMessage())));
    return results;
  }
}
