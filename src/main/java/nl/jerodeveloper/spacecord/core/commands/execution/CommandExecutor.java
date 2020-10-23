package nl.jerodeveloper.spacecord.core.commands.execution;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import nl.jerodeveloper.spacecord.core.commands.Command;
import nl.jerodeveloper.spacecord.core.commands.Executor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CommandExecutor {

    private static final HashMap<Class<?>, Method> commandMethods = new HashMap<>();
    private static final ExecutorService service = Executors.newFixedThreadPool(12);

    public static void executeCommand(Object commandObject, MessageReceivedEvent event) {
        Method method = getCommandMethod(commandObject.getClass());
        LinkedList<ExecutorArgument<?>> executorArguments = getExecutorArguments(method);
        Object[] invokeObjects = new Object[executorArguments.size()];

        for (int i = 0; i < executorArguments.size(); i++) {
            ExecutorArgument<?> executorArgument = executorArguments.get(i);
            invokeObjects[i] = executorArgument.getArgument(commandObject.getClass().getAnnotation(Command.class), event);
        }

        CompletableFuture.runAsync(() -> {
            try {
                method.invoke(commandObject, invokeObjects);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }, service);

    }

    private static Method getCommandMethod(Class<?> commandClass) {
        if (!commandMethods.containsKey(commandClass)) {
            Method commandMethod = null;

            for (Method method : commandClass.getDeclaredMethods()) {
                if (!method.isAnnotationPresent(Executor.class)) continue;
                commandMethod = method;
            }

            if (commandMethod == null) {
                throw new RuntimeException(String.format("Could not find CommandExecutor in class %s", commandClass));
            }

            commandMethods.put(commandClass, commandMethod);
        }

        return commandMethods.get(commandClass);
    }

    private static LinkedList<ExecutorArgument<?>> getExecutorArguments(Method executorMethod) {
        LinkedList<ExecutorArgument<?>> executorArguments = new LinkedList<>();

        for (Class<?> parameterType : executorMethod.getParameterTypes()) {
            Optional<ExecutorArgument<?>> executorArgument = ExecutorArgument.getExecutorArgument(parameterType);

            if (executorArgument.isPresent()) {
                executorArguments.add(executorArgument.get());
                continue;
            }
            executorArguments.add(null);
        }

        return executorArguments;
    }

}
