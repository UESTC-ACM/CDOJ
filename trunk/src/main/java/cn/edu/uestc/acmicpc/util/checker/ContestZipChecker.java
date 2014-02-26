package cn.edu.uestc.acmicpc.util.checker;

import cn.edu.uestc.acmicpc.util.checker.base.Checker;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.FileUtil;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;

import java.io.File;
import java.util.*;

/**
 * Data checker for data.zip files.
 * <p/>
 * <strong>Check items</strong>:
 * <ul>
 * <li>
 * Only a XML File named "contestInfo.xml" in the root directory.</li>
 * <li>
 * In each sub-directory contains all information and test data of one problem</li>
 * <li>
 * For each problem, its directory should named with capital alphabet startw with A.</li>
 * <li>
 * In a sub-directory, a XML File named "problemInfo.xml" and input/output data named
 * "*.in" and "*.out" </li>
 * </ul>
 * <p/>
 * <strong>For administrators</strong>:
 * <p/>
 * Please check carefully whether the structure of zip is valid.
 */
public class ContestZipChecker implements Checker<File> {

  @Override
  public void check(File file) throws AppException {
    AppExceptionUtil.assertNotNull(file);
    File[] files = file.listFiles();
    Arrays.sort(files);

    boolean hasContestInfo = false;
    Character nextProblemAlias = 'A';
    for (File current : files) {
      if ("contestInfo.xml".equals(current.getName())) {
        hasContestInfo = true;
      } else if (current.isDirectory()) {
        if (!nextProblemAlias.toString().equals(current.getName())) {
          throw new AppException("Wrong problem alias.");
        }
        File[] filesInSubDirectory = current.listFiles();
        checkProblemSubDirectory(filesInSubDirectory);
        nextProblemAlias ++;
      } else {
        throw new AppException("Contest information directory contains unknown type files.");
      }
    }

    if (!hasContestInfo) {
      throw new AppException("Contest archive has not contest information!");
    }
  }

  private void checkProblemSubDirectory(File[] files) throws AppException {
    boolean hasProblemInfo = false;
    Set<String> inputFileNames = new HashSet<>();
    Set<String> outputFileNames = new HashSet<>();

    for (File current : files) {
      if (current.isDirectory()) {
        throw new AppException("Problem information directory can't contains sub-directory.");
      }
      String fileName = current.getName();
      if ("problemInfo.xml".equals(fileName)) {
        hasProblemInfo = true;
      } else if ("spj.cc".equals(fileName)) {
        // spj checker, ignored
      } else if (fileName.endsWith(".in")) {
        inputFileNames.add(FileUtil.getFileName(current));
      } else if (fileName.endsWith(".out")) {
        outputFileNames.add(FileUtil.getFileName(current));
      } else {
        throw new AppException("Problem information directory contains unknown type file.");
      }
    }

    if (!hasProblemInfo) {
      throw new AppException("No description file in problelem information directory.");
    }

    if (inputFileNames.size() != outputFileNames.size()) {
      throw new AppException("Some data files has not input file or output file.");
    }
    if (inputFileNames.size() == 0) {
      throw new AppException("No test data files.");
    }
    for (String inputFileName : inputFileNames) {
      if (!outputFileNames.contains(inputFileName)) {
        throw new AppException("Some data files has not input file or output file.");
      }
    }
  }

}
