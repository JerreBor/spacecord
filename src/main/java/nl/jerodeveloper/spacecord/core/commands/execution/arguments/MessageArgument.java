package nl.jerodeveloper.spacecord.core.commands.execution.arguments;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import nl.jerodeveloper.spacecord.core.commands.Command;
import nl.jerodeveloper.spacecord.core.commands.execution.ExecutorArgument;

import java.util.Optional;

public class MessageArgument implements ExecutorArgument<Message> {

    @Override
    public Optional<Message> getArgument(Command command, MessageReceivedEvent event) {
        return Optional.of(event.getMessage());
    }

}
