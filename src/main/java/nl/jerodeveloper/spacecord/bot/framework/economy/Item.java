package nl.jerodeveloper.spacecord.bot.framework.economy;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.jerodeveloper.spacecord.bot.framework.economy.noise.NoiseFactor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity @Table(name = "items")
@NoArgsConstructor
@Getter @Setter
public class Item {

    @Id
    @GenericGenerator(name = "seed", strategy = "nl.jerodeveloper.spacecord.bot.framework.economy.generator.SeedGenerator")
    @GeneratedValue(generator = "seed", strategy = GenerationType.IDENTITY)
    private double seed;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private NoiseFactor noiseFactor;
    @OneToMany(mappedBy = "item")
    private List<BuyOrder> buyOrders;
    @OneToMany(mappedBy = "item")
    private List<SellOffer> sellOffers;

    public Item(String name, NoiseFactor noiseFactor, String description) {
        this.name = name;
        this.noiseFactor = noiseFactor;
        this.description = description;
    }

}
