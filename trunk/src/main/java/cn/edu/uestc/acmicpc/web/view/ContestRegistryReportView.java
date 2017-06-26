package cn.edu.uestc.acmicpc.web.view;

import cn.edu.uestc.acmicpc.db.dto.impl.contestteam.ContestTeamReportDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserReportDto;
import cn.edu.uestc.acmicpc.util.enums.ContestRegistryStatusType;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jxl.CellView;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.springframework.web.servlet.view.document.AbstractJExcelView;

@SuppressWarnings("deprecation")
public class ContestRegistryReportView extends AbstractJExcelView {

  /**
   * There are three sheets in total:<br>
   * sheets[ContestRegistryStatus.PENDING]: All pending teams<br>
   * sheets[ContestRegistryStatus.ACCEPTED]: All accepted teams<br>
   * sheets[ContestRegistryStatus.REFUSED]: All refused teams
   */
  private final Integer TOTAL_SHEETS = 3;
  private final WritableSheet sheets[] = new WritableSheet[TOTAL_SHEETS];

  /**
   * Current row id
   */
  private final Integer currentRow[] = new Integer[TOTAL_SHEETS];

  /**
   * Total teams in each sheets
   */
  private final Integer totalTeams[] = new Integer[TOTAL_SHEETS];

  @SuppressWarnings("unchecked")
  @Override
  protected void buildExcelDocument(Map<String, Object> model,
      WritableWorkbook workbook,
      HttpServletRequest request,
      HttpServletResponse response)
      throws Exception {
    // Set file name
    response.setHeader("Content-Disposition", "attachment; filename=\"Registry report.xls\"");

    // create a Excel sheet
    sheets[ContestRegistryStatusType.ACCEPTED.ordinal()] = workbook.createSheet("Accepted", 0);
    sheets[ContestRegistryStatusType.REFUSED.ordinal()] = workbook.createSheet("Refused", 1);
    sheets[ContestRegistryStatusType.PENDING.ordinal()] = workbook.createSheet("Pending", 2);

    for (int id = 0; id < TOTAL_SHEETS; id++) {
      setHeaderRow(sheets[id]);
    }

    String result = (String) model.get("result");
    if (result.equals("success")) {
      Arrays.fill(currentRow, 0, TOTAL_SHEETS, 2);
      Arrays.fill(totalTeams, 0, TOTAL_SHEETS, 0);
      List<ContestTeamReportDto> teamList = (List<ContestTeamReportDto>) model.get("list");
      for (ContestTeamReportDto team : teamList) {
        putTeam(team);
      }
    } else {
      String error_msg = (String) model.get("error_msg");
      // Show error message on first sheet.
      sheets[1].addCell(new Label(0, 2, error_msg));
      sheets[1].mergeCells(0, 2, 13, 2);
    }
    // Auto resize column
    for (int id = 0; id < TOTAL_SHEETS; id++) {
      autoResizeColumnWidth(id);
    }
  }

  private void autoResizeColumnWidth(Integer sheetId) {
    for (int colId = 0; colId < 15; colId++) {
      CellView cellViews = sheets[sheetId].getColumnView(colId);
      cellViews.setAutosize(true);
      sheets[sheetId].setColumnView(colId, cellViews);
    }
  }

  private void putTeam(ContestTeamReportDto team)
      throws WriteException {
    Integer sheetId = team.getStatus();
    Integer startRow = currentRow[sheetId];
    WritableSheet sheet = sheets[sheetId];
    for (TeamUserReportDto user : team.getTeamUsers()) {
      putUser(user, team);
    }
    for (TeamUserReportDto user : team.getInvitedUsers()) {
      putUser(user, team);
    }
    // Write team information
    totalTeams[sheetId]++;
    sheet.addCell(new Label(0, startRow, totalTeams[sheetId].toString()));
    sheet.mergeCells(0, startRow, 0, currentRow[sheetId] - 1);
    sheet.addCell(new Label(1, startRow, team.getContestTeamId().toString()));
    sheet.mergeCells(1, startRow, 1, currentRow[sheetId] - 1);
    sheet.addCell(new Label(2, startRow, team.getTeamName()));
    sheet.mergeCells(2, startRow, 2, currentRow[sheetId] - 1);
  }

  private void putUser(TeamUserReportDto user, ContestTeamReportDto team)
      throws WriteException {
    Integer sheetId = team.getStatus();

    WritableSheet sheet = sheets[sheetId];
    WritableCellFormat cellFormat = new WritableCellFormat();
    if (user.getAllow()) {
      if (team.getLeaderId().equals(user.getUserId())) {
        sheet.addCell(new Label(3, currentRow[sheetId], "Team leader"));
      } else {
        sheet.addCell(new Label(3, currentRow[sheetId], "Team member"));
      }
    } else {
      // Highlight inactive users.
      cellFormat.setBackground(Colour.RED);
      sheet.addCell(new Label(3, currentRow[sheetId], "Inactive", cellFormat));
    }
    sheet.addCell(new Label(4, currentRow[sheetId], user.getUserName(), cellFormat));
    sheet.addCell(new Label(5, currentRow[sheetId], user.getName(), cellFormat));
    sheet.addCell(new Label(6, currentRow[sheetId], user.getSex(), cellFormat));
    sheet.addCell(new Label(7, currentRow[sheetId], user.getSize(), cellFormat));
    sheet.addCell(new Label(8, currentRow[sheetId], user.getEmail(), cellFormat));
    sheet.addCell(new Label(9, currentRow[sheetId], user.getPhone(), cellFormat));
    sheet.addCell(new Label(10, currentRow[sheetId], user.getSchool(), cellFormat));
    sheet.addCell(new Label(11, currentRow[sheetId], user.getDepartment(), cellFormat));
    sheet.addCell(new Label(12, currentRow[sheetId], user.getGrade(), cellFormat));
    sheet.addCell(new Label(13, currentRow[sheetId], user.getStudentId(), cellFormat));
    sheet.addCell(new Label(14, currentRow[sheetId], user.getType(), cellFormat));
    currentRow[sheetId]++;
  }

  private void setHeaderRow(WritableSheet sheet) throws WriteException {
    // create header row
    // #
    sheet.addCell(new Label(0, 0, "#"));
    sheet.mergeCells(0, 0, 0, 1);
    // Id
    sheet.addCell(new Label(1, 0, "ID"));
    sheet.mergeCells(1, 0, 1, 1);
    // Team name
    sheet.addCell(new Label(2, 0, "Team name"));
    sheet.mergeCells(2, 0, 2, 1);
    // Team member
    sheet.addCell(new Label(3, 0, "Team member"));
    sheet.mergeCells(3, 0, 14, 0);
    sheet.addCell(new Label(3, 1, "Role"));
    sheet.addCell(new Label(4, 1, "User name"));
    sheet.addCell(new Label(5, 1, "Name"));
    sheet.addCell(new Label(6, 1, "Gender"));
    sheet.addCell(new Label(7, 1, "T-shirts size"));
    sheet.addCell(new Label(8, 1, "Email"));
    sheet.addCell(new Label(9, 1, "Phone"));
    sheet.addCell(new Label(10, 1, "School"));
    sheet.addCell(new Label(11, 1, "Department"));
    sheet.addCell(new Label(12, 1, "Grade"));
    sheet.addCell(new Label(13, 1, "Student ID"));
    sheet.addCell(new Label(14, 1, "type"));
  }
}
