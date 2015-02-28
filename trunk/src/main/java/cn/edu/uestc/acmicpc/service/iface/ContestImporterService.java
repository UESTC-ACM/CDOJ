package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.ContestDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDto;

/**
 * Contest importer service interface.
 */
public interface ContestImporterService {

  /**
   * Parse contest information via a ZIP fileInformationDto.
   *
   * @param fileInformationDto contest archive ZIP.
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.ContestDto} a new
   * contest Dto.
   * @throws AppException
   */
  public ContestDto parseContestZipArchive(FileInformationDto fileInformationDto)
      throws AppException;
}
