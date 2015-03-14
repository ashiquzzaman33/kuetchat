package com.n.userinfo.model;

import java.util.Observable;

import com.n.utility.ChangedProperty;
import com.n.utility.PropertyList;

public abstract class AbstractModel extends Observable {
	private boolean isSelected;
	private String userName;

	public boolean isSelected() {
		return isSelected;
	}

	/**
	 * Default Constructor
	 */
	public AbstractModel() {
	}

	/**
	 * Constructor with user name
	 * 
	 * @param userName
	 */
	public AbstractModel(String userName) {
		this.userName = userName;
	}

	public void setSelected(boolean isSelected) {
		setChanged();
		notifyObservers(new ChangedProperty<Integer, Boolean>(
				PropertyList.SELECTION_STATUS, true, isSelected));
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		String oldString = userName;
		this.userName = userName;
		setChanged();
		notifyObservers(new ChangedProperty<Integer, String>(
				PropertyList.USER_NAME, oldString, userName));
	}

}
