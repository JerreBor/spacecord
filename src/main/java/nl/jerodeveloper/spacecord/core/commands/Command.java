package nl.jerodeveloper.spacecord.core.commands;

import nl.jerodeveloper.spacecord.core.commands.arguments.Argument;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Command {

    String name();
    String description();
    Argument[] arguments() default {};

}
