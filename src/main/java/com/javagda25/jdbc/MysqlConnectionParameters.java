package com.javagda25.jdbc;

import lombok.Getter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Getter
public class MysqlConnectionParameters {
    private static final String PROPERTIES_FILE_NAME = "/jdbc.properties";
    private Properties properties;

    private String dbHost;
    private String dbPort;
    private String dbUsername;
    private String dbPassword;
    private String dbName;

    public MysqlConnectionParameters() throws IOException {
        loadConfigurationFrom(PROPERTIES_FILE_NAME);

        dbHost = loadParameter("jdbc.database.host");
        dbPort = loadParameter("jdbc.database.port");
        dbUsername = loadParameter("jdbc.username");
        dbPassword = loadParameter("jdbc.password");
        dbName = loadParameter("jdbc.database.name");
    }

    private Properties loadConfigurationFrom (String resourceName) throws IOException {
        properties = new Properties();

        InputStream propertiesStream = this.getClass().getResourceAsStream(resourceName);
        if (propertiesStream == null ){
            System.err.println("Rsource files cannot be loaded");
            throw new FileNotFoundException("Resources files cannot be loaded.");
        }
        properties.load(propertiesStream);
        return properties;
    }

    private String loadParameter(String propertyName) {
        return properties.getProperty(propertyName);
    }
}
