package com.vincentcodes.json.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * This annotation is used to indicate a class is serializable
 * into json and deserializable from json.
 * <p>
 * Configuration is needed to enable this annotation to force
 * the ObjectMapper to check for the presence of this annotation.
 * ObjectMapper will only check for the presence of this
 * annotation on the first class, since it assumes that the dev.
 * has acknowledged the properties inside the class are
 * serializable.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface JsonSerializable {
}
