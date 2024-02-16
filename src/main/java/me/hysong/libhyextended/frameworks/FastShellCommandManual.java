package me.hysong.libhyextended.frameworks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
/**
 * This annotation is used to mark a method as a FastShell command help manual.
 */
public @interface FastShellCommandManual {
    String value();
}
