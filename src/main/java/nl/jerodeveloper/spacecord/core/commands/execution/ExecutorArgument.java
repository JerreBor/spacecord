package nl.jerodeveloper.spacecord.core.commands.execution;

import com.google.common.collect.ImmutableMap;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import nl.jerodeveloper.spacecord.core.commands.Command;
import nl.jerodeveloper.spacecord.core.commands.execution.arguments.*;

import java.util.Optional;

public interface ExecutorArgument<T> {

    Optional<T> getArgument(Command command, MessageReceivedEvent event);

    ImmutableMap<Class<?>, ExecutorArgument<?>> executorArguments = new ImmutableMap.Builder<Class<?>, ExecutorArgument<?>>()
            .put(User.class, new UserArgument())
            .put(Member.class, new MemberArgument())
            .put(Message.class, new MessageArgument())
            .put(MessageChannel.class, new ChannelArgument())
            .put(ArgumentsArgument.class, new ArgumentsArgument())
            .build();

    static Optional<ExecutorArgument<?>> getExecutorArgument(Class<?> clazz) {
        return Optional.ofNullable(executorArguments.get(clazz));
    }

}
