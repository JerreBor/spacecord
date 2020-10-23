package nl.jerodeveloper.spacecord.bot.framework.bot;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import nl.jerodeveloper.spacecord.bot.framework.util.EmbedHelper;
import nl.jerodeveloper.spacecord.core.commands.Command;
import nl.jerodeveloper.spacecord.core.commands.CommandHandler;
import nl.jerodeveloper.spacecord.core.commands.execution.CommandValidator;
import nl.jerodeveloper.spacecord.core.commands.execution.ValidatorResult;
import org.jetbrains.annotations.NotNull;

public class MessageListener extends ListenerAdapter {

    private final CommandHandler commandHandler;

    public MessageListener(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        TextChannel textChannel = event.getTextChannel();

        for (Class<?> commandClass : commandHandler.getCommands().keySet()) {
            Command command = commandHandler.getCommandAnnotation(commandClass);
            ValidatorResult result = CommandValidator.isCommandValid(command, event);

            if (result.isValid()) {
                commandHandler.runCommand(commandClass, event);
            } else {
                sendHelpEmbed(textChannel, "Something went wrong", result.getContext());
            }
        }
    }

    private void sendHelpEmbed(TextChannel channel, String title, String description) {
        channel.sendMessage(EmbedHelper.getErrorEmbed(title, description)).queue();
    }

}
