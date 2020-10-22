package nl.jerodeveloper.spacecord.core.commands.arguments.translators;

import nl.jerodeveloper.spacecord.core.commands.arguments.ArgumentTranslator;

import java.util.Optional;

public class BooleanTranslator implements ArgumentTranslator<Boolean> {

    @Override
    public String typeName() {
        return "yes/no";
    }

    @Override
    public Optional<Boolean> translate(String source, boolean isLast) {
        return switch (source) {
            case "true", "yes" -> Optional.of(true);
            case "false", "no" -> Optional.of(false);
            default -> Optional.empty();
        };
    }
}
