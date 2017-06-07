package sergg.samples;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.PropertiesConfigurationLayout;

import java.io.*;

public class PropertiesReader {
    public static void main(String args[]) throws Throwable, ConfigurationException {
        File file = new File("my.security.properties");

        PropertiesConfiguration config = new PropertiesConfiguration();
        PropertiesConfigurationLayout layout = new PropertiesConfigurationLayout(config);
        layout.load(new InputStreamReader(new FileInputStream(file)));

        config.setProperty("test", "testValue");
        layout.save(new FileWriter("saved.security.properties", false));
    }
}
