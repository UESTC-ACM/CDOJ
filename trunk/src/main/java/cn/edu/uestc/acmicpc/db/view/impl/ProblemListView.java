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

package cn.edu.uestc.acmicpc.db.view.impl;

import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.ProblemTag;
import cn.edu.uestc.acmicpc.db.view.base.View;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */
public class ProblemListView extends View<Problem> {

    private int problemId;
    private String title;
    private String source;
    private int solved;
    private int tried;
    private boolean isSpj;
    private boolean isVisible;
    private int difficulty;
    private List<String> tags;

    /**
     * Get ProblemListView entity by problem entity.
     *
     * @param problem specific problem entity
     * @throws cn.edu.uestc.acmicpc.util.exception.AppException
     */
    public ProblemListView(Problem problem) throws AppException {
        super(problem);
        List<String> list = new LinkedList<String>();
        Collection<ProblemTag> problemTags = problem.getProblemtagsByProblemId();
        for (ProblemTag problemTag : problemTags) {
            list.add(problemTag.getTagByTagId().getName());
        }
        setTags(list);
    }

    public int getProblemId() {
        return problemId;
    }

    public void setProblemId(int problemId) {
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

    public boolean isSpj() {
        return isSpj;
    }

    public void setSpj(boolean spj) {
        isSpj = spj;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getTags() {
        return tags;
    }

    @Ignored
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
