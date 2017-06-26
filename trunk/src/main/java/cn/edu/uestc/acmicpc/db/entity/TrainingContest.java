package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TrainingContest Information
 */
@Table(name = "trainingContest")
@Entity
@KeyField("trainingContestId")
public class TrainingContest implements Serializable {

  private static final long serialVersionUID = 3878251445851680913L;

  private Integer trainingContestId;

  @Column(name = "trainingContestId", nullable = false, length = 10, unique = true)
  @Id
  @GeneratedValue
  public Integer getTrainingContestId() {
    return trainingContestId;
  }

  public void setTrainingContestId(Integer trainingContestId) {
    this.trainingContestId = trainingContestId;
  }

  private Integer trainingId;

  @Column(name = "trainingId", nullable = false, length = 10)
  public Integer getTrainingId() {
    return trainingId;
  }

  public void setTrainingId(Integer trainingId) {
    this.trainingId = trainingId;
  }

  private String title;

  @Column(name = "title", nullable = false)
  @Basic
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  private String link;

  @Column(name = "link")
  @Basic
  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  private String rankList;

  @Column(name = "rankList", nullable = false, length = 16777215)
  @Basic
  public String getRankList() {
    return rankList;
  }

  public void setRankList(String rankList) {
    this.rankList = rankList;
  }

  private Integer type;

  @Column(name = "type", nullable = false, length = 10)
  @Basic
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  private Integer platformType;

  @Column(name = "platformType", nullable = false, length = 10)
  @Basic
  public Integer getPlatformType() {
    return platformType;
  }

  public void setPlatformType(Integer platformType) {
    this.platformType = platformType;
  }

  public Training trainingByTrainingId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "trainingId", referencedColumnName = "trainingId", nullable = false, insertable = false, updatable = false)
  public Training getTrainingByTrainingId() {
    return trainingByTrainingId;
  }

  public void setTrainingByTrainingId(Training trainingByTrainingId) {
    this.trainingByTrainingId = trainingByTrainingId;
  }
}
