package nl.jerodeveloper.spacecord.bot.modules.economy;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.Getter;
import nl.jerodeveloper.spacecord.core.modular.database.Database;
import nl.jerodeveloper.spacecord.bot.framework.economy.Item;
import nl.jerodeveloper.spacecord.bot.framework.economy.noise.NoiseFactor;
import nl.jerodeveloper.spacecord.bot.framework.economy.threads.UpdatePriceTimer;
import nl.jerodeveloper.spacecord.core.modular.Module;
import nl.jerodeveloper.spacecord.core.modular.Modules;
import nl.jerodeveloper.spacecord.core.modular.injectors.logger.InjectLogger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Modules
public class EconomyModule extends Module {

    @Getter private List<Item> items;

    private final Database database;
    @InjectLogger private Logger logger;

    @Inject
    public EconomyModule(Database database) {
        this.items = new ArrayList<>();
        this.database = database;
    }

    @PostConstruct
    private void postConstruct() {
        loadItems();

        Session session = database.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Item item = new Item("Diamonds", NoiseFactor.SLIGHTLY_VOLATILE, "A highly valuable ore");
        session.save(item);
        transaction.commit();
        session.close();

        new UpdatePriceTimer(this);
    }

    private void loadItems() {
        Session session = database.getSessionFactory().openSession();

        Query query = session.createQuery("SELECT i FROM Item i");
        items = query.getResultList();

        logger.info("Loaded {} items", items.size());

        session.close();
    }

}
