package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.CompileInfoDao;
import cn.edu.uestc.acmicpc.db.entity.CompileInfo;
import cn.edu.uestc.acmicpc.service.iface.CompileInfoService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CompileInfoServiceImpl extends AbstractService implements CompileInfoService {

  private final CompileInfoDao compileInfoDao;

  @Autowired
  public CompileInfoServiceImpl(CompileInfoDao compileInfoDao) {
    this.compileInfoDao = compileInfoDao;
  }

  @Override
  public String getCompileInfo(Integer compileInfoId) throws AppException {
    return (String) compileInfoDao.getEntityByUniqueField("compileInfoId", compileInfoId,
        "content", true);
  }

  @Override
  public void updateCompileInfoContent(Integer compileInfoId, String content)
      throws AppException {
    compileInfoDao.updateEntitiesByField("content", content, "compileInfoId",
        compileInfoId.toString());
  }

  @Override
  public Integer createCompileInfo(String content)
      throws AppException {
    CompileInfo compileInfo = new CompileInfo();
    compileInfo.setContent(content);
    compileInfoDao.addOrUpdate(compileInfo);
    return compileInfo.getCompileInfoId();
  }
}
