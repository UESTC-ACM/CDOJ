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
   * Total items
   */
  private Long totalItems;
  /**
   * The minimal/maximal page will show before/after current page is current-displayDist/current+displayDist
   */
  private int displayDistance;

  private PageInfo(Long currentPage, Long countPerPage, Long totalPages, int displayDistance,
                   Long totalItems) {
    this.currentPage = currentPage;
    this.countPerPage = countPerPage;
    this.totalPages = totalPages;
    this.displayDistance = displayDistance;
    this.totalItems = totalItems;
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

  public int getDisplayDistance() {
    return displayDistance;
  }

  public void setDisplayDistance(int displayDistance) {
    this.displayDistance = displayDistance;
  }

  public Long getTotalItems() {
    return totalItems;
  }

  public void setTotalItems(Long totalItems) {
    this.totalItems = totalItems;
  }

  /**
   * Create a PageInfo object
   *
   * @param count           total number of records
   * @param countPerPage    number of records per page
   * @param displayDistance
   * @param currentPage     current page number to show
   * @return a specific PageInfo object
   */
  public static PageInfo create(Long count, Long countPerPage, int displayDistance,
                                Long currentPage) {
    countPerPage = countPerPage <= 0 ? 20 : countPerPage;
    currentPage = currentPage == null ? 1 : currentPage;

    Long totalPages = (count + countPerPage - 1) / countPerPage;
    totalPages = Math.max(1, totalPages);
    currentPage = Math.min(totalPages, Math.max(1, currentPage));

    return new PageInfo(currentPage, countPerPage, totalPages, displayDistance, count);
  }
}
