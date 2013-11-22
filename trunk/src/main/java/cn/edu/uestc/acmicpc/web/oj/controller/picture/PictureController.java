package cn.edu.uestc.acmicpc.web.oj.controller.picture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

/**
 * Controller for picture operation
 * @author mzry1992
 */
@Controller
@RequestMapping("/picture")
public class PictureController extends BaseController {

  private PictureService pictureService;

  @Autowired
  public void setPictureService(PictureService pictureService) {
    this.pictureService = pictureService;
  }

  @Deprecated
  @RequestMapping("getUploadedPictures")
  @LoginPermit(NeedLogin = true)
  public @ResponseBody
  Map<String, Object> pictureList(HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      ArrayList<String> pictures = pictureService.getPictures(getCurrentUserID(session));
      json.put("success", "true");
      json.put("pictures", pictures);
    } catch (AppException e) {
      json.put("error", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("error", "Unknown exception occurred.");
    }
    return json;
  }

  @Deprecated
  @RequestMapping(value = "uploadProblemPicture",
      method = RequestMethod.POST)
  @LoginPermit(NeedLogin = true)
  public @ResponseBody
  Map<String, Object> uploadProblemPicture(@RequestParam(value="uploadFile", required=true)
                                    MultipartFile[] files,
                                    HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      FileInformationDTO fileInformationDTO = pictureService.uploadPictures(
          FileUploadDTO.builder()
          .setFiles(Arrays.asList(files))
          .build(),
          getCurrentUserID(session));

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

  /**
   * Upload picture into "/images/{category}/{folder}/"
   * @param files Uploaded files
   * @param category Category name in path
   * @param folder Folder name in path
   * @param session Current user session
   * @return
   */
  @RequestMapping(value = "uploadPicture/{category}/{folder}",
      method = RequestMethod.POST)
  @LoginPermit(NeedLogin = true)
  public @ResponseBody
  Map<String, Object> uploadPicture(@RequestParam(value="uploadFile", required=true)
                                    MultipartFile[] files,
                                    @PathVariable("category") String category,
                                    @PathVariable("folder") String folder,
                                    HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      String directory = category + "/" + folder + "/";
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
