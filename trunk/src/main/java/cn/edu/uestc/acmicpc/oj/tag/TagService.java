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

import cn.edu.uestc.acmicpc.util.StringUtil;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.struts2.components.ContextBean;

/**
 * Base Tag Service, we can process tag service in the method <strong>start</strong> and <strong>end</strong>
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
public class TagService extends ContextBean {
    /**
     * default constructor
     *
     * @param valueStack valueStack object of the java bean
     */
    public TagService(ValueStack valueStack) {
        super(valueStack);
    }

    /**
     * put var into the <strong>context stack</strong>.
     *
     * @param var   key name
     * @param value the mapping value of var
     */
    protected void putInContext(String var, Object value) {
        if (!StringUtil.isNullOrWhiteSpace(var))
            stack.getContext().put(var, value);
    }
}
