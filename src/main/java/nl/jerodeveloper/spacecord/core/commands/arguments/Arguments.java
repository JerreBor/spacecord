package nl.jerodeveloper.spacecord.core.commands.arguments;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor
public class Arguments {

    @Getter private final String[] stringArgs;
    @Getter private final Argument[] arguments;

    public <T> Optional<T> getByName(Class<T> type, String name) {
        int argumentIndex = -1;

        for (int i = 0; i < arguments.length; i++) {
            Argument argument = arguments[i];
            if (argument.name().equalsIgnoreCase(name)) {
                argumentIndex = i;
                break;
            }
        }

        if (argumentIndex == -1) return Optional.empty();

        if (argumentIndex >= stringArgs.length) return Optional.empty();

        Optional<ArgumentTranslator<?>> optionalArgumentTranslator = ArgumentTranslator.getArgumentTranslator(type);

        if (optionalArgumentTranslator.isEmpty()) {
            return Optional.empty();
        }

        return (Optional<T>) optionalArgumentTranslator.get().translate(stringArgs[argumentIndex], argumentIndex == stringArgs.length-1);
    }

    public <T> Optional<T> getByIndex(Class<T> type, int index) {
        if (index >= stringArgs.length) return Optional.empty();

        Optional<ArgumentTranslator<?>> optionalArgumentTranslator = ArgumentTranslator.getArgumentTranslator(type);

        if (optionalArgumentTranslator.isEmpty()) {
            return Optional.empty();
        }

        return (Optional<T>) optionalArgumentTranslator.get().translate(stringArgs[index], index == stringArgs.length-1);
    }

}
