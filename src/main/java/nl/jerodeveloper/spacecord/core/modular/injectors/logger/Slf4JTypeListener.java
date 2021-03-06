package nl.jerodeveloper.spacecord.core.modular.injectors.logger;

import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.slf4j.Logger;

import java.lang.reflect.Field;

public class Slf4JTypeListener implements TypeListener {

    @Override
    public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
        Class<?> clazz = typeLiteral.getRawType();
        while (clazz != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getType() == Logger.class && field.isAnnotationPresent(InjectLogger.class)) {
                    typeEncounter.register(new Slf4JMembersInjector<I>(field));
                }
            }
            clazz = clazz.getSuperclass();
        }
    }

}
