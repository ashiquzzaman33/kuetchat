package com.socket;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Database Utility Class
 * 
 * @author Ashiquzzaman
 *
 */
public class DBUtil {

	private static String USERNAME;
	private static String PASSWORD;
	private static String DATABASE_NAME;
	private static String DATABASE_HOST;
	private static int PORT;
	private static String CONN_STRING;
	private static String CONN_STRING_WITHOUT_DB;
	static {
		Properties config = new Properties();
		try {
			config.load(Main.class.getResourceAsStream("/config.ini"));
			PORT = Integer.parseInt(config.getProperty("dbPort"));
			USERNAME = config.getProperty("userName");
			PASSWORD = config.getProperty("password");
			DATABASE_HOST = config.getProperty("dbHost");
			DATABASE_NAME = config.getProperty("dbName");
			CONN_STRING_WITHOUT_DB = "jdbc:mysql://" + DATABASE_HOST + ":"
					+ PORT + "/";
			CONN_STRING = "jdbc:mysql://" + DATABASE_HOST + ":" + PORT + "/"
					+ DATABASE_NAME;
			if (!checkDBExists()) {
				createDatabase();
				initDatabase();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
	}

	private static Connection getConnectionForCreate() throws SQLException {
		return DriverManager.getConnection(CONN_STRING_WITHOUT_DB, USERNAME,
				PASSWORD);
	}

	private static boolean checkDBExists() {
		try {
			Connection conn = DriverManager.getConnection(
					CONN_STRING_WITHOUT_DB, USERNAME, PASSWORD);
			ResultSet resultSet = conn.getMetaData().getCatalogs();
			while (resultSet.next()) {

				String databaseName = resultSet.getString(1);
				if (databaseName.equals(DATABASE_NAME)) {
					return true;
				}
			}
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private static void createDatabase() {
		String sql = "CREATE DATABASE IF NOT EXISTS `" + DATABASE_NAME
				+ "` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;";
		try (Connection conn = DBUtil.getConnectionForCreate();
				Statement stmt = conn.createStatement();) {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void initDatabase() {
		String chathistory = "CREATE TABLE IF NOT EXISTS "
				+ "`chathistory` (`sender` varchar(40) NOT NULL, "
				+ "`recipient` varchar(40) NOT NULL, "
				+ "`message` varchar(2000) NOT NULL, `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP) "
				+ "ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String logininfo = "CREATE TABLE IF NOT EXISTS `logininfo` ("
				+ "`username` varchar(40) NOT NULL,"
				+ "`password` varchar(128) NOT NULL,"
				+ "`block` tinyint(1) NOT NULL," + " PRIMARY KEY (`username`)"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String notification = "CREATE TABLE IF NOT EXISTS `notification` ("
				+ "`username` varchar(40) NOT NULL,"
				+ "`friend` varchar(40) NOT NULL,"
				+ "`status` tinyint(1) NOT NULL"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		String userinfo = " CREATE TABLE IF NOT EXISTS `userinfo` ("
				+ "`username` varchar(40) NOT NULL,"
				+ "`fullname` varchar(50) NOT NULL,"
				+ "`email` varchar(60) NOT NULL,"
				+ " `address` varchar(200) NOT NULL,"
				+ "`usertype` int(11) NOT NULL,"
				+ " `online` int(1) NOT NULL DEFAULT '0',"
				+ " UNIQUE KEY `email` (`email`)"
				+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		try (Connection conn = DBUtil.getConnection();
				Statement stmt = conn.createStatement();) {
			stmt.executeUpdate(chathistory);
			stmt.executeUpdate(logininfo);
			stmt.executeUpdate(notification);
			stmt.executeUpdate(userinfo);
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
}
