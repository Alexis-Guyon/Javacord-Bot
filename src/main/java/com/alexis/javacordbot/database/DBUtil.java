package com.alexis.javacordbot.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUtil {

    private static DataSource datasource;

    public static DataSource getDataSource() {
        if (datasource == null) {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl("jdbc:mysql://localhost/javadb");
            config.setUsername("root");
            config.setPassword("");

            config.setMaximumPoolSize(10);
            config.setAutoCommit(false);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            datasource = new HikariDataSource(config);
        }
        return datasource;
    }
}

