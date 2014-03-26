package cn.edu.uestc.acmicpc.util.settings;


import java.util.ArrayList;
import java.util.List;

/**
 * Global enumerates and constants inside project.
 * <p/>
 * <strong>WARN:</strong> this file may be rewritten carefully.
 */
public class Global {

  /**
   * Number of records per page
   */
  public static final Long RECORD_PER_PAGE = 20L;

  /**
   * Number of articles per page
   */
  public static final Long ARTICLE_PER_PAGE = 10L;

  /**
   * Article more tag, the substring before first more tag is the summary of this article
   */
  public static final String ARTICLE_MORE_TAG = "!!!more!!!";

  /**
   * User serial key's length
   */
  public static final int USER_SERIAL_KEY_LENGTH = 128;

  /**
   * Online judge result type.
   */
  public enum OnlineJudgeResultType {
    OJ_ALL("All"),
    OJ_AC("Accepted", 1),
    OJ_PE("Presentation Error", 2),
    OJ_TLE("Time Limit Exceeded", 3),
    OJ_MLE("Memory Limit Exceeded", 4),
    OJ_WA("Wrong Answer", 5),
    OJ_OLE("Output Limit Exceeded", 6),
    OJ_CE("Compilation Error", 7),
    OJ_RE("Runtime Error", 8, 9, 10, 11, 12, 15),
    OJ_RF("Restricted Function", 13),
    OJ_SE("System Error", 14),
    OJ_JUDGING("Judging", 16, 17),
    OJ_WAIT("Queuing", 0, 18),
    OJ_NOT_AC("Not accepted", 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17);

    private final static int TOTAL_RESULT_SIZE = 19;

    private final String description;

    private final List<Integer> results;

    /**
     * Get enumerate value's description.
     *
     * @return description string for specific online judge return type
     */
    public String getDescription() {
      return description;
    }

    public List<Integer> getResults() {
      return results;
    }

    private OnlineJudgeResultType(String description, int... results) {
      this.description = description;
      this.results = new ArrayList<>();
      if (results.length == 0) {
        for (int i = 0; i < TOTAL_RESULT_SIZE; i++) {
          this.results.add(i);
        }
      } else {
        for (int result : results) {
          this.results.add(result);
        }
      }
    }
  }

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
    OJ_RF("Restricted Function on test $case"),    // 13
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
    PUBLIC("Public"), PRIVATE("Private"), DIY("DIY"), INVITED("Invited"),
    INHERIT("Inherit");

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
   * User's authentication type(`type` column in user entity).
   */
  public enum AuthenticationType {
    NORMAL("Normal user"), ADMIN("Administrator"), CONSTANT("Constant user");

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

  /**
   * User's gender type
   */
  public enum Gender {
    MALE("Male"), FEMALE("Female");

    private final String description;

    private Gender(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }
  }

  /**
   * User's grade type
   */
  public enum Grade {
    SENIOR_ONE("Senior one"), SENIOR_TWO("Senior two"), SENIOR_THREE("Senior three"),
    FRESHMAN("Freshman"), SOPHOMORE("Sophomore"), JUNIOR("Junior"),
    FOURTH_YEAR_OF_UNIVERSITY("Fourth year of university"), GRADUATE("Graduate");

    private final String description;

    private Grade(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }
  }

  /**
   * User's t-shirts size type
   */
  public enum TShirtsSize {
    XS("XS"), S("S"), M("M"), L("L"), XL("XL"), XXL("XXL");

    private final String description;

    private TShirtsSize(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }
  }

  /**
   * Contest register request status
   */
  public enum ContestRegistryStatus {
    PENDING("Pending"), ACCEPTED("Accepted"), REFUSED("Refused");

    private final String description;

    private ContestRegistryStatus(String description) {
      this.description = description;
    }

    public String getDescription() {
      return description;
    }
  }

  /**
   * Article type
   */
  public enum ArticleType {
    NOTICE("Notice"), ARTICLE("Article"), COMMENT("Comment");
    private final String description;

    public String getDescription() {
      return description;
    }

    ArticleType(String description) {
      this.description = description;
    }
  }
}
