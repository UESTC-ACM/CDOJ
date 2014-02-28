package cn.edu.uestc.acmicpc.util;

import cn.edu.uestc.acmicpc.util.helper.ArrayUtil;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for {@link ArrayUtil}.
 */
public class ArrayUtilTest {

  @Test
  public void testParseIntArray() {
    Assert.assertEquals(new Integer[]{null}, ArrayUtil.parseIntArray(""));
    Assert.assertEquals(new Integer[]{1}, ArrayUtil.parseIntArray("1"));
    Assert.assertEquals(ArrayUtil.parseIntArray("1, 2, 31424 , 123154153151541234 , 1231"),
        new Integer[]{1, 2, 31424, null, 1231});
    Assert.assertEquals(ArrayUtil.parseIntArray("1,2,31424,123154153151541234,1231"),
        new Integer[]{1, 2, 31424, null, 1231});
    Assert.assertEquals(ArrayUtil.parseIntArray("1s, error"), new Integer[]{null, null});
  }
}
