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
   *
   * @param fileUploadDTO
   * @param problemId
   * @throws AppException
   */
  // TODO(mzry1992)
  public Integer uploadProblemDataFile(FileUploadDTO fileUploadDTO,
      Integer problemId) throws AppException;

  /**
   * @param problemId
   * @return problem id.
   * @throws AppException
   */
  // TODO(mzry1992): only to pass javadoc process, please change the meaning of @return.
  public Integer moveProblemDataFile(Integer problemId) throws AppException;
}
