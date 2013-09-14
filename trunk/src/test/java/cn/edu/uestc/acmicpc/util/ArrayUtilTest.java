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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.util.ArrayUtil;

/**
 * Test cases for {@link ArrayUtil}.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ArrayUtilTest {

  @Test
  public void testParseIntArray() {
    Assert.assertArrayEquals(new Integer[] { null }, ArrayUtil.parseIntArray(""));
    Assert.assertArrayEquals(new Integer[] { 1 }, ArrayUtil.parseIntArray("1"));
    Assert.assertArrayEquals(new Integer[] { 1, 2, 31424, null, 1231 },
        ArrayUtil.parseIntArray("1, 2, 31424 , 123154153151541234 , 1231"));
    Assert.assertArrayEquals(new Integer[] { 1, 2, 31424, null, 1231 },
        ArrayUtil.parseIntArray("1,2,31424,123154153151541234,1231"));
    Assert.assertArrayEquals(new Integer[] { null, null }, ArrayUtil.parseIntArray("1s, error"));
  }
}
