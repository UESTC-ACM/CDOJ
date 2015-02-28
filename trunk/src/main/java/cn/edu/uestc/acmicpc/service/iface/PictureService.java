package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDto;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDto;

/**
 * Picture service interface.
 */
public interface PictureService {

  /**
   * Upload picture into target directory.
   *
   * @param fileUploadDto {@link FileUploadDto} entity.
   * @param directory     Directory, like "/problem/1/"
   * @return {@link FileInformationDto} entity.
   * @throws AppException
   */
  public FileInformationDto uploadPicture(FileUploadDto fileUploadDto,
      String directory) throws AppException;

  /**
   * When we add new problem/article, we upload images into /{category}/new/,
   * but after we save it we should move images into right place.
   *
   * @param content      old article content
   * @param oldDirectory old directory, match the prefix
   * @param newDirectory new directory
   * @return modified content
   * @throws AppException
   */
  public String modifyPictureLocation(String content,
      String oldDirectory, String newDirectory) throws AppException;
}
