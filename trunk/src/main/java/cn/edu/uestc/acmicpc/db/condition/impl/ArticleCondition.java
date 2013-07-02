package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.JoinedProperty;
import cn.edu.uestc.acmicpc.db.dao.iface.IDAO;
import cn.edu.uestc.acmicpc.db.dao.impl.UserDAO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.ioc.condition.UserConditionAware;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class ArticleCondition extends BaseCondition implements UserConditionAware {
    private Integer startId;
    private Integer endId;

    private String title;
    private String keyword;

    private Boolean isVisible;
    private Boolean isNotice;

    private Boolean isTitleEmpty;

    public Boolean getIsTitleEmpty() {
        return isTitleEmpty;
    }

    public void setIsTitleEmpty(Boolean isTitleEmpty) {
        this.isTitleEmpty = isTitleEmpty;
    }

    @Exp(Type = ConditionType.eq)
    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean visible) {
        isVisible = visible;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Exp(Type = ConditionType.like)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Exp(MapField = "articleId", Type = ConditionType.le)
    public Integer getEndId() {
        return endId;
    }

    public void setEndId(Integer endId) {
        this.endId = endId;
    }

    @Exp(MapField = "articleId", Type = ConditionType.ge)
    public Integer getStartId() {
        return startId;
    }

    public void setStartId(Integer startId) {
        this.startId = startId;
    }


    /**
     * User's id.
     */
    private Integer userId;

    /**
     * User's name
     */
    private String userName;

    /**
     * Problem's id.
     */
    private Integer problemId;

    /**
     * Contest's id.
     */
    private Integer contestId;


    @Exp(MapField = "userByUserId", Type = ConditionType.eq, MapObject = User.class)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }


    @Override
    public void invoke(Condition condition) {
        super.invoke(condition);

        if (contestId != null) {
            if (contestId == -1)
                condition.addCriterion(Restrictions.isNull("contestByContestId"));
            else {
                try {
                    IDAO DAO = (IDAO) applicationContext.getBean("contestDAO");
                    JoinedProperty joinedProperty = new JoinedProperty(
                            Restrictions.eq("contestByContestId", DAO.get(contestId)),
                            contestId, ConditionType.eq);
                    condition.addJoinedProperty("contestByContestId", joinedProperty);
                } catch (AppException ignored) {
                }
            }
        }

        if (problemId != null) {
            if (problemId == -1)
                condition.addCriterion(Restrictions.isNull("problemByProblemId"));
            else {
                try {
                    IDAO DAO = (IDAO) applicationContext.getBean("problemDAO");
                    JoinedProperty joinedProperty = new JoinedProperty(
                            Restrictions.eq("problemByProblemId", DAO.get(problemId)),
                            contestId, ConditionType.eq);
                    condition.addJoinedProperty("problemByProblemId", joinedProperty);
                } catch (AppException ignored) {
                }
            }
        }

        if (userName != null) {
            UserDAO userDAO = applicationContext.getBean("userDAO", UserDAO.class);
            userCondition.clear();
            userCondition.setUserName(userName);
            try {
                List<User> users = (List<User>) userDAO.findAll(userCondition.getCondition());
                if (users != null && !users.isEmpty()) {
                    JoinedProperty joinedProperty = new JoinedProperty(
                            Restrictions.eq("userByUserId", users.get(0)), users.get(0).getUserId(),
                            ConditionType.eq);
                    condition.addJoinedProperty("userByUserId", joinedProperty);
                }
            } catch (AppException ignored) {
            }
        }

        if (keyword != null) {
            String[] fields = new String[]{"title", "content", "author"};
            Junction junction = Restrictions.disjunction();
            for (String field : fields) {
                junction.add(Restrictions.like(field, String.format("%%%s%%", keyword)));
            }
            condition.addCriterion(junction);
        }
        if (isTitleEmpty != null) {
            if (isTitleEmpty) {
                condition.addCriterion(Restrictions.like("title", ""));
            } else {
                condition.addCriterion(Restrictions.like("title", "_%"));
            }
        }
    }

    private UserCondition userCondition;

    @Override
    public void setUserCondition(UserCondition userCondition) {
        this.userCondition = userCondition;
    }

    @Override
    public UserCondition getUserCondition() {
        return userCondition;
    }
}
