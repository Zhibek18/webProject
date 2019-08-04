package kz.kakimzhanova.delivery.pool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * DbResourceManager provides access to db.properties file
 */
class DbResourceManager {
    private static final Logger logger = LogManager.getLogger();
    private static DbResourceManager instance  = new DbResourceManager();
    private ResourceBundle resourceBundle;

    private DbResourceManager(){
        try {
            resourceBundle = ResourceBundle.getBundle("db");
        }catch (MissingResourceException e){
            logger.log(Level.ERROR, "Could not find resource: " + e);
        }
    }
    static DbResourceManager getInstance() {
        return instance;
    }

    /**
     *
     * @param key - database parameter key
     * @return value of appropriate parameter
     */
    String getString(String key) {
        String value = null;
        if (key != null) {
            try {
                value = resourceBundle.getString(key);
            } catch (MissingResourceException e){
                logger.log(Level.ERROR, "No such key " + key + ": " + e);
            }
        } else {
            logger.log(Level.ERROR, " getValue error: Got key = null");
        }
        return value;
    }
}
