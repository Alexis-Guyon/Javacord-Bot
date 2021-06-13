package com.alexis.javacordbot;

import com.alexis.javacordbot.util.CommandHandler;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        if (args.length < 1) {
            LOGGER.info("Please provide a valid token as the first argument!");
            return;
        }

        String TOKEN = args[0];

        DiscordApi api = new DiscordApiBuilder()
                .setToken(TOKEN)
                .setAllIntents()
                .login().join();

        LOGGER.info("The bot is now online!");
        LOGGER.info(api.createBotInvite());

        new CommandHandler(api);
    }

}
