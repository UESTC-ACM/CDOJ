package cn.edu.uestc.acmicpc.checker;

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtils;

/**
 * Test cases for {@link ZipDataChecker}.
 */
@RunWith(MockitoJUnitRunner.class)
public class ZipDataCheckerTest {

  private static final Logger LOGGER = LogManager.getLogger(ZipDataCheckerTest.class);

  ZipDataChecker checker = new ZipDataChecker();
  @Mock
  File directory;
  @Mock
  File dataInput1;
  @Mock
  File dataInput2;
  @Mock
  File dataInput3;
  @Mock
  File dataOutput1;
  @Mock
  File dataOutput2;
  @Mock
  File dataOutput3;
  @Mock
  File spjFile;
  @Mock
  File otherFile;
  @Mock
  File otherDirectory;

  @Before
  public void init() {
    Mockito.when(dataInput1.getName()).thenReturn("1.in");
    Mockito.when(dataInput2.getName()).thenReturn("2.in");
    Mockito.when(dataInput3.getName()).thenReturn("3.in");
    Mockito.when(dataOutput1.getName()).thenReturn("1.out");
    Mockito.when(dataOutput2.getName()).thenReturn("2.out");
    Mockito.when(dataOutput3.getName()).thenReturn("3.out");
    Mockito.when(spjFile.getName()).thenReturn("spj.cc");
    Mockito.when(otherFile.getName()).thenReturn("other.file");
    Mockito.when(otherDirectory.isDirectory()).thenReturn(true);
  }

  @Test
  public void testCheck_withoutSpjFile_oneCase() throws AppException {
    Mockito.when(directory.listFiles()).thenReturn(new File[] { dataInput1, dataOutput1 });
    checker.check(directory);
  }

  @Test
  public void testCheck_withoutSpjFile_moreCases() throws AppException {
    Mockito.when(directory.listFiles()).thenReturn(
        new File[] { dataInput1, dataInput2, dataOutput1, dataOutput2 });
    checker.check(directory);
  }

  @Test
  public void testCheck_withSpjFile_oneCase() throws AppException {
    Mockito.when(directory.listFiles()).thenReturn(new File[] { dataInput1, dataOutput1, spjFile });
    checker.check(directory);
  }

  @Test
  public void testCheck_withSpjFile_moreCases() throws AppException {
    Mockito.when(directory.listFiles()).thenReturn(
        new File[] { dataInput1, dataOutput1, spjFile, dataInput2, dataOutput2, dataInput3,
            dataOutput3 });
    checker.check(directory);
  }

  @Test
  public void testCheck_withInvalidDataName() {
    Mockito.when(directory.listFiles()).thenReturn(
        new File[] { dataInput1, dataOutput1, spjFile, otherFile });
    try {
      checker.check(directory);
      Assert.fail();
    } catch (AppException e) {
      LOGGER.info("expected", e);
      Assert.assertTrue(AppExceptionUtils.assertEquals(new AppException(
          "Data file contains unknown file type."), e));
    }
  }

  @Test
  public void testCheck_withDirectory() {
    Mockito.when(directory.listFiles()).thenReturn(
        new File[] { dataInput1, dataOutput1, spjFile, otherDirectory });
    try {
      checker.check(directory);
      Assert.fail();
    } catch (AppException e) {
      LOGGER.info("expected", e);
      Assert.assertTrue(AppExceptionUtils.assertEquals(new AppException(
          "Data file contains directory."), e));
    }
  }

  @Test
  public void testCheck_withNullDirectory() throws AppException {
    try {
      checker.check(null);
      Assert.fail();
    } catch (NullPointerException expected) {
      LOGGER.info("expected", expected);
    }
  }

  @Test
  public void testCheck_invalidDataDirectory() {
    try {
      checker.check(otherDirectory);
      Assert.fail();
    } catch (AppException e) {
      LOGGER.info("expected", e);
      Assert.assertTrue(AppExceptionUtils
          .assertEquals(new AppException("Data file is invalid."), e));
    }
  }

  @Test
  public void testCheck_notSameInputsAndOutputs() {
    Mockito.when(directory.listFiles()).thenReturn(
        new File[] { dataInput1, dataOutput1, dataInput2 });
    try {
      checker.check(directory);
      Assert.fail();
    } catch (AppException e) {
      LOGGER.info("expected", e);
      Assert.assertTrue(AppExceptionUtils.assertEquals(new AppException(
          "Some data files has not input file or output file."), e));
    }
  }

  @Test
  public void testCheck_missMatchInputAndOutput() {
    Mockito.when(directory.listFiles()).thenReturn(
        new File[] { dataInput1, dataOutput1, dataInput2, dataOutput3 });
    try {
      checker.check(directory);
      Assert.fail();
    } catch (AppException e) {
      LOGGER.info("expected", e);
      Assert.assertTrue(AppExceptionUtils.assertEquals(new AppException(
          "Some data files has not input file or output file."), e));
    }
  }
}
