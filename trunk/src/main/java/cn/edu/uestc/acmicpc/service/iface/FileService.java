package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDto;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDto;

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
   * @param fileUploadDto {@link FileUploadDto} entity.
   * @param problemId     problem's id, if this data is uploaded in add new problem page, it
   *                      just named 'new'.
   * @return the number of data cases.
   * @throws AppException
   */
  public Integer uploadProblemDataFile(FileUploadDto fileUploadDto,
      String problemId) throws AppException;

  /**
   * Move problem data files from temporary into judge directory and return the
   * number of data cases.
   *
   * @param uploadFolder uploaded data folder.
   * @param problemId    problem's id.
   * @return and return the number of data cases.
   * @throws AppException
   */
  public Integer moveProblemDataFile(String uploadFolder, Integer problemId) throws AppException;

  /**
   * Upload contest archive ZIP file and return its FileInformationDto.
   *
   * @param fileUploadDto {@link FileUploadDto} entity.
   * @return the FileInformationDto of the archive file.
   * @throws AppException
   */
  public FileInformationDto uploadContestArchive(FileUploadDto fileUploadDto) throws AppException;
}
