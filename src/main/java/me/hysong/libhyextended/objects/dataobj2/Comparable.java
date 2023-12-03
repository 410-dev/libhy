package me.hysong.libhyextended.objects.dataobj2;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value = RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
/**
 * This annotation is used to mark a field as comparable. When using DataObject2.dataFieldEquals(), only fields marked as comparable will be compared.
 * If a class is marked as comparable, all fields will be compared.
 */
public @interface Comparable { }
