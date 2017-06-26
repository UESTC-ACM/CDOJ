package cn.edu.uestc.acmicpc.util.helper;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDto;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDto;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * File uploading util methods.
 */
public class FileUploadUtil {

  public static FileInformationDto uploadFile(FileUploadDto fileUploadDto,
      String basePath,
      String absoluteBasePath,
      String directory) throws AppException {
    if (!directory.endsWith("/")) {
      directory += "/";
    }
    String folder = basePath + directory;
    String absolutePath = absoluteBasePath + directory;
    List<MultipartFile> files = fileUploadDto.getFiles();
    if (files == null || files.size() > 1) {
      throw new AppException("Fetch uploaded file error.");
    }
    MultipartFile file = files.get(0);
    File dir = new File(absolutePath);
    if (!dir.exists()) {
      if (!dir.mkdirs()) {
        throw new AppException("Error while make directory " + dir.getName() + ".");
      }
    }

    String newName = StringUtil.generateFileName(file.getOriginalFilename());
    File newFile = new File(absolutePath + newName);
    try {
      file.transferTo(newFile);
    } catch (IOException e) {
      throw new AppException("Error while save files");
    }

    return FileInformationDto.builder()
        .setFileName(file.getOriginalFilename())
        .setFileURL(folder + newName)
        .build();
  }
}
