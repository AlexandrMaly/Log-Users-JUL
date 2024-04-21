package database;

import utils.Constants;
import view.AppView;

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
            conn = DriverManager.getConnection(Constants.DB_DRIVER +
                            Constants.DB_BASE_URL + Constants.DB_NAME);
        } catch (SQLException e) {
          LOGGER.log(Level.SEVERE, Constants.LOG_DB_ABSENT_MSG);
            appView.getOutput(e.getMessage());
        }
        return conn;
    }
}
