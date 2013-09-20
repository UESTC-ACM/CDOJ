package cn.edu.uestc.acmicpc.util.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.edu.uestc.acmicpc.util.ObjectUtil;

/**
 * Global Application Exception class
 */
public class AppException extends Exception implements Serializable, Iterable<AppError> {

  private static final long serialVersionUID = -4267655542557102261L;

  private List<AppError> errors = new ArrayList<>();

  /**
   * Construct a new application exception with {@code null} as its detail message and cause.
   */
  public AppException() {
    super();
  }

  /**
   * Construct a new exception with the specified detail message and {@code null} cause.
   *
   * @param message the detail message
   */
  public AppException(String message) {
    super(message);
  }

  /**
   * Construct a new exception with the specified detail message and cause.
   *
   * @param message the detail message
   * @param cause the cause
   */
  public AppException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Construct a new exception with the specified cause and {@code null} detail message.
   *
   * @param cause the cause
   */
  public AppException(Throwable cause) {
    super(cause);
  }

  /**
   * Construct a new exception with the specified detail message, cause, suppression enabled or
   * disabled, and writable stack trace enabled or disabled.
   *
   * @param message the detail message
   * @param cause the cause
   * @param enableSuppression whether or not suppression is enabled or disabled
   * @param writableStackTrace whether or not the stack trace should be writable
   */
  public AppException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  @Override
  public void printStackTrace() {
    System.err.println(ObjectUtil.toString(errors));
    super.printStackTrace();
  }

  @Override
  public String toString() {
    return ObjectUtil.toString(errors) + "\n" + super.toString();
  }

  /**
   * Add a single {@link AppError} into this exception.
   *
   * @param objectName error object's name.
   * @param field error object's field.
   * @param message message for error.
   */
  public void addErrors(String objectName, String field, String message) {
    errors.add(new AppError(objectName, field, message));
  }

  /**
   * Add a group of {@link AppError}s into error list.
   *
   * @param errors errors for this exception.
   */
  public void addErrors(AppError... errors) {
    if (errors != null) {
      for (AppError error : errors) {
        this.errors.add(error);
      }
    }
  }

  @Override
  public Iterator<AppError> iterator() {
    return errors.iterator();
  }
}
