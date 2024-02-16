package me.hysong.libhyextended.frameworks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
/**
 * This annotation is used to mark a method as a FastShell command.
 */
public @interface FastShellSDKSpec {
    int sdkVersion();
}
