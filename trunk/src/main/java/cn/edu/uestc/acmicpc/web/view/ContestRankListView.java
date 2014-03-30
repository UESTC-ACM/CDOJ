package cn.edu.uestc.acmicpc.web.view;

import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamReportDTO;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.rank.RankList;
import cn.edu.uestc.acmicpc.web.rank.RankListProblem;
import cn.edu.uestc.acmicpc.web.rank.RankListUser;

import org.springframework.web.servlet.view.document.AbstractJExcelView;

import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description
 */
public class ContestRankListView extends AbstractJExcelView {

  /**
   * Current used row
   */
  private Integer currentRow;

  /**
   * Map the team list by team name
   */
  private Map<String, ContestTeamReportDTO> teamMap;

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
    ContestDTO contest = (ContestDTO) model.get("contest");
    Byte contestType = (Byte) model.get("type");
    // Last used column
    Integer lastColumn;
    // Initialize current used row.
    currentRow = 1;

    if (contestType == Global.ContestType.INVITED.ordinal()) {
      // Invited type contest.
      lastColumn = setInvitedContestHeaderRow(sheet, rankList.problemList);
      currentRow += 2;
      // Get team list in model
      List<ContestTeamReportDTO> teamList = (List<ContestTeamReportDTO>) model.get("teamList");
      teamMap = new HashMap<>();
      for (ContestTeamReportDTO team: teamList) {
        teamMap.put(team.getTeamName(), team);
      }
      // Generate rank list by team.
      for (RankListUser team: rankList.rankList) {
        putTeam(team);
      }
    } else {
      //
      lastColumn = 0;
    }

    // Add contest title in row 0
    sheet.addCell(new Label(0, 0, contest.getTitle()));
    sheet.mergeCells(0, 0, lastColumn, 0);
  }

  /**
   * Write rank list row by team format.
   *
   * @param team team entity.
   */
  private void putTeam(RankListUser team) {

  }

  /**
   * Set header row and return the last used column ID.
   *
   * @param sheet excel sheet.
   * @param problemList problem list.
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
    sheet.addCell(new Label(1, 2, "Name"));
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
   * @param sheet excel sheet.
   * @param problemList problem list.
   * @param startColumn current last used column ID.
   * @return last used column ID.
   * @throws WriteException
   */
  private Integer setProblemHeaderRow(WritableSheet sheet,
                                   RankListProblem[] problemList,
                                   Integer startColumn) throws WriteException {
    Integer currentColumn = startColumn;
    for (int order = 0; order < problemList.length; order++) {
      sheet.addCell(new Label(currentColumn, currentRow, "" + (char)('A' + order)));
      sheet.addCell(new Label(currentColumn, currentRow + 1, problemList[order].solved + "/" + problemList[order].tried));

      currentColumn++;
    }

    return currentColumn - 1;
  }
}
