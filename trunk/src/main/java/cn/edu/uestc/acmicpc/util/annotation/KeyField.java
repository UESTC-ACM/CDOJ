package cn.edu.uestc.acmicpc.util.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Key field annotation for database entity.
 * 
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyField {
	public String value();
}
