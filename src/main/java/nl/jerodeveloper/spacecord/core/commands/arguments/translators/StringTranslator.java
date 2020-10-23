package nl.jerodeveloper.spacecord.core.commands.arguments.translators;

import nl.jerodeveloper.spacecord.core.commands.arguments.ArgumentTranslator;

import java.util.Optional;

public class StringTranslator implements ArgumentTranslator<String> {

    @Override
    public String typeName() {
        return "word";
    }

    @Override
    public Optional<String> translate(String source, boolean isLast) {
        return Optional.of(source);
    }

}
