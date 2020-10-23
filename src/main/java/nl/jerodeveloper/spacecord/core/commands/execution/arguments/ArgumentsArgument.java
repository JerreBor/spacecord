package nl.jerodeveloper.spacecord.core.commands.execution.arguments;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import nl.jerodeveloper.spacecord.core.commands.Command;
import nl.jerodeveloper.spacecord.core.commands.CommandHandler;
import nl.jerodeveloper.spacecord.core.commands.arguments.Argument;
import nl.jerodeveloper.spacecord.core.commands.arguments.Arguments;
import nl.jerodeveloper.spacecord.core.commands.execution.ExecutorArgument;

import java.util.Arrays;
import java.util.Optional;

public class ArgumentsArgument implements ExecutorArgument<Arguments> {

    @Override
    public Optional<Arguments> getArgument(Command command, MessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();

        String[] args = message.replaceFirst(CommandHandler.getPrefix(), "").split(" ");
        args = Arrays.copyOfRange(args, 1, args.length);

        if (command.arguments().length == 0) {
            return Optional.of(new Arguments(args, new Argument[0]));
        }

        return Optional.of(new Arguments(args, Arrays.copyOfRange(command.arguments(), 0, args.length)));
    }

}
