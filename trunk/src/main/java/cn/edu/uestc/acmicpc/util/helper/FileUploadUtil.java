package cn.edu.uestc.acmicpc.util.helper;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * File uploading util methods.
 */
public class FileUploadUtil {

  public static FileInformationDTO uploadFile(FileUploadDTO fileUploadDTO,
                                              String basePath,
                                              String absoluteBasePath,
                                              String directory) throws AppException {
    if (!directory.endsWith("/")) {
      directory += "/";
    }
    String folder = basePath + directory;
    String absolutePath = absoluteBasePath + directory;
    List<MultipartFile> files = fileUploadDTO.getFiles();
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

    return FileInformationDTO.builder()
        .setFileName(file.getOriginalFilename())
        .setFileURL(folder + newName)
        .build();
  }
}
