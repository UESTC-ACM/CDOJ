package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;

/**
 * File service interface.
 */
public interface FileService {

  public void moveFile(String source, String target) throws AppException;

  /**
   * Upload problem data file and return the number of data cases.
   *
   * @param fileUploadDTO {@link FileUploadDTO} entity.
   * @param problemId     problem's id.
   * @return the number of data cases.
   * @throws AppException
   */
  public Integer uploadProblemDataFile(FileUploadDTO fileUploadDTO,
                                       Integer problemId) throws AppException;

  /**
   * Move problem data files from temporary into judge directory  and return the number of
   * data cases.
   *
   * @param problemId problem's id.
   * @return and return the number of data cases.
   * @throws AppException
   */
  public Integer moveProblemDataFile(Integer problemId) throws AppException;
}
