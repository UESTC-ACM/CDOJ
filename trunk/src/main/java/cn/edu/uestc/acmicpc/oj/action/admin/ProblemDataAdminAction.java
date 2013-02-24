/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.oj.action.admin;

import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ProblemDTO;
import cn.edu.uestc.acmicpc.db.view.impl.ProblemDataView;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.oj.action.file.FileUploadAction;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.ZipUtil;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.io.File;
import java.util.zip.ZipFile;

/**
 * description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 4
 */
@LoginPermit(Global.AuthenticationType.ADMIN)
public class ProblemDataAdminAction extends FileUploadAction implements ProblemDAOAware {
    private IProblemDAO problemDAO;
    private Integer targetProblemId;

    private ProblemDTO problemDTO;

    private ProblemDataView problemDataView;

    @SuppressWarnings("UnusedDeclaration")
    public ProblemDataView getProblemDataView() {
        return problemDataView;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setProblemDataView(ProblemDataView problemDataView) {
        this.problemDataView = problemDataView;
    }

    @SuppressWarnings("UnusedDeclaration")
    public ProblemDTO getProblemDTO() {
        return problemDTO;
    }

    @SuppressWarnings("UnusedDeclaration")
    public void setProblemDTO(ProblemDTO problemDTO) {
        this.problemDTO = problemDTO;
    }

    @SuppressWarnings("UnusedDeclaration")
    public Integer getTargetProblemId() {
        return targetProblemId;
    }

    public void setTargetProblemId(Integer targetProblemId) {
        this.targetProblemId = targetProblemId;
    }

    /**
     * Go to problem data editor view!
     *
     * @return <strong>SUCCESS</strong> signal
     */
    public String toProblemDataEditor() {
        try {
            problemDataView = new ProblemDataView(problemDAO.get(targetProblemId));
        } catch (AppException e) {
            return setError("Specific problem doesn't exist.");
        }
        return SUCCESS;
    }

    /**
     * Upload data file, and modified the data files.
     * <p/>
     * <strong>WARN</strong>: all the origin data files may be deleted.
     * <p/>
     * <strong>INFO</strong>:
     * <p/>
     * All files in data folder should named from 1 to number of cases, input files
     * end with ".in" and output files end with ".out".
     *
     * @return <strong>JSON</strong> signal.
     */
    public String uploadProblemDataFile() {
        setSavePath("/uploads/temp");
        System.out.println(getSavePath());
        try {
            String[] files = uploadFile();
            // In this case, uploaded file should only contains one element.
            if (files == null || files.length < 1)
                throw new AppException("Fetch uploaded file error.");
            ZipFile zipFile = new ZipFile(files[0]);
            String dataPath = settings.JUDGE_DATA_PATH + "/" + targetProblemId;
            clearDirectory(dataPath);
            ZipUtil.unzipFile(zipFile, dataPath);
            json.put("result", "ok");
        } catch (AppException e) {
            json.put("result", "error");
            json.put("error_msg", e.getMessage());
        } catch (Exception e) {
            json.put("result", "error");
            json.put("error_msg", "Unknown exception occurred.");
            e.printStackTrace();
        }
        return JSON;
    }

    /**
     * Clear all the files under the path.
     * <p/>
     * <strong>WARN</strong>: this operation cannot be reverted.
     *
     * @param path absolute path value
     */
    private void clearDirectory(String path) throws AppException {
        File file = new File(path);
        if (file.exists()) {
            if (!file.delete())
                throw new AppException("can not delete the data folder.");
        }
        if (!file.mkdirs())
            throw new AppException("can not create the data folder.");
    }

    /**
     * Update problem's limits and data.
     *
     * @return <strong>SUCCESS</strong> signal
     */
    @SuppressWarnings("UnusedDeclaration")
    public String updateProblemData() {
        return SUCCESS;
    }

    @Override
    public void setProblemDAO(IProblemDAO problemDAO) {
        this.problemDAO = problemDAO;
    }
}
