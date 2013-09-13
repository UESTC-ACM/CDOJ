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

import cn.edu.uestc.acmicpc.db.condition.impl.TrainingContestCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TrainingStatusCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TrainingUserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingStatusDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingContestDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingStatusDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.ioc.condition.TrainingContestConditionAware;
import cn.edu.uestc.acmicpc.ioc.condition.TrainingStatusConditionAware;
import cn.edu.uestc.acmicpc.ioc.condition.TrainingUserConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingContestDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingStatusDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.TrainingUserDAOAware;
import cn.edu.uestc.acmicpc.ioc.dto.TrainingContestDTOAware;
import cn.edu.uestc.acmicpc.ioc.dto.TrainingStatusDTOAware;
import cn.edu.uestc.acmicpc.ioc.util.RatingUtilAware;
import cn.edu.uestc.acmicpc.ioc.util.TrainingRankListParserAware;
import cn.edu.uestc.acmicpc.oj.action.file.FileUploadAction;
import cn.edu.uestc.acmicpc.training.entity.TrainingContestRankList;
import cn.edu.uestc.acmicpc.training.entity.TrainingUserRankSummary;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.RatingUtil;
import cn.edu.uestc.acmicpc.training.parser.TrainingRankListParser;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.ParserException;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.util.List;

/**
 * Description
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@Controller
@LoginPermit(value = Global.AuthenticationType.ADMIN)
public class AdminTrainingContestEditorAction extends FileUploadAction implements
    TrainingContestDAOAware, TrainingRankListParserAware, TrainingContestDTOAware,
    TrainingStatusDAOAware, TrainingStatusConditionAware, TrainingStatusDTOAware,
    TrainingUserDAOAware, TrainingUserConditionAware, TrainingContestConditionAware,
    RatingUtilAware {

  /**
	 * 
	 */
  private static final long serialVersionUID = -5827621472522483158L;
  private Integer targetTrainingContestId;

  public Integer getTargetTrainingContestId() {
    return targetTrainingContestId;
  }

  public void setTargetTrainingContestId(Integer targetTrainingContestId) {
    this.targetTrainingContestId = targetTrainingContestId;
  }

  @Autowired
  private TrainingContestDTO trainingContestDTO;

  @SuppressWarnings("unchecked")
  @Validations(requiredStrings = { @RequiredStringValidator(fieldName = "trainingContestDTO.title",
      key = "error.title.validation") })
  public String toEditContest() {
    try {
      if (targetTrainingContestId == null)
        throw new AppException("No such contest!");
      TrainingContest trainingContest = trainingContestDAO.get(targetTrainingContestId);
      if (trainingContest == null)
        throw new AppException("No such contest!");

      trainingContestDTO.updateEntity(trainingContest);
      trainingContestDAO.update(trainingContest);

      File rankFile = new File(getTrainingRankFileName());
      if (rankFile.exists()) {
        // Update rank list
        TrainingContestRankList trainingContestRankList =
            trainingRankListParser.parse(rankFile, trainingContest.getIsPersonal(),
                trainingContest.getType());
        // Delete old records
        trainingStatusCondition.clear();
        trainingStatusCondition.setTrainingContestId(trainingContest.getTrainingContestId());
        trainingStatusDAO.deleteEntitiesByCondition(trainingStatusCondition.getCondition());
        // Add new records
        for (TrainingUserRankSummary trainingUserRankSummary : trainingContestRankList
            .getTrainingUserRankSummaryList()) {
          TrainingStatus trainingStatus = trainingStatusDTO.getEntity();

          trainingStatus.setRank(trainingUserRankSummary.getRank());
          trainingStatus.setSolve(trainingUserRankSummary.getSolved());
          trainingStatus.setPenalty(trainingUserRankSummary.getPenalty());
          trainingStatus.setSummary(trainingRankListParser.encodeTrainingUserSummary(
              trainingUserRankSummary,
              trainingContest.getType()));

          trainingStatus.setTrainingUserByTrainingUserId(trainingUserDAO
              .get(trainingUserRankSummary.getUserId()));
          trainingStatus.setTrainingContestByTrainingContestId(trainingContest);

          trainingStatusDAO.add(trainingStatus);
        }
        // Recovery records
        trainingUserCondition.clear();
        if (trainingContest.getIsPersonal())
          trainingUserCondition.setType(Global.TrainingUserType.PERSONAL.ordinal());
        else
          trainingUserCondition.setType(Global.TrainingUserType.TEAM.ordinal());

        List<TrainingUser> trainingUserList =
            (List<TrainingUser>) trainingUserDAO.findAll(trainingUserCondition.getCondition());
        for (TrainingUser trainingUser : trainingUserList) {
          trainingUser.setCompetitions(0);
          trainingUser.setRating(1200.0);
          trainingUser.setVolatility(550.0);
          trainingUser.setRatingVary(null);
          trainingUser.setVolatilityVary(null);
          trainingUserDAO.update(trainingUser);
        }

        // Update rating
        trainingContestCondition.clear();
        trainingContestCondition.setIsPersonal(trainingContest.getIsPersonal());
        trainingContestCondition.setOrderFields("trainingContestId");
        trainingContestCondition.setOrderAsc("true");
        List<TrainingContest> trainingContests =
            (List<TrainingContest>) trainingContestDAO.findAll(trainingContestCondition
                .getCondition());
        for (TrainingContest trainingContest1 : trainingContests) {
          ratingUtil.updateRating(trainingContest1);
        }
      }

      json.put("result", "ok");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return JSON;
  }

  /**
   * Upload rank file, and return content.
   * 
   * @return <strong>JSON</strong> signal.
   */
  @SkipValidation
  public String uploadTrainingRankFile() {
    try {
      if (targetTrainingContestId == null)
        throw new AppException("No such contest!");
      TrainingContest trainingContest = trainingContestDAO.get(targetTrainingContestId);
      if (trainingContest == null)
        throw new AppException("No such contest!");

      setSavePath(settings.SETTING_UPLOAD_FOLDER + "/temp");
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

      TrainingContestRankList trainingContestRankList =
          trainingRankListParser.parse(targetFile, trainingContest.getIsPersonal(),
              trainingContest.getType());
      json.put("trainingContestRankList", trainingContestRankList);
      json.put("success", "true");
    } catch (AppException | ParserException e) {
      json.put("error", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("error", "Unknown exception occurred.");
    }
    return JSON;
  }

  private String getTrainingRankFileName() {
    return settings.SETTING_UPLOAD_FOLDER + "/training_contest_" + targetTrainingContestId + ".xls";
  }

  @Autowired
  private ITrainingContestDAO trainingContestDAO;

  @Override
  public void setTrainingContestDAO(ITrainingContestDAO trainingContestDAO) {
    this.trainingContestDAO = trainingContestDAO;
  }

  @Autowired
  private TrainingRankListParser trainingRankListParser;

  @Override
  public void setTrainingRankListParserAware(TrainingRankListParser trainingRankListParser) {
    this.trainingRankListParser = trainingRankListParser;
  }

  @Override
  public void setTrainingContestDTO(TrainingContestDTO trainingContestDTO) {
    this.trainingContestDTO = trainingContestDTO;
  }

  @Override
  public TrainingContestDTO getTrainingContestDTO() {
    return trainingContestDTO;
  }

  @Autowired
  private ITrainingStatusDAO trainingStatusDAO;

  @Override
  public void setTrainingStatusDAO(ITrainingStatusDAO trainingStatusDAO) {
    this.trainingStatusDAO = trainingStatusDAO;
  }

  @Autowired
  private TrainingStatusCondition trainingStatusCondition;

  @Override
  public void setTrainingStatusCondition(TrainingStatusCondition trainingStatusCondition) {
    this.trainingStatusCondition = trainingStatusCondition;
  }

  @Override
  public TrainingStatusCondition getTrainingStatusCondition() {
    return trainingStatusCondition;
  }

  @Autowired
  private TrainingStatusDTO trainingStatusDTO;

  @Override
  public void setTrainingStatusDTO(TrainingStatusDTO trainingStatusDTO) {
    this.trainingStatusDTO = trainingStatusDTO;
  }

  @Override
  public TrainingStatusDTO getTrainingStatusDTO() {
    return trainingStatusDTO;
  }

  @Autowired
  private ITrainingUserDAO trainingUserDAO;

  @Override
  public void setTrainingUserDAO(ITrainingUserDAO trainingUserDAO) {
    this.trainingUserDAO = trainingUserDAO;
  }

  @Autowired
  private TrainingUserCondition trainingUserCondition;

  @Override
  public void setTrainingUserCondition(TrainingUserCondition trainingUserCondition) {
    this.trainingUserCondition = trainingUserCondition;
  }

  @Override
  public TrainingUserCondition getTrainingUserCondition() {
    return this.trainingUserCondition;
  }

  @Autowired
  private TrainingContestCondition trainingContestCondition;

  @Override
  public void setTrainingContestCondition(TrainingContestCondition trainingContestCondition) {
    this.trainingContestCondition = trainingContestCondition;
  }

  @Override
  public TrainingContestCondition getTrainingContestCondition() {
    return trainingContestCondition;
  }

  @Autowired
  private RatingUtil ratingUtil;

  @Override
  public void setRatingUtil(RatingUtil ratingUtil) {
    this.ratingUtil = ratingUtil;
  }
}
