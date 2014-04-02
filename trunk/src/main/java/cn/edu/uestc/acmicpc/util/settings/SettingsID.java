package cn.edu.uestc.acmicpc.util.settings;

/**
 * Settings id map.
 */
public enum SettingsID {
  HOST(1),
  ENCODING(2),
  @Deprecated UPLOAD_FOLDER(3),
  @Deprecated PICTURE_FOLDER(4),
  JUDGE_CORE(5),
  @Deprecated DATA_PATH(6),
  WORK_PATH(7),
  JUDGES(8),
  EMAIL(9),
  RECORD_PER_PAGE(10);

  private Integer id;

  SettingsID(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
