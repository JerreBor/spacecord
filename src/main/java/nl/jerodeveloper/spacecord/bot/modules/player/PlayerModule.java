package nl.jerodeveloper.spacecord.bot.modules.player;

import nl.jerodeveloper.spacecord.bot.modules.economy.EconomyModule;
import nl.jerodeveloper.spacecord.core.modular.Module;
import nl.jerodeveloper.spacecord.core.modular.Modules;
import nl.jerodeveloper.spacecord.core.modular.dependencies.Dependencies;
import nl.jerodeveloper.spacecord.core.modular.dependencies.Dependency;
import nl.jerodeveloper.spacecord.core.modular.injectors.logger.InjectLogger;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;

@Dependencies({@Dependency(EconomyModule.class)})
@Modules
public class PlayerModule extends Module {

    @InjectLogger private Logger logger;

    @PostConstruct
    private void postConstruct() {

    }

}
