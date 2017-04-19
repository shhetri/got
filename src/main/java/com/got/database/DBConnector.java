package com.got.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnector {

    static Connection connection = null;

    public static Connection connect(DatabaseType databaseType, String host, String port, String database, String username, String password) {
        Properties connectionProps = new Properties();
        connectionProps.put("user", username);
        connectionProps.put("password", password);

        if (databaseType.equals(DatabaseType.MYSQL)) {
            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                connection = DriverManager.getConnection(
                        "jdbc:mysql://" +
                                host +
                                ":" + port + "/" + database,
                        connectionProps);
            } catch (SQLException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }
        } else if (databaseType.equals(DatabaseType.DERBY)) {
            try {
                Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
                connection = DriverManager.getConnection(
                        "jdbc:" + host + ":" +
                                port + "/" + database +
                                ";create=true",
                        connectionProps);
            } catch (SQLException | IllegalAccessException | InstantiationException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return connection;
    }

}
