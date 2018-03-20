package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dto.impl.UserDto;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.util.Set;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Database query criteria for user entity.
 */
public class UserCriteria extends BaseCriteria<User, UserDto> {

  public UserCriteria() {
    super(User.class, UserDto.class);
  }

  public Integer userId;
  public Set<Integer> userIds;
  public String userNameForUniqueQuery;
  public String userName;
  public String nickName;
  public Integer type;
  public Integer departmentId;
  public String school;
  public String keyword;
  public String emailForUniqueQuery;

  @Override
  DetachedCriteria updateCriteria(DetachedCriteria criteria) throws AppException {
    if (userId != null) {
      criteria.add(Restrictions.eq("userId", userId));
    } else if (userIds != null) {
      if (userIds.isEmpty()) {
        criteria.add(Restrictions.sqlRestriction("1=0"));
      } else {
        criteria.add(Restrictions.in("userId", userIds));
      }
    }
    if (userNameForUniqueQuery != null) {
      criteria.add(Restrictions.like("userName", userNameForUniqueQuery));
    } else if (userName != null) {
      criteria.add(Restrictions.like("userName", wrapLike(userName)));
    }
    if (nickName != null) {
      criteria.add(Restrictions.like("nickName", wrapLike(nickName)));
    }
    if (type != null) {
      criteria.add(Restrictions.eq("type", type));
    }
    if (departmentId != null) {
      criteria.add(Restrictions.eq("departmentId", departmentId));
    }
    if (school != null) {
      criteria.add(Restrictions.like("school", wrapLike(school)));
    }
    if (keyword != null) {
      criteria.add(Restrictions.or(
          Restrictions.like("userName", wrapLike(keyword)),
          Restrictions.like("nickName", wrapLike(keyword))));
    }
    if (emailForUniqueQuery != null) {
      criteria.add(Restrictions.like("email", emailForUniqueQuery));
    }
    return criteria;
  }
}
