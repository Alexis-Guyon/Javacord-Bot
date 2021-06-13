package com.alexis.javacordbot;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;

public class Embeds {

    /*
Default Embed
 */
    public static EmbedBuilder defaultEmbed() {
        return new EmbedBuilder().setColor(Constants.COLOR);
    }

    /*
    Error Embed
     */
    public static EmbedBuilder errorEmbed() {
        return new EmbedBuilder().setColor(Color.RED);
    }

}
