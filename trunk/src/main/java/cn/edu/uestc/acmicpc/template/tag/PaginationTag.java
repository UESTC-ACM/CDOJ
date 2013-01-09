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

package cn.edu.uestc.acmicpc.template.tag;

import com.opensymphony.xwork2.util.ValueStack;
import org.apache.struts2.components.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A tag to show the pagination.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

public class PaginationTag extends Tag {

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

    @Override
    public Component getBean(ValueStack valueStack, HttpServletRequest request, HttpServletResponse response) {
        return new PaginationTagService(valueStack);
    }

    /**
     * put params into paginationTagService
     */
    @Override
    protected void populateParams() {
        super.populateParams();
        PaginationTagService paginationTagService = (PaginationTagService)getComponent();
        paginationTagService.setCurrent(getCurrent());
        paginationTagService.setTotal(getTotal());
        paginationTagService.setBaseUrl(getBaseUrl());
        paginationTagService.setDisplayDist(getDisplayDist());
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
