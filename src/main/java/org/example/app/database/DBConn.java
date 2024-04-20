package org.example.app.database;

import org.example.app.utils.Users;
import org.example.app.view.AppView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConn {

    private static final Logger LOGGER =
            Logger.getLogger(DBConn.class.getName());

    public static Connection connect() {

        AppView appView = new AppView();
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(Users.DB_DRIVER +
                            Users.DB_BASE_URL + Users.DB_NAME);
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, Users.LOG_DB_ABSENT_MSG);
            appView.getOutput(e.getMessage());
        }
        return conn;
    }
}
