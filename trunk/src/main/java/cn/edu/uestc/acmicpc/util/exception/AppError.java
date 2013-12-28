package cn.edu.uestc.acmicpc.util.exception;


/**
 * Error information for {@link AppException}.
 */
public class AppError {

  private String objectName;
  private String field;
  private String message;

  /**
   * Creates a {@link AppError} object to describe a error in
   * {@link AppException}.
   * 
   * @param objectName
   *          object's name for the error, may be DTO's name or common object's
   *          name
   * @param field
   *          error field path
   * @param message
   *          error message
   */
  public AppError(String objectName, String field, String message) {
    this.objectName = objectName;
    this.field = field;
    this.message = message;
  }

  /**
   * @return error field path
   */
  public String getField() {
    return field;
  }

  /**
   * Sets API error's field path.
   * 
   * @param field
   *          field path of the API error object
   */
  public void setField(String field) {
    this.field = field;
  }

  /**
   * @return error message for the error
   */
  public String getMessage() {
    return message;
  }

  /**
   * Sets the error message
   * 
   * @param message
   *          the error message to be set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return object's name of the error
   */
  public String getObjectName() {
    return objectName;
  }

  /**
   * Set object's name of the error.
   * 
   * @param objectName
   *          object name of the error
   */
  public void setObjectName(String objectName) {
    this.objectName = objectName;
  }
}
