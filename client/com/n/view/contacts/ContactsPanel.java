package com.n.view.contacts;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import com.n.userinfo.controller.UserController;
import com.n.utility.view.UtilityMethod;
import com.n.view.ChatFrame;

/**
 * ContactsPanel hold People contacts and Group. User Select here to whom with
 * chat
 * 
 *
 */
public class ContactsPanel extends JPanel {

	private static final String GROUP_LIST = "grouplist";
	final private static String USER_LIST = "userlist";
	private static final long serialVersionUID = 2591261139391637246L;
	/**
	 * Button to show user
	 */
	private JToggleButton userListButton;
	/**
	 * Button to show Group name
	 */
	private JToggleButton groupListButton;
	/**
	 * ContactsButton Selected or Not
	 */
	UserController friendInfoController;
	private boolean isUserListButtonSelected = true;
	/**
	 * UserList and Group list holder layout to show Contacts List or Group list
	 * at a time
	 */
	private CardLayout layout = new CardLayout();
	/**
	 * User List
	 */
	private UserListPanel userListPanel;
	/**
	 * Group list
	 */
	private GroupListPanel groupListPanel;
	/**
	 * Hold userList and GroupList panel. This Panel Use Cardlayout to show
	 * userList and groupList panel at a time
	 */
	private JPanel userAndGroupListHolder = new JPanel();

	public ContactsPanel(ChatFrame chatFrame) {
		setLayout(new BorderLayout());
		friendInfoController = chatFrame.getUserController();

		// Top area to show contactsButton and Group Button
		{
			JPanel conAndGroup = new JPanel();
			conAndGroup.setLayout(null);
			conAndGroup.setPreferredSize(new Dimension(0, 50));
			// Contacts Button
			{
				userListButton = new JToggleButton("Contacts",
						UtilityMethod.getImageIcon("/resources/cont.png"));
				userListButton.setFont(new Font("Arial", Font.PLAIN, 14));
				userListButton.setBounds(0, 0, 137, 50);
				userListButton.setContentAreaFilled(false);
				userListButton.setBackground(Color.WHITE);
				userListButton.setOpaque(true);
				userListButton.setFocusPainted(false);
				userListButton.setHorizontalTextPosition(JButton.RIGHT);
				userListButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {

						if (!isUserListButtonSelected) {
							SwingUtilities.invokeLater(new Runnable() {
								public void run() {
									groupListButton.setBackground(new Color(
											220, 220, 220));
									userListButton.setBackground(Color.white);

									isUserListButtonSelected = true;
									// TODO Other staff goes here
									layout.show(userAndGroupListHolder,
											USER_LIST);
									revalidate();

								}
							});

						}

					}
				});
				conAndGroup.add(userListButton);
			}
			// Group Button
			{
				groupListButton = new JToggleButton("Group",
						UtilityMethod.getImageIcon("/resources/group.png"));
				groupListButton.setFocusPainted(false);
				groupListButton.setFont(new Font("Arial", Font.PLAIN, 14));
				groupListButton.setBounds(137, 0, 137, 50);
				groupListButton.setContentAreaFilled(false);
				groupListButton.setBackground(new Color(220, 220, 220));
				groupListButton.setOpaque(true);
				groupListButton.setHorizontalTextPosition(JButton.RIGHT);
				groupListButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (isUserListButtonSelected) {
							SwingUtilities.invokeLater(new Runnable() {

								@Override
								public void run() {
									userListButton.setBackground(new Color(220,
											220, 220));
									groupListButton.setBackground(Color.white);
									isUserListButtonSelected = false;
									// TODO Here other staff goes
									layout.show(userAndGroupListHolder,
											GROUP_LIST);
									revalidate();

								}
							});

						}
					}
				});
				conAndGroup.add(groupListButton);
			}
			// Contacts List Panel show-> UserList and Group List
			{
				userAndGroupListHolder = new JPanel();
				userAndGroupListHolder.setLayout(layout);
				// User List Panel
				{

					userListPanel = new UserListPanel();
					friendInfoController.addObserver(userListPanel);
					userAndGroupListHolder.add(userListPanel, USER_LIST);
				}
				// Group List panel
				{
					groupListPanel = new GroupListPanel();
					userAndGroupListHolder.add(groupListPanel, GROUP_LIST);
				}
			}
			add(conAndGroup, BorderLayout.NORTH);
			add(userAndGroupListHolder, BorderLayout.CENTER);
		}
	}

	public void addFriend(String userName, String fullName, String address,
			int status, boolean notified) {
		friendInfoController.addFriend(userName, fullName, address,
				UtilityMethod.getImageIcon("/resources/dummyprofile.png"),
				status, notified);
	}

	public void setPresence(String sender, int presenceType) {
		userListPanel.setPresence(sender, presenceType);
	}

	public void refresh() {
		userListPanel.rearrangeFriendPanel();

	}
}
