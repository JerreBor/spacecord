package nl.jerodeveloper.spacecord.core.commands.execution.arguments;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import nl.jerodeveloper.spacecord.core.commands.Command;
import nl.jerodeveloper.spacecord.core.commands.execution.ExecutorArgument;

import java.util.Optional;

public class UserArgument implements ExecutorArgument<User> {

    @Override
    public Optional<User> getArgument(Command command, MessageReceivedEvent event) {
        return Optional.of(event.getMember().getUser());
    }

}
