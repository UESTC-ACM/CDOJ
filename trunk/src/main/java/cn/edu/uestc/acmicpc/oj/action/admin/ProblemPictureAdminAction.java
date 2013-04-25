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

package cn.edu.uestc.acmicpc.oj.action.admin;

import cn.edu.uestc.acmicpc.oj.action.file.FileUploadAction;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Action for manage problem's picture.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class ProblemPictureAdminAction extends FileUploadAction {

    private Integer targetProblemId;

    public Integer getTargetProblemId() {
        return targetProblemId;
    }

    public void setTargetProblemId(Integer targetProblemId) {
        this.targetProblemId = targetProblemId;
    }

    public String toPictureList() {
        try {
            if (targetProblemId == null)
                throw new AppException("Error, target problem id is null!");

        } catch (AppException e) {
            json.put("error", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Unknown exception occurred.");
        }
        return JSON;
    }

}
