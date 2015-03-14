package com.common;

import java.io.Serializable;

public class UserDetails implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int userType;
	public String fullName;
	public String userName;
	public String email;
	public String address;
	public int online;
	public boolean isNotified;

	public UserDetails(String _userName, String _fullName, int _userType,
			String _email, String _address, int _online) {
		this.userName = _userName;
		this.fullName = _fullName;
		this.userType = _userType;
		this.email = _email;
		this.address = _address;
		this.online = _online;
	}

	public UserDetails(String str) {
		String[] strs = str.split("@1@");
		this.userName = strs[0];
		this.fullName = strs[1];
		this.userType = Integer.parseInt(strs[2]);
		this.email = strs[3];
		this.address = strs[4];
		this.online = Integer.parseInt(strs[5]);
	}

	@Override
	public String toString() {
		return userName + "@1@" + fullName + "@1@" + userType + "@1@" + email
				+ "@1@" + address + "@1@" + online;

	}

}
