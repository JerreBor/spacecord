package nl.jerodeveloper.spacecord.core.commands.arguments;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Argument {

    String name();
    String description();
    boolean required();
    Class<?> type();

}
