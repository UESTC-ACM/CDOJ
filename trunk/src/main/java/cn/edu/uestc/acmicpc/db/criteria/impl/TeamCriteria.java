package cn.edu.uestc.acmicpc.db.criteria.impl;

import cn.edu.uestc.acmicpc.db.criteria.base.BaseCriteria;
import cn.edu.uestc.acmicpc.db.dto.Fields;
import cn.edu.uestc.acmicpc.db.dto.impl.TeamDto;
import cn.edu.uestc.acmicpc.db.entity.Team;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Team database criteria entity.
 */
public class TeamCriteria extends BaseCriteria<Team, TeamDto> {

  public TeamCriteria(Fields resultFields) {
    super(Team.class, TeamDto.class, resultFields);
  }

  public TeamCriteria() {
    this(null);
  }

  public Integer teamId;

  public String teamName;

  public Integer leaderId;

  public Boolean allow;

  @Override
  public DetachedCriteria getCriteria() throws AppException {
    DetachedCriteria criteria = super.getCriteria();
    if (teamId != null) {
      criteria.add(Restrictions.eq("teamId", teamId));
    }
    if (teamName != null) {
      criteria.add(Restrictions.eq("teamName", teamName));
    }
    return criteria;
  }
}
