package nl.jerodeveloper.spacecord.core.commands.arguments.translators;

import nl.jerodeveloper.spacecord.core.commands.arguments.ArgumentTranslator;

import java.security.Permission;
import java.util.Optional;

public class IntegerTranslator implements ArgumentTranslator<Integer> {

    @Override
    public String typeName() {
        return "number";
    }

    @Override
    public Optional<Integer> translate(String source, boolean isLast) {
        try {
            return Optional.of(Integer.parseInt(source));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
