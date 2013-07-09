package cn.edu.uestc.acmicpc.training.entity;

import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingUserDAO;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;
import cn.edu.uestc.acmicpc.util.exception.ParserException;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingContestRankList {
    private List<TrainingContestProblemSummaryView> problemSummary;
    private List<TrainingUserRankSummary> trainingUserRankSummaryList;
    private Integer problemCount;

    public TrainingContestRankList(List<String[]> rankList, Boolean isPersonal, ITrainingUserDAO trainingUserDAO) throws FieldNotUniqueException, AppException, ParserException {
        problemSummary = new LinkedList<>();
        trainingUserRankSummaryList = new LinkedList<>();

        problemCount = rankList.get(0).length - 1;
        for (int i = 0; i < problemCount; i++) {
            TrainingContestProblemSummaryView trainingContestProblemSummaryView = new TrainingContestProblemSummaryView(i);
            problemSummary.add(trainingContestProblemSummaryView);
        }

        for (String[] userInfo : rankList) {
            String name = userInfo[0];
            TrainingUser trainingUser = trainingUserDAO.getEntityByUniqueField("name", name);
            //If there are no such user or it's not allowed, just continue
            if (trainingUser == null || !trainingUser.getAllow())
                continue;
            //If user type is wrong
            if (trainingUser.getType() == Global.TrainingUserType.PERSONAL.ordinal() && !isPersonal)
                continue;
            if (trainingUser.getType() == Global.TrainingUserType.TEAM.ordinal() && isPersonal)
                continue;

            TrainingUserRankSummary trainingUserRankSummary = new TrainingUserRankSummary(trainingUser, userInfo);
            trainingUserRankSummaryList.add(trainingUserRankSummary);
        }

        calcProblemSummary();
        sortRankList();
    }

    public void calcProblemSummary() {
        for (int i = 0; i < problemCount; i++) {
            //Get basic information
            Integer totTried = 0;
            Integer totSolved = 0;
            Integer firstSolvedTime = Integer.MAX_VALUE;
            for (TrainingUserRankSummary anTrainingUserRankSummaryList1 : trainingUserRankSummaryList) {
                TrainingProblemSummaryInfo trainingProblemSummaryInfo = anTrainingUserRankSummaryList1.getTrainingProblemSummaryInfoList()[i];
                totTried += trainingProblemSummaryInfo.getTried();
                if (trainingProblemSummaryInfo.getSolved()) {
                    totSolved++;
                    firstSolvedTime = Math.min(firstSolvedTime, trainingProblemSummaryInfo.getSolutionTime());
                }
            }
            problemSummary.get(i).setTried(totTried);
            problemSummary.get(i).setSolved(totSolved);
            //Set first solved
            for (TrainingUserRankSummary anTrainingUserRankSummaryList : trainingUserRankSummaryList)
                if (anTrainingUserRankSummaryList.getTrainingProblemSummaryInfoList()[i].getSolved() &&
                        anTrainingUserRankSummaryList.getTrainingProblemSummaryInfoList()[i].getSolutionTime().equals(firstSolvedTime))
                    anTrainingUserRankSummaryList.getTrainingProblemSummaryInfoList()[i].setFirstSolved(true);
        }
    }
    public void sortRankList() {
        //Sort
        Collections.sort(trainingUserRankSummaryList, new Comparator<TrainingUserRankSummary>() {
            @Override
            public int compare(TrainingUserRankSummary a, TrainingUserRankSummary b) {
                if (a.getSolved().equals(b.getSolved())) {
                    if (a.getPenalty().equals(b.getPenalty())) {
                        return a.getNickName().compareTo(b.getNickName());
                    }
                    return a.getPenalty().compareTo(b.getPenalty());
                }
                return (b.getSolved().compareTo(a.getSolved()));
            }
        });

        //Rank
        for (int i = 0; i < trainingUserRankSummaryList.size(); i++)
            trainingUserRankSummaryList.get(i).setRank(i + 1);
    }
    public List<TrainingContestProblemSummaryView> getProblemSummary() {
        return problemSummary;
    }

    public void setProblemSummary(List<TrainingContestProblemSummaryView> problemSummary) {
        this.problemSummary = problemSummary;
    }

    public List<TrainingUserRankSummary> getTrainingUserRankSummaryList() {
        return trainingUserRankSummaryList;
    }

    public void setTrainingUserRankSummaryList(List<TrainingUserRankSummary> trainingUserRankSummaryList) {
        this.trainingUserRankSummaryList = trainingUserRankSummaryList;
    }

}
