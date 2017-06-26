package cn.edu.uestc.acmicpc.db.dto;

import com.google.common.collect.ImmutableSet;
import java.util.Set;

/**
 * Utility class for {@link Fields} related operations.
 */
public class FieldsUtil {

  private FieldsUtil() {
  }

  public static Set<String> getFieldNamesByFieldSet(Set<? extends Fields> fields) {
    ImmutableSet.Builder<String> builder = ImmutableSet.builder();
    for (Fields field : fields) {
      builder.add(field.getProjection().getField());
    }
    return builder.build();
  }
}
