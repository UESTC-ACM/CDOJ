package cn.edu.uestc.acmicpc.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * All test cases for {@code StringUtil.compareSkipSpaces} methods.
 */
@RunWith(BlockJUnit4ClassRunner.class)
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
