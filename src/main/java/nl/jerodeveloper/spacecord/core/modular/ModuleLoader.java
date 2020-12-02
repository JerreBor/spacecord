package nl.jerodeveloper.spacecord.core.modular;

import com.google.inject.Injector;
import com.netflix.governator.guice.LifecycleInjector;
import com.netflix.governator.lifecycle.ClasspathScanner;
import com.netflix.governator.lifecycle.LifecycleManager;
import lombok.Getter;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import nl.jerodeveloper.spacecord.bot.BotManager;
import nl.jerodeveloper.spacecord.core.modular.database.DatabaseModule;
import nl.jerodeveloper.spacecord.core.commands.Command;
import nl.jerodeveloper.spacecord.core.commands.CommandHandler;
import nl.jerodeveloper.spacecord.core.modular.dependencies.Dependencies;
import nl.jerodeveloper.spacecord.core.modular.dependencies.Dependency;
import nl.jerodeveloper.spacecord.core.modular.listeners.Listener;
import nl.jerodeveloper.spacecord.core.modular.timers.Timer;

import java.util.*;
import java.util.stream.Collectors;

public class ModuleLoader {

    private final BotManager botManager;
    @Getter private CommandHandler commandHandler;
    private final Injector injector;

    // Sorting
    private final Map<Class<? extends Module>, List<Class<? extends Module>>> modulesAndDependencies;
    private final Set<Class<?>> moduleClasses;
    private final List<Class<? extends Module>> visited;
    private final LinkedList<Class<? extends Module>> sortedList;

    // Scanner
    private final Set<Class<?>> scannedClasses;

    // Modules
    private final Map<Class<? extends Module>, Module> moduleMap;

    // On Load
    private final LinkedList<Runnable> onLoad;

    // Timer
    private final java.util.Timer timerScheduler;

    public ModuleLoader(BotManager botManager) throws Exception {
        this.botManager = botManager;

        ClasspathScanner scanner = new ClasspathScanner(Collections.singletonList("nl.jerodeveloper.spacecord.bot"),
                Arrays.asList(Modules.class, Listener.class, Timer.class, Command.class));

        this.scannedClasses = scanner.getClasses();

        this.moduleClasses = scannedClasses.stream().filter(Module.class::isAssignableFrom).collect(Collectors.toSet());
        this.commandHandler = new CommandHandler(new HashMap<>());

        this.injector = LifecycleInjector.builder()
                .withModules(new DefaultModule(botManager), new DatabaseModule())
                .usingClasspathScanner(scanner)
                .build()
                .createInjector();

/*        injector.getMembersInjector(CommandHandler.class).injectMembers(commandHandler);
        injector.getMembersInjector(JDA.class).injectMembers(botManager.getJda());
        injector.getMembersInjector(java.util.Timer.class).injectMembers(timer);*/

        LifecycleManager lifecycleManager = injector.getInstance(LifecycleManager.class);

        this.modulesAndDependencies = new HashMap<>();
        this.visited = new ArrayList<>();
        this.sortedList = new LinkedList<>();

        this.moduleMap = new LinkedHashMap<>();

        this.onLoad = new LinkedList<>();

        this.timerScheduler = new java.util.Timer();

        injector.injectMembers(this);

        lifecycleManager.start();
    }

    public void load() {
        for (Class<?> module : moduleClasses) {
            Class<? extends Module> moduleClass = (Class<? extends Module>) module;
            if (!module.isAnnotationPresent(Dependencies.class)) {
                modulesAndDependencies.put(moduleClass, Collections.emptyList());
                continue;
            }

            Dependencies annotation = module.getAnnotation(Dependencies.class);
            Dependency[] dependencies = annotation.value();
            List<Class<? extends Module>> moduleDependencies = new ArrayList<>();

            for (Dependency dependency : dependencies) {
                if (dependency.value().equals(module)) continue;

                moduleDependencies.add(dependency.value());
            }

            modulesAndDependencies.put(moduleClass, moduleDependencies);
        }

        for (Map.Entry<Class<? extends Module>, List<Class<? extends Module>>> entry : modulesAndDependencies.entrySet()) {
            Class<? extends Module> module = entry.getKey();

            if (visited.contains(module)) continue;

            sort(module, entry.getValue());
        }

        for (Class<? extends Module> moduleClass : sortedList) {
            injector.injectMembers(moduleClass);
            Module module = injector.getInstance(moduleClass);
            moduleMap.put(moduleClass, module);
            module.onLoad();

            for (Class<?> clazz : scannedClasses.stream().filter(aClass -> !Module.class.isAssignableFrom(aClass)).collect(Collectors.toSet())) {
                Object object = injector.getInstance(clazz);
                if (clazz.isAnnotationPresent(Command.class)) {

                    Command command = clazz.getAnnotation(Command.class);

                    if (command.module().equals(moduleClass)) {
                        commandHandler.addCommand(object);
                    }
                } else if (clazz.isAnnotationPresent(Listener.class)) {
                    if (!ListenerAdapter.class.isAssignableFrom(clazz)) {
                        throw new IllegalStateException("Listener " + clazz + " has Listener annotation but doesn't extend ListenerAdapter");
                    }

                    Listener listener = clazz.getAnnotation(Listener.class);

                    if (listener.module().equals(moduleClass)) {
                        botManager.getJda().addEventListener(object);
                    }
                } else if (clazz.isAnnotationPresent(Timer.class)) {
                    if (!TimerTask.class.isAssignableFrom(clazz)) {
                        throw new IllegalStateException("Timer " + clazz + " has Timer annotation but doesn't extend TimerTask");
                    }

                    Timer timer = clazz.getAnnotation(Timer.class);

                    if (timer.module().equals(moduleClass)) {
                        timerScheduler.schedule((TimerTask) object, timer.delay(), timer.interval());
                    }
                }
                injector.injectMembers(object);
            }
        }

        onLoad.forEach(Runnable::run);
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

    public void disable() {

    }

    public void onLoad(Runnable onLoad) {
        this.onLoad.add(onLoad);
    }

    public <T extends Module> T getModule(Class<T> moduleClass) {
        return (T) moduleMap.get(moduleClass);
    }

}
