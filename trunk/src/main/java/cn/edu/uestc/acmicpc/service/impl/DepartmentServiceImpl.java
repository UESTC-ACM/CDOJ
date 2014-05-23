package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.dao.iface.DepartmentDao;
import cn.edu.uestc.acmicpc.db.dto.impl.department.DepartmentDTO;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 * Description
 */
@Service
public class DepartmentServiceImpl extends AbstractService implements DepartmentService {

  private final DepartmentDao departmentDao;
  private List<DepartmentDTO> departmentDTOList;

  @Autowired
  public DepartmentServiceImpl(DepartmentDao departmentDao) {
    this.departmentDao = departmentDao;
  }

  @PostConstruct
  public void init() throws AppException {
    try {
      departmentDTOList = departmentDao.findAll(DepartmentDTO.class, DepartmentDTO.builder(),
          new Condition());
    } catch (NullPointerException e) {
      departmentDTOList = new LinkedList<>();
    }
  }

  @Override
  public String getDepartmentName(Integer departmentId) {
    for (DepartmentDTO department : departmentDTOList)
      if (department.getDepartmentId().equals(departmentId))
        return department.getName();
    return null;
  }

  @Override
  public List<DepartmentDTO> getDepartmentList() {
    return departmentDTOList;
  }

  @Override
  public DepartmentDao getDao() {
    return departmentDao;
  }
}
