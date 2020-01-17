package com.redstonedaedalus.suggestionmanager.database;

import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLProvider {
    private static final Logger logger = LoggerFactory.getLogger("SQLProvider");
    private final BasicDataSource source = new BasicDataSource();

    private final SuggestionController suggestionController = new SuggestionController(this);

    public SQLProvider(String driverClassName, String url, String username, String password, int initialSize, int max) {
        // Set properties for data MySQL data source
        this.source.setDriverClassName(driverClassName);
        this.source.setUrl(url);
        this.source.setUsername(username);
        this.source.setPassword(password);
        this.source.setInitialSize(initialSize);
        this.source.setMaxActive(max);
    }

    public Connection grabConnection() throws SQLException {
        return this.source.getConnection();
    }

    public Connection grabConnection(String username, String password) throws SQLException {
        return this.source.getConnection(username, password);
    }

    public void performTest() throws SQLException {
        logger.debug("Performing database test...");

        Statement query = this.source.getConnection().createStatement();
        ResultSet tables = query.executeQuery("SHOW TABLES IN suggestion_manager;");

        while (tables.next()) logger.debug("Database test found table " + tables.getString(1));
    }

    public SuggestionController getSuggestionController() {
        return this.suggestionController;
    }

    public static class Builder {
        private String driverClassName = "com.mysql.jdbc.driver";
        private String url;
        private String username;
        private String password;
        private int initialSize;
        private int max;

        public Builder setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setInitialSize(int initialSize) {
            this.initialSize = initialSize;
            return this;
        }

        public Builder setMax(int max) {
            this.max = max;
            return this;
        }

        public SQLProvider build() {
            return new SQLProvider(this.driverClassName, this.url, this.username, this.password, this.initialSize, this.max);
        }
    }
}
