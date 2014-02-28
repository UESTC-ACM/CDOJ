package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.service.iface.ContestImporterService;
import cn.edu.uestc.acmicpc.util.checker.ContestZipChecker;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.ZipUtil;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDTO;
import cn.edu.uestc.acmicpc.web.xml.XmlNode;
import cn.edu.uestc.acmicpc.web.xml.XmlParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.zip.ZipFile;

@Service
public class ContestImporterServiceImpl extends AbstractService implements ContestImporterService {

  private final Settings settings;

  @Autowired
  public ContestImporterServiceImpl(Settings settings) {
    this.settings = settings;
  }

  private void parseContestZipArchive(FileInformationDTO fileInformationDTO) throws AppException {
    ZipFile zipFile;
    try {
      zipFile = new ZipFile(fileInformationDTO.getFileName());
    } catch (IOException e) {
      throw new AppException("Create zipFile object failed.");
    }
    String tempDirectory = settings.SETTING_UPLOAD_FOLDER + "/" + fileInformationDTO.getFileName();
    ZipUtil.unzipFile(zipFile, tempDirectory, new ContestZipChecker());
    Contest contest = parseContestInfo(tempDirectory);
  }

  private Contest parseContestInfo(String directory) throws AppException {
    XmlParser xmlParser = new XmlParser(directory + "/" + "contestInfo.xml");
    XmlNode root;
    try {
      root = xmlParser.parse().getChildList().get(0);
    } catch (IndexOutOfBoundsException e) {
      throw new AppException("No root node in contest information file.");
    }

    Contest contest = new Contest();
    for (XmlNode node : root.getChildList()) {
      String tagName = node.getTagName().trim();
      String innerText = node.getInnerText().trim();
      if ("title".equals(tagName)) {
        contest.setTitle(innerText);
      } else if ("length".equals(tagName)) {
        contest.setLength(Integer.parseInt(innerText));
      } else if ("type".equals(tagName)) {
        contest.setType(getContestTypeByte(innerText));
      } else if ("startTime".equals(tagName)) {
        contest.setTime(Timestamp.valueOf(innerText));
      } else if ("description".equals(tagName)) {
        contest.setDescription(innerText);
      } else if ("visible".equals(tagName)) {
        if ("1".equals(innerText)) {
          contest.setIsVisible(true);
        }
      }
    }
    return contest;
  }

  private static Byte getContestTypeByte(String contestTypeString) {
    Global.ContestType contestType;
    switch (contestTypeString) {
      case "Private":
        contestType = Global.ContestType.PRIVATE;
        break;
      case "Invited":
        contestType = Global.ContestType.INVITED;
        break;
      case "DIY":
        contestType = Global.ContestType.DIY;
        break;
      default:
        contestType = Global.ContestType.PUBLIC;
    }
    return (byte) contestType.ordinal();
  }
}
