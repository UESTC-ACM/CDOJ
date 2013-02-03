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

    @SuppressWarnings("UnusedDeclaration")
    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    @SuppressWarnings("UnusedDeclaration")
    public Long getTotalPages() {
        return totalPages;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setTotalPages(Long totalPages) {
        this.totalPages = totalPages;
    }

    @SuppressWarnings("UnusedDeclaration")
    public String getBaseURL() {
        return baseURL;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    @SuppressWarnings("UnusedDeclaration")
    public int getDisplayDistance() {
        return displayDistance;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setDisplayDistance(int displayDistance) {
        this.displayDistance = displayDistance;
    }

    public String getHtmlString() {
        return htmlString;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setHtmlString(String htmlString) {
        this.htmlString = htmlString;
    }

    /**
     * get a li tag formated like <li class="sytle"><a href="url">name</a></li>
     *
     * @param style   the CSS style of li tag
     * @param toPage  the page to go
     * @param name    the name display on the pagination
     * @param baseURL the baseURL to show
     * @return a li tag
     */
    private static String getLiTag(String style, Long toPage, String name, String baseURL) {
        String result = "<li";
        if (style != null)
            result += String.format(" class=\"%s\"", style);
        result += ">";
        String url;
        if (toPage == null)
            url = "#";
        else
            url = String.format("%s%d", baseURL, toPage);
        result += String.format("<a href=\"%s\">%s</a>", url, name);
        result += "</li>\n";
        return result;
    }

    /**
     * Create a PageInfo object
     *
     * @param count        total number of records
     * @param countPerPage number of records per page
     * @param currentPage  current page number to show
     * @return a specific PageInfo object
     */
    public static PageInfo create(Long count, Long countPerPage, String baseURL,
                                  int displayDistance, Long currentPage) {
        countPerPage = countPerPage <= 0 ? 20 : countPerPage;
        currentPage = currentPage == null ? 1 : currentPage;

        Long totalPages = (count + countPerPage - 1) / countPerPage;
        currentPage = currentPage > totalPages ? totalPages : currentPage;
        currentPage = currentPage < 1 ? 1 : currentPage;

        // TODO format the HTML content use htmlString
        String htmlString = "<div class=\"pagination pagination-centered\">\n" +
                "            <ul>\n";
        if (currentPage == 1) {
            htmlString += getLiTag("disabled", null, "← First", baseURL);
            htmlString += getLiTag("disabled", null, "«", baseURL);
        } else {
            htmlString += getLiTag(null, 1L, "← First", baseURL);
            htmlString += getLiTag(null, currentPage - 1, "«", baseURL);
        }


        if (totalPages <= 3 + displayDistance * 2) {
            for (long i = 1; i <= totalPages; i++)
                if (i == currentPage)
                    htmlString += getLiTag("active", i, String.format("%d", i), baseURL);
                else
                    htmlString += getLiTag(null, i, String.format("%d", i), baseURL);
        } else {
            if (currentPage <= 2 + displayDistance) {
                for (long i = 1; i <= 2 + displayDistance * 2; i++)
                    if (i == currentPage)
                        htmlString += getLiTag("active", i, String.format("%d", i), baseURL);
                    else
                        htmlString += getLiTag(null, i, String.format("%d", i), baseURL);
                htmlString += getLiTag("disabled", null, "...", baseURL);
            } else if (currentPage >= totalPages - 1 - displayDistance) {
                htmlString += getLiTag("disabled", null, "...", baseURL);
                for (long i = totalPages - 1 - displayDistance * 2; i <= totalPages; i++)
                    if (i == currentPage)
                        htmlString += getLiTag("active", i, String.format("%d", i), baseURL);
                    else
                        htmlString += getLiTag(null, i, String.format("%d", i), baseURL);
            } else {
                htmlString += getLiTag("disabled", null, "...", baseURL);
                for (long i = currentPage - displayDistance; i <= currentPage + displayDistance; i++)
                    if (i == currentPage)
                        htmlString += getLiTag("active", i, String.format("%d", i), baseURL);
                    else
                        htmlString += getLiTag(null, i, String.format("%d", i), baseURL);
                htmlString += getLiTag("disabled", null, "...", baseURL);
            }
        }

        if (currentPage.equals(totalPages)) {
            htmlString += getLiTag("disabled", null, "»", baseURL);
            htmlString += getLiTag("disabled", null, "Last →", baseURL);
        } else {
            htmlString += getLiTag(null, currentPage + 1, "»", baseURL);
            htmlString += getLiTag(null, totalPages, "Last →", baseURL);
        }

        htmlString += "\n" +
                "            </ul>\n" +
                "        </div>\n";

        return new PageInfo(currentPage, countPerPage, baseURL, displayDistance, htmlString);
    }
}
