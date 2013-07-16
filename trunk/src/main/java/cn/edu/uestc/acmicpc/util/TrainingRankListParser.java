/*
 * cdoj, UESTC ACMICPC Online Judge
 *
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 * mzry1992 <@link muziriyun@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package cn.edu.uestc.acmicpc.util;

import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingUserDAO;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingUserDAOAware;
import cn.edu.uestc.acmicpc.training.entity.TrainingContestRankList;
import cn.edu.uestc.acmicpc.training.entity.TrainingProblemSummaryInfo;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;
import cn.edu.uestc.acmicpc.util.exception.ParserException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingRankListParser implements TrainingUserDAOAware {

    public TrainingContestRankList parse(TrainingContest trainingContest) throws ParserException, BiffException, AppException, FieldNotUniqueException, IOException {
        List<String[]> valueList = parseDatabase(trainingContest);
        return parse(valueList, trainingContest.getIsPersonal(), trainingContest.getType());
    }

    public TrainingContestRankList parse(File file, Boolean isPersonal, Integer type) throws IOException, BiffException, FieldNotUniqueException, ParserException, AppException {
        List<String[]> excelValueList = parseXls(file);
        return parse(excelValueList, isPersonal, type);
    }

    /**
     * Parse xls file to TrainingContestRankList entity
     *
     * @param excelValueList parsed excel value list
     * @param isPersonal     contest type
     * @return TrainingContestRankList
     * @throws IOException
     * @throws BiffException
     * @throws ParserException
     * @throws FieldNotUniqueException
     * @throws AppException
     */
    public TrainingContestRankList parse(List<String[]> excelValueList, Boolean isPersonal, Integer type) throws IOException, BiffException, ParserException, FieldNotUniqueException, AppException {
        if (excelValueList == null || excelValueList.size() == 0)
            throw new ParserException("Error while parse xls document, Please check it!");
        String[] header = excelValueList.get(0);
        Map<String, Integer> headerMap = new HashMap<>();
        Set<Integer> referencedColumns = new HashSet<>();
        for (int i = 0; i < header.length; i++) {
            header[i] = header[i].trim();
            if (header[i].compareToIgnoreCase("rank") == 0) {
                if (headerMap.containsKey("rank"))
                    throw new ParserException("There are multiple columns reference to rank");
                headerMap.put("rank", i);
                referencedColumns.add(i);
            }
            if (header[i].compareToIgnoreCase("name") == 0 ||
                    header[i].compareToIgnoreCase("team") == 0 ||
                    header[i].compareToIgnoreCase("id") == 0 ||
                    header[i].compareToIgnoreCase("nick name") == 0) {
                if (headerMap.containsKey("name"))
                    throw new ParserException("There are multiple columns reference to name");
                headerMap.put("name", i);
                referencedColumns.add(i);
            }
            if (header[i].compareToIgnoreCase("solved") == 0 ||
                    header[i].compareToIgnoreCase("solve") == 0) {
                if (headerMap.containsKey("solved"))
                    throw new ParserException("There are multiple columns reference to solved");
                headerMap.put("solved", i);
                referencedColumns.add(i);
            }
            if (header[i].compareToIgnoreCase("penalty") == 0) {
                if (headerMap.containsKey("penalty"))
                    throw new ParserException("There are multiple columns reference to penalty");
                headerMap.put("penalty", i);
                referencedColumns.add(i);
            }
        }
        if (!headerMap.containsKey("name"))
            throw new ParserException("There are no columns reference to name");

        if (type != Global.TrainingContestType.NORMAL.ordinal()) {
            if (!headerMap.containsKey("penalty"))
                throw new ParserException("There are no columns reference to penalty");

            List<String[]> excelRankList = new LinkedList<>();
            for (int i = 1; i < excelValueList.size(); i++) {
                String[] oldLine = excelValueList.get(i);
                String[] newLine = new String[2];
                newLine[0] = oldLine[headerMap.get("name")];
                newLine[1] = oldLine[headerMap.get("penalty")];

                excelRankList.add(newLine);
            }
            return new TrainingContestRankList(excelRankList, isPersonal, trainingUserDAO, type);
        } else {
            Integer problemCount = header.length - referencedColumns.size();

            List<String[]> excelRankList = new LinkedList<>();
            for (int i = 1; i < excelValueList.size(); i++) {
                String[] oldLine = excelValueList.get(i);
                String[] newLine = new String[problemCount + 1];
                newLine[0] = oldLine[headerMap.get("name")];
                Integer problemIterator = 0;
                for (int j = 0; j < oldLine.length; j++)
                    if (!referencedColumns.contains(j))
                        newLine[++problemIterator] = oldLine[j];
                if (!problemIterator.equals(problemCount))
                    throw new ParserException("Rank list format error, Row " + i + " contains " + problemIterator + " problems but there are " + problemCount + " problem in total");
                excelRankList.add(newLine);
            }
            return new TrainingContestRankList(excelRankList, isPersonal, trainingUserDAO, type);
        }
    }

    /**
     * Parse xls file
     *
     * @param file xls file entity
     * @return A list of String[]
     * @throws BiffException
     * @throws IOException
     */
    public List<String[]> parseXls(File file) throws BiffException, IOException {
        List<String[]> excelValueList = new LinkedList<>();
        if (file.exists() && file.canRead()
                && (file.getName().lastIndexOf(".xls") >= 1)) {
            Workbook workbook = null;
            try {
                workbook = Workbook.getWorkbook(file);
                Sheet sheet = workbook.getSheet(0);
                int row = sheet.getRows();
                int col = sheet.getColumns();
                for (int r = 0; r < row; r++) {
                    String[] rowValue = new String[col];
                    for (int c = 0; c < col; c++) {
                        rowValue[c] = sheet.getCell(c, r).getContents() != null ? sheet
                                .getCell(c, r).getContents() : "";
                    }
                    excelValueList.add(rowValue);
                }
            } finally {
                if (workbook != null) {
                    workbook.close();
                }
            }
        }
        return excelValueList;
    }

    public List<String[]> parseDatabase(TrainingContest trainingContest) throws ParserException {
        List<String[]> valueList = new LinkedList<>();
        Integer summaryLength = -1;
        for (TrainingStatus trainingStatus : trainingContest.getTrainingStatusesByTrainingContestId()) {

            if (trainingContest.getType() != Global.TrainingContestType.NORMAL.ordinal()) {
                String[] result = new String[2];
                result[0] = trainingStatus.getTrainingUserByTrainingUserId().getName();
                result[1] = trainingStatus.getPenalty().toString();
                valueList.add(result);
            } else {
                String[] summary = parseTrainingUserSummary(trainingStatus.getSummary());
                String[] result = new String[summary.length + 1];
                result[0] = trainingStatus.getTrainingUserByTrainingUserId().getName();
                System.arraycopy(summary, 0, result, 1, summary.length);
                valueList.add(result);

                if (summaryLength == -1)
                    summaryLength = result.length;
                else if (summaryLength != result.length)
                    throw new ParserException("Summary in database length different error");
            }
        }
        if (trainingContest.getType() != Global.TrainingContestType.NORMAL.ordinal()) {
            String[] header = new String[2];
            header[0] = "name";
            header[1] = "penalty";
            valueList.add(0, header);
        } else {
            String[] header = new String[summaryLength];
            header[0] = "name";
            for (int i = 1; i < header.length; i++)
                header[i] = String.valueOf(i);
            valueList.add(0, header);
        }
        return valueList;
    }

    public String[] parseTrainingUserSummary(String summary) {
        Integer columnCount = 1;
        for (int i = 0; i < summary.length(); i++)
            if (summary.charAt(i) == '|')
                columnCount++;
        String[] splitList = summary.split("\\|");
        String[] result = new String[columnCount];
        System.arraycopy(splitList, 0, result, 0, splitList.length);
        for (int i = splitList.length; i < columnCount; i++)
            result[i] = "";
        return result;
    }

    public String encodeTariningUserSummary(TrainingProblemSummaryInfo[] trainingProblemSummaryInfos, Integer type) {
        StringBuilder stringBuilder = new StringBuilder();
        Boolean first = true;
        for (TrainingProblemSummaryInfo trainingProblemSummaryInfo : trainingProblemSummaryInfos) {
            if (!first)
                stringBuilder.append("|");
            first = false;
            if (!trainingProblemSummaryInfo.getSolved()) {
                if (trainingProblemSummaryInfo.getTried() > 0)
                    stringBuilder.append(trainingProblemSummaryInfo.getTried()).append("/--");
            } else
                stringBuilder.append(trainingProblemSummaryInfo.getTried()).append("/").append(trainingProblemSummaryInfo.getSolutionTime());
        }
        return stringBuilder.toString();
    }

    @Autowired
    private ITrainingUserDAO trainingUserDAO;

    @Override
    public void setTrainingUserDAO(ITrainingUserDAO trainingUserDAO) {
        this.trainingUserDAO = trainingUserDAO;
    }
}
