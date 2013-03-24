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

package cn.edu.uestc.acmicpc.oj.test.util;

import cn.edu.uestc.acmicpc.util.StringUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * All test cases for {@code StringUtil.compareSkipSpaces} methods.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext-test.xml"})
public class CompareSkipSpacesTest {
    /**
     * Custom equal, first and second string are same.
     */
    @Test
    public void testSame() {
        Assert.assertEquals(0, StringUtil.compareSkipSpaces("cat", "cat"));
    }

    /**
     * First string has some white space({@code ' '} in it, and they are
     * same by deleting all the white spaces.
     */
    @Test
    public void testSameByDeletingWhiteSpace() {
        Assert.assertEquals(0, StringUtil.compareSkipSpaces("c a t", "cat"));
    }

    /**
     * The string has some white space({@code ' '} in it,and the some at the end of the string.
     */

    @Test
    public void testSameAboutEndingSpaces() {
        Assert.assertEquals(0, StringUtil.compareSkipSpaces("c a t    ", " c a t"));
    }

    /**
     * base test of the different string with white space.
     */

    @Test
    public void testDifferentAboutEndingSpaces() {
        Assert.assertEquals("cat".compareTo("dog"), StringUtil.compareSkipSpaces("c a t    ", " d o g"));
    }

    /**
     * The string has some Tab character in it,I think they are also white and space.
     */

    @Test
    public void testAboutTabSpaces() {
        Assert.assertEquals(0, StringUtil.compareSkipSpaces("\tc\ta\tt\t", "\tc\ta\tt\t"));
    }

    /**
     * The string has some Tab character in it,And this time the two string is not the same
     */

    @Test
    public void testAboutTabSpacesWithDifferentString() {
        Assert.assertEquals("cat".compareTo("catt"), StringUtil.compareSkipSpaces("\tc\ta\tt\t", "\tc\ta\tt\tt"));
    }

    /**
     * string with special character and they are the same
     */

    @Test
    public void testSameAboutSpecialCharacter() {
        Assert.assertEquals(0, StringUtil.compareSkipSpaces("\t\012\ta\tt\t", "\t\012\ta\tt\t"));
    }

    /**
     * string with special character and they are the same
     */

    @Test
    public void testDifferentAboutSpecialCharacter() {
        Assert.assertEquals(1, StringUtil.compareSkipSpaces("\t\012\ta\tt\t", "\t\012\t\001a\tt\t"));
    }
}
