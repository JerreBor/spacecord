package nl.jerodeveloper.spacecord.bot.framework.util;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.time.Instant;

public class EmbedHelper {

    public static String FOOTER = "© SpaceCord 2020";

    public static MessageEmbed getSuccessEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setFooter(FOOTER)
                .setTimestamp(Instant.now())
                .setColor(Color.GREEN)
                .build();
    }

    public static MessageEmbed getErrorEmbed(String title, String description) {
        return new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setFooter(FOOTER)
                .setTimestamp(Instant.now())
                .setColor(Color.RED)
                .build();
    }

}
