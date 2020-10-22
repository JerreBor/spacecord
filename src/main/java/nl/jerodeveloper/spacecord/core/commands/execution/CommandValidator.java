package nl.jerodeveloper.spacecord.core.commands.execution;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import nl.jerodeveloper.spacecord.core.commands.Command;
import nl.jerodeveloper.spacecord.core.commands.CommandHandler;
import nl.jerodeveloper.spacecord.core.commands.arguments.Argument;
import nl.jerodeveloper.spacecord.core.commands.arguments.ArgumentTranslator;

import java.util.Optional;

public class CommandValidator {

    public static InvalidReason isCommandValid(Command command, MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || !event.getMessage().getContentRaw().startsWith(CommandHandler.getPrefix())) return InvalidReason.AUTHOR_IS_BOT;

        String message = event.getMessage().getContentRaw();

        String commandName = message.split(" ")[0].replaceFirst(CommandHandler.getPrefix(), "");

        if (!commandName.equalsIgnoreCase(command.name())) return InvalidReason.NAME_NOT_EQUAL;

        String[] args = message.replaceFirst(CommandHandler.getPrefix(), "").split(" ");

        int requiredArgumentLength = 0;

        for (Argument argument : command.arguments()) {
            if (argument.required()) requiredArgumentLength++;
        }

        if (args.length < requiredArgumentLength) return InvalidReason.TOO_FEW_ARGUMENTS;

        for (int i = 0; i < args.length; i++) {
            if (i >= command.arguments().length) continue;

            Argument argument = command.arguments()[i];
            String stringArg = args[i];

            Optional<ArgumentTranslator<?>> translatorOptional = ArgumentTranslator.getArgumentTranslator(argument.type());

            if (translatorOptional.isEmpty()) {
                return InvalidReason.NO_TRANSLATOR_FOUND;
            }

            Optional<?> optional = translatorOptional.get().translate(stringArg, i == args.length-1);

            if (optional.isEmpty()) return InvalidReason.COULD_NOT_TRANSLATE;
        }

        return InvalidReason.NONE;
    }

}
