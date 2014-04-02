package cn.edu.uestc.acmicpc.web.oj.controller.picture;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;

/**
 * Controller for picture operation
 *
 * @author mzry1992
 */
@Controller
@RequestMapping("/picture")
public class PictureController extends BaseController {

  private PictureService pictureService;

  @Autowired
  public PictureController(PictureService pictureService) {
    this.pictureService = pictureService;
  }

  @RequestMapping(value = "uploadProblemPicture/{problemId}", method = RequestMethod.POST)
  @LoginPermit(AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> uploadProblemPicture(@RequestParam(value = "uploadFile", required = true)
                                    MultipartFile[] files,
                                    @PathVariable("problemId") String problemId) {
    Map<String, Object> json = new HashMap<>();
    try {
      String directory = "problem/" + problemId + "/";
      FileInformationDTO fileInformationDTO = pictureService.uploadPicture(
          FileUploadDTO.builder()
              .setFiles(Arrays.asList(files))
              .build(),
          directory);

      json.put("success", "true");
      json.put("uploadedFile", fileInformationDTO.getFileName());
      json.put("uploadedFileUrl", fileInformationDTO.getFileURL());
    } catch (AppException e) {
      e.printStackTrace();
      json.put("error", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("error", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping(value = "uploadContestPicture/{contestId}", method = RequestMethod.POST)
  @LoginPermit(AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> uploadContestPicture(@RequestParam(value = "uploadFile", required = true)
                                           MultipartFile[] files,
                                           @PathVariable("contestId") String contestId) {
    Map<String, Object> json = new HashMap<>();
    try {
      String directory = "contest/" + contestId + "/";
      FileInformationDTO fileInformationDTO = pictureService.uploadPicture(
          FileUploadDTO.builder()
              .setFiles(Arrays.asList(files))
              .build(),
          directory);

      json.put("success", "true");
      json.put("uploadedFile", fileInformationDTO.getFileName());
      json.put("uploadedFileUrl", fileInformationDTO.getFileURL());
    } catch (AppException e) {
      e.printStackTrace();
      json.put("error", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("error", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping(value = "uploadArticlePicture/{userName}/{articleId}", method = RequestMethod.POST)
  @LoginPermit(NeedLogin = true)
  public
  @ResponseBody
  Map<String, Object> uploadArticlePicture(@RequestParam(value = "uploadFile", required = true)
                                           MultipartFile[] files,
                                           @PathVariable("userName") String userName,
                                           @PathVariable("articleId") String articleId,
                                           HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      UserDTO userDTO = getCurrentUser(session);
      if (!userDTO.getUserName().equals(userName)) {
        throw new AppException("Permission denied!");
      }
      String directory = "article/" + userName + "/" + articleId + "/";
      FileInformationDTO fileInformationDTO = pictureService.uploadPicture(
          FileUploadDTO.builder()
              .setFiles(Arrays.asList(files))
              .build(),
          directory);

      json.put("success", "true");
      json.put("uploadedFile", fileInformationDTO.getFileName());
      json.put("uploadedFileUrl", fileInformationDTO.getFileURL());
    } catch (AppException e) {
      e.printStackTrace();
      json.put("error", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("error", "Unknown exception occurred.");
    }
    return json;
  }
}
