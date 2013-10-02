package cn.edu.uestc.acmicpc.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.dto.impl.department.DepartmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Description
 */
@Service
@Primary
public class DepartmentServiceImpl extends AbstractService implements DepartmentService {

  private final IDepartmentDAO departmentDAO;
  private List<DepartmentDTO> departmentDTOList;

  @Autowired
  public DepartmentServiceImpl(IDepartmentDAO departmentDAO) {
    this.departmentDAO = departmentDAO;
  }

  @PostConstruct
  public void init() {
    try {
      departmentDTOList = departmentDAO.findAll(DepartmentDTO.class, DepartmentDTO.builder(),
          new Condition());
    } catch (AppException e) {
      departmentDTOList = new LinkedList<>();
    }
  }

  @Override
  public String getDepartmentName(Integer departmentId) {
    for (DepartmentDTO department: departmentDTOList)
      if (department.getDepartmentId().equals(departmentId))
        return department.getName();
    return null;
  }

  @Override
  public List<DepartmentDTO> getDepartmentList() {
    return departmentDTOList;
  }

  @Override
  public IDepartmentDAO getDAO() {
    return departmentDAO;
  }
}
