package nl.jerodeveloper.spacecord.core.modular;

import com.google.inject.Inject;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import nl.jerodeveloper.spacecord.bot.BotManager;

@Getter
public abstract class Module {

    @Inject private JDA jda;
    @Inject private BotManager botManager;

    protected void onEnable() {}
    protected void onLoad() {}
    protected void onDisable() {}

}
