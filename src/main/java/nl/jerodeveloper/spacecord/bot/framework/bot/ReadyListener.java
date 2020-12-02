package nl.jerodeveloper.spacecord.bot.framework.bot;

import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import nl.jerodeveloper.spacecord.bot.BotManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

public class ReadyListener extends ListenerAdapter {

    private final BotManager botManager;
    private final Logger logger;

    public ReadyListener(BotManager botManager) {
        this.botManager = botManager;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        botManager.getModuleLoader().onLoad(() -> {
            event.getJDA().addEventListener(new MessageListener(botManager.getModuleLoader().getCommandHandler()));
        });
    }
}
