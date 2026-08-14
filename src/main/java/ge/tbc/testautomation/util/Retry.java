package ge.tbc.testautomation.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Retry {
    int count() default 0;
}