package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.service.iface.FileService;
import cn.edu.uestc.acmicpc.util.checker.ZipDataChecker;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.helper.FileUtil;
import cn.edu.uestc.acmicpc.util.helper.ZipUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDto;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDto;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

  private String getDataZipFileName(String problemId) {
    return settings.UPLOAD_FOLDER + "/problem_" + problemId + ".zip";
  }

  @Override
  public Integer uploadProblemDataFile(FileUploadDto fileUploadDto, String problemId)
      throws AppException {
    List<MultipartFile> files = fileUploadDto.getFiles();
    if (files == null || files.size() > 1) {
      throw new AppException("Fetch uploaded file error.");
    }
    MultipartFile file = files.get(0);
    File targetFile = new File(getDataZipFileName(problemId));
    if (targetFile.exists() && !targetFile.delete()) {
      throw new AppException("Internal exception: target file exists and can not be deleted.");
    }
    try {
      file.transferTo(targetFile);
    } catch (IOException e) {
      throw new AppException("Error while save files");
    }

    try {
      ZipFile zipFile = new ZipFile(getDataZipFileName(problemId));
      String tempDirectory = settings.UPLOAD_FOLDER + "/" + problemId;
      ZipUtil.unzipFile(zipFile, tempDirectory, new ZipDataChecker());

      File dataPath = new File(tempDirectory);
      File[] dataFiles = dataPath.listFiles();
      AppExceptionUtil.assertNotNull(dataFiles);
      return dataFiles.length / 2;
    } catch (IOException e) {
      throw new AppException("Error while unzip file");
    }
  }

  private Integer moveProblemDataFile(String tempDirectory, String dataPath) throws AppException {
    File currentFile = new File(tempDirectory);
    File targetFile = new File(dataPath);
    // If the uploaded file list is empty, that means we don't need update
    // the data folder.
    int dataCount = 0;
    boolean foundSpj = false;
    File[] files = currentFile.listFiles();
    // Sort files by name.
    if (files != null) {
      Arrays.sort(files, new Comparator<File>() {
        @Override
        public int compare(File o1, File o2) {
          return FileUtil.getFileName(o1).compareTo(FileUtil.getFileName(o2));
        }
      });
    }
    Map<String, Integer> fileMap = new TreeMap<>();
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
      if (!targetFile.exists()) {
        if (!targetFile.mkdirs()) {
          throw new AppException("Cannot make data directory");
        }
      }
      for (String file : fileMap.keySet()) {
        File fromFile = new File(tempDirectory + '/' + file + ".in");
        File toFile = new File(dataPath + '/' + fileMap.get(file) + ".in");
        if (!fromFile.renameTo(toFile)) {
          throw new AppException("Cannot rename file: " + file + ".in");
        }
        fromFile = new File(tempDirectory + '/' + file + ".out");
        toFile = new File(dataPath + '/' + fileMap.get(file) + ".out");
        if (!fromFile.renameTo(toFile)) {
          throw new AppException("Cannot rename file: " + file + ".out");
        }
      }
      if (foundSpj) {
        File fromFile = new File(tempDirectory + "/spj.cc");
        File toFile = new File(dataPath + "/spj.cc");
        if (!fromFile.renameTo(toFile)) {
          throw new AppException("Cannot rename SPJ.cc");
        }
      }
      FileUtil.clearDirectory(tempDirectory);
    }

    if (foundSpj) {
      Runtime runtime = Runtime.getRuntime();
      try {
        Process process = runtime.exec(String.format("g++ %s/spj.cc -o %s/spj -O2", dataPath,
            dataPath));
        process.waitFor();
        if (process.exitValue() != 0) {
          throw new AppException("Error while compile spj.cc");
        }
      } catch (Exception e) {
        throw new AppException("Error while compile spj.cc");
      }
    }
    return dataCount;
  }

  @Override
  public Integer moveProblemDataFile(String uploadFolder, Integer problemId) throws AppException {
    String dataPath = settings.DATA_PATH + "/" + problemId;
    String tempDirectory = settings.UPLOAD_FOLDER + "/" + uploadFolder;
    return moveProblemDataFile(tempDirectory, dataPath);
  }

  @Override
  public FileInformationDto uploadContestArchive(FileUploadDto fileUploadDto) throws AppException {
    List<MultipartFile> files = fileUploadDto.getFiles();
    if (files == null || files.size() > 1) {
      throw new AppException("Fetch uploaded file error.");
    }
    MultipartFile file = files.get(0);
    File targetFile = new File(getContestArchiveZipFileName());
    if (targetFile.exists() && !targetFile.delete()) {
      throw new AppException("Internal exception: target file exists and can not be deleted.");
    }
    try {
      file.transferTo(targetFile);
    } catch (IOException e) {
      throw new AppException("Error while save files");
    }
    return FileInformationDto.builder()
        .setFileName(targetFile.getName())
        .build();
  }

  private String getContestArchiveZipFileName() {
    return settings.UPLOAD_FOLDER + "/contest_" + System.currentTimeMillis() + ".zip";
  }

  @Override
  public void moveFile(String source, String target) throws AppException {
    File sourceFile = new File(source);
    File targetFile = new File(target);
    if (!sourceFile.renameTo(targetFile)) {
      throw new AppException("Can not move file!");
    }
  }

}
