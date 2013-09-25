package cn.edu.uestc.acmicpc.util.log;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

/** Color-coded console appender for log4j. */
public class ANSIConsoleAppender extends ConsoleAppender {

  private static final int NORMAL = 0;
  private static final int BRIGHT = 1;
  @SuppressWarnings("unused")
  private static final int FOREGROUND_BLACK = 30;
  private static final int FOREGROUND_RED = 31;
  private static final int FOREGROUND_GREEN = 32;
  private static final int FOREGROUND_YELLOW = 33;
  private static final int FOREGROUND_BLUE = 34;
  @SuppressWarnings("unused")
  private static final int FOREGROUND_MAGENTA = 35;
  private static final int FOREGROUND_CYAN = 36;
  @SuppressWarnings("unused")
  private static final int FOREGROUND_WHITE = 37;

  private static final String PREFIX = "\u001b[";
  private static final String SUFFIX = "m";
  private static final char SEPARATOR = ';';
  private static final String END_COLOUR = PREFIX + SUFFIX;

  private static final String FATAL_COLOR = PREFIX
      + BRIGHT + SEPARATOR + FOREGROUND_RED + SUFFIX;
  private static final String ERROR_COLOR = PREFIX
      + NORMAL + SEPARATOR + FOREGROUND_RED + SUFFIX;
  private static final String WARN_COLOR = PREFIX
      + NORMAL + SEPARATOR + FOREGROUND_YELLOW + SUFFIX;
  private static final String INFO_COLOR = PREFIX
      + NORMAL + SEPARATOR + FOREGROUND_GREEN + SUFFIX;
  private static final String DEBUG_COLOR = PREFIX
      + NORMAL + SEPARATOR + FOREGROUND_CYAN + SUFFIX;
  private static final String TRACE_COLOR = PREFIX
      + NORMAL + SEPARATOR + FOREGROUND_BLUE + SUFFIX;

  @Override
  protected void subAppend(LoggingEvent event) {
    this.qw.write(getColor(event.getLevel()));
    super.subAppend(event);
    this.qw.write(END_COLOUR);

    if (this.immediateFlush) {
      this.qw.flush();
    }
  }

  /**
   * Get the appropriate control characters to change the color for the specified logging level.
   *
   * @param level log level.
   * @return color prefix for the level.
   */
  private String getColor(Level level) {
    switch (level.toInt()) {
      case Priority.FATAL_INT:
        return FATAL_COLOR;
      case Priority.ERROR_INT:
        return ERROR_COLOR;
      case Priority.WARN_INT:
        return WARN_COLOR;
      case Priority.INFO_INT:
        return INFO_COLOR;
      case Priority.DEBUG_INT:
        return DEBUG_COLOR;
      default:
        return TRACE_COLOR;
    }
  }
}
