package cn.edu.uestc.acmicpc.util.exception;

import cn.edu.uestc.acmicpc.util.helper.ObjectUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.springframework.validation.FieldError;

/**
 * Application exception to describe errors on UI fields.
 */
public class FieldException extends AppException implements Iterable<FieldError> {

  private static final long serialVersionUID = 8730271948825458848L;
  private final List<FieldError> errors = new ArrayList<>();

  /**
   * Creates a new {@link FieldException} with {@code null} as its detail
   * message and cause.
   */
  public FieldException() {
    super("field_error");
  }

  /**
   * Creates a new {@link FieldException} with {@link FieldError} information
   *
   * @param objectName
   *          error object's name.
   * @param field
   *          error object's field.
   * @param message
   *          message for error.
   */
  public FieldException(String objectName, String field, String message) {
    super("field_error");
    addErrors(objectName, field, message);
  }

  /**
   * Creates a new {@link FieldException} with {@link FieldError} information
   *
   * @param field
   *          error object's field.
   * @param message
   *          message for error.
   */
  public FieldException(String field, String message) {
    super("field_error");
    addErrors(field, field, message);
  }

  /**
   * Adds a single {@link FieldError} into this exception.
   *
   * @param objectName
   *          error object's name.
   * @param field
   *          error object's field.
   * @param message
   *          message for error.
   */
  public void addErrors(String objectName, String field, String message) {
    errors.add(new FieldError(objectName, field, message));
  }

  /**
   * Adds a group of {@link FieldError}s into error list.
   *
   * @param errors
   *          errors for this exception.
   */
  public void addErrors(FieldError... errors) {
    if (errors != null) {
      Collections.addAll(this.errors, errors);
    }
  }

  /**
   * @return a {@link Iterator} for all API errors in this exception.
   */
  @Override
  public Iterator<FieldError> iterator() {
    return errors.iterator();
  }

  @Override
  public String toString() {
    return ObjectUtil.toString(errors) + "\n" + super.toString();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof FieldException) {
      FieldException e = (FieldException) obj;
      return super.equals(obj) && Objects.equals(errors, e.errors);
    }
    return false;
  }

  public List<FieldError> getErrors() {
    return errors;
  }
}
