package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.ICompileInfoDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IDAO;
import cn.edu.uestc.acmicpc.db.entity.CompileInfo;
import cn.edu.uestc.acmicpc.service.iface.CompileInfoService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * Description
 */
@Service
@Primary
public class CompileInfoServiceImpl extends AbstractService implements CompileInfoService {

  private ICompileInfoDAO compileInfoDAO;

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
