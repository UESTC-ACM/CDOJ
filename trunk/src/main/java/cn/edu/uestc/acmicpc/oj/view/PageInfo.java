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

package cn.edu.uestc.acmicpc.oj.view;

/**
 * Object to build view's page tags.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 2
 */
public class PageInfo {
    /**
     * Current page number
     */
    private Long currentPage;
    /**
     * Total number of pages
     */
    private Long totalPages;
    /**
     * The base URL, in other words, URL prefix
     */
    private String baseURL;
    /**
     * The min/max page will show before/after current page is current-displayDist/current+displayDist
     */
    private int displayDistance;

    /**
     * HTML content for output
     */
    private String htmlString;

    private PageInfo(Long currentPage, Long totalPages,
                     String baseURL, int displayDistance, String htmlString) {
        this.currentPage = currentPage;
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
     * Create a PageInfo object
     *
     * @param count        total number of records
     * @param countPerPage number of records per page
     * @param queryString  html query string, which start with ? in URL string
     * @return a specific PageInfo object
     */
    public static PageInfo create(Long count, Long countPerPage, String baseURL,
                                  int displayDistance, String queryString) {
        queryString = queryString == null ? "" : queryString;
        countPerPage = countPerPage <= 0 ? 20 : countPerPage;
        Long currentPage = 1L;
        if (queryString.startsWith("page=")
                || queryString.indexOf("&page=") > -1) {
            String page = queryString.replaceAll(".*&?page=", "");
            page = page.replaceAll("&.*", "");
            try {
                currentPage = Long.parseLong(page);
            } catch (Exception e) {
            }
        }
        queryString = queryString.replaceAll("&?page=\\d+", "");
        queryString = queryString.length() < 1 || queryString.startsWith("&") ? queryString
                : "&" + queryString;
        currentPage = currentPage == 0 ? 1 : currentPage;
        Long max = (count + countPerPage - 1) / countPerPage;
        currentPage = currentPage > max ? max : currentPage;
        currentPage = currentPage < 1 ? 1 : currentPage;
        // TODO format the HTML content use htmlString
        String htmlString = "";
        return new PageInfo(currentPage, countPerPage, baseURL, displayDistance, htmlString);
    }
}
