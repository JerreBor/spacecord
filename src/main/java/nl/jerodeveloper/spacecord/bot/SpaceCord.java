package nl.jerodeveloper.spacecord.bot;

import lombok.Getter;
import org.apache.commons.configuration.ConfigurationException;

import javax.security.auth.login.LoginException;
import java.io.IOException;

public class SpaceCord {

    @Getter private static BotManager botManager;

    public static void main(String[] args) throws LoginException, IOException, ConfigurationException {
        botManager = new BotManager();

        getBotManager().load();
    }

}
