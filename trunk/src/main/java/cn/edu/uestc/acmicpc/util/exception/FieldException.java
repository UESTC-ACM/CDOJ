package cn.edu.uestc.acmicpc.util.exception;

import cn.edu.uestc.acmicpc.util.ObjectUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.validation.FieldError;

/**
 * Description
 */
public class FieldException extends AppException implements Iterable<FieldError> {

  private static final long serialVersionUID = 8730271948825458848L;
  private List<FieldError> errors = new ArrayList<>();

  /**
   * Construct a new field exception with {@code null} as its detail message and cause.
   */
  public FieldException() {
    super("field_error");
  }

  /**
   * Construct a new field exception with {@link FieldError} information
   *
   * @param objectName error object's name.
   * @param field error object's field.
   * @param message message for error.
   */
  public FieldException(String objectName, String field, String message) {
    super("field_error");
    addErrors(objectName, field, message);
  }

  /**
   * Construct a new field exception with {@link FieldError} information
   *
   * @param field error object's field.
   * @param message message for error.
   */
  public FieldException(String field, String message) {
    super("field_error");
    addErrors(field, field, message);
  }

  @Override
  public String toString() {
    return ObjectUtil.toString(errors) + "\n" + super.toString();
  }

  /**
   * Add a single {@link FieldError} into this exception.
   *
   * @param objectName error object's name.
   * @param field error object's field.
   * @param message message for error.
   */
  public void addErrors(String objectName, String field, String message) {
    errors.add(new FieldError(objectName, field, message));
  }

  /**
   * Add a group of {@link FieldError}s into error list.
   *
   * @param errors errors for this exception.
   */
  public void addErrors(FieldError... errors) {
    if (errors != null) {
      for (FieldError error : errors) {
        this.errors.add(error);
      }
    }
  }

  @Override
  public Iterator<FieldError> iterator() {
    return errors.iterator();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof FieldException) {
      FieldException e = (FieldException) obj;
      if (!super.equals(obj)) {
        return false;
      }
      return errors.equals(e.errors);
    }
    return false;
  }
}
