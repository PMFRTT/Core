package core.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private Connection connection;

    public boolean isConnected() {
        return (connection != null);
    }

    public boolean connect() throws SQLException {
        if (!isConnected()) {
            String host = "localhost";
            String port = "3306";
            String database = "pmfrtt_core_network";
            String username = "root";
            String password = SQLPasswordReader.readPassword();
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false", username, password);
            return true;
        }
        return false;
    }

    public void disconnect() throws SQLException {
        if (isConnected()) {
            connection.close();
            connection = null;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public MySQL() {

    }

}
