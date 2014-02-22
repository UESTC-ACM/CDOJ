package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.service.iface.ZipService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;
import cn.edu.uestc.acmicpc.web.dto.FileUploadDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author fanjianjin
 */

@Service
public class ZipServiceImpl extends AbstractFileUploadService implements ZipService {

  private final Settings settings;

  @Autowired
  public ZipServiceImpl(Settings settings) {
    this.settings = settings;
  }

  @Override
  public FileInformationDTO uploadContestZip(FileUploadDTO fileUploadDTO,
                                      String directory) throws AppException {
    return uploadFile(fileUploadDTO, settings.SETTING_CONTEST_ZIP_FOLDER,
        settings.SETTING_CONTEST_ZIP_FOLDER_ABSOLUTE, directory);
  }
}
