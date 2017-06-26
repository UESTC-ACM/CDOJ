package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dto.field.TeamFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TeamDto;
import cn.edu.uestc.acmicpc.db.entity.Team;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Team database criteria entity.
 */
public class TeamCriteria extends BaseCriteria<Team, TeamDto> {

  public TeamCriteria() {
    super(Team.class, TeamDto.class);
  }

  public Integer teamId;
  public String teamName;
  public Integer leaderId;
  public Integer userId;
  public Boolean allow;

  @Override
  DetachedCriteria updateCriteria(DetachedCriteria criteria) throws AppException {
    if (teamId != null) {
      criteria.add(Restrictions.eq("teamId", teamId));
    }
    if (teamName != null) {
      criteria.add(Restrictions.ilike("teamName", teamName));
    }
    if (leaderId != null) {
      criteria.add(Restrictions.eq("leaderId", leaderId));
    }
    if (userId != null) {
      criteria.add(Restrictions.eq("teamUsers.userId", userId));
      addAlias(TeamFields.ALIAS_TEAM_USERS);
    }
    if (allow != null) {
      criteria.add(Restrictions.eq("teamUsers.allow", allow));
      addAlias(TeamFields.ALIAS_TEAM_USERS);
    }
    return criteria;
  }
}
