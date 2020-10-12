package battlemetrics_rust.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    public  String getUrl() {
        return getProperty("url");
    }

    public  battlemetrics_rust.utils.Browser getBrowser() {
        return Browser.valueOf(getProperty("browser"));
    }

    private  String getProperty(String propertyName) {
        if (System.getProperty(propertyName) == null) {
            return getPropertyFromFile(propertyName);
        } else {
            return System.getProperty(propertyName);
        }
    }

    private  String getPropertyFromFile(String propertyName) {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/test/resources/framework.battlemetrics.properties");
            prop.load(input);
        } catch (IOException ex) {
            System.out.println("Cannot read property value for " + propertyName);
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop.getProperty(propertyName);
    }
}
