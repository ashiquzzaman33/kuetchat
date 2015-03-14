package com.n.userinfo.model;

import com.n.utility.ChangedProperty;
import com.n.utility.PropertyList;

public class IdentityModel extends AbstractModel {
	private String passwrod;
	private boolean isSelected;
	private static IdentityModel oSingleton;
	public int userType;
	public String fullName;
	public String userName;
	public String email;
	public String address;
	public boolean online;

	public static IdentityModel getIdentityModel() {
		return oSingleton;
	}

	public IdentityModel(String userName, String fullName, String password,
			String email, int userType) {
		super(userName);
		oSingleton = null;
		this.fullName = fullName;
		this.passwrod = password;
		this.email = email;
		this.userType = userType;
		oSingleton = this;
	}

	public static enum Property {
		userName, fullName, ipAddress, password, email, phone, country, postalAddress, userType, addminPanelAccess, active
	}

	public String getPasswrod() {
		return passwrod;
	}

	public String getEmail() {
		return email;
	}

	public int getUserType() {
		return userType;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		setChanged();
		this.isSelected = isSelected;
		notifyObservers(new ChangedProperty<Integer, Boolean>(
				PropertyList.SELECTION_STATUS, false, isSelected));
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		setChanged();
		String oldString = this.fullName;
		this.fullName = fullName;
		notifyObservers(new ChangedProperty<IdentityModel.Property, String>(
				IdentityModel.Property.fullName, oldString, fullName));
	}

	public void setPasswrod(String passwrod) {
		setChanged();
		String oldString = this.passwrod;
		this.passwrod = passwrod;
		notifyObservers(new ChangedProperty<IdentityModel.Property, String>(
				IdentityModel.Property.password, oldString, passwrod));
	}

	public void setEmail(String email) {
		setChanged();
		String oldString = this.email;
		this.email = email;
		notifyObservers(new ChangedProperty<IdentityModel.Property, String>(
				IdentityModel.Property.email, oldString, email));
	}

	public void setUserType(int userType) {
		setChanged();
		int old = this.userType;
		this.userType = userType;
		notifyObservers(new ChangedProperty<IdentityModel.Property, Integer>(
				IdentityModel.Property.userName, old, userType));
	}
}
