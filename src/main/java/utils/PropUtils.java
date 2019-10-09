package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropUtils {

    private static final Logger logger = LoggerFactory.getLogger(PropUtils.class);

    private static Properties prop;

    public static Properties getInstance(String fileName) throws IOException {
        if (prop == null) {
            prop = new Properties();
            prop.load(new FileInputStream(new File("src/test/resources/" + fileName)));
        }

        return prop;
    }

    /*
     * Read a property from the *.properties file and return the value if value
     * not exist return a 'Value not set or empty' exception
     */
    public static synchronized String readProperty(String property, String fileName) {
        String value = null;
        try {
            prop = getInstance(fileName);
            value = prop.getProperty(property);
            if (value == null || value.isEmpty()) {
                throw new Exception("Value not set or empty. Property is: " + property);
            }

        } catch (Exception e) {
            logger.info("Did not succeed to run property from properties file", e);
        }

        return value;
    }

    public static synchronized void writeProperty(String fileName, String propertyKey, String propertyValue) {
        try {
            prop = getInstance(fileName);

            prop.setProperty(propertyKey, propertyValue);

        } catch (Exception e) {
            logger.info("Did not succeed to set property", e);
        }

    }

}