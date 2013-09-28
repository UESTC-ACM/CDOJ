package cn.edu.uestc.acmicpc.db.dto.impl;

import java.util.List;


import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;

/**
 * Data transfer object for {@link Problem}.
 */
public class ProblemListDTO implements BaseDTO<Problem>{
  
  private Integer problemId;
  private String title;
  private String source;
  private Integer solved;
  private Integer tried;
  private Boolean isSpj;
  private Boolean isVisible;
  private Integer difficulty;
  private List<String> tags;
  
  public ProblemListDTO() {
  }
  
  private ProblemListDTO(Integer problemId, String title, String source,Integer solved, 
                         Integer tried, Boolean isSpj, Boolean isVisible, Integer difficulty) {
    this.problemId = problemId;
    this.title = title;
    this.source = source;
    this.solved = solved;
    this.tried = tried;
    this.isSpj = isSpj;
    this.isVisible = isVisible;
    this.difficulty = difficulty;
    //TODO(mzry1992): Add tags.
  }

  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public Integer getSolved() {
    return solved;
  }

  public void setSolved(Integer solved) {
    this.solved = solved;
  }

  public Integer getTried() {
    return tried;
  }

  public void setTried(Integer tried) {
    this.tried = tried;
  }
  
  public Boolean getIsSpj() {
    return isSpj;
  }

  public void setIsSpj(Boolean isSpj) {
    this.isSpj = isSpj;
  }

  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
  }

  public Integer getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Integer difficulty) {
    this.difficulty = difficulty;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public static Builder builder() {
    return new Builder();
  }
  
  /** Builder for {@link ProblemListDTO}. */
  public static class Builder {
    
    private Builder() {
    }
    
    private Integer problemId;
    private String title="";
    private String source="";
    private Integer solved;
    private Integer tried;
    private Boolean isSpj;
    private Boolean isVisible;
    private Integer difficulty;
    //TODO(mzry1992): Add tags;
    private List<String> tags;
    
    public Builder setProblemId(Integer problemID){
      this.problemId = problemID;
      return this;
    }
    
    public Builder setTitle(String title){
      this.title = title;
      return this;
    }
    
    public Builder setSource(String source){
      this.source = source;
      return this;
    }
    
    public Builder setSolved(Integer solved){
      this.solved = solved;
      return this;
    }
    
    public Builder setTried(Integer tried){
      this.tried = tried;
      return this;
    }
    
    public Builder setIsSpj(Boolean isSpj){
      this.isSpj = isSpj;
      return this;
    }
    
    public Builder setIsVisible(Boolean isVisivle){
      this.isVisible = isVisivle;
      return this;
    }
    
    public Builder setDifficulty(Integer difficulty){
      this.difficulty = difficulty;
      return this;
    }
    
    public Builder setTags(List<String> tags){
      this.tags = tags;
      return this;
    }
    
    public ProblemListDTO build(){
      return new ProblemListDTO(problemId, title, source, solved, tried, isSpj, 
          isVisible, difficulty);
    }
  }
}
