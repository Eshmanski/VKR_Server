package com.data.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBWorker {
    private final String driverName = "com.mysql.cj.jdbc.Driver";
    private final String connectionString = "jdbc:mysql://127.0.0.1:3306/ktm?allowMultiQueries=true";
    private final String login = "root";
    private final String password = "znFO9jc1oWHh";

    private boolean wasDriverFound = false;
    private Connection connection;

    public DBWorker() {
        try {
            Class.forName(driverName);
            wasDriverFound = true;
        } catch (ClassNotFoundException e) {
            System.out.println("Can`t get class. No driver found");
            e.printStackTrace();
        }
    }

    public void connect() {
        if(wasDriverFound) {
            try {
                connection = DriverManager.getConnection(connectionString, login, password);
            } catch (SQLException e) {
                System.out.println("Can`t connect to database");
                e.getStackTrace();
                return;
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        if(wasDriverFound) {
            try {
                if(!connection.isClosed())
                    connection.close();
            } catch (SQLException e) {
                System.out.println("Can`t close connection");
                e.getStackTrace();
                return;
            }
        }
    }
}
