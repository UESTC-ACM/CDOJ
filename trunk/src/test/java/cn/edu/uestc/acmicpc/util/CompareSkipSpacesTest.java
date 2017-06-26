package cn.edu.uestc.acmicpc.util;

import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * All test cases for {@code StringUtil.compareSkipSpaces} methods.
 */
public class CompareSkipSpacesTest {

  @Test
  public void testSame() {
    Assert.assertEquals(StringUtil.compareSkipSpaces("cat", "cat"), 0);
  }

  @Test
  public void testSame_deletingWhiteSpace() {
    Assert.assertEquals(StringUtil.compareSkipSpaces("c a t", "cat"), 0);
  }

  @Test
  public void testSame_endingSpaces() {
    Assert.assertEquals(StringUtil.compareSkipSpaces("c a t    ", " c a t"), 0);
  }

  @Test
  public void testDifferent_endingSpaces() {
    Assert
        .assertEquals(StringUtil.compareSkipSpaces("c a t    ", " d o g"), "cat".compareTo("dog"));
  }

  @Test
  public void testSame_tabSpaces() {
    Assert.assertEquals(StringUtil.compareSkipSpaces("\tc\ta\tt\t", "\tc\ta\tt\t"), 0);
  }

  @Test
  public void testDifferent_tabSpacesWithDifferentString() {
    Assert.assertEquals(StringUtil.compareSkipSpaces("\tc\ta\tt\t", "\tc\ta\tt\tt"),
        "cat".compareTo("catt"));
  }

  @Test
  public void testSame_specialCharacter() {
    Assert.assertEquals(StringUtil.compareSkipSpaces("\t\012\ta\tt\t", "\t\012\ta\tt\t"), 0);
  }

  @Test
  public void testDifferent_specialCharacter() {
    Assert.assertEquals(StringUtil.compareSkipSpaces("\t\012\ta\tt\t", "\t\012\t\001a\tt\t"), 1);
  }
}
