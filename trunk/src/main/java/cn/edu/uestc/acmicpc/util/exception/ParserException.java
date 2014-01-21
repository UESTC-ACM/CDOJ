package cn.edu.uestc.acmicpc.util.exception;

import java.io.Serializable;

/**
 * A simple exception for Rank list Parser.
 */
public class ParserException extends Exception implements Serializable {

  private static final long serialVersionUID = -7175100990197608823L;

  /**
   * Creates a new {@link ParserException} with {@code null} as its detail
   * message and cause.
   */
  public ParserException() {
    super();
  }

  /**
   * Creates a new {@link ParserException} with its detail message.
   *
   * @param message message for error.
   */
  public ParserException(String message) {
    super(message);
  }
}
