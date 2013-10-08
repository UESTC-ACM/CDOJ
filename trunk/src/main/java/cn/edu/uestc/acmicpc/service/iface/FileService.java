package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;

/**
 * Description
 * TODO(mzry1992)
 */
public interface FileService {

  public void moveFile(String source, String target) throws AppException;

  /**
   * TODO(mzry1992)
   *
   * @param fileUploadDTO
   * @param problemId
   * @throws AppException
   */
  public Integer uploadProblemDataFile(FileUploadDTO fileUploadDTO,
                                       Integer problemId) throws AppException;

  /**
   * TODO(mzry1992)
   * @param problemId
   * @return
   * @throws AppException
   */
  public Integer moveProblemDataFile(Integer problemId) throws AppException;
}
