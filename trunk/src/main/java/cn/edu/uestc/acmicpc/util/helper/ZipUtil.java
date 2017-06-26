package cn.edu.uestc.acmicpc.util.helper;

import cn.edu.uestc.acmicpc.util.checker.base.Checker;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Operations for zip files.
 */
public class ZipUtil {

  /**
   * Unzip the zip file and put all the files in the zip file to the path.
   * <p>
   * <strong>WARN:</strong> If the file list can not pass the checker's
   * validation, delete all the contents in the {@code path}, and the
   * {@code path} itself.
   *
   * @param zipFile
   *          zipFile object
   * @param path
   *          target path
   * @param checker
   *          checker to validate ZIP files.
   * @throws AppException
   *           if exception occurred, convert them into {@code AppException}
   *           object.
   */
  public static void unzipFile(ZipFile zipFile, String path, Checker<File> checker)
      throws AppException {
    try {
      Enumeration<?> enumeration = zipFile.entries();
      while (enumeration.hasMoreElements()) {
        ZipEntry zipEntry = (ZipEntry) enumeration.nextElement();
        if (zipEntry.isDirectory()) {
          if (!new File(path + "/" + zipEntry.getName()).mkdirs()) {
            throw new Exception();
          }
          continue;
        }
        File file = new File(path + "/" + zipEntry.getName());
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
          if (!parent.mkdirs()) {
            throw new AppException();
          }
        }
        FileUtil.saveToFile(zipFile.getInputStream(zipEntry), new FileOutputStream(file));
      }
      zipFile.close();
      File targetFile = new File(path);
      try {
        checker.check(targetFile);
      } catch (AppException e) {
        FileUtil.clearDirectory(targetFile.getAbsolutePath());
        throw e;
      }
    } catch (AppException e) {
      throw e;
    } catch (Exception e) {
      e.printStackTrace();
      throw new AppException("Unzip zip file error.");
    }
  }
}
