package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.service.iface.FileService;
import cn.edu.uestc.acmicpc.util.Settings;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Implement for {@link FileService}
 */
@Service
@Primary
public class FileServiceImpl extends AbstractService implements FileService {

  private final Settings settings;

  @Autowired
  public FileServiceImpl(Settings settings) {
    this.settings = settings;
  }

  @Override
  public ArrayList<String> getProblemPictures(Integer problemId) throws AppException {
    File dir = new File(settings.SETTING_PROBLEM_PICTURE_FOLDER_ABSOLUTE + problemId + "/");
    if (!dir.exists())
      if (!dir.mkdirs())
        throw new AppException("Error while make picture directory!");
    File files[] = dir.listFiles();
    if (files == null)
      throw new AppException("Error while list pictures!");
    ArrayList<String> pictures = new ArrayList<>();
    pictures.clear();
    for (File file : files) {
      String fileName = file.getName();
      ImageInputStream iis = null;
      try {
        iis = ImageIO.createImageInputStream(file);
      } catch (IOException e) {
        throw new AppException("Error while create image input stream.");
      }
      Iterator<?> iter = ImageIO.getImageReaders(iis);
      if (iter.hasNext())
        pictures.add(settings.SETTING_PROBLEM_PICTURE_FOLDER + problemId + "/" + fileName);
    }
    return pictures;
  }

  @Override
  public FileInformationDTO uploadProblemPictures(FileUploadDTO fileUploadDTO,
                                                  Integer problemId) throws AppException {
    List<MultipartFile> files = fileUploadDTO.getFiles();
    if (files == null || files.size() > 1)
      throw new AppException("Fetch uploaded file error.");
    MultipartFile file = files.get(0);
    File dir = new File(settings.SETTING_PROBLEM_PICTURE_FOLDER_ABSOLUTE + problemId + "/");
    if (!dir.exists())
      if (!dir.mkdirs())
        throw new AppException("Error while make picture directory!");

    String newName = StringUtil.generateFileName(file.getOriginalFilename());
    File newFile =
        new File(settings.SETTING_PROBLEM_PICTURE_FOLDER_ABSOLUTE + problemId + "/"
            + newName);
    try {
      file.transferTo(newFile);
    } catch (IOException e) {
      throw new AppException("Error while rename files");
    }

    return FileInformationDTO.builder()
        .setFileName(file.getOriginalFilename())
        .setFileURL(settings.SETTING_PROBLEM_PICTURE_FOLDER + problemId + "/" + newName)
        .build();
  }
}
