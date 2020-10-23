package nl.jerodeveloper.spacecord.core.commands.execution.arguments;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import nl.jerodeveloper.spacecord.core.commands.Command;
import nl.jerodeveloper.spacecord.core.commands.execution.ExecutorArgument;

import java.util.Optional;

public class ChannelArgument implements ExecutorArgument<MessageChannel> {

    @Override
    public Optional<MessageChannel> getArgument(Command command, MessageReceivedEvent event) {
        return Optional.of(event.getChannel());
    }

}
