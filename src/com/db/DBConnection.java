package com.db;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static final String URL = "jdbc:db2://192.168.2.203:50000/TRNGDB";
	private static final String USER = "db2inst1";
	private static final String PASSWORD = "Welcome@123";

	public static Connection getConnection() throws SQLException {
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("DB2 Connection Established Successfully!");
			return connection;
		} catch (ClassNotFoundException e) {
			throw new SQLException("DB2 Driver not found!", e);
		}
	}

	public static void main(String[] args) throws SQLException {
      getConnection();
	}
}
