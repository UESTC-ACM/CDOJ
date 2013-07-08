package cn.edu.uestc.acmicpc.training.rank;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class RanklistFormatParser {
    public static ProblemSummaryInfo getProblemSummaryInfo(String grid) {
        ProblemSummaryInfo problemSummaryInfo = new ProblemSummaryInfo();
        //""
        if (grid == "")
            return problemSummaryInfo;

        //VJ style
        //(-x)


        //hh:mm:ss

        //hh:mm:ss(-x)

        return problemSummaryInfo;
    }
}
