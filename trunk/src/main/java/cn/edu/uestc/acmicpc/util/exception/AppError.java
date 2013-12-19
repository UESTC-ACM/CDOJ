package cn.edu.uestc.acmicpc.util.exception;

import cn.edu.uestc.acmicpc.util.helper.ObjectUtil;

/** Error information for {@link AppException}. */
public class AppError {

  private String objectName;
  private String field;
  private String message;

  public AppError(String objectName, String field, String message) {
    this.objectName = objectName;
    this.field = field;
    this.message = message;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getObjectName() {
    return objectName;
  }

  public void setObjectName(String objectName) {
    this.objectName = objectName;
  }

  @Override
  public String toString() {
    return ObjectUtil.toString(this);
  }

}
