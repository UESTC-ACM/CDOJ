package cn.edu.uestc.acmicpc.util;

import cn.edu.uestc.acmicpc.db.dao.iface.IDepartmentDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ILanguageDAO;
import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.db.entity.Language;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Global enumerates and constants inside project.
 * <p/>
 * <strong>WARN:</strong> this file may be rewritten carefully.
 */
public class Global {

  /**
   * Number of records per page
   */
  public static final Long RECORD_PER_PAGE = 50L;

  /**
   * User serial key's length
   */
  public static final int USER_SERIAL_KEY_LENGTH = 128;

  public enum OnlineJudgeReturnType {
    OJ_WAIT("Queuing"),                             // 0
    OJ_AC("Accepted"),                              // 1
    OJ_PE("Presentation Error on test $case"),      // 2
    OJ_TLE("Time Limit Exceeded on test $case"),    // 3
    OJ_MLE("Memory Limit Exceeded on test $case"),  // 4
    OJ_WA("Wrong Answer on test $case"),            // 5
    OJ_OLE("Output Limit Exceeded on test $case"),  // 6
    OJ_CE("Compilation Error"),                     // 7
    OJ_RE_SEGV("Runtime Error on test $case"),      // 8
    OJ_RE_FPE("Runtime Error on test $case"),       // 9
    OJ_RE_BUS("Runtime Error on test $case"),       // 10
    OJ_RE_ABRT("Runtime Error on test $case"),      // 11
    OJ_RE_UNKNOWN("Runtime Error on test $case"),   // 12
    OJ_RF( "Restricted Function on test $case"),    // 13
    OJ_SE("System Error on test $case"),            // 14
    OJ_RE_JAVA("Runtime Error on test $case"),      // 15
    OJ_JUDGING("Queuing"),                          // 16
    OJ_RUNNING("Running on test $case"),            // 17
    OJ_REJUDGING("Queuing");                        // 18

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
  private final IDepartmentDAO departmentDAO;

  /**
   * Language DAO using for get all languages.
   */
  private final ILanguageDAO languageDAO;

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
    try {
      this.departmentList = (List<Department>) departmentDAO.findAll();
      this.languageList = (List<Language>) languageDAO.findAll();

      this.authenticationTypeList = new ArrayList<>();
      Collections.addAll(authenticationTypeList, AuthenticationType.values());

      this.contestTypeList = new ArrayList<>();
      Collections.addAll(contestTypeList, ContestType.values());

      this.trainingContestTypeList = new ArrayList<>();
      Collections.addAll(trainingContestTypeList, TrainingContestType.values());
    } catch (Exception e) {
    }
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
