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

package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Problem entity data transform object.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 2
 */
@SuppressWarnings("UnusedDeclaration")
public class ProblemDTO extends BaseDTO<Problem> {
    /**
     * Input: problem id
     */
    private Integer problemId;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getSampleInput() {
        return sampleInput;
    }

    public void setSampleInput(String sampleInput) {
        this.sampleInput = sampleInput;
    }

    public String getSampleOutput() {
        return sampleOutput;
    }

    public void setSampleOutput(String sampleOutput) {
        this.sampleOutput = sampleOutput;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(int memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public int getSolved() {
        return solved;
    }

    public void setSolved(int solved) {
        this.solved = solved;
    }

    public int getTried() {
        return tried;
    }

    public void setTried(int tried) {
        this.tried = tried;
    }

    public boolean getIsSpj() {
        return isSpj;
    }

    public void setIsSpj(boolean spj) {
        isSpj = spj;
    }

    public boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean visible) {
        isVisible = visible;
    }

    public int getOutputLimit() {
        return outputLimit;
    }

    public void setOutputLimit(int outputLimit) {
        this.outputLimit = outputLimit;
    }

    public int getJavaTimeLimit() {
        return javaTimeLimit;
    }

    public void setJavaTimeLimit(int javaTimeLimit) {
        this.javaTimeLimit = javaTimeLimit;
    }

    public int getJavaMemoryLimit() {
        return javaMemoryLimit;
    }

    public void setJavaMemoryLimit(int javaMemoryLimit) {
        this.javaMemoryLimit = javaMemoryLimit;
    }

    public int getDataCount() {
        return dataCount;
    }

    public void setDataCount(int dataCount) {
        this.dataCount = dataCount;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    private String description;
    private String input;
    private String output;
    private String sampleInput;
    private String sampleOutput;
    private String hint;
    private String source;
    private int timeLimit;
    private int memoryLimit;
    private int solved;
    private int tried;
    private boolean isSpj;
    private boolean isVisible;
    private int outputLimit;
    private int javaTimeLimit;
    private int javaMemoryLimit;
    private int dataCount;
    private int difficulty;

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    @Override
    public Problem getEntity() throws AppException {
        // TODO to get specific operations
        return super.getEntity();
    }

    @Override
    public void updateEntity(Problem problem) {
        // TODO to get specific operations
        super.updateEntity(problem);
    }

    @Override
    protected Class<Problem> getReferenceClass() {
        return Problem.class;
    }
}
