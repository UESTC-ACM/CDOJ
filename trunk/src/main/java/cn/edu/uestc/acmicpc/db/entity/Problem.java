package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;
import cn.edu.uestc.acmicpc.util.enums.ProblemType;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Problem information.
 */
@Table(name = "problem")
@Entity
@KeyField("problemId")
public class Problem implements Serializable {

  private static final long serialVersionUID = -334230877056963653L;
  private Integer problemId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return "Problem{" + "problemId=" + problemId + ", title='" + title + '\'' + ", description='"
        + description + '\'' + ", input='" + input + '\'' + ", output='" + output + '\''
        + ", sampleInput='" + sampleInput + '\'' + ", sampleOutput='" + sampleOutput + '\''
        + ", hint='" + hint + '\'' + ", source='" + source + '\'' + ", timeLimit=" + timeLimit
        + ", memoryLimit=" + memoryLimit + ", solved=" + solved + ", tried=" + tried + ", isSpj="
        + isSpj + ", isVisible=" + isVisible + ", outputLimit=" + outputLimit + ", dataCount=" + dataCount
        + ", difficulty=" + difficulty + ", type=" + type.name() + '}';
  }

  @Column(name = "problemId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  private String title;

  @Column(name = "title", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  private String description;

  @Column(name = "description", nullable = false, insertable = true, updatable = true,
      length = 65535, precision = 0)
  @Basic
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  private String input;

  @Column(name = "input", nullable = false, insertable = true, updatable = true, length = 65535,
      precision = 0)
  @Basic
  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  private String output;

  @Column(name = "output", nullable = false, insertable = true, updatable = true, length = 65535,
      precision = 0)
  @Basic
  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }

  private String sampleInput;

  @Column(name = "sampleInput", nullable = false, insertable = true, updatable = true,
      length = 65535, precision = 0)
  @Basic
  public String getSampleInput() {
    return sampleInput;
  }

  public void setSampleInput(String sampleInput) {
    this.sampleInput = sampleInput;
  }

  private String sampleOutput;

  @Column(name = "sampleOutput", nullable = false, insertable = true, updatable = true,
      length = 65535, precision = 0)
  @Basic
  public String getSampleOutput() {
    return sampleOutput;
  }

  public void setSampleOutput(String sampleOutput) {
    this.sampleOutput = sampleOutput;
  }

  private String hint;

  @Column(name = "hint", nullable = false, insertable = true, updatable = true, length = 65535,
      precision = 0)
  @Basic
  public String getHint() {
    return hint;
  }

  public void setHint(String hint) {
    this.hint = hint;
  }

  private String source = "";

  @Column(name = "source", nullable = false, insertable = true, updatable = true, length = 100,
      precision = 0)
  @Basic
  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  private Integer timeLimit = 1000;

  @Column(name = "timeLimit", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getTimeLimit() {
    return timeLimit;
  }

  public void setTimeLimit(Integer timeLimit) {
    this.timeLimit = timeLimit;
  }

  private Integer memoryLimit = 64;

  @Column(name = "memoryLimit", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getMemoryLimit() {
    return memoryLimit;
  }

  public void setMemoryLimit(Integer memoryLimit) {
    this.memoryLimit = memoryLimit;
  }

  private Integer solved = 0;

  @Column(name = "solved", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getSolved() {
    return solved;
  }

  public void setSolved(Integer solved) {
    this.solved = solved;
  }

  private Integer tried = 0;

  @Column(name = "tried", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getTried() {
    return tried;
  }

  public void setTried(Integer tried) {
    this.tried = tried;
  }

  private Boolean isSpj;

  @Column(name = "isSPJ", nullable = false, insertable = true, updatable = true, length = 0,
      precision = 0)
  @Basic
  public Boolean getIsSpj() {
    return isSpj;
  }

  public void setIsSpj(Boolean spj) {
    isSpj = spj;
  }

  private Boolean isVisible;

  @Column(name = "isVisible", nullable = false, insertable = true, updatable = true, length = 0,
      precision = 0)
  @Basic
  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean visible) {
    isVisible = visible;
  }

  private Integer outputLimit = 64;

  @Column(name = "outputLimit", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getOutputLimit() {
    return outputLimit;
  }

  public void setOutputLimit(Integer outputLimit) {
    this.outputLimit = outputLimit;
  }

  private Integer dataCount = 0;

  @Column(name = "dataCount", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getDataCount() {
    return dataCount;
  }

  public void setDataCount(Integer dataCount) {
    this.dataCount = dataCount;
  }

  private Integer difficulty = 1;

  @Column(name = "difficulty", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Integer difficulty) {
    this.difficulty = difficulty;
  }

  private ProblemType type = ProblemType.NORMAL;
  @Column(name = "type", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Enumerated(EnumType.ORDINAL)
  @Basic
  public ProblemType getType() {
    return type;
  }
  
  public void setType(ProblemType type) {
    this.type = type;
  }

  private Collection<ContestProblem> contestproblemsByProblemId;

  @OneToMany(mappedBy = "problemByProblemId", cascade = CascadeType.ALL)
  public Collection<ContestProblem> getContestproblemsByProblemId() {
    return contestproblemsByProblemId;
  }

  public void setContestproblemsByProblemId(Collection<ContestProblem> contestproblemsByProblemId) {
    this.contestproblemsByProblemId = contestproblemsByProblemId;
  }

  private Collection<ProblemTag> problemtagsByProblemId;

  @OneToMany(mappedBy = "problemByProblemId", cascade = CascadeType.ALL)
  public Collection<ProblemTag> getProblemtagsByProblemId() {
    return problemtagsByProblemId;
  }

  public void setProblemtagsByProblemId(Collection<ProblemTag> problemtagsByProblemId) {
    this.problemtagsByProblemId = problemtagsByProblemId;
  }

  private Collection<Status> statusesByProblemId;

  @OneToMany(mappedBy = "problemByProblemId", cascade = CascadeType.ALL)
  public Collection<Status> getStatusesByProblemId() {
    return statusesByProblemId;
  }

  public void setStatusesByProblemId(Collection<Status> statusesByProblemId) {
    this.statusesByProblemId = statusesByProblemId;
  }

  private Collection<Article> articlesByProblemId;

  @OneToMany(mappedBy = "problemByProblemId", cascade = CascadeType.ALL)
  public Collection<Article> getArticlesByProblemId() {
    return articlesByProblemId;
  }

  public void setArticlesByProblemId(Collection<Article> articlesByProblemId) {
    this.articlesByProblemId = articlesByProblemId;
  }
}
