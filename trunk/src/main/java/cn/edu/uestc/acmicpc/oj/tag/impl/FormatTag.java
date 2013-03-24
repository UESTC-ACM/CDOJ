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

package cn.edu.uestc.acmicpc.oj.tag.impl;

import cn.edu.uestc.acmicpc.oj.tag.base.Tag;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.struts2.components.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class FormatTag extends Tag {

    /**
     * String need to replace
     */
    private String value;

    @Override
    public Component getBean(ValueStack valueStack, HttpServletRequest request, HttpServletResponse response) {
        return new FormatTagService(valueStack);
    }

    /**
     * put params into paginationTagService
     */
    @Override
    protected void populateParams() {
        super.populateParams();
        FormatTagService formatTagService = (FormatTagService) getComponent();
        formatTagService.setValue(getValue());
    }

    @SuppressWarnings("WeakerAccess")
    public String getValue() {
        return value;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setValue(String value) {
        this.value = value;
    }
}
