package nl.jerodeveloper.spacecord.bot.framework.player;

import lombok.*;
import nl.jerodeveloper.spacecord.bot.framework.player.location.Location;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity @Table(name = "players")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class Player {

    private float balance;
    private Location location;

}
