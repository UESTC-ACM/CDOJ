package cn.edu.uestc.acmicpc.db.condition.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;

/**
 * Problem search condition.
 */
@Repository
public class ProblemCondition extends BaseCondition {
//
//  /**
//   * Start user id.
//   */
//  private Integer startId;
//  /**
//   * End user id.
//   */
//  private Integer endId;
//
//  /**
//   * Title.
//   */
//  private String title;
//  private String source;
//
//  /**
//   * Keyword for {@code description}, {@code input}, {@code output}, {@code sampleInput},
//   * {@code sampleOutput} and {@code hint}.
//   */
//  private String keyword;
//
//  private Boolean isSpj;
//
//  private Boolean isVisible;
//
//  private Integer startDifficulty;
//
//  private Boolean isTitleEmpty;
//
//  public Boolean getIsTitleEmpty() {
//    return isTitleEmpty;
//  }
//
//  public void setIsTitleEmpty(Boolean isTitleEmpty) {
//    this.isTitleEmpty = isTitleEmpty;
//  }
//
//  @Exp(mapField = "difficulty", type = ConditionType.le)
//  public Integer getEndDifficulty() {
//    return endDifficulty;
//  }
//
//  public void setEndDifficulty(Integer endDifficulty) {
//    this.endDifficulty = endDifficulty;
//  }
//
//  @Exp(mapField = "difficulty", type = ConditionType.ge)
//  public Integer getStartDifficulty() {
//    return startDifficulty;
//  }
//
//  public void setStartDifficulty(Integer startDifficulty) {
//    this.startDifficulty = startDifficulty;
//  }
//
//  @Exp(type = ConditionType.eq)
//  public Boolean getIsVisible() {
//    return isVisible;
//  }
//
//  public void setIsVisible(Boolean visible) {
//    isVisible = visible;
//  }
//
//  @Exp(type = ConditionType.eq)
//  public Boolean getIsSpj() {
//    return isSpj;
//  }
//
//  public void setIsSpj(Boolean spj) {
//    isSpj = spj;
//  }
//
//  public String getKeyword() {
//    return keyword;
//  }
//
//  public void setKeyword(String keyword) {
//    this.keyword = keyword;
//  }
//
//  @Exp(type = ConditionType.like)
//  public String getSource() {
//    return source;
//  }
//
//  public void setSource(String source) {
//    this.source = source;
//  }
//
//  @Exp(type = ConditionType.like)
//  public String getTitle() {
//    return title;
//  }
//
//  public void setTitle(String title) {
//    this.title = title;
//  }
//
//  @Exp(mapField = "problemId", type = ConditionType.le)
//  public Integer getEndId() {
//    return endId;
//  }
//
//  public void setEndId(Integer endId) {
//    this.endId = endId;
//  }
//
//  @Exp(mapField = "problemId", type = ConditionType.ge)
//  public Integer getStartId() {
//    return startId;
//  }
//
//  public void setStartId(Integer startId) {
//    this.startId = startId;
//  }
//
//  private Integer endDifficulty;
//
//  @Override
//  public void invoke(Condition condition) {
//    super.invoke(condition);
//    if (keyword != null) {
//      String[] fields =
//          new String[] { "description", "input", "output", "sampleInput", "sampleOutput", "hint" };
//      Junction junction = Restrictions.disjunction();
//      for (String field : fields) {
//        junction.add(Restrictions.like(field, String.format("%%%s%%", keyword)));
//      }
//      condition.addCriterion(junction);
//    }
//    if (isTitleEmpty != null) {
//      if (isTitleEmpty) {
//        condition.addCriterion(Restrictions.like("title", ""));
//      } else {
//        condition.addCriterion(Restrictions.like("title", "_%"));
//      }
//    }
//  }
}
