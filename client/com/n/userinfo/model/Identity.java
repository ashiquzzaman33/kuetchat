package com.n.userinfo.model;

import java.util.Observable;

import com.n.utility.ChangedProperty;

public class Identity extends Observable {
	private String userName;
	private String fullName;
	private String passwrod;
	private String email;
	private String postalAddress;
	private int userType;
	private boolean active;

	public static enum Property {
		userName, fullName, password, email, postalAddress, userType, active
	}

	public String getUserName() {
		return userName;
	}

	public String getFullName() {
		return fullName;
	}

	public String getPasswrod() {
		return passwrod;
	}

	public String getEmail() {
		return email;
	}

	public String getPostalAddress() {
		return postalAddress;
	}

	public int getUserType() {
		return userType;
	}

	public boolean isActive() {
		return active;
	}

	public void setUserName(String userName) {
		setChanged();
		String oldString = this.userName;
		this.userName = userName;
		notifyObservers(new ChangedProperty<Identity.Property, String>(
				Identity.Property.userName, oldString, userName));
	}

	public void setFullName(String fullName) {
		setChanged();
		String oldString = this.fullName;
		this.fullName = fullName;
		notifyObservers(new ChangedProperty<Identity.Property, String>(
				Identity.Property.fullName, oldString, fullName));
	}

	public void setPasswrod(String passwrod) {
		setChanged();
		String oldString = this.passwrod;
		this.passwrod = passwrod;
		notifyObservers(new ChangedProperty<Identity.Property, String>(
				Identity.Property.password, oldString, passwrod));
	}

	public void setEmail(String email) {
		setChanged();
		String oldString = this.email;
		this.email = email;
		notifyObservers(new ChangedProperty<Identity.Property, String>(
				Identity.Property.email, oldString, email));
	}

	public void setPostalAddress(String postalAddress) {
		setChanged();
		String oldString = this.postalAddress;
		this.postalAddress = postalAddress;
		notifyObservers(new ChangedProperty<Identity.Property, String>(
				Identity.Property.postalAddress, oldString, postalAddress));
	}

	public void setUserType(int userType) {
		setChanged();
		int old = this.userType;
		this.userType = userType;
		notifyObservers(new ChangedProperty<Identity.Property, Integer>(
				Identity.Property.userName, old, userType));
	}

	public void setActive(boolean active) {
		setChanged();
		boolean oldString = this.active;
		this.active = active;
		notifyObservers(new ChangedProperty<Identity.Property, Boolean>(
				Identity.Property.active, oldString, active));
	}

}
