package cn.edu.uestc.acmicpc.service.impl;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.DepartmentDTO;
import cn.edu.uestc.acmicpc.db.entity.Department;
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

  private DepartmentDTO getDepartmentDTO(Department department) {
    return DepartmentDTO.builder()
        .setDepartmentId(department.getDepartmentId())
        .setName(department.getName())
        .build();
  }

  @SuppressWarnings("unchecked")
  @PostConstruct
  public void init() throws AppException {
    List<Department> departmentList;
    try {
      departmentList = (List<Department>) departmentDAO.findAll();
    } catch (NullPointerException e) {
      departmentList = new LinkedList<>();
    }
    departmentDTOList = new LinkedList<>();
    for (Department department: departmentList)
      departmentDTOList.add(getDepartmentDTO(department));
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
