package com.github.tozymc.tchelper.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field to be targeted by the object mapper, with custom node path.
 *
 * @see org.spongepowered.configurate.objectmapping.meta.NodeResolver
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NodePath {
  String value();
}
