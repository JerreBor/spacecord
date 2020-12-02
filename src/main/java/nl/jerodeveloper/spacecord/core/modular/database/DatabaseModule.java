package nl.jerodeveloper.spacecord.core.modular.database;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import nl.jerodeveloper.spacecord.core.config.Configuration;

@Singleton
public class DatabaseModule extends AbstractModule {

    @Provides
    @Singleton
    public Database getDatabase(Configuration configuration) {
        Database database = new Database(
                configuration.getString("database.user"),
                configuration.getString("database.password"),
                configuration.getString("database.url"),
                configuration.getString("database.database"),
                configuration.getString("database.dialect"),
                configuration.getInt("database.port")
        );

        database.connect();

        return database;
    }

}
