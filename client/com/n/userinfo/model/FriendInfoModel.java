package com.n.userinfo.model;

import javax.swing.ImageIcon;

import com.common.Constant;
import com.common.Message;
import com.n.utility.ChangedProperty;
import com.n.utility.PropertyList;

public class FriendInfoModel extends AbstractModel {

	private String fullName;
	private String country;
	private boolean isNotified;
	private Message arrivedMessage;

	private int onlineStatus = Constant.OFFLINE;
	private ImageIcon profilePic;

	public FriendInfoModel(String userName, String fullName, String counry,
			ImageIcon profilePic, int onlineStatus, boolean isNotified) {
		super(userName);
		this.fullName = fullName;
		this.country = counry;
		this.profilePic = profilePic;
		this.onlineStatus = onlineStatus;
		this.isNotified = isNotified;
	}

	public String getCountry() {
		return country;
	}

	public boolean getIsNotified() {
		return isNotified;
	}

	public Message getArrivedMessage() {
		return arrivedMessage;
	}

	public int getOnlineStatus() {
		return onlineStatus;
	}

	public void setCountry(String country) {
		String oldString = country;
		this.country = country;
		setChanged();
		notifyObservers(new ChangedProperty<Integer, String>(
				PropertyList.COUNTRY, oldString, country));
	}

	public void setIsNotified(boolean isNotified) {
		boolean old = isNotified;
		this.isNotified = isNotified;
		setChanged();
		notifyObservers(new ChangedProperty<Integer, Boolean>(
				PropertyList.NOTIFICATION, old, isNotified));
	}

	public void setArrivedMessage(Message arrivedMessage) {
		Message oldString = arrivedMessage;
		this.arrivedMessage = arrivedMessage;
		setChanged();
		notifyObservers(new ChangedProperty<Integer, Message>(
				PropertyList.ARRIVE_MESSAGE, oldString, arrivedMessage));
	}

	public void setOnlineStatus(int onlineStatus) {
		int oldString = onlineStatus;
		this.onlineStatus = onlineStatus;
		setChanged();
		notifyObservers(new ChangedProperty<Integer, Integer>(
				PropertyList.ONLINE_STATUS, oldString, onlineStatus));
	}

	public ImageIcon getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(ImageIcon profilePic) {
		ImageIcon oldString = profilePic;
		this.profilePic = profilePic;
		setChanged();
		notifyObservers(new ChangedProperty<Integer, ImageIcon>(
				PropertyList.PROFILE_PIC, oldString, profilePic));
	}

	public String getFullNameString() {
		return fullName;
	}

	public void setFullNameString(String fullNameString) {
		String oldString = fullNameString;
		this.fullName = fullNameString;
		setChanged();
		notifyObservers(new ChangedProperty<Integer, String>(
				PropertyList.FULL_NAME, oldString, fullNameString));
	}
}
