package nl.jerodeveloper.spacecord.core.commands;

import lombok.Getter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import nl.jerodeveloper.spacecord.core.commands.execution.CommandExecutor;

import java.util.HashMap;

public class CommandHandler {

    @Getter private final HashMap<Class<?>, Object> commands;
    @Getter private static final String prefix = "space!";

    public CommandHandler(HashMap<Class<?>, Object> commands) {
        this.commands = commands;
    }

    public Command getCommandAnnotation(Class<?> commandClass) {
        if (!commands.containsKey(commandClass) || !commandClass.isAnnotationPresent(Command.class)) {
            return null;
        }

        return commands.get(commandClass).getClass().getAnnotation(Command.class);
    }

    public void runCommand(Class<?> command, MessageReceivedEvent event) {
        CommandExecutor.executeCommand(commands.get(command), event);
    }

    public void addCommand(Object object) {
        if (!object.getClass().isAnnotationPresent(Command.class)) {
            throw new IllegalStateException(String.format("Command with class %s does not have Command annotation", object.getClass()));
        }

        commands.put(object.getClass(), object);
    }

}
