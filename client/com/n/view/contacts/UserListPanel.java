package com.n.view.contacts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.function.BiConsumer;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import com.common.Constant;
import com.n.utility.ChangedProperty;
import com.n.utility.PropertyList;
import com.n.utility.view.JScrollPanelMod;
import com.n.utility.view.UtilityMethod;
import com.n.view.BasePanel;

/**
 * User contacts list display here and user select a contact to chat
 */
public class UserListPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 8604723300163166959L;
	/**
	 * Filter Panel hold filter action (All, Online, Away, Busy, Offline )
	 * Filter
	 */
	private JPanel filterPanel;
	private int activeFilter = -1;
	/**
	 * User List show in here
	 */
	/**
	 * Friend Panel List
	 */
	private ArrayList<FriendInfoView> friendInfoViews = new ArrayList<FriendInfoView>();
	private JScrollPanelMod userListInScrollPane = new JScrollPanelMod();

	public static final int USER_TILE_HEIGHT = 53;

	public UserListPanel() {
		setLayout(new BorderLayout());
		// TODO REMOVE
		setBackground(Color.BLUE);
		// Filter panel
		{

			filterPanel = new JPanel(null);
			filterPanel.setBackground(Color.WHITE);
			filterPanel.setBorder(null);
			filterPanel.setPreferredSize(new Dimension(0, 35));
			// All or Online popup
			PopupComboAllOrOnline allOrOnline = new PopupComboAllOrOnline();
			filterPanel.add(allOrOnline);
			add(filterPanel, BorderLayout.NORTH);
		}
		{
			// TODO DUMMY To Remove

			userListInScrollPane.setVerticalGap(1);

			add(userListInScrollPane, BorderLayout.CENTER);
		}
		rearrangeFriendPanel();
	}

	/**
	 * Combobox for All and Online Filter
	 */
	class PopupComboAllOrOnline extends JPanel implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JPopupMenu pop;
		JCheckBoxMenuItem[] cbList;
		String[] menuList = { "All", "Online", "Away", "Busy", "Offline" };
		JLabel label = new JLabel("All");

		JLabel icoLabel = new JLabel(
				UtilityMethod.getImageIcon("/resources/downarrow.png"));

		public PopupComboAllOrOnline() {

			ButtonGroup btnGroup = new ButtonGroup();
			setLayout(null);
			setBackground(Color.WHITE);
			setBounds(0, 5, 275, 30);
			pop = new JPopupMenu();
			cbList = new JCheckBoxMenuItem[menuList.length];
			for (int i = 0; i < menuList.length; i++) {
				cbList[i] = new JCheckBoxMenuItem(menuList[i]);
				cbList[i].addActionListener(PopupComboAllOrOnline.this);
				pop.add(cbList[i]);
				btnGroup.add(cbList[i]);
			}
			cbList[0].setSelected(true);
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					pop.show(PopupComboAllOrOnline.this, 45, 30);
				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					setBackground(new Color(217, 217, 217));
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					setBackground(Color.WHITE);
				}
			});
			label.setBounds(10, 0, 35, 30);
			icoLabel.setBounds(35, 0, 30, 30);

			add(label);
			add(icoLabel);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			switch (arg0.getActionCommand()) {
			case "All":
				activeFilter = -1;
				break;
			case "Online":
				activeFilter = Constant.ONLINE;
				break;
			case "Away":
				activeFilter = Constant.AWAY;
				break;
			case "Busy":
				activeFilter = Constant.BUSY;
				break;
			case "Offline":
				activeFilter = Constant.OFFLINE;
				break;

			default:
				break;
			}
			label.setText(arg0.getActionCommand());
			rearrangeFriendPanel();
		}
	}

	private void addFriendInArray(FriendInfoView view) {
		friendInfoViews.add(view);
		rearrangeFriendPanel();
	}

	public void rearrangeFriendPanel() {
		Collections.sort(friendInfoViews, new FriendInfoViewComparator());
		userListInScrollPane.clear();
		validate();
		revalidate();
		if (activeFilter == -1) {
			for (FriendInfoView friendInfoView : friendInfoViews) {
				userListInScrollPane.add(friendInfoView);
			}
		} else {

			for (FriendInfoView friendInfoView : friendInfoViews) {
				if (activeFilter == friendInfoView.onlineStatus)
					userListInScrollPane.add(friendInfoView);
			}
		}
		userListInScrollPane.validate();
		userListInScrollPane.revalidate();
		userListInScrollPane.repaint();
	}

	/**
	 * Custom Comparator using in Collection.Sort() method
	 * 
	 * @author Ashiquzzaman
	 *
	 */
	public class FriendInfoViewComparator implements Comparator<FriendInfoView> {
		@Override
		public int compare(FriendInfoView o1, FriendInfoView o2) {
			if (o1.onlineStatus == o2.onlineStatus) {
				return o1.getFullName().compareTo(o2.getFullName());
			}
			return o1.onlineStatus - o2.onlineStatus;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg == null)
			return;
		ChangedProperty<Integer, ?> changedProperty = (ChangedProperty<Integer, ?>) arg;
		if (changedProperty.isChange(PropertyList.ADD_FRIEND)) {

			FriendInfoView friendInfoView = (FriendInfoView) changedProperty
					.getNewValue(PropertyList.ADD_FRIEND);

			friendInfoView.setSize(BasePanel.CONTACT_PANEL_WIDTH,
					UserListPanel.USER_TILE_HEIGHT);
			this.addFriendInArray(friendInfoView);
		}
		if (changedProperty.isChange(PropertyList.REMOVE_FRIEND)) {
			HashMap<String, FriendInfoView> list = (HashMap<String, FriendInfoView>) changedProperty
					.getNewValue(PropertyList.REMOVE_FRIEND);
			friendInfoViews.clear();
			list.forEach(new BiConsumer<String, FriendInfoView>() {
				@Override
				public void accept(String t, FriendInfoView u) {
					friendInfoViews.add(u);

				}
			});
			rearrangeFriendPanel();
		}
	}

	public void setPresence(String sender, int presenceType) {
		for (FriendInfoView friendInfoView : friendInfoViews) {
			if (friendInfoView.getUserName().equals(sender)) {
				friendInfoView.onlineStatus = presenceType;
			}
		}
		rearrangeFriendPanel();
	}
}
