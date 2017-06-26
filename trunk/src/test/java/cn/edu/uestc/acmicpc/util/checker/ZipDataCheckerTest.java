package cn.edu.uestc.acmicpc.util.checker;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.io.File;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test cases for {@link ZipDataChecker}.
 */
public class ZipDataCheckerTest {

  private static final Logger LOGGER = LogManager.getLogger(ZipDataCheckerTest.class);

  ZipDataChecker checker = new ZipDataChecker();
  File directory;
  File dataInput1;
  File dataInput2;
  File dataInput3;
  File dataOutput1;
  File dataOutput2;
  File dataOutput3;
  File spjFile;
  File otherFile;
  File otherDirectory;

  @BeforeMethod
  public void init() {
    directory = mock(File.class);
    dataInput1 = mock(File.class);
    dataInput2 = mock(File.class);
    dataInput3 = mock(File.class);
    dataOutput1 = mock(File.class);
    dataOutput2 = mock(File.class);
    dataOutput3 = mock(File.class);
    spjFile = mock(File.class);
    otherFile = mock(File.class);
    otherDirectory = mock(File.class);
    when(dataInput1.getName()).thenReturn("1.in");
    when(dataInput2.getName()).thenReturn("2.in");
    when(dataInput3.getName()).thenReturn("3.in");
    when(dataOutput1.getName()).thenReturn("1.out");
    when(dataOutput2.getName()).thenReturn("2.out");
    when(dataOutput3.getName()).thenReturn("3.out");
    when(spjFile.getName()).thenReturn("spj.cc");
    when(otherFile.getName()).thenReturn("other.file");
    when(otherDirectory.isDirectory()).thenReturn(true);
  }

  @Test
  public void testCheck_withoutSpjFile_oneCase() throws AppException {
    when(directory.listFiles()).thenReturn(new File[]{dataInput1, dataOutput1});
    checker.check(directory);
  }

  @Test
  public void testCheck_withoutSpjFile_moreCases() throws AppException {
    when(directory.listFiles()).thenReturn(
        new File[]{dataInput1, dataInput2, dataOutput1, dataOutput2});
    checker.check(directory);
  }

  @Test
  public void testCheck_withSpjFile_oneCase() throws AppException {
    when(directory.listFiles()).thenReturn(new File[]{dataInput1, dataOutput1, spjFile});
    checker.check(directory);
  }

  @Test
  public void testCheck_withSpjFile_moreCases() throws AppException {
    when(directory.listFiles()).thenReturn(
        new File[]{dataInput1, dataOutput1, spjFile, dataInput2, dataOutput2, dataInput3,
            dataOutput3});
    checker.check(directory);
  }

  @Test
  public void testCheck_withInvalidDataName() {
    when(directory.listFiles()).thenReturn(
        new File[]{dataInput1, dataOutput1, spjFile, otherFile});
    try {
      checker.check(directory);
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals(new AppException("Problem information directory contains unknown file type."), e);
    }
  }

  @Test
  public void testCheck_withDirectory() {
    when(directory.listFiles()).thenReturn(
        new File[]{dataInput1, dataOutput1, spjFile, otherDirectory});
    try {
      checker.check(directory);
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals(e, new AppException("Problem information contains sub-directory."));
    }
  }

  @Test
  public void testCheck_withNullDirectory() throws AppException {
    try {
      checker.check(null);
      Assert.fail();
    } catch (AppException expected) {
      LOGGER.info("expected", expected);
    }
  }

  @Test
  public void testCheck_invalidDataDirectory() {
    try {
      checker.check(otherDirectory);
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals(e, new AppException("Data file is invalid."));
    }
  }

  @Test
  public void testCheck_notSameInputsAndOutputs() {
    when(directory.listFiles()).thenReturn(
        new File[]{dataInput1, dataOutput1, dataInput2});
    try {
      checker.check(directory);
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals(e,
          new AppException("Some data files has not input file or output file."));
    }
  }

  @Test
  public void testCheck_missMatchInputAndOutput() {
    when(directory.listFiles()).thenReturn(
        new File[]{dataInput1, dataOutput1, dataInput2, dataOutput3});
    try {
      checker.check(directory);
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals(e,
          new AppException("Some data files has not input file or output file."));
    }
  }

  @Test
  public void testCheck_noTestData() {
    when(directory.listFiles()).thenReturn(new File[] {});
    try {
      checker.check(directory);
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals(e, new AppException("No test data."));
    }
  }
}
