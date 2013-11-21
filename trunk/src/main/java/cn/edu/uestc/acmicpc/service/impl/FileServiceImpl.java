package cn.edu.uestc.acmicpc.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.uestc.acmicpc.service.iface.FileService;
import cn.edu.uestc.acmicpc.util.FileUtil;
import cn.edu.uestc.acmicpc.util.Settings;
import cn.edu.uestc.acmicpc.util.ZipUtil;
import cn.edu.uestc.acmicpc.util.checker.ZipDataChecker;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;

/**
 * Implement for {@link FileService}
 */
@Service
public class FileServiceImpl extends AbstractService implements FileService {

  private final Settings settings;

  @Autowired
  public FileServiceImpl(Settings settings) {
    this.settings = settings;
  }

  private String getDataZipFileName(Integer problemId) {
    return settings.SETTING_UPLOAD_FOLDER + "/problem_" + problemId + ".zip";
  }

  @Override
  public Integer uploadProblemDataFile(FileUploadDTO fileUploadDTO, Integer problemId)
      throws AppException {
    List<MultipartFile> files = fileUploadDTO.getFiles();
    if (files == null || files.size() > 1)
      throw new AppException("Fetch uploaded file error.");
    MultipartFile file = files.get(0);
    File targetFile = new File(getDataZipFileName(problemId));
    if (targetFile.exists() && !targetFile.delete())
      throw new AppException("Internal exception: target file exists and can not be deleted.");
    try {
      file.transferTo(targetFile);
    } catch (IOException e) {
      throw new AppException("Error while save files");
    }

    try {
      ZipFile zipFile = new ZipFile(getDataZipFileName(problemId));
      String tempDirectory = settings.SETTING_UPLOAD_FOLDER + "/" + problemId;
      ZipUtil.unzipFile(zipFile, tempDirectory, new ZipDataChecker());

      File dataPath = new File(tempDirectory);
      File[] dataFiles = dataPath.listFiles();
      AppExceptionUtil.assertNotNull(dataFiles);
      return dataFiles.length / 2;
    } catch (IOException e) {
      throw new AppException("Error while unzip file");
    }
  }

  @Override
  public Integer moveProblemDataFile(Integer problemId) throws AppException {
    String dataPath = settings.JUDGE_DATA_PATH + "/" + problemId;
    String tempDirectory = settings.SETTING_UPLOAD_FOLDER + "/" + problemId;
    File targetFile = new File(dataPath);
    File currentFile = new File(tempDirectory);
    // If the uploaded file list is empty, that means we don't update
    // the data folder.
    int dataCount = 0;
    boolean foundSpj = false;
    File[] files = currentFile.listFiles();
    Map<String, Integer> fileMap = new HashMap<>();
    if (files != null) {
      for (File file : files) {
        if (file.getName().endsWith(".in")) {
          fileMap.put(FileUtil.getFileName(file), ++dataCount);
        } else if (file.getName().equals("spj.cc")) {
          foundSpj = true;
        }
      }
    }

    if (dataCount != 0) {
      FileUtil.clearDirectory(dataPath);
      try {
        FileUtil.moveDirectory(currentFile, targetFile);
      } catch (IOException e) {
        throw new AppException("Error while move data files");
      }
      for (String file : fileMap.keySet()) {
        File fromFile = new File(dataPath + '/' + file + ".in");
        File toFile = new File(dataPath + '/' + fileMap.get(file) + ".in");
        if (!fromFile.renameTo(toFile))
          throw new AppException("Cannot rename file: " + file + ".in");
        fromFile = new File(dataPath + '/' + file + ".out");
        toFile = new File(dataPath + '/' + fileMap.get(file) + ".out");
        if (!fromFile.renameTo(toFile))
          throw new AppException("Cannot rename file: " + file + ".out");
      }
      FileUtil.clearDirectory(tempDirectory);
    }

    if (foundSpj) {
      Runtime runtime = Runtime.getRuntime();
      try {
        Process process = runtime.exec(String.format("g++ %s/spj.cc -o %s/spj -O2", dataPath, dataPath));
        process.waitFor();
      } catch (Exception e) {
        throw new AppException("Error while compile spj.cc");
      }
      File source = new File(String.format("%s/spj.cc", dataPath));
      if (!source.delete())
        throw new AppException("Cannot remove spj source file");
    }
    return dataCount;
  }

  @Override
  public void moveFile(String source, String target) throws AppException {
    File sourceFile = new File(settings.SETTING_ABSOLUTE_PATH + source);
    File targetFile = new File(settings.SETTING_ABSOLUTE_PATH + target);
    if (!sourceFile.renameTo(targetFile))
      throw new AppException("Can not move file!");
  }

}
