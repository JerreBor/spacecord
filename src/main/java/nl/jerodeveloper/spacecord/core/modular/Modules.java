package nl.jerodeveloper.spacecord.core.modular;

import com.netflix.governator.annotations.AutoBindSingleton;

import javax.inject.Singleton;
import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@AutoBindSingleton(multiple = true, baseClass = Module.class)
@Inherited
@Singleton
public @interface Modules {
}
