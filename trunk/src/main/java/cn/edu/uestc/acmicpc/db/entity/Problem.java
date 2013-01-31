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

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Problem information.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 5
 */
@Table(name = "problem", schema = "", catalog = "uestcoj")
@Entity
public class Problem implements Serializable {
    private static final long serialVersionUID = -334230877056963653L;
    private int problemId;

    @Column(name = "problemId", nullable = false, insertable = true,
            updatable = true, length = 10, precision = 0, unique = true)
    @Id
    @GeneratedValue
    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
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

    private int timeLimit;

    @Column(name = "timeLimit", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    private int memoryLimit;

    @Column(name = "memoryLimit", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public int getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(int memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    private int solved;

    @Column(name = "solved", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public int getSolved() {
        return solved;
    }

    public void setSolved(int solved) {
        this.solved = solved;
    }

    private int tried;

    @Column(name = "tried", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public int getTried() {
        return tried;
    }

    public void setTried(int tried) {
        this.tried = tried;
    }

    private boolean isSpj;

    @Column(name = "isSPJ", nullable = false, insertable = true, updatable = true,
            length = 0, precision = 0)
    @Basic
    public boolean getIsSpj() {
        return isSpj;
    }

    public void setIsSpj(boolean spj) {
        isSpj = spj;
    }

    private boolean isVisible;

    @Column(name = "isVisible", nullable = false, insertable = true, updatable = true,
            length = 0, precision = 0)
    @Basic
    public boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean visible) {
        isVisible = visible;
    }

    private int outputLimit;

    @Column(name = "outputLimit", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public int getOutputLimit() {
        return outputLimit;
    }

    public void setOutputLimit(int outputLimit) {
        this.outputLimit = outputLimit;
    }

    private int javaTimeLimit;

    @Column(name = "javaTimeLimit", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public int getJavaTimeLimit() {
        return javaTimeLimit;
    }

    public void setJavaTimeLimit(int javaTimeLimit) {
        this.javaTimeLimit = javaTimeLimit;
    }

    private int javaMemoryLimit;

    @Column(name = "javaMemoryLimit", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public int getJavaMemoryLimit() {
        return javaMemoryLimit;
    }

    public void setJavaMemoryLimit(int javaMemoryLimit) {
        this.javaMemoryLimit = javaMemoryLimit;
    }

    private int dataCount;

    @Column(name = "dataCount", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    private int difficulty;

    @Column(name = "difficulty", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Problem problem = (Problem) o;

        if (dataCount != problem.dataCount) return false;
        if (difficulty != problem.difficulty) return false;
        if (isSpj != problem.isSpj) return false;
        if (isVisible != problem.isVisible) return false;
        if (javaMemoryLimit != problem.javaMemoryLimit) return false;
        if (javaTimeLimit != problem.javaTimeLimit) return false;
        if (memoryLimit != problem.memoryLimit) return false;
        if (outputLimit != problem.outputLimit) return false;
        if (problemId != problem.problemId) return false;
        if (solved != problem.solved) return false;
        if (timeLimit != problem.timeLimit) return false;
        if (tried != problem.tried) return false;
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

    private Collection<Discuss> discussesByProblemId;

    @OneToMany(mappedBy = "problemByProblemId")
    public Collection<Discuss> getDiscussesByProblemId() {
        return discussesByProblemId;
    }

    public void setDiscussesByProblemId(Collection<Discuss> discussesByProblemId) {
        this.discussesByProblemId = discussesByProblemId;
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
}
