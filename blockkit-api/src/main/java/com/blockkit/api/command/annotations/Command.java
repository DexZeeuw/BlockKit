package com.blockkit.api.command.annotations;

import java.lang.annotation.*;

/**
 * Marks a class as a top-level command holder.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String value();
    String permission() default "";
    String description() default "";
}
