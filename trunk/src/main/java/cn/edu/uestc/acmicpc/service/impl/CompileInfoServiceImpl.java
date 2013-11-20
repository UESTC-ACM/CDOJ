package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.ICompileInfoDAO;
import cn.edu.uestc.acmicpc.service.iface.CompileInfoService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description
 */
@Service
public class CompileInfoServiceImpl extends AbstractService implements CompileInfoService {

  private final ICompileInfoDAO compileInfoDAO;

  @Autowired
  public CompileInfoServiceImpl(ICompileInfoDAO compileInfoDAO) {
    this.compileInfoDAO = compileInfoDAO;
  }

  @Override
  public String getCompileInfo(Integer compileInfoId) throws AppException {
    return (String)compileInfoDAO.getEntityByUniqueField("compileInfoId", compileInfoId,
        "content", true);
  }

  @Override
  public ICompileInfoDAO getDAO() {
    return compileInfoDAO;
  }
}
