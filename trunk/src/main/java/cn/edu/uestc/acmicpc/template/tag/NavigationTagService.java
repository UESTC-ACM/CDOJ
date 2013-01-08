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

import java.io.Writer;
import java.util.Dictionary;
import java.util.List;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * A tag service to show the navigation.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */

public class NavigationTagService extends TagService {

    private String current;

    public NavigationTagService(ValueStack valueStack) {
        super(valueStack);
    }

    @Override
    public boolean start(Writer writer) {
        try {

            List<Dictionary<String,Dictionary<String,String>>> pages;

            int id = 0;
            if (getCurrent() != null) {
                writer.write(getCurrent());
            }
        }catch (Exception e) {
        }
        return super.start(writer);
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

}
