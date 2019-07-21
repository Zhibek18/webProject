package kz.kakimzhanova.project.connection;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ConnectionDB {
    private static Logger logger = LogManager.getLogger();
    public static Connection getConnection() throws SQLException {
        ResourceBundle resource = ResourceBundle.getBundle("db");

        String url = resource.getString("db.url");

        String user = resource.getString("db.user");

        String pass = resource.getString("db.password");
        return DriverManager.getConnection(url, user, pass);
    }
}
