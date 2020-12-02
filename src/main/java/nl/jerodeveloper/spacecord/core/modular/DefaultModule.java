package nl.jerodeveloper.spacecord.core.modular;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import net.dv8tion.jda.api.JDA;
import nl.jerodeveloper.spacecord.bot.BotManager;
import nl.jerodeveloper.spacecord.core.config.Configuration;
import nl.jerodeveloper.spacecord.core.modular.injectors.logger.Slf4JTypeListener;

public class DefaultModule extends AbstractModule {

    private final BotManager botManager;

    public DefaultModule(BotManager botManager) {
        this.botManager = botManager;
    }

    @Override
    protected void configure() {
        bind(JDA.class).toProvider(botManager::getJda);
        bind(Configuration.class).toProvider(botManager::getConfiguration);
        bindListener(Matchers.any(), new Slf4JTypeListener());
    }
}
