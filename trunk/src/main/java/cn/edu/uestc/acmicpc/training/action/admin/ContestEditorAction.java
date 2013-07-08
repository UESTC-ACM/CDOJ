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

package cn.edu.uestc.acmicpc.training.action.admin;

import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.oj.action.file.FileUploadAction;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.io.File;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class ContestEditorAction extends FileUploadAction {

    private Integer targetTrainingContestId;

    public Integer getTargetTrainingContestId() {
        return targetTrainingContestId;
    }

    public void setTargetTrainingContestId(Integer targetTrainingContestId) {
        this.targetTrainingContestId = targetTrainingContestId;
    }

    /**
     * Upload rank file, and return content.
     *
     * @return <strong>JSON</strong> signal.
     */
    public String uploadTrainingRankFile() {
        try {
            setSavePath(settings.SETTING_UPLOAD_FOLDER);
            String[] files = uploadFile();
            // In this case, uploaded file should only contains one element.
            if (files == null || files.length != 1)
                throw new AppException("Fetch uploaded file error.");
            File tempFile = new File(files[0]);
            File targetFile = new File(getTrainingRankFileName());
            if (targetFile.exists() && !targetFile.delete())
                throw new AppException("Internal exception: target file exists and can not be deleted.");
            if (!tempFile.renameTo(targetFile))
                throw new AppException("Internal exception: can not move file.");


            json.put("success", "true");
        } catch (AppException e) {
            json.put("error", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            json.put("error", "Unknown exception occurred.");
        }
        return JSON;
    }

    private String getTrainingRankFileName() {
        return settings.SETTING_UPLOAD_FOLDER + "/summer_training_contest_" + targetTrainingContestId + ".xls";
    }
}
