package me.hy.libhyextended.sql;

import lombok.NonNull;
import lombok.Setter;
import me.hy.libhycore.CoreLogger;

import java.sql.*;

@Setter
public class SQLConnection {
    @NonNull private String protocol = "jdbc:mariadb://";
    @NonNull private String address = "localhost";
    @NonNull private String port = "3306";
    @NonNull private String driverName = "org.mariadb.jdbc.Driver";

    @NonNull private String databaseName;
    @NonNull private String username;
    @NonNull private String password;
    @NonNull private int secondsTimeout = 10;
    @NonNull private boolean useAutoClose = true;
    @NonNull private boolean useSQLite = false;

    private Connection connection;

    public SQLConnection(String databaseName, String username, String password) {
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
    }

    public SQLConnection(){}

    private void buildConnection() throws SQLException {
        try {
            Class.forName(driverName);
            connection = DriverManager.getConnection(protocol + address + ":" + port + "/" + databaseName, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(81);
        } catch (SQLException e) {
            throw e;
        }
    }

    private void buildSQLiteConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(81);
        } catch (SQLException e) {
            throw e;
        }
    }

    public ResultSet executeQuery(String query, Object... parameters) throws SQLException {
        if (useSQLite) {
            buildSQLiteConnection();
        } else {
            buildConnection();
        }
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                preparedStatement.setObject(i + 1, parameters[i]);
            }
        }
        if (useAutoClose) autoClose();
        return preparedStatement.executeQuery();
    }

    public void close() throws SQLException {
        this.connection.close();
    }

    private void autoClose() {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(1000L * secondsTimeout);
                connection.close();
                CoreLogger.debug(CoreLogger.EventType.INFO, "Connection automatically closed after " + secondsTimeout + " seconds.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        t.start();
    }
}

