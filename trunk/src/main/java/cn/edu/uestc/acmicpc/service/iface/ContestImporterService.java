package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDTO;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;

/**
 * Contest importer service interface.
 */
public interface ContestImporterService {

  /**
   * Parse contest information via a ZIP fileInformationDTO.
   *
   * @param fileInformationDTO contest archive ZIP.
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDTO} a new contest DTO.
   * @throws AppException
   */
  public ContestDTO parseContestZipArchive(FileInformationDTO fileInformationDTO) throws AppException;
}
