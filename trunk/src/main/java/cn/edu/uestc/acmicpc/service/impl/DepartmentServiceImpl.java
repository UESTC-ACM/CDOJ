package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.impl.DepartmentCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.DepartmentDao;
import cn.edu.uestc.acmicpc.db.dto.field.DepartmentFields;
import cn.edu.uestc.acmicpc.db.dto.impl.DepartmentDto;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Description
 */
@Service
public class DepartmentServiceImpl extends AbstractService implements DepartmentService {

  private final DepartmentDao departmentDao;
  private final Map<Integer, DepartmentDto> departments;

  @Autowired
  public DepartmentServiceImpl(DepartmentDao departmentDao) {
    this.departmentDao = departmentDao;
    this.departments = new HashMap<>();
  }

  @PostConstruct
  public void init() throws AppException {
    departments.clear();
    try {
      DepartmentCriteria criteria = new DepartmentCriteria(DepartmentFields.ALL_FIELDS);
      List<DepartmentDto> departmentDtoList = departmentDao.findAll(criteria.getCriteria(), null);
      departmentDtoList.stream().forEach(dto -> departments.put(dto.getDepartmentId(), dto));
    } catch (NullPointerException e) {
    }
  }

  @Override
  public String getDepartmentName(Integer departmentId) {
    DepartmentDto department = departments.get(departmentId);
    if (department != null) {
      return department.getName();
    }
    return null;
  }

  @Override
  public List<DepartmentDto> getDepartmentList() {
    return new LinkedList<>(departments.values());
  }
}
