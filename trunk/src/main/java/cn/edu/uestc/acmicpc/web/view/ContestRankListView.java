package cn.edu.uestc.acmicpc.web.view;

import cn.edu.uestc.acmicpc.db.dto.impl.ContestDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contestteam.ContestTeamReportDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserReportDto;
import cn.edu.uestc.acmicpc.util.enums.ContestType;
import cn.edu.uestc.acmicpc.web.rank.RankList;
import cn.edu.uestc.acmicpc.web.rank.RankListItem;
import cn.edu.uestc.acmicpc.web.rank.RankListProblem;
import cn.edu.uestc.acmicpc.web.rank.RankListUser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.springframework.web.servlet.view.document.AbstractJExcelView;

/**
 * Contest rank list excel view
 */
@SuppressWarnings("deprecation")
public class ContestRankListView extends AbstractJExcelView {

  /**
   * Current used row
   */
  private Integer currentRow;

  /**
   * Map the team list by team name
   */
  private Map<String, ContestTeamReportDto> teamMap;

  @SuppressWarnings("unchecked")
  @Override
  protected void buildExcelDocument(Map<String, Object> model,
      WritableWorkbook workbook,
      HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    // Set file name
    response.setHeader("Content-Disposition", "attachment; filename=\"Rank list.xls\"");

    // Create sheet
    WritableSheet sheet = workbook.createSheet("Rank list", 0);

    // Confirm the database query is successful.
    String result = (String) model.get("result");
    if (!result.equals("success")) {
      String error_msg = (String) model.get("error_msg");
      // Show error message on first sheet.
      sheet.addCell(new Label(0, 0, error_msg));
      return;
    }

    // Get data in model
    RankList rankList = (RankList) model.get("rankList");
    ContestDto contest = (ContestDto) model.get("contest");
    Byte contestType = (Byte) model.get("type");
    // Last used column
    Integer lastColumn;
    // Initialize current used row.
    currentRow = 1;

    if (contestType == ContestType.INVITED.ordinal()) {
      // Invited type contest.
      lastColumn = setInvitedContestHeaderRow(sheet, rankList.problemList);
      currentRow += 2;
      // Get team list in model
      List<ContestTeamReportDto> teamList = (List<ContestTeamReportDto>) model.get("teamList");
      teamMap = new HashMap<>();
      for (ContestTeamReportDto team : teamList) {
        teamMap.put(team.getTeamName(), team);
      }
      // Generate rank list by team.
      for (RankListUser team : rankList.rankList) {
        putTeam(sheet, team);
      }
    } else {
      // Normal contest.
      lastColumn = setNormalContestHeaderRow(sheet, rankList.problemList);
      currentRow += 2;
      // Generate rank list by user.
      for (RankListUser user : rankList.rankList) {
        putUser(sheet, user);
      }
    }

    // Add contest title in row 0
    sheet.addCell(new Label(0, 0, contest.getTitle()));
    sheet.mergeCells(0, 0, lastColumn, 0);
  }

  private void putUser(WritableSheet sheet,
      RankListUser user) throws WriteException {
    // create header row
    // #
    sheet.addCell(new Label(0, currentRow, user.rank.toString()));
    // User name
    sheet.addCell(new Label(1, currentRow, user.name));
    // Nick name
    sheet.addCell(new Label(2, currentRow, user.nickName));

    // Solved
    sheet.addCell(new Label(3, currentRow, user.solved.toString()));

    // Penalty
    sheet.addCell(new Label(4, currentRow, formatPenaltyTime(user.penalty)));

    putRankListItem(sheet, user.itemList, 5, currentRow, currentRow);
    currentRow++;
  }

  /**
   * Write rank list row by team format.
   *
   * @param team
   *          team entity.
   */
  private void putTeam(WritableSheet sheet, RankListUser team) throws WriteException {
    Integer startRow = currentRow;
    // create header row
    // #
    sheet.addCell(new Label(0, startRow, team.rank.toString()));
    // Team name
    sheet.addCell(new Label(1, startRow, team.name));

    // Team member
    ContestTeamReportDto teamDetail = teamMap.get(team.name);
    for (TeamUserReportDto user : teamDetail.getTeamUsers()) {
      putTeamUser(sheet, user, teamDetail);
    }
    for (TeamUserReportDto user : teamDetail.getInvitedUsers()) {
      putTeamUser(sheet, user, teamDetail);
    }

    // Solved
    sheet.addCell(new Label(14, startRow, team.solved.toString()));

    // Penalty
    sheet.addCell(new Label(15, startRow, formatPenaltyTime(team.penalty)));

    sheet.mergeCells(0, startRow, 0, currentRow - 1);
    sheet.mergeCells(1, startRow, 1, currentRow - 1);
    sheet.mergeCells(14, startRow, 14, currentRow - 1);
    sheet.mergeCells(15, startRow, 15, currentRow - 1);

    putRankListItem(sheet, team.itemList, 16, startRow, currentRow - 1);
  }

  private void putRankListItem(WritableSheet sheet,
      RankListItem[] items,
      Integer startColumn,
      Integer startRow,
      Integer endRow) throws WriteException {
    Integer currentColumn = startColumn;
    for (RankListItem item : items) {
      String content;
      if (item.solved) {
        content = formatPenaltyTime(item.solvedTime / 1000) + "\n";
      } else {
        content = "\n";
      }
      if (item.tried > 0) {
        content = content + "(-" + item.tried + ")";
      }

      WritableCellFormat cellFormat = new WritableCellFormat();
      cellFormat.setWrap(true);
      cellFormat.setAlignment(Alignment.CENTRE);
      sheet.addCell(new Label(currentColumn, startRow, content, cellFormat));
      if (startRow < endRow) {
        sheet.mergeCells(currentColumn, startRow, currentColumn, endRow);
      }
      currentColumn++;
    }
  }

  /**
   * Format penalty time.
   *
   * @param time
   *          penalty time.
   * @return formatted penalty time.
   */
  private String formatPenaltyTime(Long time) {
    Long timeSecond = time % 60;
    time = time / 60;
    Long timeMinute = time % 60;
    Long timeHours = time / 60;

    return String.format("%d:%02d:%02d", timeHours, timeMinute, timeSecond);
  }

  /**
   * Put team user.
   *
   * @param sheet
   *          excel sheet.
   * @param user
   *          {@link TeamUserReportDto} entity.
   * @param team
   *          {@link ContestTeamReportDto} entity.
   * @throws WriteException
   */
  private void putTeamUser(WritableSheet sheet,
      TeamUserReportDto user,
      ContestTeamReportDto team)
      throws WriteException {
    WritableCellFormat cellFormat = new WritableCellFormat();
    if (user.getAllow()) {
      if (team.getLeaderId().equals(user.getUserId())) {
        sheet.addCell(new Label(2, currentRow, "Team leader"));
      } else {
        sheet.addCell(new Label(2, currentRow, "Team member"));
      }
    } else {
      // Highlight inactive users.
      cellFormat.setBackground(Colour.RED);
      sheet.addCell(new Label(2, currentRow, "Inactive", cellFormat));
    }
    sheet.addCell(new Label(3, currentRow, user.getUserName(), cellFormat));
    sheet.addCell(new Label(4, currentRow, user.getName(), cellFormat));
    sheet.addCell(new Label(5, currentRow, user.getSex(), cellFormat));
    sheet.addCell(new Label(6, currentRow, user.getSize(), cellFormat));
    sheet.addCell(new Label(7, currentRow, user.getEmail(), cellFormat));
    sheet.addCell(new Label(8, currentRow, user.getPhone(), cellFormat));
    sheet.addCell(new Label(9, currentRow, user.getSchool(), cellFormat));
    sheet.addCell(new Label(10, currentRow, user.getDepartment(), cellFormat));
    sheet.addCell(new Label(11, currentRow, user.getGrade(), cellFormat));
    sheet.addCell(new Label(12, currentRow, user.getStudentId(), cellFormat));
    sheet.addCell(new Label(13, currentRow, user.getType(), cellFormat));
    currentRow++;
  }

  private Integer setNormalContestHeaderRow(WritableSheet sheet,
      RankListProblem[] problemList) throws WriteException {
    // create header row
    // #
    sheet.addCell(new Label(0, currentRow, "#"));
    sheet.mergeCells(0, currentRow, 0, currentRow + 1);
    // User name
    sheet.addCell(new Label(1, currentRow, "Name"));
    sheet.mergeCells(1, currentRow, 1, currentRow + 1);
    // Nick name
    sheet.addCell(new Label(2, currentRow, "Nick name"));
    sheet.mergeCells(2, currentRow, 2, currentRow + 1);

    // Solved
    sheet.addCell(new Label(3, currentRow, "Solved"));
    sheet.mergeCells(3, currentRow, 3, currentRow + 1);

    // Penalty
    sheet.addCell(new Label(4, currentRow, "Penalty"));
    sheet.mergeCells(4, currentRow, 4, currentRow + 1);

    // Problem
    return setProblemHeaderRow(sheet, problemList, 5);
  }

  /**
   * Set header row and return the last used column ID.
   *
   * @param sheet
   *          excel sheet.
   * @param problemList
   *          problem list.
   * @return last used column ID.
   * @throws WriteException
   */
  private Integer setInvitedContestHeaderRow(WritableSheet sheet,
      RankListProblem[] problemList) throws WriteException {
    // create header row
    // #
    sheet.addCell(new Label(0, currentRow, "#"));
    sheet.mergeCells(0, currentRow, 0, currentRow + 1);
    // Team name
    sheet.addCell(new Label(1, currentRow, "Name"));
    sheet.mergeCells(1, currentRow, 1, currentRow + 1);
    // Team member
    sheet.addCell(new Label(2, currentRow, "Team"));
    sheet.mergeCells(2, currentRow, 13, currentRow);
    sheet.addCell(new Label(2, currentRow + 1, "Role"));
    sheet.addCell(new Label(3, currentRow + 1, "User name"));
    sheet.addCell(new Label(4, currentRow + 1, "Name"));
    sheet.addCell(new Label(5, currentRow + 1, "Gender"));
    sheet.addCell(new Label(6, currentRow + 1, "T-shirts size"));
    sheet.addCell(new Label(7, currentRow + 1, "Email"));
    sheet.addCell(new Label(8, currentRow + 1, "Phone"));
    sheet.addCell(new Label(9, currentRow + 1, "School"));
    sheet.addCell(new Label(10, currentRow + 1, "Department"));
    sheet.addCell(new Label(11, currentRow + 1, "Grade"));
    sheet.addCell(new Label(12, currentRow + 1, "Student ID"));
    sheet.addCell(new Label(13, currentRow + 1, "type"));

    // Solved
    sheet.addCell(new Label(14, currentRow, "Solved"));
    sheet.mergeCells(14, currentRow, 14, currentRow + 1);

    // Penalty
    sheet.addCell(new Label(15, currentRow, "Penalty"));
    sheet.mergeCells(15, currentRow, 15, currentRow + 1);

    // Problem
    return setProblemHeaderRow(sheet, problemList, 16);
  }

  /**
   * Add problem column head.
   *
   * @param sheet
   *          excel sheet.
   * @param problemList
   *          problem list.
   * @param startColumn
   *          current last used column ID.
   * @return last used column ID.
   * @throws WriteException
   */
  private Integer setProblemHeaderRow(WritableSheet sheet,
      RankListProblem[] problemList,
      Integer startColumn) throws WriteException {
    Integer currentColumn = startColumn;
    for (int order = 0; order < problemList.length; order++) {
      sheet.addCell(new Label(currentColumn, currentRow, "" + (char) ('A' + order)));
      sheet.addCell(new Label(currentColumn, currentRow + 1, problemList[order].solved + "/"
          + problemList[order].tried));

      currentColumn++;
    }

    return currentColumn - 1;
  }
}
