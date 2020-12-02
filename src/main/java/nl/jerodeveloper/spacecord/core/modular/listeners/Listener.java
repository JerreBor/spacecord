package nl.jerodeveloper.spacecord.core.modular.listeners;

import nl.jerodeveloper.spacecord.core.modular.Module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Listener {

    Class<? extends Module> module();

}
