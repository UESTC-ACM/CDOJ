package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.entity.CompileInfo;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Description
 * TODO(mzry1992)
 */
public interface CompileInfoService extends DatabaseService<CompileInfo, Integer> {

  public String getCompileInfo(Integer compileInfoId) throws AppException;
}
