package nl.jerodeveloper.spacecord.bot.framework.bot;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import nl.jerodeveloper.spacecord.core.modular.ModuleLoader;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadyListener extends ListenerAdapter {

    private final ModuleLoader moduleLoader;
    private final Logger logger;

    public ReadyListener(ModuleLoader moduleLoader) {
        this.moduleLoader = moduleLoader;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        logger.info("Enabling modules...");
        moduleLoader.enable(event.getJDA());

        event.getJDA().addEventListener(new MessageListener(moduleLoader.getCommandHandler()));
    }
}
