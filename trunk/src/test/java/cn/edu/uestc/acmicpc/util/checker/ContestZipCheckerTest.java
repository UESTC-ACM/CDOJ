package cn.edu.uestc.acmicpc.util.checker;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.io.File;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test cases for {@link cn.edu.uestc.acmicpc.util.checker.ContestZipChecker}
 */
public class ContestZipCheckerTest {

  ContestZipChecker contestZipChecker;
  ZipDataChecker zipDataChecker;
  File directory;
  File contestInformationFile;
  File problemDir1;
  File problemInformationFile1;
  File dataInput1_1;
  File dataInput1_2;
  File dataOutput1_1;
  File dataOutput1_2;
  File problemDir2;
  File problemInformationFile2;
  File dataInput2_1;
  File dataOutput2_1;
  File spjFile2;
  File hiddenDirectory;
  File otherFile;

  @BeforeMethod
  public void init() {
    contestZipChecker = new ContestZipChecker();
    zipDataChecker = mock(ZipDataChecker.class);
    contestZipChecker.setZipDataChecker(zipDataChecker);
    directory = mock(File.class);
    contestInformationFile = mock(File.class);
    problemDir1 = mock(File.class);
    problemInformationFile1 = mock(File.class);
    dataInput1_1 = mock(File.class);
    dataInput1_2 = mock(File.class);
    dataOutput1_1 = mock(File.class);
    dataOutput1_2 = mock(File.class);
    problemDir2 = mock(File.class);
    problemInformationFile2 = mock(File.class);
    dataInput2_1 = mock(File.class);
    dataOutput2_1 = mock(File.class);
    spjFile2 = mock(File.class);
    hiddenDirectory = mock(File.class);
    otherFile = mock(File.class);
    when(contestInformationFile.getName()).thenReturn("contestInfo.xml");
    when(problemDir1.getName()).thenReturn("A");
    when(problemDir1.isDirectory()).thenReturn(true);
    when(problemInformationFile1.getName()).thenReturn("problemInfo.xml");
    when(dataInput1_1.getName()).thenReturn("1.in");
    when(dataInput1_2.getName()).thenReturn("2.in");
    when(dataOutput1_1.getName()).thenReturn("1.out");
    when(dataOutput1_2.getName()).thenReturn("2.out");
    when(problemDir2.getName()).thenReturn("B");
    when(problemDir2.isDirectory()).thenReturn(true);
    when(problemInformationFile2.getName()).thenReturn("problemInfo.xml");
    when(dataInput2_1.getName()).thenReturn("1.in");
    when(dataOutput2_1.getName()).thenReturn("1.out");
    when(spjFile2.getName()).thenReturn("spj.cc");
    when(hiddenDirectory.getName()).thenReturn(".DS_Store");
    when(hiddenDirectory.isDirectory()).thenReturn(true);
    when(otherFile.getName()).thenReturn("otherFile");
  }

  @Test
  public void testCheck_oneProblem() throws AppException {
    when(problemDir1.listFiles()).thenReturn(new File[] {
        problemInformationFile1, dataInput1_1, dataInput1_2, dataOutput1_1, dataOutput1_2
    });
    when(directory.listFiles()).thenReturn(new File[] {
        contestInformationFile, problemDir1
    });
    contestZipChecker.check(directory);
    verify(zipDataChecker).check(problemDir1);
  }

  @Test
  public void testCheck_multiProblems() throws AppException {
    when(problemDir1.listFiles()).thenReturn(new File[] {
        problemInformationFile1, dataInput1_1, dataInput1_2, dataOutput1_1, dataOutput1_2
    });
    when(problemDir2.listFiles()).thenReturn(new File[]{
        problemInformationFile2, dataInput2_1, dataOutput2_1, spjFile2
    });
    when(directory.listFiles()).thenReturn(new File[] {
        contestInformationFile, problemDir1, problemDir2
    });
    contestZipChecker.check(directory);
    verify(zipDataChecker).check(problemDir1);
    verify(zipDataChecker).check(problemDir2);
  }

  @Test
  public void testCheck_noContestInformationFile() throws AppException {
    when(problemDir1.listFiles()).thenReturn(new File[] {
        problemInformationFile1, dataInput1_1, dataInput1_2, dataOutput1_1, dataOutput1_2
    });
    when(directory.listFiles()).thenReturn(new File[] {
        problemDir1
    });
    try {
      contestZipChecker.check(directory);
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals(new AppException("Contest archive has not contest information!"), e);
    }
  }

  @Test
  public void testCheck_lackProblemInformationFile() throws AppException {
    when(problemDir1.listFiles()).thenReturn(new File[]{
        dataInput1_1, dataInput1_2, dataOutput1_1, dataOutput1_2
    });
    when(problemDir2.listFiles()).thenReturn(new File[]{
        problemInformationFile2, dataInput2_1, dataOutput2_1, spjFile2
    });
    when(directory.listFiles()).thenReturn(new File[]{
        contestInformationFile, problemDir1, problemDir2
    });
    try {
      contestZipChecker.check(directory);
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals(new AppException("No description file in problem information directory."), e);
    }
  }

  @Test
  public void testCheck_invalidFile() throws AppException {
    when(problemDir1.listFiles()).thenReturn(new File[] {
        problemInformationFile1, dataInput1_1, dataInput1_2, dataOutput1_1, dataOutput1_2
    });
    when(directory.listFiles()).thenReturn(new File[] {
        contestInformationFile, problemDir1, otherFile
    });
    try {
      contestZipChecker.check(directory);
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals(new AppException("Contest information directory contains unknown type files."), e);
    }
  }

  @Test
   public void testCheck_containsHiddenDirectory_1() throws AppException {
    when(problemDir1.listFiles()).thenReturn(new File[] {
        problemInformationFile1, dataInput1_1, dataInput1_2, dataOutput1_1, dataOutput1_2
    });
    when(directory.listFiles()).thenReturn(new File[]{
        contestInformationFile, problemDir1, hiddenDirectory
    });
    contestZipChecker.check(directory);
    verify(zipDataChecker).check(problemDir1);
    verify(zipDataChecker, times(0)).check(hiddenDirectory);
  }

  @Test
  public void testCheck_containsHiddenDirectory_2() throws AppException {
    when(problemDir1.listFiles()).thenReturn(new File[] {
        problemInformationFile1, dataInput1_1, dataInput1_2, dataOutput1_1, dataOutput1_2
    });
    when(directory.listFiles()).thenReturn(new File[]{
        contestInformationFile, problemDir1, hiddenDirectory
    });
    when(hiddenDirectory.getName()).thenReturn("__MACOSX");
    contestZipChecker.check(directory);
    verify(zipDataChecker).check(problemDir1);
    verify(zipDataChecker, times(0)).check(hiddenDirectory);
  }
}
