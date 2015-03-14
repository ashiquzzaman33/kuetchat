package com.socket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database Utility Class
 * 
 * @author Ashiquzzaman
 *
 */
public class DBUtil {

	private static final String USERNAME = "dbuser";
	private static final String PASSWORD = "dbpassword";
	private static final String CONN_STRING = "jdbc:mysql://localhost/kuetchatdb";

	public static Connection getConnection() throws SQLException {

		return DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
	}
}
