package nl.jerodeveloper.spacecord.core.modular.dependencies;

import nl.jerodeveloper.spacecord.core.modular.Module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Dependency {

    Class<? extends Module> value();

}
