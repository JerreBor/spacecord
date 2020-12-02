package nl.jerodeveloper.spacecord.bot.framework.economy.threads;

import com.google.inject.Inject;
import nl.jerodeveloper.spacecord.bot.modules.economy.EconomyModule;
import nl.jerodeveloper.spacecord.core.modular.timers.ModulePhase;
import nl.jerodeveloper.spacecord.core.modular.timers.Timer;

import java.util.TimerTask;

@Timer(interval = 10000, delay = 10000, module = EconomyModule.class, phase = ModulePhase.ENABLE)
public class UpdatePriceTimer extends TimerTask {

    private final EconomyModule module;

    @Inject
    public UpdatePriceTimer(EconomyModule economyModule) {
        this.module = economyModule;
    }

    @Override
    public void run() {

    }

}
