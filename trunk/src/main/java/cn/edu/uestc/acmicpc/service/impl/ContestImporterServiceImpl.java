package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dto.impl.ContestDto;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestProblemDto;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDto;
import cn.edu.uestc.acmicpc.service.iface.ContestImporterService;
import cn.edu.uestc.acmicpc.service.iface.ContestProblemService;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.service.iface.FileService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.util.checker.ContestZipChecker;
import cn.edu.uestc.acmicpc.util.enums.ContestType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.helper.FileUtil;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.helper.ZipUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.FileInformationDto;
import cn.edu.uestc.acmicpc.web.xml.XmlNode;
import cn.edu.uestc.acmicpc.web.xml.XmlParser;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContestImporterServiceImpl implements ContestImporterService {

  private final Settings settings;

  private final FileService fileService;

  private final ProblemService problemService;

  private final ContestService contestService;

  private final ContestProblemService contestProblemService;

  private final ArrayList<String> problemDataDirectories;

  private static final String[] contestBasicInfoTagNames = new String[]{
      "title", "length", "type", "startTime", "description", "visible", "problems"
  };

  private static final String[] problemBasicInfoTagNames = new String[]{
      "title", "description", "timeLimit", "memoryLimit", "input", "output",
      "sampleInput", "sampleOutput", "source"
  };

  private static final String[] problemAdditionalInfoTagNames = new String[]{
      "hint", "specialJudge"
  };

  private static final Map<String, String> problemTagsSetter;

  static {
    problemTagsSetter = new HashMap<>();
    problemTagsSetter.put("title", "setTitle");
    problemTagsSetter.put("description", "setDescription");
    problemTagsSetter.put("input", "setInput");
    problemTagsSetter.put("output", "setOutput");
    problemTagsSetter.put("sampleInput", "setSampleInput");
    problemTagsSetter.put("sampleOutput", "setSampleOutput");
    problemTagsSetter.put("timeLimit", "setTimeLimit");
    problemTagsSetter.put("memoryLimit", "setMemoryLimit");
    problemTagsSetter.put("source", "setSource");
    problemTagsSetter.put("specialJudge", "setIsSpj");
    problemTagsSetter.put("hint", "setHint");
  }

  @Autowired
  public ContestImporterServiceImpl(Settings settings, FileService fileService,
                                    ProblemService problemService, ContestService contestService,
                                    ContestProblemService contestProblemService) {
    this.settings = settings;
    this.fileService = fileService;
    this.problemService = problemService;
    this.contestService = contestService;
    this.contestProblemService = contestProblemService;
    this.problemDataDirectories = new ArrayList<>();
  }

  @Override
  public ContestDto parseContestZipArchive(FileInformationDto fileInformationDto)
      throws AppException {
    ZipFile zipFile;
    try {
      zipFile = new ZipFile(settings.UPLOAD_FOLDER + fileInformationDto.getFileName());
    } catch (IOException e) {
      throw new AppException("Create zipFile object failed.");
    }
    String tempDirectory = settings.UPLOAD_FOLDER + "/"
        + fileInformationDto.getFileName().replaceAll(".zip", "");
    ZipUtil.unzipFile(zipFile, tempDirectory, new ContestZipChecker());
    ContestDto contestDto;
    try {
      contestDto = parseContestInfo(tempDirectory);
    } catch (AppException e) {
      throw new AppException(e.getMessage());
    } finally {
      FileUtil.clearDirectory(tempDirectory);
    }
    return contestDto;
  }

  private ContestDto parseContestInfo(String directory) throws AppException {
    XmlParser xmlParser = new XmlParser(directory + "/" + "contestInfo.xml");
    XmlNode root;
    try {
      root = xmlParser.parse().getChildList().get(0);
    } catch (IndexOutOfBoundsException e) {
      throw new AppException("No root node in contest information file.");
    }

    ContestDto contestDto = new ContestDto();
    Set<String> tagSet = new HashSet<>(Arrays.asList(contestBasicInfoTagNames));
    ArrayList<ProblemDto> contestProblems = null;
    for (XmlNode node : root.getChildList()) {
      String tagName = node.getTagName().trim();
      String innerText = node.getInnerText().trim();
      if (tagSet.contains(tagName)) {
        tagSet.remove(tagName);
      } else {
        throw new AppException("Tag name can't occurred multiple times in " +
            "contest information file.");
      }
      if (StringUtil.isNullOrEmpty(innerText)) {
        throw new AppException(String.format("Invalid value in tag %s", tagName));
      }

      try {
        switch (tagName) {
          case "title":
            contestDto.setTitle(innerText);
            break;
          case "length":
            contestDto.setLength(Integer.parseInt(innerText) * 60);
            break;
          case "type":
            contestDto.setType(getContestType(innerText));
            break;
          case "startTime":
            contestDto.setTime(Timestamp.valueOf(innerText));
            break;
          case "description":
            contestDto.setDescription(innerText);
            break;
          case "visible":
            contestDto.setIsVisible(Boolean.parseBoolean(innerText));
            break;
          case "problems":
            contestProblems = parseContestProblems(node, directory);
            break;
        }
      } catch (Exception e) {
        throw new AppException(e.getMessage());
      }
    }
    if (!tagSet.isEmpty()) {
      throw new AppException(String.format("Tags %s not occurred in problem information file.",
          tagSet));
    }

    if (contestProblems == null) {
      throw new AppException("No contest problems declared.");
    }

    problemService.createProblems(contestProblems);
    Integer contestId = contestService.createNewContest();
    contestDto.setContestId(contestId);

    contestService.updateContest(contestDto);
    Integer problemOrder = 0;
    for (int i = 0; i < contestProblems.size(); i++) {
      Integer problemId = contestProblems.get(i).getProblemId();
      String problemDataDirectory = problemDataDirectories.get(i);
      ContestProblemDto contestProblemDto = new ContestProblemDto();
      contestProblemDto.setProblemId(problemId);
      contestProblemDto.setContestId(contestId);
      contestProblemDto.setOrder(problemOrder);
      contestProblemService.createNewContestProblem(contestProblemDto);
      fileService.moveProblemDataFile(problemDataDirectory, problemId);
      problemOrder++;
    }

    return contestDto;
  }

  private static Byte getContestType(String contestTypeString) {
    ContestType contestType;
    switch (contestTypeString) {
      case "Private":
        contestType = ContestType.PRIVATE;
        break;
      case "Invited":
        contestType = ContestType.INVITED;
        break;
      case "DIY":
        contestType = ContestType.DIY;
        break;
      default:
        contestType = ContestType.PUBLIC;
    }
    return (byte) contestType.ordinal();
  }

  private ArrayList<ProblemDto> parseContestProblems(XmlNode problemsNode, String rootDirectory)
      throws AppException {
    AppExceptionUtil.assertNotNull(problemsNode);
    ArrayList<ProblemDto> contestProblems = new ArrayList<>();
    for (XmlNode node : problemsNode.getChildList()) {
      String problemDirectory = rootDirectory + "/" + node.getInnerText().trim();
      ProblemDto problem = parseContestProblem(problemDirectory);
      problemDataDirectories.add(problemDirectory.replaceFirst(settings.UPLOAD_FOLDER, ""));
      contestProblems.add(problem);
    }
    return contestProblems;
  }

  private ProblemDto parseContestProblem(String directory) throws AppException {
    XmlParser xmlParser = new XmlParser(directory + "/" + "problemInfo.xml");
    XmlNode root;
    try {
      root = xmlParser.parse().getChildList().get(0);
    } catch (IndexOutOfBoundsException e) {
      throw new AppException("No root node in problem information file.");
    }

    ProblemDto problemDto = new ProblemDto();
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
        throw new AppException("Tag name can't occurred multiple times in " +
            "problem information file.");
      }
      if (StringUtil.isNullOrEmpty(innerText)) {
        throw new AppException(String.format("Invalid value in tag %s", tagName));
      }

      try {
        String setter = problemTagsSetter.get(tagName);
        if (setter.endsWith("Limit")) {
          Method method = problemDto.getClass().getMethod(setter, Integer.class);
          method.invoke(problemDto, Integer.parseInt(innerText));
        } else if (setter.equals("specialJudge")) {
          problemDto.setIsVisible(Boolean.parseBoolean(innerText));
        } else {
          Method method = problemDto.getClass().getMethod(setter, String.class);
          method.invoke(problemDto, innerText);
        }
      } catch (Exception e) {
        throw new AppException("Exception thrown when parse contest problem information");
      }
    }

    if (!basicTagSet.isEmpty()) {
      throw new AppException(String.format("Tags %s not occurred in problem information file.",
          basicTagSet));
    }

    problemDto.setIsVisible(false);
    if (additionalTagSet.contains("specialJudge")) {
      problemDto.setIsSpj(false);
    }
    return problemDto;
  }

}
