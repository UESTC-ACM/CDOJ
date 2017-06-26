package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.util.dto.RecentContestDto;
import java.util.List;

/**
 * Recent contest service interface
 */
public interface RecentContestService {

  /**
   * Get recent contest list
   *
   * @return list of {@link cn.edu.uestc.acmicpc.util.dto.RecentContestDto}
   * entity.
   */
  public List<RecentContestDto> getRecentContestList();
}
