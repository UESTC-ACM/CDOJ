package cn.edu.uestc.acmicpc.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.util.Settings;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;

@Service
public class PictureServiceImpl extends AbstractService implements PictureService {

  private final Settings settings;

  @Autowired
  public PictureServiceImpl(Settings settings) {
    this.settings = settings;
  }

  @Override
  public ArrayList<String> getPictures(Integer userId) throws AppException {
    //TODO(mzry1992) use database
    String folder = settings.SETTING_USER_PICTURE_FOLDER + userId + "/";
    String path = settings.SETTING_USER_PICTURE_FOLDER_ABSOLUTE + userId + "/";
    File dir = new File(path);
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
        pictures.add(folder + fileName);
    }
    return pictures;
  }

  @Override
  public FileInformationDTO uploadPictures(FileUploadDTO fileUploadDTO,
      Integer userId) throws AppException {
    String folder = settings.SETTING_USER_PICTURE_FOLDER + userId + "/";
    String path = settings.SETTING_USER_PICTURE_FOLDER_ABSOLUTE + userId + "/";
    List<MultipartFile> files = fileUploadDTO.getFiles();
    if (files == null || files.size() > 1)
      throw new AppException("Fetch uploaded file error.");
    MultipartFile file = files.get(0);
    File dir = new File(path);
    if (!dir.exists())
      if (!dir.mkdirs())
        throw new AppException("Error while make picture directory!");

    String newName = StringUtil.generateFileName(file.getOriginalFilename());
    File newFile =
        new File(path + newName);
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

  @Override
  public FileInformationDTO uploadPicture(FileUploadDTO fileUploadDTO,
      String directory) throws AppException {
    String folder = settings.SETTING_PICTURE_FOLDER + directory;
    String path = settings.SETTING_PICTURE_FOLDER_ABSOLUTE + directory;
    List<MultipartFile> files = fileUploadDTO.getFiles();
    if (files == null || files.size() > 1)
      throw new AppException("Fetch uploaded file error.");
    MultipartFile file = files.get(0);
    File dir = new File(path);
    if (!dir.exists())
      if (!dir.mkdirs())
        throw new AppException("Error while make picture directory!");

    String newName = StringUtil.generateFileName(file.getOriginalFilename());
    File newFile =
        new File(path + newName);
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

  @Override
  public String modifyPictureLocation(String content, String oldDirectory,
      String newDirectory) throws AppException {
    String imagePatternString = "!\\[.*\\]\\(" + oldDirectory + "(\\S*)\\)";
    Pattern imagePattern = Pattern.compile(imagePatternString);
    Matcher matcher = imagePattern.matcher(content);
    while (matcher.find()) {
      String imageLocation = matcher.group(1);

      String oldImageLocation = settings.SETTING_ABSOLUTE_PATH + oldDirectory + imageLocation;
      String newImageLocation = settings.SETTING_ABSOLUTE_PATH + newDirectory + imageLocation;

      File oldFile = new File(oldImageLocation);
      if (!oldFile.exists()) {
        continue;
      }

      File newPath = new File(settings.SETTING_ABSOLUTE_PATH + newDirectory);
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
    content = content.replaceAll(imagePatternString,   imageReplacePatternString);
    return content;
  }

}
