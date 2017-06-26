package cn.edu.uestc.acmicpc.util.checker;

import cn.edu.uestc.acmicpc.util.checker.base.Checker;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.helper.FileUtil;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Data checker for data.zip files.
 * <p>
 * <strong>Check items</strong>:
 * <ul>
 * <li>
 * All file names in the zip file will match "*.in" or "*.out".</li>
 * <li>
 * The file name will be numbered from 1 to number of test cases.</li>
 * <li>
 * For all the data files, input file and output file will match.</li>
 * </ul>
 * <p>
 * <strong>For developers</strong>:
 * <p>
 * This checker will not consider the folders in the zip file, that means if the
 * zip file only contains a folder and the folder contains a valid file
 * structure, this checker will not check the files in the folder. <strong>For
 * administrators</strong>:
 * <p>
 * Please put all data files in the zip file's root, rather than a specific
 * folder.
 */
public class ZipDataChecker implements Checker<File> {

  @Override
  public void check(File file) throws AppException {
    AppExceptionUtil.assertNotNull(file);
    File[] files = file.listFiles();
    if (files == null) {
      throw new AppException("Data file is invalid.");
    }

    Set<String> fileSet = new HashSet<>();
    List<String> outputFileList = new LinkedList<>();
    for (File current : files) {
      if (current.isDirectory()) {
        throw new AppException("Problem information contains sub-directory.");
      }
      if (current.getName().endsWith(".in")) {
        fileSet.add(FileUtil.getFileName(current));
      } else if (current.getName().endsWith(".out")) {
        outputFileList.add(FileUtil.getFileName(current));
      } else if (current.getName().equals("spj.cc")) {
        // spj checker, ignored
      } else if (current.getName().equals("problemInfo.xml")) {
        // problem info, ignored by default
      } else {
        throw new AppException("Problem information directory contains unknown file type.");
      }
    }

    if (outputFileList.size() != fileSet.size()) {
      throw new AppException("Some data files has not input file or output file.");
    }

    if (fileSet.size() == 0) {
      throw new AppException("No test data.");
    }

    for (String outputFile : outputFileList) {
      if (!fileSet.contains(outputFile)) {
        throw new AppException("Some data files has not input file or output file.");
      }
    }
  }
}
