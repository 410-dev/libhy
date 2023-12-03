package me.hysong.libhyextended.objects.dataobj2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
/**
 * This annotation is used to mark a field as not JSONCodable. When using DataObject2.toJson() or anything similar, only fields not marked as not JSONCodable will be included if the class is marked as JSONCodable.
 * This annotation cannot be used on a class.
 */
public @interface NotJSONCodable {
}
