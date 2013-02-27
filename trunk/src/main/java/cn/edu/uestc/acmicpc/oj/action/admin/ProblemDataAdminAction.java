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
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.view.impl.ProblemDataView;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.oj.action.file.FileUploadAction;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.ZipUtil;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import com.opensymphony.xwork2.validator.annotations.IntRangeFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.struts2.interceptor.validation.SkipValidation;

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
    @SkipValidation
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
    @SkipValidation
    public String uploadProblemDataFile() {
        setSavePath("/uploads/temp");
        System.out.println(getSavePath());
        try {
            String[] files = uploadFile();
            // In this case, uploaded file should only contains one element.
            if (files == null || files.length < 1)
                throw new AppException("Fetch uploaded file error.");
            File tempFile = new File(files[0]);
            File targetFile = new File(getSavePath()+"/problem_"+targetProblemId+".zip");
            tempFile.renameTo(targetFile);
            json.put("success", "true");
        } catch (AppException e) {
            json.put("success", "false");
            json.put("error", e.getMessage());
        } catch (Exception e) {
            json.put("success", "false");
            json.put("error", "Unknown exception occurred.");
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
     * To update problem test information.
     * <p/>
     * <strong>JSON output</strong>:
     * <ul>
     * <li>
     * For success: {"result":"ok"}
     * </li>
     * <li>
     * For error: {"result":"error", "error_msg":<strong>error message</strong>}
     * </li>
     * </ul>
     *
     * @return <strong>JSON</strong> signal
     *
     *
    "problemDTO.timeLimit":null,
    "problemDTO.memoryLimit":undefined,
    "problemDTO.outputLimit":undefined,
    "problemDTO.javaTimeLimit":undefined,
    "problemDTO.javaMemoryLimit":undefined,
    "problemDTO.isSpj":undefined

     */
    @Validations(
            intRangeFields = {
                    @IntRangeFieldValidator(
                            fieldName = "problemDTO.timeLimit",
                            min = "0",
                            max = "60000",
                            key = "error.timeLimit.validation"
                    ),
                    @IntRangeFieldValidator(
                            fieldName = "problemDTO.javaTimeLimit",
                            min = "0",
                            max = "60000",
                            key = "error.timeLimit.validation"
                    ),
                    @IntRangeFieldValidator(
                            fieldName = "problemDTO.memoryLimit",
                            min = "0",
                            max = "262144",
                            key = "error.memoryLimit.validation"
                    ),
                    @IntRangeFieldValidator(
                            fieldName = "problemDTO.javaMemoryLimit",
                            min = "0",
                            max = "262144",
                            key = "error.memoryLimit.validation"
                    ),
                    @IntRangeFieldValidator(
                            fieldName = "problemDTO.outputLimit",
                            min = "0",
                            max = "262144",
                            key = "error.outputLimit.validation"
                    )
            }
    )
    @SuppressWarnings("UnusedDeclaration")
    public String updateProblemData() {
        setSavePath("/uploads/temp");
        try {
            Problem problem = null;
            if (problemDTO.getProblemId() != null) { //edit
                problem = problemDAO.get(problemDTO.getProblemId());
                problemDTO.updateEntity(problem);
            }
            if (problem == null)
                throw new AppException("No such problem!");
            problemDAO.addOrUpdate(problem);

            ZipFile zipFile = new ZipFile(getSavePath()+"/problem_"+targetProblemId+".zip");
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

    @Override
    public void setProblemDAO(IProblemDAO problemDAO) {
        this.problemDAO = problemDAO;
    }
}
