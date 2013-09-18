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
