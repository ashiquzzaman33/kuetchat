package com.n.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import com.client.app.StartApp;
import com.client.controller.AppController;
import com.common.Constant;
import com.common.Message;
import com.kuetchat.client.utility.BackgroundWork;
import com.kuetchat.client.utility.BackgroundWorkable;
import com.n.userinfo.controller.UserController;
import com.n.userinfo.model.IdentityModel;
import com.n.utility.CONSTANT;
import com.n.utility.view.ModButton;
import com.n.view.contacts.ContactsPanel;
import com.n.view.contacts.IdentityUserView;

public class ChatFrame extends BasePanel {
	public static ChatFrame chatFrame;
	public MainView mainView;
	private UserController friendInfoController;
	private ContactsPanel contactsPanel;

	public ChatFrame(String _username, String _fullName, String _password,
			String _email, int _userType) {
		chatFrame = this;
		// Controller
		friendInfoController = UserController.getFriendInfoController();
		// MENU
		{

			ModButton menu = new ModButton("Home", Color.decode("#CEDFF0"),
					Color.decode("#9fc9f2"), Color.decode("#529ae3"));
			menu.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					mainView.showPanel(CONSTANT.BROWSER_STRING);
				}
			});
			ModButton logout = new ModButton("Log Out",
					Color.decode("#CEDFF0"), Color.decode("#9fc9f2"),
					Color.decode("#529ae3"));
			logout.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						AppController.getInstance().send(
								new Message(Constant.SIGN_OUT, IdentityModel
										.getIdentityModel().getUserName(),
										"sign out", "server", -1));
						ChatFrame.this.dispose();
						StartApp.getInstance().start();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			// menu.add(item);
			menu.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// Other Menu

				}
			});
			addMenu(menu);
			addMenu(logout);
		}
		// MAIN VIEW
		{
			mainView = new MainView(this);

			friendInfoController.addObserver(mainView);
			addMainView(mainView);
		}
		// PROFILE
		{

			IdentityModel identityModel = new IdentityModel(_username,
					_fullName, _password, _email, _userType);
			IdentityUserView myProfilePanel = new IdentityUserView(this);
			IdentityModel.getIdentityModel().addObserver(myProfilePanel);
			UserController.getFriendInfoController().addMainPanelElement(
					IdentityModel.getIdentityModel(), myProfilePanel);
			addProfilePanel(myProfilePanel);
		}
		// CONTACT PANEL
		{
			contactsPanel = new ContactsPanel(this);
			addContactPanel(contactsPanel);
		}
		new BackgroundWork(new BackgroundWorkable() {

			@Override
			public void finished() {
				contactsPanel.setPresence("", Constant.BUSY);
			}

			@Override
			public Long donInBackground() {
				try {
					Thread.sleep(100);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}
		}).execute();
		;

	}

	public static ChatFrame getInstance() throws IOException {
		if (chatFrame != null) {
			return chatFrame;
		} else {
			throw new IOException("Chat Frame is null.");
		}
	}

	public ContactsPanel getContactsPanel() {
		return contactsPanel;
	}

	public UserController getUserController() {
		return friendInfoController;
	}

	public static ChatFrame getFreshInstance(String userName, String fullName,
			String string, String email, int userType) {
		new ChatFrame(userName, fullName, string, email, userType);
		return chatFrame;
	}
}
