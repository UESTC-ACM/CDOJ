package cn.edu.uestc.acmicpc.oj.controller.base;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.util.exception.FieldException;

/**
 * BaseController TODO: Exception handler description
 */
@Controller
public class BaseController {

  /**
   * Put field errors into binding result
   *
   * @param fieldException field exception
   * @param validateResult prev validate result
   */
  protected void putFieldErrorsIntoBindingResult(FieldException fieldException, BindingResult validateResult) {
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
   * @param countPerPage number of records per page
   * @param baseURL base URL
   * @param displayDistance display distance for page numbers
   * @return return a PageInfo object and put the HTML content into request attribute list.
   */
  protected PageInfo buildPageInfo(Long count, Long currentPage, Long countPerPage, String baseURL,
                                   Integer displayDistance) {
    return PageInfo.create(count, countPerPage, baseURL,
        displayDistance == null ? 4 : displayDistance, currentPage);
  }

}
