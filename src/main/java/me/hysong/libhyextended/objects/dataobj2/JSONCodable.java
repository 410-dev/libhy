package me.hysong.libhyextended.objects.dataobj2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface JSONCodable {
    JSONCodableAction[] codableOn() default JSONCodableAction.ALL;
}
