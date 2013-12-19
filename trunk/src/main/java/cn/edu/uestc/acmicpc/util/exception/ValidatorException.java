package cn.edu.uestc.acmicpc.util.exception;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple exception implement for JSON inside.
 */
public class ValidatorException extends Exception {

  private static final long serialVersionUID = 2653305329765940347L;
  private final Map<String, String> json;

  /**
   * Constructor for multiple keys and values
   *
   * @param json JSON Mapping
   */
  public ValidatorException(Map<String, String> json) {
    super();
    this.json = json;
  }

  public ValidatorException(String key, String value) {
    super();
    json = new HashMap<>();
    json.put(key, value);
  }

  public Map<String, String> getJson() {
    return json;
  }
}
