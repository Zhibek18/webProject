package kz.kakimzhanova.delivery.command;

import java.util.ResourceBundle;

public class ConfigurationManager {
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("config");
    private ConfigurationManager(){  }
    public static String getString(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
}
