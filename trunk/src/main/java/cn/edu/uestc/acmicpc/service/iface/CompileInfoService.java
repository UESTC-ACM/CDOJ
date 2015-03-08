package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * CompileInfo service interface.
 */
public interface CompileInfoService {

  /**
   * Get compile info by compile info id.
   *
   * @param compileInfoId compile info's id.
   * @return string value of compile info.
   * @throws AppException
   */
  public String getCompileInfo(Integer compileInfoId) throws AppException;

  /**
   * Update compile info.
   *
   * @param compileInfoId compile info's id.
   * @param content       new content.
   * @throws AppException
   */
  public void updateCompileInfoContent(Integer compileInfoId, String content) throws AppException;

  /**
   * Create a new compile info record.
   *
   * @param content compile info content.
   * @return record's id.
   * @throws AppException
   */
  public Integer createCompileInfo(String content) throws AppException;
}
