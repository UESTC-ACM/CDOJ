package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;

/**
 * File service interface.
 */
public interface FileService {

  /**
   * Move file from source to target.
   *
   * @param source source address.
   * @param target target address.
   * @throws AppException
   */
  public void moveFile(String source, String target) throws AppException;

  /**
   * Upload problem data file and return the number of data cases.
   *
   * @param fileUploadDTO {@link FileUploadDTO} entity.
   * @param problemId     problem's id, if this data is uploaded in add new problem page, it just named 'new'.
   * @return the number of data cases.
   * @throws AppException
   */
  public Integer uploadProblemDataFile(FileUploadDTO fileUploadDTO,
                                       String problemId) throws AppException;

  /**
   * Move problem data files from temporary into judge directory  and return the number of
   * data cases.
   *
   * @param uploadFolder uploaded data folder.
   * @param problemId problem's id.
   * @return and return the number of data cases.
   * @throws AppException
   */
  public Integer moveProblemDataFile(String uploadFolder, Integer problemId) throws AppException;


  /**
   * Upload contest archive ZIP file and return its FileInformationDTO.
   * @param fileUploadDTO {@link FileUploadDTO} entity.
   * @return the FileInformationDTO of the archive file.
   * @throws AppException
   */
  public FileInformationDTO uploadContestArchive(FileUploadDTO fileUploadDTO) throws AppException;
}
