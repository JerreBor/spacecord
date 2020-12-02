package nl.jerodeveloper.spacecord.bot.framework.economy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity @Table(name = "buy_orders")
@NoArgsConstructor
@Getter @Setter
public class BuyOrder {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID uuid;
    @ManyToOne
    @JoinColumn
    private Item item;
    @Column
    private int amount;
    @Column(name = "price_per_unit")
    private double pricePerUnit;

    public BuyOrder(Item item, int amount, double pricePerUnit) {
        this.item = item;
        this.amount = amount;
        this.pricePerUnit = pricePerUnit;
    }

}
