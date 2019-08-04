package kz.kakimzhanova.delivery.command;

import java.util.ResourceBundle;

/**
 * ConfigurationManager provides access to jsp pages
 */
public class ConfigurationManager {
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("config");
    private ConfigurationManager(){  }

    /**
     * getString method finds jsp page in resources/config.properties file by given key
     * @param key - key
     * @return jsp page path
     */
    public static String getString(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
}
