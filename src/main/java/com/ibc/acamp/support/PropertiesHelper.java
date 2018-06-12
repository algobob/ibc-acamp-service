package com.ibc.acamp.support;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {

    private static Properties appProps = new Properties();

    private PropertiesHelper(){}

    public static void load(String env) throws IOException {
        String propertiesFilePath = String.format("/%s.properties",env);
        appProps.load(getPropertiesFileAsStream(propertiesFilePath));
        loadDatabasePropertiesFromEnvVarsIfRequired();
    }

    private static void loadDatabasePropertiesFromEnvVarsIfRequired() {
        if (isTravisEnv()) {
            appProps.put("db.jdbc.url", System.getenv("JDBC_DATABASE_URL"));
            appProps.put("db.jdbc.user", System.getenv("JDBC_DATABASE_USERNAME"));
            appProps.put("db.jdbc.password", System.getenv("JDBC_DATABASE_PASSWORD"));
        }
    }

    public static boolean isTravisEnv() {
        return System.getenv().containsKey("TRAVIS");
    }

    private static InputStream getPropertiesFileAsStream(String propertiesFilePath) throws FileNotFoundException {
        return PropertiesHelper.class.getResourceAsStream(propertiesFilePath);
    }

    public static String getProps(String property) {
        return appProps.getProperty(property);
    }

    public static String getJdbcUrl() {
        return appProps.getProperty("db.jdbc.url");
    }

    public static String getJdbcUsername() {
        return appProps.getProperty("db.jdbc.user");
    }

    public static String getJdbcPassword() {
        return appProps.getProperty("db.jdbc.password");
    }

    public static String getJdbcSchema() {
        return appProps.getProperty("db.jdbc.schema");
    }
}
