package com.socket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.common.Constant;
import com.common.Message;
import com.common.UserDetails;

/**
 * <h1>Database Class</h1> Every Database related action and query is done here.
 * 
 * @author Ashiquzzaman & Rashik Hasnat
 *
 */
public class Database {
	public Database() throws SQLException {
		DBUtil.getConnection();
	}

	/**
	 * Check Whether usernaem exists or not
	 * 
	 * @param username
	 * @return
	 * @throws Exception
	 */
	public boolean usernameExists(String username) throws Exception {
		String sql = "SELECT * FROM `logininfo`  WHERE `username`=?";
		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, username);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new Exception("Server Error! Please try again Leter. "
					+ e.getMessage());
		}
	}

	/**
	 * Check Whether email address registered or not
	 * 
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public boolean emaiExists(String email) throws Exception {
		String sql1 = "SELECT * FROM `userinfo`  WHERE `email`=?";

		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt1 = conn.prepareStatement(sql1,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt1.setString(1, email);
			ResultSet res1 = stmt1.executeQuery();
			if (res1.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			throw new Exception("Server Error! Please try again Leter. "
					+ e.getMessage());
		}
	}

	/**
	 * Get history Message of an user
	 * 
	 * @param userString
	 * @return
	 */
	public ArrayList<Message> getHistoryMessage(String userString) {
		String sql1 = "SELECT  `sender`, `recipient`, `message`, `date` FROM `chathistory` WHERE "
				+ "`sender`=? or `recipient`=? order by `date`";

		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt1 = conn.prepareStatement(sql1,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt1.setString(1, userString);
			stmt1.setString(2, userString);

			ResultSet res1 = stmt1.executeQuery();
			ArrayList<Message> list = new ArrayList<Message>();
			while (res1.next()) {
				Message temp = new Message(Constant.HISTORY, res1.getString(1),
						res1.getString(3), res1.getString(2), -1);
				temp.date = res1.getTimestamp(4);
				list.add(temp);

			}
			return list;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return new ArrayList<Message>();
	}

	/**
	 * Get notification List of an user
	 * 
	 * @param userName
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public String getNotificationList(String userName, boolean status)
			throws Exception {
		String sql1 = "SELECT  `username`  FROM `notification` WHERE `friend` = ? and `status`=?";

		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt1 = conn.prepareStatement(sql1,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt1.setString(1, userName);
			stmt1.setBoolean(2, true);
			ResultSet res1 = stmt1.executeQuery();
			String str = "";
			while (res1.next()) {
				str += res1.getString(1) + "@1@";

			}
			System.err.println(str);
			return str;
		} catch (SQLException e) {
			throw new Exception("Server Error! Please try again Leter. "
					+ e.getMessage());
		}
	}

	private boolean checkNotificationCr(String userName, String fName)
			throws Exception {
		String sql1 = "SELECT `username`, `friend`, `status` FROM `notification` WHERE `username`=? and `friend`=?";

		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt1 = conn.prepareStatement(sql1,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt1.setString(1, userName);
			stmt1.setString(2, fName);
			ResultSet res1 = stmt1.executeQuery();
			if (res1.next()) {
				return true;
			}
			return false;
		} catch (SQLException e) {
			throw new Exception("Server Error! Please try again Leter. "
					+ e.getMessage());
		}
	}

	public void setNotification(String userName, String friend, boolean status)
			throws Exception {
		String updateString = "UPDATE `notification` SET `status`=? WHERE `username`=? and `friend`=?";
		String insertString = "INSERT INTO `notification`(`username`, `friend`, `status`) VALUES (?,?,?)";

		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt1 = conn.prepareStatement(
						updateString, Statement.RETURN_GENERATED_KEYS);
				java.sql.PreparedStatement stmt2 = conn.prepareStatement(
						insertString, Statement.RETURN_GENERATED_KEYS);) {
			if (checkNotificationCr(userName, friend)) {
				stmt1.setBoolean(1, status);
				stmt1.setString(2, userName);
				stmt1.setString(3, friend);
				stmt1.executeUpdate();
			} else {
				stmt2.setString(1, userName);
				stmt2.setString(2, friend);
				stmt2.setBoolean(3, status);
				stmt2.executeUpdate();
			}
		} catch (SQLException e) {
			throw new Exception("Server Error! Please try again Leter. "
					+ e.getMessage());
		}
	}

	/**
	 * User login check
	 * 
	 * @param username
	 * @param password
	 * @return UserDetails used by Client app
	 * @throws Exception
	 */
	public UserDetails login(String username, String password) throws Exception {
		String sqlLogin = "SELECT  `password`, `block` FROM `logininfo` WHERE `username`=?";
		try (Connection conn = DBUtil.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sqlLogin,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, username);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				if (password.equals(res.getString(1))
						&& res.getBoolean(2) == false) {
					return getUserDetail(username);
				} else
					return null;
			}
		} catch (Exception e) {
			throw e;
		}
		return null;

	}

	/**
	 * 
	 * @param username
	 * @return UserDetails
	 * @throws Exception
	 */
	public UserDetails getUserDetail(String username) throws Exception {
		UserDetails d = null;
		String sqlInfo = "SELECT `fullname`, `email`, `address`, `usertype`,"
				+ " `online` FROM `userinfo` WHERE `username`=?";
		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt = conn.prepareStatement(
						sqlInfo, Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, username);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				d = new UserDetails(username, res.getString(1), res.getInt(4),
						res.getString(2), res.getString(3), res.getInt(5));
			}
		} catch (SQLException e) {
			throw new Exception("Server Error! Please try again Leter. "
					+ e.getMessage());
		}
		return d;

	}

	public ArrayList<UserDetails> getUserList() throws Exception {
		String sql;
		ArrayList<UserDetails> aud = new ArrayList<UserDetails>();
		sql = "SELECT `username`, `fullname`, `email`, `address`, `usertype`, `online` FROM `userinfo`  ORDER BY `online` DESC";

		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {
			ResultSet res = stmt.executeQuery();
			String str = "";
			while (res.next()) {
				UserDetails ud = new UserDetails(res.getString(1),
						res.getString(2), res.getInt(5), res.getString(3),
						res.getString(4), res.getInt(6));
				aud.add(ud);

			}
			return aud;
		} catch (SQLException e) {
			throw new Exception("Server Error! Please try again Leter. "
					+ e.getMessage());
		}
	}

	/**
	 * Change Presence Status
	 * 
	 * @param userName
	 * @param flag
	 * @throws Exception
	 */
	public void setOnline(String userName, int flag) throws Exception {
		String sql = "UPDATE `userinfo` SET `online`= ? WHERE `username`=?";
		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {

			stmt.setInt(1, flag);
			stmt.setString(2, userName);
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new Exception("Server Error! Please try again Leter. "
					+ e.getMessage());
		}
	}

	/**
	 * User registration
	 * 
	 * @param userDetails
	 * @param pass
	 * @return
	 * @throws Exception
	 */
	public boolean userRegistration(UserDetails userDetails, String pass)
			throws Exception {
		if (usernameExists(userDetails.userName)) {
			throw new Exception("Username already registered.");
		}
		if (emaiExists(userDetails.email)) {
			throw new Exception("Email already registered.");
		}
		String sql = "INSERT INTO  `logininfo`(username, password, block) VALUES(?, ?, ?)";
		String sql1 = "INSERT INTO `userinfo`(`username`, `fullname`, `email`, `address`, `usertype`, `online`)"
				+ "VALUES (?,?,?,?,?, ?)";

		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);

				java.sql.PreparedStatement stmt1 = conn.prepareStatement(sql1,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, userDetails.userName);
			stmt.setString(2, pass);
			stmt.setBoolean(3, false);
			stmt.executeUpdate();

			stmt1.setString(1, userDetails.userName);
			stmt1.setString(2, userDetails.fullName);
			stmt1.setString(3, userDetails.email);
			stmt1.setString(4, userDetails.address);
			stmt1.setInt(5, userDetails.userType);
			stmt1.setBoolean(6, true);
			stmt1.executeUpdate();

		} catch (SQLException e) {
			throw new Exception("Server Error! Please try again Leter. "
					+ e.getMessage());
		}
		return true;
	}

	public boolean isOnline(String username) {
		String sql = "SELECT online FROM `logininfo`  WHERE `username`=? ";
		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, username);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				return res.getBoolean(1);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return false;
	}

	/**
	 * Save User Chat History To database
	 * 
	 * @param msg
	 */
	public void saveChatHistory(Message msg) {
		String sql = "INSERT INTO `chathistory`(`sender`, `recipient`, `message`) VALUES (?, ?, ?)";
		try (Connection conn = DBUtil.getConnection();
				java.sql.PreparedStatement stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);) {
			stmt.setString(1, msg.sender);
			stmt.setString(2, msg.recipient);
			stmt.setString(3, msg.content);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}
}
