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

package cn.edu.uestc.acmicpc.oj.tag;

import com.opensymphony.xwork2.util.ValueStack;

import java.io.Writer;

/**
 * A tag service to show the pagination.
 * <p/>
 * ← First | « | {pages} |  | » | Last →
 * and {pages} is like this:
 * <p/>
 * situation 1: the total pages is small than 3+displayDist*2
 * 1 | 2 | --- | total
 * <p/>
 * situation 2: the current pages is small than 2+dispalyDist
 * midPage
 * 1 | midPage-displayDist | --- | ? |  2+displayDist  | ? |  --- | 2+2*displayDist | ...
 * <p/>
 * situation 3: the current pages is big than total-1-displayDist
 * midPage
 * ...| total-1-2*displayDist | --- | ? |  total-1-displayDist  | ? |  --- | total-1 | total
 * <p/>
 * other situations:
 * midPage
 * ...| current-displayDist | --- | ? | current | ? |  --- | current+displayDist | ...
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

public class PaginationTagService extends TagService {

    /**
     * current page
     */
    private Integer current;

    /**
     * total page
     */
    private Integer total;

    /**
     * the base url like /problemset/pages/
     */
    private String baseUrl;

    /**
     * the min/max page will show before/after current page is current-displayDist/current+displayDist
     */
    private Integer displayDist;

    public PaginationTagService(ValueStack valueStack) {
        super(valueStack);
    }

    /**
     * get a li tag formated like <li class="sytle"><a href="url">name</a></li>
     *
     * @param style  the CSS style of li tag
     * @param toPage the page to go
     * @param name   the name display on the pagination
     * @return a li tag
     */
    private String getLiTag(String style, Integer toPage, String name) {
        String result = "<li";
        if (style != null)
            result += String.format(" class=\"%s\"", style);
        result += ">";
        String url;
        if (toPage == null)
            url = "#";
        else
            url = String.format("%s%d", getBaseUrl(), toPage);
        result += String.format("<a href=\"%s\">%s</a>", url, name);
        result += "</li>\n";
        return result;
    }

    /**
     * start parse pagination
     *
     * @param writer
     * @return super.start(writer)
     */
    @Override
    public boolean start(Writer writer) {
        try {
            if (current != null && total != null && baseUrl != null) {
                if (current < 1 || current > total)
                    current = 1;//@TODO return an error will be better?

                writer.write("<div class=\"pagination pagination-centered\">\n" +
                        "            <ul>\n");
                if (current == 1) {
                    writer.write(getLiTag("disabled", null, "← First"));
                    writer.write(getLiTag("disabled", null, "«"));
                } else {
                    writer.write(getLiTag(null, 1, "← First"));
                    writer.write(getLiTag(null, current - 1, "«"));
                }


                if (total <= 3 + displayDist * 2) {
                    for (int i = 1; i <= total; i++)
                        if (i == current)
                            writer.write(getLiTag("active", i, String.format("%d", i)));
                        else
                            writer.write(getLiTag(null, i, String.format("%d", i)));
                } else {
                    if (current <= 2 + displayDist) {
                        for (int i = 1; i <= 2 + displayDist * 2; i++)
                            if (i == current)
                                writer.write(getLiTag("active", i, String.format("%d", i)));
                            else
                                writer.write(getLiTag(null, i, String.format("%d", i)));
                        writer.write(getLiTag("disabled", null, "..."));
                    } else if (current >= total - 1 - displayDist) {
                        writer.write(getLiTag("disabled", null, "..."));
                        for (int i = total - 1 - displayDist * 2; i <= total; i++)
                            if (i == current)
                                writer.write(getLiTag("active", i, String.format("%d", i)));
                            else
                                writer.write(getLiTag(null, i, String.format("%d", i)));
                    } else {
                        writer.write(getLiTag("disabled", null, "..."));
                        for (int i = current - displayDist; i <= current + displayDist; i++)
                            if (i == current)
                                writer.write(getLiTag("active", i, String.format("%d", i)));
                            else
                                writer.write(getLiTag(null, i, String.format("%d", i)));
                        writer.write(getLiTag("disabled", null, "..."));
                    }
                }

                if (current.equals(total)) {
                    writer.write(getLiTag("disabled", null, "»"));
                    writer.write(getLiTag("disabled", null, "Last →"));
                } else {
                    writer.write(getLiTag(null, current + 1, "»"));
                    writer.write(getLiTag(null, total, "Last →"));
                }

                writer.write("\n" +
                        "            </ul>\n" +
                        "        </div>\n");
            }
        } catch (Exception e) {
        }
        return super.start(writer);
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Integer getDisplayDist() {
        return displayDist;
    }

    public void setDisplayDist(Integer displayDist) {
        this.displayDist = displayDist;
    }

}
