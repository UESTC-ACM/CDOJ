package cn.edu.uestc.acmicpc.util.exception;

import java.io.Serializable;

/**
 * A simple exception for Ranklist Parser.
 */
public class ParserException extends Exception implements Serializable {

  private static final long serialVersionUID = -7175100990197608823L;

  public ParserException() {
    super();
  }

  public ParserException(String message) {
    super(message);
  }
}
