package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;

import java.util.ArrayList;

/**
 * Description
 * TODO(mzry1992)
 */
public interface FileService {

  /**
   * TODO(mzry1992)
   * @param problemId
   * @return
   * @throws AppException
   */
  public ArrayList<String> getProblemPictures(Integer problemId) throws AppException;

  /**
   * TODO(mzry1992)
   * @param fileUploadDTO
   * @param problemId
   * @return
   * @throws AppException
   */
  public FileInformationDTO uploadProblemPictures(FileUploadDTO fileUploadDTO,
                                                  Integer problemId) throws AppException;
}
