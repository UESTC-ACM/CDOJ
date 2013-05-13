package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.IContestProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.db.entity.ContestUser;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.ioc.dao.ContestProblemDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.util.ArrayUtil;
import cn.edu.uestc.acmicpc.util.annotation.Ignore;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Contest entity data transform object.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
public class ContestDTO extends BaseDTO<Contest>
        implements ContestProblemDAOAware, ProblemDAOAware {

    @Autowired
    private IContestProblemDAO contestProblemDAO;

    @Autowired
    private IProblemDAO problemDAO;

    private Integer contestId;
    private String title;
    private String description;
    private Byte type;
    private Timestamp time;
    private Integer length;
    private Boolean isVisible;
    private List<Integer> problemList;

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    @Ignore
    public List<Integer> getProblemList() {
        return problemList;
    }

    public void setProblemList(List<Integer> problemList) {
        this.problemList = problemList;
    }

    public Integer getContestId() {
        return contestId;
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

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

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = new Timestamp(time);
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean visible) {
        isVisible = visible;
    }

    @Override
    public Contest getEntity() throws AppException {
        Contest contest = super.getEntity();

        contest.setIsVisible(false);

        Collection<ContestProblem> contestProblems = new LinkedList<>();
        for (int index = 0; index < problemList.size(); index++) {
            Integer id = problemList.get(index);

            ContestProblem contestProblem = new ContestProblem();
            contestProblem.setOrder(index);
            contestProblem.setContestByContestId(contest);
            contestProblem.setProblemByProblemId(problemDAO.get(id));
            contestProblemDAO.addOrUpdate(contestProblem);

            contestProblems.add(contestProblem);
        }
        contest.setContestProblemsByContestId(contestProblems);

        Collection<ContestUser> contestUsers = new LinkedList<>();
        contest.setContestUsersByContestId(contestUsers);

        Collection<Status> contestStatus = new LinkedList<>();
        contest.setStatusesByContestId(contestStatus);

        return contest;
    }

    @Override
    public void updateEntity(Contest contest) {
        super.updateEntity(contest);
    }

    @Override
    protected Class<Contest> getReferenceClass() {
        return Contest.class;
    }

    @Override
    public void setContestProblemDAO(IContestProblemDAO contestProblemDAO) {
        this.contestProblemDAO = contestProblemDAO;
    }

    @Override
    public void setProblemDAO(IProblemDAO problemDAO) {
        this.problemDAO = problemDAO;
    }
}
