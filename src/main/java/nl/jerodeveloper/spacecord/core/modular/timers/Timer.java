package nl.jerodeveloper.spacecord.core.modular.timers;

import com.netflix.governator.guice.lazy.LazySingleton;
import nl.jerodeveloper.spacecord.core.modular.Module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@LazySingleton
public @interface Timer {

    long interval();
    long delay();
    Class<? extends Module> module();
    ModulePhase phase();

}
