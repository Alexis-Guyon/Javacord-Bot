package com.alexis.javacordbot;

import com.alexis.javacordbot.database.DBUtil;
import com.alexis.javacordbot.util.CommandHandler;
import com.mysql.jdbc.PreparedStatement;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {
        DBUtil.getDataSource().getConnection();

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

        // DB Connect

        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            DataSource dataSource = DBUtil.getDataSource();
            connection = dataSource.getConnection();

            LOGGER.info("The Connection Object is of Class: " + connection.getClass().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
