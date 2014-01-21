package cn.edu.uestc.acmicpc.web.dto;

/**
 * Object to build view's page tags.
 */
public class PageInfo {

  /**
   * Current page number
   */
  private Long currentPage;
  /**
   * Number of records per page.
   */
  private Long countPerPage;
  /**
   * Total number of pages
   */
  private Long totalPages;
  /**
   * The base URL, in other words, URL prefix
   */
  private String baseURL;
  /**
   * The minimal/maximal page will show before/after current page is current-displayDist/current+displayDist
   */
  private int displayDistance;

  /**
   * HTML content for output
   */
  private String htmlString;

  private PageInfo(Long currentPage, Long countPerPage, Long totalPages, String baseURL, int displayDistance,
                   String htmlString) {
    this.currentPage = currentPage;
    this.countPerPage = countPerPage;
    this.totalPages = totalPages;
    this.baseURL = baseURL;
    this.displayDistance = displayDistance;
    this.htmlString = htmlString;
  }

  public Long getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Long currentPage) {
    this.currentPage = currentPage;
  }

  public Long getCountPerPage() {
    return countPerPage;
  }

  public void setCountPerPage(Long countPerPage) {
    this.countPerPage = countPerPage;
  }

  public Long getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Long totalPages) {
    this.totalPages = totalPages;
  }

  public String getBaseURL() {
    return baseURL;
  }

  public void setBaseURL(String baseURL) {
    this.baseURL = baseURL;
  }

  public int getDisplayDistance() {
    return displayDistance;
  }

  public void setDisplayDistance(int displayDistance) {
    this.displayDistance = displayDistance;
  }

  public String getHtmlString() {
    return htmlString;
  }

  public void setHtmlString(String htmlString) {
    this.htmlString = htmlString;
  }

  /**
   * get a {@code li} tag formatted like {@code <li class="sytle"><a href="url">name</a></li>}
   *
   * @param style   the CSS style of {@code li} tag
   * @param toPage  the page to go
   * @param name    the name display on the pagination
   * @param baseURL the baseURL to show
   * @return a {@code li} tag
   */
  private static String getLiTag(String style, Long toPage, String name, String baseURL) {
    String result = "<li";
    if (style != null)
      result += String.format(" class=\"%s\"", style);
    result += ">";
    String url;
    if (toPage == null)
      result += String.format("<a>%s</a>", name);
    else {
      url = String.format("%s%d", baseURL, toPage);
      result += String.format("<a href=\"%s\">%s</a>", url, name);
    }
    result += "</li>\n";
    return result;
  }

  /**
   * Create a PageInfo object
   *
   * @param count           total number of records
   * @param countPerPage    number of records per page
   * @param baseURL         page's base URL
   * @param displayDistance
   * @param currentPage     current page number to show
   * @return a specific PageInfo object
   */
  public static PageInfo create(Long count, Long countPerPage, String baseURL, int displayDistance,
                                Long currentPage) {
    countPerPage = countPerPage <= 0 ? 20 : countPerPage;
    currentPage = currentPage == null ? 1 : currentPage;

    Long totalPages = (count + countPerPage - 1) / countPerPage;
    totalPages = Math.max(1, totalPages);
    currentPage = Math.min(totalPages, Math.max(1, currentPage));

    // Calculate display range
    Long startPage = Math.max(1, currentPage - displayDistance);
    Long endPage = Math.min(totalPages, startPage + displayDistance * 2);
    // Try to display 2 * displayDistance + 1 pages
    if (endPage == totalPages) {
      startPage = Math.max(1, endPage - displayDistance * 2);
    }

    // Build html
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<ul class=\"pagination pagination-sm\">\n");
    if (startPage > 1) {
      stringBuilder.append(getLiTag(null, 1L, "<i class=\"fa fa-arrow-left\"></i>", baseURL));
    }
    for (long i = startPage; i <= endPage; i++) {
      if (i == currentPage)
        stringBuilder.append(getLiTag("active", i, String.format("%d", i), baseURL));
      else
        stringBuilder.append(getLiTag(null, i, String.format("%d", i), baseURL));
    }
    if (endPage < totalPages) {
      stringBuilder.append(getLiTag(null, totalPages, "<i class=\"fa fa-arrow-right\"></i>", baseURL));
    }
    stringBuilder.append("\n            </ul>\n");

    return new PageInfo(currentPage, countPerPage, totalPages, baseURL, displayDistance, stringBuilder.toString());
  }
}
