package cn.edu.uestc.acmicpc.util;

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

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.util.Global;

/**
 * Util class test
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:applicationContext-test.xml" })
public class UtilTest {

  @Autowired
  private Global global;

  @Test
  public void testGlobal() {
    Assert.assertNotNull("Constructor global instance is null.", global);
    List<Department> departments = global.getDepartmentList();
    Assert.assertEquals(18, departments.size());
  }

  private static String VJ_1Y = "^(\\d{1,2})\\s*:\\s*(\\d{2})\\s*:\\s*(\\d{2})$";
  private static String VJ_NORMAL =
      "^(\\d{1,2})\\s*:\\s*(\\d{2})\\s*:\\s*(\\d{2})\\s*\\(\\s*-\\s*(\\d+)\\s*\\)$";
  private static String VJ_FAIL = "^\\(\\s*-\\s*(\\d+)\\s*\\)$";
  private static String PC_NORMAL = "^(\\d+)\\s*/\\s*(\\d+)$";
  private static String PC_FAIL = "^(\\d+)\\s*/\\s*-\\s*-$";

  @Test
  public void regexTest() {
    Pattern pattern;
    Matcher matcher;
    String query;

    pattern = Pattern.compile(VJ_1Y);
    query = "4 : 28 : 00";
    matcher = pattern.matcher(query);
    if (matcher.find()) {
      assert matcher.group(1).equals("4") && matcher.group(2).equals("28")
          && matcher.group(3).equals("00");
    } else {
      assert false;
    }

    pattern = Pattern.compile(VJ_NORMAL);
    query = "4:35:00 (-2)";
    matcher = pattern.matcher(query);
    Assert.assertTrue(matcher.find());

    pattern = Pattern.compile(VJ_FAIL);
    query = "(-5 )";
    matcher = pattern.matcher(query);
    Assert.assertTrue(matcher.find());

    pattern = Pattern.compile(PC_NORMAL);
    query = "14/ 294";
    matcher = pattern.matcher(query);
    Assert.assertTrue(matcher.find());

    pattern = Pattern.compile(PC_FAIL);
    query = "16/- -";
    matcher = pattern.matcher(query);
    Assert.assertTrue(matcher.find());
  }
}
