package cn.edu.uestc.acmicpc.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ILanguageDAO;
import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.db.entity.Language;
import cn.edu.uestc.acmicpc.oj.entity.ContestRankList;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Global enumerates and constants inside project.
 * <p/>
 * <strong>WARN:</strong> this file may be rewritten carefully.
 */
@Repository
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class Global {

  /**
   * User serial key's length
   */
  public static final int USER_SERIAL_KEY_LENGTH = 128;

  public enum OnlineJudgeReturnType {
    OJ_WAIT("Queuing"), OJ_AC("Accepted"), OJ_PE("Presentation Error on test $case"), OJ_TLE(
        "Time Limit Exceeded on test $case"), OJ_MLE("Memory Limit Exceeded on test $case"), OJ_WA(
        "Wrong Answer on test $case"), OJ_OLE("Output Limit Exceeded on test $case"), OJ_CE(
        "Compilation Error"), OJ_RE_SEGV("Runtime Error on test $case"), OJ_RE_FPE(
        "Runtime Error on test $case"), OJ_RE_BUS("Runtime Error on test $case"), OJ_RE_ABRT(
        "Runtime Error on test $case"), OJ_RE_UNKNOWN("Runtime Error on test $case"), OJ_RF(
        "Restricted Function on test $case"), OJ_SE("System Error on test $case"), OJ_RE_JAVA(
        "Runtime Error on test $case"), OJ_JUDGING("Queuing"), OJ_RUNNING("Running on test $case"),
    OJ_REJUDGING("Queuing");

    private String description;

    /**
     * Get enumerate value's description.
     *
     * @return description string for specific online judge return type
     */
    public String getDescription() {
      return description;
    }

    private OnlineJudgeReturnType(String description) {
      this.description = description;

    }
  }

  /**
   * Problem status for author problem flag.
   */
  public enum AuthorStatusType {
    NONE, PASS, FAIL
  }

  /**
   * Contest type for contest entity
   */
  public enum ContestType {
    PUBLIC("public"), PRIVATE("private"), DIY("DIY"), INVITED("invited");

    private final String description;

    private ContestType(String description) {
      this.description = description;
    }

    /**
     * Get enumerate value's description.
     *
     * @return description string for specific contest type
     */
    public String getDescription() {
      return description;
    }
  }

  public enum TrainingContestType {
    NORMAL("normal"), ADJUST("adjust"), CF("cf"), TC("tc"), TEAM("team"), OTHERS("others"),
    UNRATED("unrated"), ABSENT("absent");

    private final String description;

    private TrainingContestType(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }
  }

  public enum TrainingUserType {
    PERSONAL("Personal"), TEAM("Team");

    private final String description;

    private TrainingUserType(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }
  }

  /**
   * User's authentication type(`type` column in user entity).
   */
  public enum AuthenticationType {
    NORMAL("normal user"), ADMIN("administrator"), CONSTANT("constant user");

    private final String description;

    private AuthenticationType(String description) {
      this.description = description;
    }

    /**
     * Get enumerate value's description.
     *
     * @return description string for specific authentication type
     */
    public String getDescription() {
      return description;
    }
  }

  @Autowired
  public Global(IDepartmentDAO departmentDAO,
      ILanguageDAO languageDAO) {
    this.departmentDAO = departmentDAO;
    this.languageDAO = languageDAO;
  }

  /**
   * Department DAO using for get all departments.
   */
  private IDepartmentDAO departmentDAO;

  /**
   * Language DAO using for get all languages.
   */
  private ILanguageDAO languageDAO;

  /**
   * Department list.
   */
  private List<Department> departmentList;

  private List<Language> languageList;

  /**
   * authentication type list
   */
  private List<AuthenticationType> authenticationTypeList;

  /**
   * contest type list
   */
  private List<ContestType> contestTypeList;

  /**
   * training contest type list
   */
  private List<TrainingContestType> trainingContestTypeList;
  /**
   * Cache used contest ranklist
   */
  private Map<Integer, ContestRankList> contestRankListMap;

  public Map<Integer, ContestRankList> getContestRankListMap() {
    return contestRankListMap;
  }

  public void setContestRankListMap(Map<Integer, ContestRankList> contestRankListMap) {
    this.contestRankListMap = contestRankListMap;
  }

  /**
   * Get all languages.
   *
   * @return compile language list
   */
  public List<Language> getLanguageList() {
    return languageList;
  }

  /**
   * Initializer.
   *
   * @throws AppException
   */
  @SuppressWarnings("unchecked")
  @PostConstruct
  public void init() throws AppException {
    contestRankListMap = new HashMap<>();
    this.departmentList = (List<Department>) departmentDAO.findAll();
    this.languageList = (List<Language>) languageDAO.findAll();

    this.authenticationTypeList = new ArrayList<>();
    Collections.addAll(authenticationTypeList, AuthenticationType.values());

    this.contestTypeList = new ArrayList<>();
    Collections.addAll(contestTypeList, ContestType.values());

    this.trainingContestTypeList = new ArrayList<>();
    Collections.addAll(trainingContestTypeList, TrainingContestType.values());
  }

  /**
   * Get all departments in database.
   *
   * @return All departments
   */
  public List<Department> getDepartmentList() {
    return departmentList;
  }

  /**
   * Get all authentications.
   *
   * @return All authentication type
   */
  public List<AuthenticationType> getAuthenticationTypeList() {
    return authenticationTypeList;
  }

  /**
   * Get all contest types.
   *
   * @return All contest type
   */
  public List<ContestType> getContestTypeList() {
    return contestTypeList;
  }

  public List<TrainingContestType> getTrainingContestTypeList() {
    return trainingContestTypeList;
  }
}
