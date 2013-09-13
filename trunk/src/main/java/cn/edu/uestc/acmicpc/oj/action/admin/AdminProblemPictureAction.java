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

import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.oj.action.file.FileUploadAction;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Action for manage problem's picture.
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@Controller
@LoginPermit(value = Global.AuthenticationType.ADMIN)
public class AdminProblemPictureAction extends FileUploadAction implements ProblemDAOAware {

  /**
	 * 
	 */
  private static final long serialVersionUID = -2646396871159130073L;
  @Autowired
  private IProblemDAO problemDAO;

  @Override
  public void setProblemDAO(IProblemDAO problemDAO) {
    this.problemDAO = problemDAO;
  }

  private Integer targetProblemId;

  public void setTargetProblemId(Integer targetProblemId) {
    this.targetProblemId = targetProblemId;
  }

  /**
   * Get all uploaded pictures of target problem.
   * 
   * @return <strong>JSON</strong> signal.
   */
  public String toPictureList() {
    try {
      if (targetProblemId == null)
        throw new AppException("Error, target problem id is null!");
      Problem problem = problemDAO.get(targetProblemId);
      if (problem == null)
        throw new AppException("Error, target problem doesn't exist!");
      File dir = new File(settings.SETTING_PROBLEM_PICTURE_FOLDER_ABSOLUTE + targetProblemId + "/");
      if (!dir.exists())
        if (!dir.mkdirs())
          throw new AppException("Error while make picture directory!");
      File files[] = dir.listFiles();
      if (files == null)
        throw new AppException("Error while list pictures!");
      ArrayList<String> pictures = new ArrayList<>();
      pictures.clear();
      for (File file : files) {
        String fileName = file.getName();
        ImageInputStream iis = ImageIO.createImageInputStream(file);
        Iterator<?> iter = ImageIO.getImageReaders(iis);
        if (iter.hasNext())
          pictures.add(settings.SETTING_PROBLEM_PICTURE_FOLDER + targetProblemId + "/" + fileName);
      }
      json.put("success", "true");
      json.put("pictures", pictures);
    } catch (AppException e) {
      json.put("error", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("error", "Unknown exception occurred.");
    }
    return JSON;
  }

  /**
   * Upload pictures
   * 
   * @return <strong>JSON</strong> signal.
   */
  public String uploadPicture() {
    try {
      if (targetProblemId == null)
        throw new AppException("Error, target problem id is null!");
      Problem problem = problemDAO.get(targetProblemId);
      if (problem == null)
        throw new AppException("Error, target problem doesn't exist!");

      setSavePath(settings.SETTING_UPLOAD_FOLDER + "problem/" + targetProblemId);
      String[] files = uploadFile();
      // In this case, uploaded file should contains only one element.
      if (files == null || files.length > 1)
        throw new AppException("Fetch uploaded file error.");
      File dir = new File(settings.SETTING_PROBLEM_PICTURE_FOLDER_ABSOLUTE + targetProblemId + "/");
      if (!dir.exists())
        if (!dir.mkdirs())
          throw new AppException("Error while make picture directory!");

      String newName = StringUtil.generateFileName(files[0]);
      File oldFile = new File(files[0]);
      File newFile =
          new File(settings.SETTING_PROBLEM_PICTURE_FOLDER_ABSOLUTE + targetProblemId + "/"
              + newName);
      if (!oldFile.renameTo(newFile))
        throw new AppException("Error while rename files");
      files[0] = newName;

      json.put("success", "true");
      json.put("uploadedFile", files[0]);
      json.put("uploadedFileUrl", settings.SETTING_PROBLEM_PICTURE_FOLDER + targetProblemId + "/"
          + newName);
    } catch (AppException e) {
      json.put("error", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("error", "Unknown exception occurred.");
    }
    return JSON;
  }

}
