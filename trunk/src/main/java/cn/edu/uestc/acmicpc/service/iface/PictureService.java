package cn.edu.uestc.acmicpc.service.iface;

import java.util.ArrayList;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;

public interface PictureService {

  /**
   * TODO(mzry1992)
   *
   * @param userId
   * @return
   * @throws AppException
   */
  @Deprecated
  public ArrayList<String> getPictures(Integer userId) throws AppException;

  /**
   * TODO(mzry1992)
   *
   * @param fileUploadDTO
   * @param userId
   * @return
   * @throws AppException
   */
  @Deprecated
  public FileInformationDTO uploadPictures(FileUploadDTO fileUploadDTO,
      Integer userId) throws AppException;

  /**
   * Upload picture into dir
   *
   * @param fileUploadDTO
   * @param directory Directory, like "/problem/1/"
   * @return File information DTO
   * @throws AppException
   */
  public FileInformationDTO uploadPicture(FileUploadDTO fileUploadDTO,
      String directory) throws AppException;

  /**
   * When we add new problem/article, we upload images into /{category}/new/,
   * but after we save it we should move images into right place.
   *
   * @param content old article content
   * @param oldDirectory old directory, match the prefix
   * @param newDirectory new directory
   * @return modified content
   * @throws AppException
   */
  public String modifyPictureLocation(String content,
      String oldDirectory, String newDirectory) throws AppException;
}
