package nl.jerodeveloper.spacecord.core.commands.execution;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import nl.jerodeveloper.spacecord.core.commands.Command;
import nl.jerodeveloper.spacecord.core.commands.CommandHandler;
import nl.jerodeveloper.spacecord.core.commands.arguments.Argument;
import nl.jerodeveloper.spacecord.core.commands.arguments.ArgumentTranslator;

import java.util.Optional;

public class CommandValidator {

    public static ValidatorResult isCommandValid(Command command, MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || !event.getMessage().getContentRaw().startsWith(CommandHandler.getPrefix())) return new ValidatorResult(false, "Author is a bot", false);

        String message = event.getMessage().getContentRaw();

        String commandName = message.split(" ")[0].replaceFirst(CommandHandler.getPrefix(), "");

        if (!commandName.equalsIgnoreCase(command.name())) return new ValidatorResult(false, String.format("%s does not equal %s", commandName.toLowerCase(), command.name()), false);

        String[] args = message.replaceFirst(CommandHandler.getPrefix(), "").split(" ");

        int requiredArgumentLength = 0;

        for (Argument argument : command.arguments()) {
            if (argument.required()) requiredArgumentLength++;
        }

        if (args.length < requiredArgumentLength) return new ValidatorResult(false, String.format("This command requires %s arguments", requiredArgumentLength), true);

        for (int i = 0; i < args.length; i++) {
            if (i >= command.arguments().length) continue;

            Argument argument = command.arguments()[i];
            String stringArg = args[i];

            Optional<ArgumentTranslator<?>> translatorOptional = ArgumentTranslator.getArgumentTranslator(argument.type());

            if (translatorOptional.isEmpty()) {
                return new ValidatorResult(false, String.format("Could not find a translator for type %s", argument.type().getSimpleName()), true);
            }

            Optional<?> optional = translatorOptional.get().translate(stringArg, i == args.length-1);

            if (optional.isEmpty()) return new ValidatorResult(false, String.format("Argument %s has to be %s", i, translatorOptional.get().typeName()), true);
        }

        return new ValidatorResult(true, "", false);
    }

}
