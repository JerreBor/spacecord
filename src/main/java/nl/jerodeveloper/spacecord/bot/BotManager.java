package nl.jerodeveloper.spacecord.bot;

import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import nl.jerodeveloper.spacecord.bot.framework.bot.ReadyListener;
import nl.jerodeveloper.spacecord.core.config.Configuration;
import nl.jerodeveloper.spacecord.core.modular.ModuleLoader;
import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class BotManager {

    @Getter private JDA jda;
    @Getter private final ModuleLoader moduleLoader;
    @Getter private final Logger logger;
    @Getter private final Configuration configuration;

    public BotManager() {
        this.moduleLoader = new ModuleLoader(this);
        this.logger = LoggerFactory.getLogger(getClass());
        this.configuration = new Configuration();
    }

    public void load() throws LoginException, IOException, ConfigurationException {
        configuration.loadSettings();

        this.jda = JDABuilder
                .createDefault(configuration.getString("bot.token"))
                .addEventListeners(new ReadyListener(moduleLoader))
                .build();

        getLogger().info("Loading modules...");
        moduleLoader.load();
    }

}
