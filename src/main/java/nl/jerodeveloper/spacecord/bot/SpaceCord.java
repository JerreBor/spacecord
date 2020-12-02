package nl.jerodeveloper.spacecord.bot;

import lombok.Getter;

public class SpaceCord {

    @Getter private static BotManager botManager;

    public static void main(String[] args) throws Exception {
        System.setProperty("org.jboss.logging.provider", "slf4j");

        botManager = new BotManager();

        getBotManager().load();
    }

}
