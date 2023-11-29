package domain.server.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PrettyProperties {

    private static PrettyProperties instance = null;
    private Properties prop;


    public static PrettyProperties getInstance() {
        if(instance == null) {
            instance = new PrettyProperties();
        }
        return instance;
    }

    private PrettyProperties() {
        this.prop = new Properties();
        this.readProperties();
    }

    private void readProperties() {
        try {
            InputStream input = PrettyProperties.class.getClassLoader().getResourceAsStream("config.properties");
            if (input != null) {
                this.prop.load(input);
            } else {
                System.err.println("El archivo config.properties no se encontró en el classpath.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String propertyFromName(String name) {
        return this.prop.getProperty(name, null);
    }
}
