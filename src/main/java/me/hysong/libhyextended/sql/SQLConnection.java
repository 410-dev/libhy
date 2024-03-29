package me.hysong.libhyextended.sql;

import lombok.NonNull;
import lombok.Setter;
import me.hysong.libhycore.CoreLogger;
import org.jetbrains.annotations.NotNull;

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

    private int secondsTimeout = 10;
    private boolean useAutoClose = true;
    private boolean useSQLite = false;

    private Connection connection;

    private static boolean printDebug = false;
    public static void setPrintDebug(boolean printDebug) {
        SQLConnection.printDebug = printDebug;
    }

    public SQLConnection(@NotNull String databaseName, @NotNull String username, @NotNull String password) {
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
            if (databaseName != null && !databaseName.equals("")) throw new RuntimeException("SQLite does not support database name!");
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + address);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(81);
        } catch (SQLException e) {
            throw e;
        }
    }

    public PreparedStatement buildPrepareStatement(String query, Object... parameters) throws SQLException {
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

        if (printDebug) {
            String buildQuery = query;
            if (parameters != null) {
                for (Object parameter : parameters) {
                    buildQuery = buildQuery.replaceFirst("\\?", parameter.toString());
                }
            }
            CoreLogger.debug(CoreLogger.EventType.INFO, "Query: " + buildQuery.replaceAll("\\n","\\\\n"));
        }

        return preparedStatement;
    }

    public void executeUpdate(String query, Object... parameters) throws SQLException {
        PreparedStatement preparedStatement = buildPrepareStatement(query, parameters);
        if (useAutoClose) autoClose();
        preparedStatement.executeUpdate();
    }

    public ResultSet executeQuery(String query, Object... parameters) throws SQLException {
        PreparedStatement preparedStatement = buildPrepareStatement(query, parameters);
        if (useAutoClose) autoClose();
        return preparedStatement.executeQuery();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
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

