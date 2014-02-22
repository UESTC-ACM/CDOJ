package cn.edu.uestc.acmicpc.service.iface;

import java.util.ArrayList;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;

/**
 * Zip service interface.
 */
public interface ZipService {

  /**
   * Upload contest archive ZIP into target directory.
   *
   * @param fileUploadDTO {@link FileUploadDTO} entity.
   * @param directory     Directory, like "/contest/1/"
   * @return {@link FileInformationDTO} entity.
   * @throws AppException
   */
  public FileInformationDTO uploadContestZip(FileUploadDTO fileUploadDTO,
                                      String directory) throws AppException;
}
