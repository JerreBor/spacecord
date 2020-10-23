package nl.jerodeveloper.spacecord.core.commands.arguments;

import com.google.common.collect.ImmutableMap;
import nl.jerodeveloper.spacecord.core.commands.arguments.translators.BooleanTranslator;
import nl.jerodeveloper.spacecord.core.commands.arguments.translators.IntegerTranslator;
import nl.jerodeveloper.spacecord.core.commands.arguments.translators.StringTranslator;

import java.util.Optional;

public interface ArgumentTranslator<T> {

    String typeName();
    Optional<T> translate(String source, boolean isLast);

    ImmutableMap<Class<?>, ArgumentTranslator<?>> translators = new ImmutableMap.Builder<Class<?>, ArgumentTranslator<?>>()
            .put(String.class, new StringTranslator())
            .put(Integer.class, new IntegerTranslator())
            .put(Boolean.class, new BooleanTranslator())
            .build();

    static Optional<ArgumentTranslator<?>> getArgumentTranslator(Class<?> clazz) {
        return Optional.ofNullable(translators.get(clazz));
    }

}
