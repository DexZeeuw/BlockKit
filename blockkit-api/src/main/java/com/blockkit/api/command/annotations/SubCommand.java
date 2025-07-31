package com.blockkit.api.command.annotations;

import java.lang.annotation.*;

/**
 * Marks a method as a subcommand handler.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubCommand {
    String[] value();
    String permission() default "";
    String description() default "";
}
