package cn.edu.uestc.acmicpc.util.log;

public class LogConstants {

  public static final int NORMAL = 0;
  public static final int BRIGHT = 1;
  public static final int FOREGROUND_BLACK = 30;
  public static final int FOREGROUND_RED = 31;
  public static final int FOREGROUND_GREEN = 32;
  public static final int FOREGROUND_YELLOW = 33;
  public static final int FOREGROUND_BLUE = 34;
  public static final int FOREGROUND_MAGENTA = 35;
  public static final int FOREGROUND_CYAN = 36;
  public static final int FOREGROUND_WHITE = 37;

  public static final String PREFIX = "\u001b[";
  public static final String SUFFIX = "m";
  public static final char SEPARATOR = ';';
  public static final String END_COLOR = PREFIX + SUFFIX;

  public static final String FATAL_COLOR = PREFIX + BRIGHT + SEPARATOR
      + FOREGROUND_RED + SUFFIX;
  public static final String ERROR_COLOR = PREFIX + NORMAL + SEPARATOR
      + FOREGROUND_RED + SUFFIX;
  public static final String WARN_COLOR = PREFIX + NORMAL + SEPARATOR
      + FOREGROUND_YELLOW + SUFFIX;
  public static final String INFO_COLOR = PREFIX + NORMAL + SEPARATOR
      + FOREGROUND_GREEN + SUFFIX;
  public static final String DEBUG_COLOR = PREFIX + NORMAL + SEPARATOR
      + FOREGROUND_CYAN + SUFFIX;
  public static final String TRACE_COLOR = PREFIX + NORMAL + SEPARATOR
      + FOREGROUND_BLUE + SUFFIX;

  private LogConstants() {
  }
}
