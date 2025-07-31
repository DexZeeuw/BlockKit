package com.blockkit.api.command.annotations;

import java.lang.annotation.*;

/**
 * Marks a method as a tab-completion provider.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Completer {
    String[] value();
}
