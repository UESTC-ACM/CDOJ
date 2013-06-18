/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Problem information.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
@Table(name = "problem", schema = "", catalog = "uestcoj")
@Entity
@KeyField("problemId")
public class Problem implements Serializable {
    private static final long serialVersionUID = -334230877056963653L;
    private Integer problemId;

    private Integer version;

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
        return "Problem{" +
                "problemId=" + problemId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                ", sampleInput='" + sampleInput + '\'' +
                ", sampleOutput='" + sampleOutput + '\'' +
                ", hint='" + hint + '\'' +
                ", source='" + source + '\'' +
                ", timeLimit=" + timeLimit +
                ", memoryLimit=" + memoryLimit +
                ", solved=" + solved +
                ", tried=" + tried +
                ", isSpj=" + isSpj +
                ", isVisible=" + isVisible +
                ", outputLimit=" + outputLimit +
                ", javaTimeLimit=" + javaTimeLimit +
                ", javaMemoryLimit=" + javaMemoryLimit +
                ", dataCount=" + dataCount +
                ", difficulty=" + difficulty +
                '}';
    }

    @Column(name = "problemId", nullable = false, insertable = true,
            updatable = true, length = 10, precision = 0, unique = true)
    @Id
    @GeneratedValue
    public Integer getProblemId() {
        return problemId;
    }

    @SuppressWarnings("SameParameterValue")
    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    private String title;

    @Column(name = "title", nullable = false, insertable = true, updatable = true,
            length = 50, precision = 0)
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

    @Column(name = "input", nullable = false, insertable = true, updatable = true,
            length = 65535, precision = 0)
    @Basic
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    private String output;

    @Column(name = "output", nullable = false, insertable = true, updatable = true,
            length = 65535, precision = 0)
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

    @Column(name = "hint", nullable = false, insertable = true, updatable = true,
            length = 65535, precision = 0)
    @Basic
    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    private String source;

    @Column(name = "source", nullable = false, insertable = true, updatable = true,
            length = 100, precision = 0)
    @Basic
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    private Integer timeLimit;

    @Column(name = "timeLimit", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    private Integer memoryLimit;

    @Column(name = "memoryLimit", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public Integer getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(Integer memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    private Integer solved;

    @Column(name = "solved", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public Integer getSolved() {
        return solved;
    }

    public void setSolved(Integer solved) {
        this.solved = solved;
    }

    private Integer tried;

    @Column(name = "tried", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public Integer getTried() {
        return tried;
    }

    @SuppressWarnings("SameParameterValue")
    public void setTried(Integer tried) {
        this.tried = tried;
    }

    private Boolean isSpj;

    @Column(name = "isSPJ", nullable = false, insertable = true, updatable = true,
            length = 0, precision = 0)
    @Basic
    public Boolean getIsSpj() {
        return isSpj;
    }

    public void setIsSpj(Boolean spj) {
        isSpj = spj;
    }

    private Boolean isVisible;

    @Column(name = "isVisible", nullable = false, insertable = true, updatable = true,
            length = 0, precision = 0)
    @Basic
    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean visible) {
        isVisible = visible;
    }

    private Integer outputLimit;

    @Column(name = "outputLimit", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public Integer getOutputLimit() {
        return outputLimit;
    }

    public void setOutputLimit(Integer outputLimit) {
        this.outputLimit = outputLimit;
    }

    private Integer javaTimeLimit;

    @Column(name = "javaTimeLimit", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public Integer getJavaTimeLimit() {
        return javaTimeLimit;
    }

    public void setJavaTimeLimit(Integer javaTimeLimit) {
        this.javaTimeLimit = javaTimeLimit;
    }

    private Integer javaMemoryLimit;

    @Column(name = "javaMemoryLimit", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public Integer getJavaMemoryLimit() {
        return javaMemoryLimit;
    }

    public void setJavaMemoryLimit(Integer javaMemoryLimit) {
        this.javaMemoryLimit = javaMemoryLimit;
    }

    private Integer dataCount;

    @Column(name = "dataCount", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public Integer getDataCount() {
        return dataCount;
    }

    public void setDataCount(Integer dataCount) {
        this.dataCount = dataCount;
    }

    private Integer difficulty;

    @Column(name = "difficulty", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Problem problem = (Problem) o;

        if (!dataCount.equals(problem.dataCount)) return false;
        if (!difficulty.equals(problem.difficulty)) return false;
        if (isSpj != problem.isSpj) return false;
        if (isVisible != problem.isVisible) return false;
        if (!javaMemoryLimit.equals(problem.javaMemoryLimit)) return false;
        if (!javaTimeLimit.equals(problem.javaTimeLimit)) return false;
        if (!memoryLimit.equals(problem.memoryLimit)) return false;
        if (!outputLimit.equals(problem.outputLimit)) return false;
        if (!problemId.equals(problem.problemId)) return false;
        if (!solved.equals(problem.solved)) return false;
        if (!timeLimit.equals(problem.timeLimit)) return false;
        if (!tried.equals(problem.tried)) return false;
        if (description != null ? !description.equals(problem.description) : problem.description != null) return false;
        if (hint != null ? !hint.equals(problem.hint) : problem.hint != null) return false;
        if (input != null ? !input.equals(problem.input) : problem.input != null) return false;
        if (output != null ? !output.equals(problem.output) : problem.output != null) return false;
        if (sampleInput != null ? !sampleInput.equals(problem.sampleInput) : problem.sampleInput != null) return false;
        if (sampleOutput != null ? !sampleOutput.equals(problem.sampleOutput) : problem.sampleOutput != null)
            return false;
        if (source != null ? !source.equals(problem.source) : problem.source != null) return false;
        if (title != null ? !title.equals(problem.title) : problem.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = problemId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (input != null ? input.hashCode() : 0);
        result = 31 * result + (output != null ? output.hashCode() : 0);
        result = 31 * result + (sampleInput != null ? sampleInput.hashCode() : 0);
        result = 31 * result + (sampleOutput != null ? sampleOutput.hashCode() : 0);
        result = 31 * result + (hint != null ? hint.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + timeLimit;
        result = 31 * result + memoryLimit;
        result = 31 * result + solved;
        result = 31 * result + tried;
        result = 31 * result + (isSpj ? 1 : 0);
        result = 31 * result + (isVisible ? 1 : 0);
        result = 31 * result + outputLimit;
        result = 31 * result + javaTimeLimit;
        result = 31 * result + javaMemoryLimit;
        result = 31 * result + dataCount;
        result = 31 * result + difficulty;
        return result;
    }

    private Collection<ContestProblem> contestproblemsByProblemId;

    @OneToMany(mappedBy = "problemByProblemId")
    public Collection<ContestProblem> getContestproblemsByProblemId() {
        return contestproblemsByProblemId;
    }

    public void setContestproblemsByProblemId(Collection<ContestProblem> contestproblemsByProblemId) {
        this.contestproblemsByProblemId = contestproblemsByProblemId;
    }

    private Collection<ProblemTag> problemtagsByProblemId;

    @OneToMany(mappedBy = "problemByProblemId")
    public Collection<ProblemTag> getProblemtagsByProblemId() {
        return problemtagsByProblemId;
    }

    public void setProblemtagsByProblemId(Collection<ProblemTag> problemtagsByProblemId) {
        this.problemtagsByProblemId = problemtagsByProblemId;
    }

    private Collection<Status> statusesByProblemId;

    @OneToMany(mappedBy = "problemByProblemId")
    public Collection<Status> getStatusesByProblemId() {
        return statusesByProblemId;
    }

    public void setStatusesByProblemId(Collection<Status> statusesByProblemId) {
        this.statusesByProblemId = statusesByProblemId;
    }

    private Collection<Article> articlesByProblemId;

    @OneToMany(mappedBy = "problemByProblemId")
    public Collection<Article> getArticlesByProblemId() {
        return articlesByProblemId;
    }

    public void setArticlesByProblemId(Collection<Article> articlesByProblemId) {
        this.articlesByProblemId = articlesByProblemId;
    }
}
