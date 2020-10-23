package nl.jerodeveloper.spacecord.core.commands.execution;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ValidatorResult {

    @Getter private final boolean valid;
    @Getter private final String context;

}
