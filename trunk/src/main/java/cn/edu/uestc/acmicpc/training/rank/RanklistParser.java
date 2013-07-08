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

package cn.edu.uestc.acmicpc.training.rank;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class RanklistParser {

    public ContestRankList parse(File file) throws IOException, BiffException, AppException {
        List<String[]> excelValueList = parseXls(file);
        if (excelValueList == null || excelValueList.size() == 0)
            throw new AppException("Error while parse xls document, Please check it!");
        String[] header = excelValueList.get(0);
        AppException headerFormatException = new AppException("Xls format error! First line should be (rank) (name|team|id) (solved) (penalty)");
        if (header == null || header.length < 4)
            throw headerFormatException;
        if (header[0].compareToIgnoreCase("rank") != 0)
            throw headerFormatException;
        if (header[1].compareToIgnoreCase("name") != 0 || header[1].compareToIgnoreCase("team") != 0 || header[1].compareToIgnoreCase("id") != 0)
            throw headerFormatException;
        if (header[2].compareToIgnoreCase("solved") != 0)
            throw headerFormatException;
        if (header[3].compareToIgnoreCase("penalty") != 0)
            throw headerFormatException;
        ContestRankList ranklist = new ContestRankList(excelValueList);
        return ranklist;
    }

    public List<String[]> parseXls(File file) throws BiffException, IOException {
        List<String[]> excelValueList = new LinkedList<String[]>();
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
            } catch (BiffException e) {
                throw e;
            } catch (IOException e) {
                throw e;
            } finally {
                if (workbook != null) {
                    workbook.close();
                }
            }
        }
        return excelValueList;
    }
}
