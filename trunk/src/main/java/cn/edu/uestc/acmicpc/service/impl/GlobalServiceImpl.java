package cn.edu.uestc.acmicpc.service.impl;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.util.Global;

/**
 * Implementation for {@link GlobalService}.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@Service
@Primary
public class GlobalServiceImpl extends AbstractService implements GlobalService {

  @Override
  public List<Global.AuthenticationType> getAuthenticationTypeList() {
    return Arrays.asList(Global.AuthenticationType.values());
  }

  @Override
  public String getAuthenticationName(Integer type) {
    for (Global.AuthenticationType authenticationType : Global.AuthenticationType.values())
      if (authenticationType.ordinal() == type)
        return authenticationType.getDescription();
    return null;
  }
}
