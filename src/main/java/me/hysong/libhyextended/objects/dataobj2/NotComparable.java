package me.hysong.libhyextended.objects.dataobj2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
/**
 * This annotation is used to mark a field as not comparable. When using DataObject2.dataFieldEquals(), only fields not marked as not comparable will be compared if the class is marked as comparable.
 * This annotation cannot be used on a class.
 */
public @interface NotComparable {
}
