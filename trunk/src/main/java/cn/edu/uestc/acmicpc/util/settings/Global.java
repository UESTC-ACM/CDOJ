package cn.edu.uestc.acmicpc.util.settings;

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

  /**
   * Online judge return type.
   */
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

    private final String description;

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

  /**
   * Training contest type.
   */
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

  /**
   * Training user type.
   */
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

}
