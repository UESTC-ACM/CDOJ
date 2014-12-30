package cn.edu.uestc.acmicpc.util.exception;

/**
 * Specific exception for DAO, which indicates the fields for query are not
 * unique.
 */
public class FieldNotUniqueException extends Exception {

  private static final long serialVersionUID = 3215323391088880832L;

  /**
   * Creates a new application exception with {@code null} as its detail message
   * and cause.
   */
  public FieldNotUniqueException() {
    super();
  }

  /**
   * Creates a new exception with the specified detail message and {@code null}
   * cause.
   *
   * @param message
   *          the detail message
   */
  public FieldNotUniqueException(String message) {
    super(message);
  }

  /**
   * Creates a new exception with the specified detail message and cause.
   *
   * @param message
   *          the detail message
   * @param cause
   *          the cause
   */
  public FieldNotUniqueException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a new exception with the specified cause and {@code null} detail
   * message.
   *
   * @param cause
   *          the cause
   */
  public FieldNotUniqueException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new exception with the specified detail message, cause,
   * suppression enabled or disabled, and writable stack trace enabled or
   * disabled.
   *
   * @param message
   *          the detail message
   * @param cause
   *          the cause
   * @param enableSuppression
   *          whether or not suppression is enabled or disabled
   * @param writableStackTrace
   *          whether or not the stack trace should be writable
   */
  public FieldNotUniqueException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
