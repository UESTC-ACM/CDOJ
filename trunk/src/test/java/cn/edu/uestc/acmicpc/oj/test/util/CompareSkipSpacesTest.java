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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.util.StringUtil;

/**
 * All test cases for {@code StringUtil.compareSkipSpaces} methods.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-test.xml" })
public class CompareSkipSpacesTest {

  @Test
  public void testSame() {
    Assert.assertEquals(0, StringUtil.compareSkipSpaces("cat", "cat"));
  }

  @Test
  public void testSame_deletingWhiteSpace() {
    Assert.assertEquals(0, StringUtil.compareSkipSpaces("c a t", "cat"));
  }

  @Test
  public void testSame_endingSpaces() {
    Assert.assertEquals(0, StringUtil.compareSkipSpaces("c a t    ", " c a t"));
  }

  @Test
  public void testDifferent_endingSpaces() {
    Assert
        .assertEquals("cat".compareTo("dog"), StringUtil.compareSkipSpaces("c a t    ", " d o g"));
  }

  @Test
  public void testSame_tabSpaces() {
    Assert.assertEquals(0, StringUtil.compareSkipSpaces("\tc\ta\tt\t", "\tc\ta\tt\t"));
  }

  @Test
  public void testDifferent_tabSpacesWithDifferentString() {
    Assert.assertEquals("cat".compareTo("catt"),
        StringUtil.compareSkipSpaces("\tc\ta\tt\t", "\tc\ta\tt\tt"));
  }

  @Test
  public void testSame_specialCharacter() {
    Assert.assertEquals(0, StringUtil.compareSkipSpaces("\t\012\ta\tt\t", "\t\012\ta\tt\t"));
  }

  @Test
  public void testDifferent_specialCharacter() {
    Assert.assertEquals(1, StringUtil.compareSkipSpaces("\t\012\ta\tt\t", "\t\012\t\001a\tt\t"));
  }
}
