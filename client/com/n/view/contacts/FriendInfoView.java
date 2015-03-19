package com.n.view.contacts;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.common.Constant;
import com.n.userinfo.controller.UserController;
import com.n.utility.ChangedProperty;
import com.n.utility.PropertyList;
import com.n.utility.view.UtilityMethod;

/**
 * Friend Contact panel.
 *
 */
public class FriendInfoView extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1975981075486385048L;
	private ImageIcon[] logoOnlineStatusIcons = {
			UtilityMethod.getImageIcon("/resources/logo_online_large.png"),
			UtilityMethod.getImageIcon("/resources/logo_away_large.png"),
			UtilityMethod.getImageIcon("/resources/logo_busy_large.png"),
			UtilityMethod.getImageIcon("/resources/logo_offline_large.png")

	};
	private ImageIcon[] backgrounds = {
			UtilityMethod
					.getImageIcon("/resources/friendbackgroundselected.png"),
			UtilityMethod.getImageIcon("/resources/friendbackground.png") };
	private ImageIcon iconNotification = UtilityMethod
			.getImageIcon("/resources/iconotification.png");

	private ImageIcon profiIcon;
	public int onlineStatus = Constant.ONLINE;
	private int currentBackground = 1;

	private JLabel lblFullName;
	private JLabel lblCountry;
	private String name;
	private String country;
	boolean isNotified = true;
	private String userName;

	public FriendInfoView(String userName, String fullName, String counry,
			ImageIcon profilePic, int onlineStatus, boolean isNotified) {
		setLayout(null);
		setCursor(new Cursor(Cursor.HAND_CURSOR));

		this.setUserName(userName);
		this.name = fullName;
		this.country = counry;
		this.profiIcon = profilePic;
		this.onlineStatus = onlineStatus;
		this.isNotified = isNotified;

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				UserController.getFriendInfoController().setSelectedFriend(
						FriendInfoView.this.userName);
				repaint();

			}
		});

		// User name Label
		lblFullName = new JLabel(name);
		lblFullName.setFont(new Font("Arial", Font.PLAIN, 13));
		lblFullName.setBounds(100, 10, 140, 20);
		add(lblFullName);
		// Country Label
		lblCountry = new JLabel(country);
		lblCountry.setHorizontalAlignment(SwingConstants.LEFT);
		lblCountry.setFont(new Font("Arial", Font.PLAIN, 12));
		lblCountry.setBounds(100, 30, 140, 20);
		add(lblCountry);
		revalidate();

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgrounds[currentBackground].getImage(), 0, 0,
				getSize().width, getSize().height, this);

		g.drawImage(profiIcon.getImage(), 5, 8, 40, 35, null);

		g.drawImage(logoOnlineStatusIcons[onlineStatus].getImage(), 50, 10, 40,
				35, null);
		if (isNotified) {
			g.drawImage(iconNotification.getImage(), getSize().width - 25, 20,
					15, 15, this);
		}
	}

	public String getFullName() {
		return name;
	}

	@Override
	public void update(Observable o, Object arg) {

		ChangedProperty<Integer, ?> propertyChange = (ChangedProperty<Integer, ?>) arg;
		// Online Status Change
		if (propertyChange.isChange(PropertyList.ONLINE_STATUS)) {
			onlineStatus = (int) propertyChange
					.getNewValue(PropertyList.ONLINE_STATUS);
		}
		// NOTIFICATION Status
		if (propertyChange.isChange(PropertyList.NOTIFICATION)) {

			isNotified = (boolean) propertyChange
					.getNewValue(PropertyList.NOTIFICATION);
		}
		// Profile Pic change
		if (propertyChange.isChange(PropertyList.PROFILE_PIC)) {
			profiIcon = (ImageIcon) propertyChange
					.getNewValue(PropertyList.PROFILE_PIC);
		}
		// Name Change
		if (propertyChange.isChange(PropertyList.FULL_NAME)) {
			name = (String) propertyChange.getNewValue(PropertyList.FULL_NAME);
			lblFullName.setText(name);
		}
		// Country Change
		if (propertyChange.isChange(PropertyList.COUNTRY)) {
			country = (String) propertyChange.getNewValue(PropertyList.COUNTRY);
			lblCountry.setText(country);
		}
		if (propertyChange.isChange(PropertyList.SELECTION_STATUS)) {
			boolean status = (boolean) propertyChange
					.getNewValue(PropertyList.SELECTION_STATUS);
			if (status) {
				currentBackground = 0;
			} else {
				currentBackground = 1;
			}
			repaint();
		}
		repaint();
		revalidate();
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
