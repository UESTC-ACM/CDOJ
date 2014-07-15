package cn.edu.uestc.acmicpc.util.enums;

/**
 * User's grade type
 */
public enum GradeType implements EnumType {
  SENIOR_ONE("Senior one"), SENIOR_TWO("Senior two"), SENIOR_THREE("Senior three"),
  FRESHMAN("Freshman"), SOPHOMORE("Sophomore"), JUNIOR("Junior"),
  FOURTH_YEAR_OF_UNIVERSITY("Fourth year of university"), GRADUATE("Graduate");

  private final String description;

  private GradeType(String description) {
    this.description = description;
  }

  @Override
  public String getDescription() {
    return description;
  }
}
