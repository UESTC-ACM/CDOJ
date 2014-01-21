package cn.edu.uestc.acmicpc.util.log;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Color-coded console appender for log4j.
 */
public class ANSIConsoleAppender extends ConsoleAppender {

  @Override
  protected void subAppend(LoggingEvent event) {
    this.qw.write(getColor(event.getLevel()));
    super.subAppend(event);
    this.qw.write(LogConstants.END_COLOUR);

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
        return LogConstants.FATAL_COLOR;
      case Priority.ERROR_INT:
        return LogConstants.ERROR_COLOR;
      case Priority.WARN_INT:
        return LogConstants.WARN_COLOR;
      case Priority.INFO_INT:
        return LogConstants.INFO_COLOR;
      case Priority.DEBUG_INT:
        return LogConstants.DEBUG_COLOR;
      default:
        return LogConstants.TRACE_COLOR;
    }
  }
}
