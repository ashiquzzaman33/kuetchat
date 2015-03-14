package com.n.userinfo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.client.controller.AppController;
import com.common.Constant;
import com.common.Message;
import com.n.userinfo.model.AbstractModel;
import com.n.userinfo.model.FriendInfoModel;
import com.n.userinfo.model.IdentityModel;
import com.n.utility.ChangedProperty;
import com.n.utility.PropertyList;
import com.n.view.MainChatPanel;
import com.n.view.MainView;
import com.n.view.contacts.FriendInfoView;

/**
 * Controller of FriendInfoView and FriendInfoModel
 *
 */
public class UserController extends Observable {
	private static UserController singletonObject = new UserController();
	private HashMap<String, AbstractModel> modelList;
	private HashMap<String, FriendInfoView> friendInfoViewList;
	private String selectedFriend;

	public static UserController getFriendInfoController() {
		return singletonObject;
	}

	private UserController() {
		modelList = new HashMap<String, AbstractModel>();
		friendInfoViewList = new HashMap<String, FriendInfoView>();
	}

	/**
	 * Add friend to Friend list
	 * 
	 * @param userName
	 * @param fullName
	 * @param counry
	 *            use as email
	 * @param profilePic
	 * @param onlineStatus
	 * @param isNotified
	 * 
	 */
	public void addFriend(String userName, String fullName, String counry,
			ImageIcon profilePic, int onlineStatus, boolean isNotified) {
		FriendInfoModel model = new FriendInfoModel(userName, fullName, counry,
				profilePic, onlineStatus, isNotified);

		MainChatPanel mainChatPanel = new MainChatPanel(model, profilePic);
		FriendInfoView view = new FriendInfoView(userName, fullName, counry,
				profilePic, onlineStatus, isNotified);

		model.addObserver(mainChatPanel);
		model.addObserver(view);
		modelList.put(userName, model);
		friendInfoViewList.put(userName, view);
		setChanged();
		notifyObservers(new ChangedProperty<Integer, FriendInfoView>(
				PropertyList.ADD_FRIEND, null, view));

		MainView.getMainView().addMainPanel(mainChatPanel, userName);
	}

	public void addMainPanelElement(AbstractModel model, JPanel view) {
		modelList.put(model.getUserName(), model);
		// MainView.getMainView().addMainPanel(view, model.getUserName());
	}

	public void removeFriend(String userName) {

		if (modelList.get(userName) != null)
			modelList.remove(userName);
		if (friendInfoViewList.get(userName) != null) {
			friendInfoViewList.remove(userName);
		}
		setChanged();
		notifyObservers(new ChangedProperty<Integer, HashMap<String, FriendInfoView>>(
				PropertyList.REMOVE_FRIEND, null, friendInfoViewList));
	}

	public void setSelectedFriend(String selectedFriend) {

		if (this.selectedFriend != selectedFriend) {
			if (this.selectedFriend != null
					&& modelList.get(this.selectedFriend) != null) {
				((AbstractModel) modelList.get(this.selectedFriend))
						.setSelected(false);
				AbstractModel model = modelList.get(selectedFriend);
				if (model instanceof FriendInfoModel) {
					FriendInfoModel model1 = (FriendInfoModel) model;
					if (model1.getIsNotified())
						try {
							model1.setIsNotified(false);
							AppController.getInstance().send(
									new Message(Constant.NOTIFICATION_CLEAR,
											IdentityModel.getIdentityModel()
													.getUserName(), "false",
											selectedFriend, -1));

						} catch (IOException e) {
							e.printStackTrace();
						}
				}
			}

			this.selectedFriend = selectedFriend;
			if (modelList.get(selectedFriend) != null) {
				((AbstractModel) modelList.get(selectedFriend))
						.setSelected(true);

				if (modelList.get(selectedFriend) instanceof FriendInfoModel) {
					FriendInfoModel model = (FriendInfoModel) modelList
							.get(selectedFriend);
					if (model.getIsNotified())
						try {
							model.setIsNotified(false);
							AppController.getInstance().send(
									new Message(Constant.NOTIFICATION_CLEAR,
											IdentityModel.getIdentityModel()
													.getUserName(), "false",
											selectedFriend, -1));

						} catch (IOException e) {
							e.printStackTrace();
						}
				}
			}
		}

		setChanged();
		notifyObservers(new ChangedProperty<Integer, String>(
				PropertyList.CHANGE_MAIN_CHAT_WINDOW, "", selectedFriend));
	}

	/**
	 * 
	 * @param userName
	 *            UserName whose model is needed.
	 * @return FriendInfoModel of the user
	 */
	public FriendInfoModel getFriendInfoModel(String userName) {
		return (FriendInfoModel) modelList.get(userName);
	}

	/**
	 * 
	 * @param userName
	 *            UserName whose View is needed.
	 * @return FriendInfoView of the user
	 */
	public FriendInfoView getFriendInfoView(String userName) {
		return friendInfoViewList.get(userName);
	}

	public HashMap<String, FriendInfoView> getViews() {
		return friendInfoViewList;
	}

	public HashMap<String, AbstractModel> getModels() {
		return modelList;
	}

	public String getSelectedFriend() {
		return selectedFriend;
	}

	public void messageArrived(String userName, Message message) {
		if (modelList.get(userName) instanceof FriendInfoModel) {
			FriendInfoModel infoModel = (FriendInfoModel) modelList
					.get(userName);
			infoModel.setArrivedMessage(message);
			if (!userName.equals(getSelectedFriend())) {
				infoModel.setIsNotified(true);
			} else {
				try {
					AppController.getInstance().send(
							new Message(Constant.NOTIFICATION_CLEAR,
									IdentityModel.getIdentityModel()
											.getUserName(), "false",
									selectedFriend, -1));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

	public void historyArrived(Message msg) {

		String friend = "";
		if (msg.sender.equals(IdentityModel.getIdentityModel().getUserName())) {
			friend = msg.recipient;
		} else {
			friend = msg.sender;
		}
		if (modelList.get(friend) instanceof FriendInfoModel) {
			FriendInfoModel infoModel = (FriendInfoModel) modelList.get(friend);
			infoModel.setArrivedMessage(msg);// TODO
		}
	}

	public void setInitialNotification(String str) {
		String[] strs = str.split("@1@");
		for (String string : strs) {
			System.err.println(str);
			if (!string.equals("")) {
				if (modelList.get(string) instanceof FriendInfoModel) {
					FriendInfoModel infoModel = (FriendInfoModel) modelList
							.get(string);
					if (!string.equals(getSelectedFriend())) {
						infoModel.setIsNotified(true);
					}
				}
			}
		}
	}
}
