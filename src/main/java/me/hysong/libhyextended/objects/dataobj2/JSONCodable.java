package me.hysong.libhyextended.objects.dataobj2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
/**
 * This annotation is used to mark a field as JSONCodable. When using DataObject2.toJson() or anything similar, only fields marked as JSONCodable will be included.
 * If a class is marked as JSONCodable, all fields will be included.
 */
public @interface JSONCodable {
    JSONCodableAction[] codableOn() default JSONCodableAction.ALL;
}
