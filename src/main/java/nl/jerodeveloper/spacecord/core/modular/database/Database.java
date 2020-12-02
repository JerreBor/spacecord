package nl.jerodeveloper.spacecord.core.modular.database;

import lombok.Getter;
import nl.jerodeveloper.spacecord.bot.framework.economy.BuyOrder;
import nl.jerodeveloper.spacecord.bot.framework.economy.Item;
import nl.jerodeveloper.spacecord.bot.framework.economy.SellOffer;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class Database {

    private final String username;
    private final String password;
    private final String url;
    private final String database;
    private final String dialect;
    private final int port;
    private final Logger logger;
    private SessionFactory sessionFactory;

    public Database(String username, String password, String url, String database, String dialect, int port) {
        this.username = username;
        this.password = password;
        this.url = url;
        this.database = database;
        this.dialect = dialect;
        this.port = port;
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void connect() {
        Configuration configuration = new Configuration().configure();
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect." + getDialect());
        configuration.setProperty("hibernate.connection.url", String.format("%s:%s/%s", getUrl(), getPort(), getDatabase()));
        configuration.setProperty("hibernate.connection.username", getUsername());
        configuration.setProperty("hibernate.connection.password", getPassword());
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        configuration.configure();

        configuration.addAnnotatedClass(Item.class);
        configuration.addAnnotatedClass(BuyOrder.class);
        configuration.addAnnotatedClass(SellOffer.class);

        this.sessionFactory = configuration.buildSessionFactory();
    }

}
