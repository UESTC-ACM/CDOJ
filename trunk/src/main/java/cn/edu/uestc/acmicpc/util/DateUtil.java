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

package cn.edu.uestc.acmicpc.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Global method for date operations.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
public class DateUtil {
    /**
     * Parse date string.
     *
     * @param string date string to parse
     * @return Date object for the string
     * @throws ParseException
     */
    @SuppressWarnings("UnusedDeclaration")
    public static Date valueOf(String string) throws ParseException {
        return new SimpleDateFormat("yyyy/MM/dd").parse(string);
    }

    /**
     * Date add operation.
     *
     * @param date   date we want to add
     * @param field  add field name
     * @param offset add offset
     * @return new date after add operation
     */
    @SuppressWarnings("SameParameterValue")
    public static Date add(Date date, int field, int offset) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(field, offset);
        return cd.getTime();
    }
}
