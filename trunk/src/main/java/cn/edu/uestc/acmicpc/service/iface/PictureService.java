package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;

import java.util.ArrayList;

/**
 * Picture service interface.
 */
public interface PictureService {

  /**
   * Gets a user's all pictures.
   *
   * @param userId user id.
   * @return picture's file name.
   * @throws AppException
   */
  @Deprecated
  public ArrayList<String> getPictures(Integer userId) throws AppException;

  /**
   * Upload pictures.
   *
   * @param fileUploadDTO file upload DTO to for file uploading.
   * @param userId        user id.
   * @return a DTO containing file information the user uploaded.
   * @throws AppException
   */
  @Deprecated
  public FileInformationDTO uploadPictures(FileUploadDTO fileUploadDTO,
                                           Integer userId) throws AppException;

  /**
   * Upload picture into target directory.
   *
   * @param fileUploadDTO {@link FileUploadDTO} entity.
   * @param directory     Directory, like "/problem/1/"
   * @return {@link FileInformationDTO} entity.
   * @throws AppException
   */
  public FileInformationDTO uploadPicture(FileUploadDTO fileUploadDTO,
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
