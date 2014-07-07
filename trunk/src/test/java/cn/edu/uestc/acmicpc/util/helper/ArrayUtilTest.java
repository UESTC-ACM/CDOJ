package cn.edu.uestc.acmicpc.util.helper;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test cases for {@link cn.edu.uestc.acmicpc.util.helper.ArrayUtil}.
 */
public class ArrayUtilTest {

  @Test
  public void testParseIntArray_null() {
    Assert.assertEquals(new Integer[0], ArrayUtil.parseIntArray(null));
  }

  @Test
  public void testParseIntArray_empty() {
    Assert.assertEquals(ArrayUtil.parseIntArray(""), new Integer[]{null});
  }

  @Test
  public void testParseIntArray_singleElement() {
    Assert.assertEquals(ArrayUtil.parseIntArray("1"), new Integer[]{1});
  }

  @Test
  public void testParseIntArray_spaces() {
    Assert.assertEquals(ArrayUtil.parseIntArray("1, 2, 31424 , 123154153151541234 , 1231"),
        new Integer[]{1, 2, 31424, null, 1231});
  }

  @Test
  public void testParseIntArray_noSpaces() {
    Assert.assertEquals(ArrayUtil.parseIntArray("1,2,31424,123154153151541234,1231"),
        new Integer[]{1, 2, 31424, null, 1231});
  }

  @Test
  public void testParseIntArray_illegal() {
    Assert.assertEquals(ArrayUtil.parseIntArray("1s, error"), new Integer[]{null, null});
  }

  @Test
  public void testToArray_null() {
    Assert.assertEquals(ArrayUtil.toArray(null), new Object[0]);
  }

  @Test
  public void testToArray_emptyList() {
    Assert.assertEquals(ArrayUtil.toArray(new String[0]), new Object[0]);
  }
}
