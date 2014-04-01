package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.FileUploadUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PictureServiceImpl extends AbstractService implements PictureService {

  private final Settings settings;

  @Autowired
  public PictureServiceImpl(Settings settings) {
    this.settings = settings;
  }

  @Override
  public FileInformationDTO uploadPicture(FileUploadDTO fileUploadDTO,
                                          String directory) throws AppException {
    return FileUploadUtil.uploadFile(fileUploadDTO, settings.PICTURE_FOLDER,
        settings.PICTURE_FOLDER, directory);
  }

  @Override
  public String modifyPictureLocation(String content, String oldDirectory,
                                      String newDirectory) throws AppException {
    String imagePatternString = "!\\[.*\\]\\(" + oldDirectory + "(\\S*)\\)";
    Pattern imagePattern = Pattern.compile(imagePatternString);
    Matcher matcher = imagePattern.matcher(content);
    while (matcher.find()) {
      String imageLocation = matcher.group(1);

      String oldImageLocation = settings.PICTURE_FOLDER + oldDirectory + imageLocation;
      String newImageLocation = settings.PICTURE_FOLDER + newDirectory + imageLocation;

      File oldFile = new File(oldImageLocation);
      if (!oldFile.exists()) {
        continue;
      }

      File newPath = new File(settings.PICTURE_FOLDER + newDirectory);
      if (!newPath.exists()) {
        if (!newPath.mkdirs()) {
          throw new AppException("Error while make picture directory!");
        }
      }

      if (!oldFile.renameTo(new File(newImageLocation))) {
        throw new AppException("Unable to move images!");
      }
    }
    String imageReplacePatternString = "!\\[.*\\]\\(" + newDirectory + "$1\\)";
    content = content.replaceAll(imagePatternString, imageReplacePatternString);
    return content;
  }

}
