package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class ArticleCondition extends BaseCondition {
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

    @Override
    public void invoke(Condition condition) {
        super.invoke(condition);
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
}
