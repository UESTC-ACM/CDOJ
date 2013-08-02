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

package cn.edu.uestc.acmicpc.oj.action.file;

import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class ExcelExportAction extends BaseAction {

  public InputStream getExcelInputStream(List<String[]> table) {
    Label label;
    WritableWorkbook workbook;
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {
      workbook = Workbook.createWorkbook(os);
      WritableSheet sheet = workbook.createSheet("Sheet1", 0);
      for (int r = 0; r < table.size(); r++)
        for (int c = 0; c < table.get(r).length; c++) {
          label = new Label(c, r, table.get(r)[c]);
          sheet.addCell(label);
        }
      workbook.write();
      workbook.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ByteArrayInputStream(os.toByteArray());
  }
}
