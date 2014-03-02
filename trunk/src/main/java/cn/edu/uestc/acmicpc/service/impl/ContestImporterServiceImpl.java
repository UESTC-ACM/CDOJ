package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.db.entity.Problem;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.zip.ZipFile;

@Service
public class ContestImporterServiceImpl extends AbstractService implements ContestImporterService {

  private final Settings settings;

  private final IContestDAO contestDAO;

  private final IProblemDAO problemDAO;

  private static final String[] contestBasicInfoTagNames = new String[] {
      "title", "length", "type", "startTime", "description", "visible", "problems"
  };

  private static final String[] problemBasicInfoTagNames = new String[] {
      "title", "description", "timeLimit", "memoryLimit", "input", "output",
      "sampleInput", "sampleOutput", "source"
  };

  private static final String[] problemAdditionalInfoTagNames = new String[] {
      "javaTimeLimit", "javaMemoryLimit", "hint", "specialJudge"
  };

  @Autowired
  public ContestImporterServiceImpl(Settings settings, IContestDAO contestDAO, IProblemDAO problemDAO) {
    this.settings = settings;
    this.contestDAO = contestDAO;
    this.problemDAO = problemDAO;
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
    Set<String> tagSet = new HashSet<>(Arrays.asList(contestBasicInfoTagNames));
    Collection<Problem> contestProblems = null;
    for (XmlNode node : root.getChildList()) {
      String tagName = node.getTagName().trim();
      String innerText = node.getInnerText().trim();
      if (tagSet.contains(tagName)) {
        tagSet.remove(tagName);
      } else {
        throw new AppException("Tag name can't occurred multiple times in contest information file.");
      }
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
        contest.setIsVisible(Boolean.parseBoolean(innerText));
      } else if ("problems".equals(tagName)) {
        contestProblems = parseContestProblems(node, directory);
      }
    }
    if (!tagSet.isEmpty()) {
      throw new AppException(String.format("Tags %s not occurred in problem information file.", tagSet));
    }

    if (contestProblems == null) {
      throw new AppException("No contest problems declared.");
    }

    contestDAO.add(contest);
    int problemOrder = 1;
    for (Problem problem : contestProblems) {
      problemDAO.add(problem);
      ContestProblem contestProblem = new ContestProblem();
      contestProblem.setContestByContestId(contest);
      contestProblem.setProblemByProblemId(problem);
      contestProblem.setOrder(problemOrder);
      problemOrder ++;
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

  private Collection<Problem> parseContestProblems(XmlNode problemsNode, String rootDirectory) throws AppException {
    if (problemsNode == null) {
      throw new AppException("Invalid XmlNode.");
    }
    Collection<Problem> contestProblems = new ArrayList<>();
    for (XmlNode node : problemsNode.getChildList()) {
      Problem problem = parseContestProblem(rootDirectory + "/" + node.getInnerText().trim());
      contestProblems.add(problem);
    }
    return contestProblems;
  }

  private Problem parseContestProblem(String directory) throws AppException {
    XmlParser xmlParser = new XmlParser(directory + "/" + "problemInfo.xml");
    XmlNode root;
    try {
      root = xmlParser.parse().getChildList().get(0);
    } catch (IndexOutOfBoundsException e) {
      throw new AppException("No root node in problem information file.");
    }

    Problem problem = new Problem();
    Set<String> basicTagSet = new HashSet<>(Arrays.asList(problemBasicInfoTagNames));
    Set<String> additionalTagSet = new HashSet<>(Arrays.asList(problemAdditionalInfoTagNames));
    for (XmlNode node : root.getChildList()) {
      String tagName = node.getTagName().trim();
      String innerText = node.getInnerText().trim();
      if (basicTagSet.contains(tagName)) {
        basicTagSet.remove(tagName);
      } else if (additionalTagSet.contains(tagName)) {
        additionalTagSet.remove(tagName);
      } else {
        throw new AppException("Tag name can't occurred multiple times in problem information file.");
      }
      if ("title".equals(tagName)) {
        problem.setTitle(innerText);
      } else if ("description".equals(tagName)) {
        problem.setDescription(innerText);
      } else if ("input".equals(tagName)) {
        problem.setInput(innerText);
      } else if ("output".equals(tagName)) {
        problem.setOutput(innerText);
      } else if ("sampleInput".equals(tagName)) {
        problem.setSampleInput(innerText);
      } else if ("sampleOutput".equals(tagName)) {
        problem.setSampleOutput(innerText);
      } else if ("timeLimit".equals(tagName)) {
        problem.setTimeLimit(Integer.parseInt(innerText));
      } else if ("memoryLimit".equals(tagName)) {
        problem.setMemoryLimit(Integer.parseInt(innerText));
      } else if ("javaTimeLimit".equals(tagName)) {
        problem.setJavaTimeLimit(Integer.parseInt(innerText));
      } else if ("javaMemoryLimit".equals(tagName)) {
        problem.setJavaMemoryLimit(Integer.parseInt(innerText));
      } else if ("source".equals(tagName)) {
        problem.setSource(innerText);
      } else if ("hint".equals(tagName)) {
        problem.setHint(innerText);
      } else if ("specialJudge".equals(tagName)) {
        problem.setIsSpj(Boolean.getBoolean(tagName));
      }
    }
    if (!basicTagSet.isEmpty()) {
      throw new AppException(String.format("Tags %s not occurred in problem information file.", basicTagSet));
    }
    problem.setIsVisible(false);
    if (additionalTagSet.contains("specialJudge")) {
      problem.setIsSpj(false);
    }
    if (additionalTagSet.contains("javaTimeLimit")) {
      problem.setJavaTimeLimit(problem.getTimeLimit() * 3);
    }
    if (additionalTagSet.contains("javaMemoryLimit")) {
      problem.setJavaMemoryLimit(problem.getMemoryLimit());
    }
    return problem;
  }

}
