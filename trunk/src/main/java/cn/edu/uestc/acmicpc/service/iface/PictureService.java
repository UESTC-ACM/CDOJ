package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;

import java.util.ArrayList;

public interface PictureService {

  /**
   * TODO(mzry1992)
   * @param userId
   * @return
   * @throws AppException
   */
  public ArrayList<String> getPictures(Integer userId) throws AppException;

  /**
   * TODO(mzry1992)
   * @param fileUploadDTO
   * @param userId
   * @return
   * @throws AppException
   */
  public FileInformationDTO uploadPictures(FileUploadDTO fileUploadDTO,
      Integer userId) throws AppException;
}
