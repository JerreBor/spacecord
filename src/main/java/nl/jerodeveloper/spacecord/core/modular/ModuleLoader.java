package nl.jerodeveloper.spacecord.core.modular;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import nl.jerodeveloper.spacecord.bot.BotManager;
import nl.jerodeveloper.spacecord.core.commands.Command;
import nl.jerodeveloper.spacecord.core.commands.CommandHandler;
import nl.jerodeveloper.spacecord.core.modular.dependencies.Dependencies;
import nl.jerodeveloper.spacecord.core.modular.dependencies.Dependency;
import nl.jerodeveloper.spacecord.core.modular.listeners.Listener;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.util.*;

public class ModuleLoader {

    private final Reflections reflections;
    private final Injector injector;
    private final JDA jda;
    @Getter private CommandHandler commandHandler;

    // Sorting
    private final Map<Class<? extends Module>, List<Class<? extends Module>>> modulesAndDependencies;
    private final Set<Class<? extends Module>> moduleClasses;
    private final List<Class<? extends Module>> visited;
    private final LinkedList<Class<? extends Module>> sortedList;

    public ModuleLoader(BotManager botManager) {
        this.reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("nl.jerodeveloper.spacecord.bot.modules"))
                .setScanners(new SubTypesScanner(), new TypeAnnotationsScanner())
        );

        this.injector = Guice.createInjector(new DefaultModule(botManager));
        this.jda = botManager.getJda();

        this.modulesAndDependencies = new HashMap<>();
        this.moduleClasses = reflections.getSubTypesOf(Module.class);
        this.visited = new ArrayList<>();
        this.sortedList = new LinkedList<>();
    }

    public void load() {
        for (Class<? extends Module> module : moduleClasses) {
            if (!module.isAnnotationPresent(Dependencies.class)) {
                modulesAndDependencies.put(module, Collections.emptyList());
                continue;
            }

            Dependencies annotation = module.getAnnotation(Dependencies.class);
            Dependency[] dependencies = annotation.value();
            List<Class<? extends Module>> moduleDependencies = new ArrayList<>();

            for (Dependency dependency : dependencies) {
                if (dependency.value() == module) continue;

                moduleDependencies.add(dependency.value());
            }

            modulesAndDependencies.put(module, moduleDependencies);
        }

        for (Map.Entry<Class<? extends Module>, List<Class<? extends Module>>> entry : modulesAndDependencies.entrySet()) {
            Class<? extends Module> module = entry.getKey();

            if (visited.contains(module)) continue;

            List<Class<? extends Module>> dependencies = entry.getValue();

            sort(module, entry.getValue());
        }

        for (Class<? extends Module> moduleClass : sortedList) {
            injector.injectMembers(moduleClass);
            Module module = injector.getInstance(moduleClass);
            module.onLoad();
        }
    }

    private void sort(Class<? extends Module> toSort, List<Class<? extends Module>> dependencies) {
        visited.add(toSort);

        for (Class<? extends Module> dependency : dependencies) {
            if (!visited.contains(dependency)) {
                sort(dependency, modulesAndDependencies.get(dependency));
            }
        }

        sortedList.add(toSort);
    }

    public void enable(JDA jda) {
        CommandHandler.Builder handlerBuilder = new CommandHandler.Builder();

        // Register commands
        for (Class<?> commandClass : reflections.getTypesAnnotatedWith(Command.class)) {
            injector.injectMembers(commandClass);
            handlerBuilder.addCommand(injector.getInstance(commandClass));
        }

        // Register listeners
        for (Class<?> listenerClass : reflections.getTypesAnnotatedWith(Listener.class)) {
            if (!ListenerAdapter.class.isAssignableFrom(listenerClass)) {
                throw new IllegalStateException("Listener " + listenerClass + " has Listener annotation but doesn't extend ListenerAdapter");
            }

            injector.injectMembers(listenerClass);
            jda.addEventListener(injector.getInstance(listenerClass));
        }

        for (Class<? extends Module> moduleClass : reflections.getSubTypesOf(Module.class)) {

            Module module = injector.getInstance(moduleClass);
            module.onEnable();
        }

        this.commandHandler = handlerBuilder.build();
    }

}
