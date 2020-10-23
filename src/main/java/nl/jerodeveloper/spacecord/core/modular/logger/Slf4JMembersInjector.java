package nl.jerodeveloper.spacecord.core.modular.logger;

import com.google.inject.MembersInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public class Slf4JMembersInjector<T> implements MembersInjector<T> {

    private final Field field;
    private final Logger logger;

    public Slf4JMembersInjector(Field field) {
        this.field = field;
        this.logger = LoggerFactory.getLogger(field.getDeclaringClass());
        field.setAccessible(true);
    }

    @Override
    public void injectMembers(T t) {
        try {
            field.set(t, logger);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
