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
        loadDatabasePropertiesFromEnvVarsIfRequired(env.equals("dev"));
    }

    private static void loadDatabasePropertiesFromEnvVarsIfRequired(boolean isRequired) {
        if (isRequired) {
            appProps.put("db.jdbc.url", System.getenv("JDBC_DATABASE_URL"));
            appProps.put("db.jdbc.user", System.getenv("JDBC_DATABASE_USERNAME"));
            appProps.put("db.jdbc.password", System.getenv("JDBC_DATABASE_PASSWORD"));
        }
    }

    private static InputStream getPropertiesFileAsStream(String propertiesFilePath) throws FileNotFoundException {
        return PropertiesHelper.class.getResourceAsStream(propertiesFilePath);
    }

    public static String getProps(String property) {
        return appProps.getProperty(property);
    }
}
