package nl.jerodeveloper.spacecord.bot.framework.economy.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Random;

public class SeedGenerator implements IdentifierGenerator {

    private final Random random;

    public SeedGenerator() {
        this.random = new Random();
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return random.nextGaussian() * 255;
    }
}
