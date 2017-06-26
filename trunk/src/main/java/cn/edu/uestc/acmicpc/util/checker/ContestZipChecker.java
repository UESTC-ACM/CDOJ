package cn.edu.uestc.acmicpc.util.checker;

import cn.edu.uestc.acmicpc.util.checker.base.Checker;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import java.io.File;
import java.util.Arrays;

/**
 * Data checker for data.zip files.
 * <p>
 * <strong>Check items</strong>:
 * <ul>
 * <li>
 * Only a XML File named "contestInfo.xml" in the root directory.
 * <li>
 * In each sub-directory contains all information and test data of one problem</li>
 * <li>
 * In a sub-directory, a XML File named "problemInfo.xml" and input/output data
 * named with "*.in" and "*.out"</li>
 * </ul>
 * <p>
 * <strong>For administrators</strong>:
 * <p>
 * Please check carefully whether the structure of zip is valid.
 */
public class ContestZipChecker implements Checker<File> {

  private ZipDataChecker zipDataChecker;

  public ContestZipChecker() {
    zipDataChecker = new ZipDataChecker();
  }

  public void setZipDataChecker(ZipDataChecker zipDataChecker) {
    this.zipDataChecker = zipDataChecker;
  }

  @Override
  public void check(File file) throws AppException {
    AppExceptionUtil.assertNotNull(file);
    File[] files = file.listFiles();
    Arrays.sort(files);

    boolean hasContestInfo = false;
    for (File current : files) {
      if ("contestInfo.xml".equals(current.getName())) {
        hasContestInfo = true;
      } else if (current.isDirectory()) {
        if (current.getName().startsWith(".") ||
            current.getName().startsWith("_")) {
          continue;
        }
        checkProblemSubDirectory(current);
      } else {
        throw new AppException("Contest information directory contains unknown type files.");
      }
    }

    if (!hasContestInfo) {
      throw new AppException("Contest archive has not contest information!");
    }
  }

  private void checkProblemSubDirectory(File dir) throws AppException {
    AppExceptionUtil.assertNotNull(dir);
    File[] files = dir.listFiles();
    boolean hasProblemInfo = false;
    for (File current : files) {
      String fileName = current.getName();
      if ("problemInfo.xml".equals(fileName)) {
        hasProblemInfo = true;
      }
    }

    if (!hasProblemInfo) {
      throw new AppException("No description file in problem information directory.");
    }

    zipDataChecker.check(dir);
  }

}
