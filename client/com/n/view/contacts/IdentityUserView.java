package com.n.view.contacts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

import com.client.controller.AppController;
import com.common.Constant;
import com.common.Message;
import com.n.userinfo.controller.UserController;
import com.n.userinfo.model.IdentityModel;
import com.n.utility.ChangedProperty;
import com.n.utility.PropertyList;
import com.n.utility.view.JPanelBackImage;
import com.n.utility.view.UtilityMethod;
import com.n.view.BasePanel;
import com.n.view.ChatFrame;

/**
 * Profile Header show Profile pic, Mode and Name After click this Main Box show
 * Full profile of user.
 * 
 * @see MyProfileFull
 *
 */
public class IdentityUserView extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6916674250476116111L;
	private JLabel nameLabel;
	private JPanel modePanel;
	private JPanelBackImage profilePicPanel;
	private Color rolloverColor = new Color(229, 229, 229);
	private Color defaultColor = Color.white;//
	private Color selectedColor = new Color(193, 217, 241);
	private JPopupMenu popupChangeProfilePic = new JPopupMenu();
	private JMenuItem changeProfilePic = new JMenuItem("Change Picture");
	private ImageIcon profilePicture;
	// TODO Change for test MyProfile1
	private PopupComboUserMode userModeSelection;
	JPanel holderPanel;
	ChatFrame chatFrame;
	private boolean isSelected = false;

	public IdentityUserView(ChatFrame chatFrame) {
		this.chatFrame = chatFrame;
		setLayout(new BorderLayout());
		JSeparator separator = new JSeparator();
		separator.setPreferredSize(new Dimension(0, 1));
		separator.setBackground(new Color(217, 217, 217));
		add(separator, BorderLayout.SOUTH);

		profilePicture = UtilityMethod
				.getImageIconFormFile(BasePanel.APPLICATION_IMAGE_DIR + "\\"
						+ IdentityModel.getIdentityModel().getUserName()
						+ ".png");

		holderPanel = new JPanel();

		holderPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		setBackground(defaultColor);
		popupChangeProfilePic.add(changeProfilePic);
		changeProfilePic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				profilePicChooser();
			}
		});

		// Profile pic
		{
			if (profilePicture == null) {
				profilePicture = UtilityMethod
						.getImageIcon("/resources/cont.png");
				try {

					UtilityMethod.saveImageToFile(profilePicture,
							BasePanel.APPLICATION_IMAGE_DIR, IdentityModel
									.getIdentityModel().getUserName());

				} catch (IOException e1) {
					System.err.println(e1.getMessage());
				}
			}
			profilePicPanel = new JPanelBackImage(profilePicture);
			profilePicPanel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON3) {
						popupChangeProfilePic.show(IdentityUserView.this,
								e.getX(), e.getY());
					}
				};
			});
			profilePicPanel.setPreferredSize(new Dimension(60, 42));
			profilePicPanel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						profilePicChooser();
					}
				};
			});
		}
		// MODE PANEL
		{
			modePanel = new JPanel();
			modePanel.setBackground(defaultColor);
			modePanel.setPreferredSize(new Dimension(50, 42));
			modePanel.setLayout(new BorderLayout());
			userModeSelection = new PopupComboUserMode();
			userModeSelection.setBounds(32, 10, 40, 25);
			modePanel.add(userModeSelection);
		}
		holderPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!IdentityModel.getIdentityModel().isSelected()) {
					holderPanel.setBackground(rolloverColor);
					userModeSelection.setBackground(rolloverColor);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (!IdentityModel.getIdentityModel().isSelected()) {
					holderPanel.setBackground(defaultColor);
					userModeSelection.setBackground(defaultColor);

				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				UserController.getFriendInfoController().setSelectedFriend(
						IdentityModel.getIdentityModel().getUserName());
				IdentityModel.getIdentityModel().setSelected(true);
				// selectMyProfile(true);
				// chatFrame.mainView.showPanel(CONSTANT.MY_PROFILE_STRING);
				// TODO If not complete
			}
		});
		// Name Label
		{
			nameLabel = new JLabel(IdentityModel.getIdentityModel()
					.getUserName());
			nameLabel.setPreferredSize(new Dimension(150, 30));
			nameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		}
		holderPanel.setBackground(defaultColor);
		modePanel.setBackground(defaultColor);
		holderPanel.add(profilePicPanel);

		holderPanel.add(modePanel);
		holderPanel.add(nameLabel);

		holderPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(holderPanel, BorderLayout.CENTER);
	}

	public void selectMyProfile(boolean value) {
		if (value) {
			if (isSelected) {
				holderPanel.setBackground(selectedColor);
				userModeSelection.setBackground(selectedColor);
				UserController.getFriendInfoController().setSelectedFriend(
						IdentityModel.getIdentityModel().getUserName());
			}
		} else {
			holderPanel.setBackground(defaultColor);
			userModeSelection.setBackground(defaultColor);
		}
		repaint();
		revalidate();
		isSelected = value;

	}

	public void profilePicChooser() {
		JFileChooser fileChooser = new JFileChooser();
		FileFilter allFileFilter = fileChooser.getChoosableFileFilters()[0];
		if (allFileFilter != null)
			fileChooser.removeChoosableFileFilter(allFileFilter);
		// Get array of available formats
		String[] suffices = ImageIO.getReaderFileSuffixes();
		FileFilter imageFilter = new FileNameExtensionFilter("Image files",
				ImageIO.getReaderFileSuffixes());
		fileChooser.addChoosableFileFilter(imageFilter);
		for (int i = 0; i < suffices.length; i++) {
			FileFilter filter = new FileNameExtensionFilter(
					suffices[i].toUpperCase() + "(*."
							+ suffices[i].toLowerCase() + ")", suffices[i]);
			fileChooser.addChoosableFileFilter(filter);
		}

		int x = fileChooser.showOpenDialog(this);
		if (x == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file != null) {
				try {

					BufferedImage img = Scalr.resize(ImageIO.read(file),
							Method.BALANCED, 200, Scalr.OP_ANTIALIAS);

					profilePicture = new ImageIcon(img);
					profilePicPanel.setImage(profilePicture.getImage());

					UtilityMethod.saveImageToFile(img,
							BasePanel.APPLICATION_IMAGE_DIR, IdentityModel
									.getIdentityModel().getUserName());
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
		}
	}

	/**
	 * Combobox for Online, Away, Busy, Offline mode
	 *
	 */
	class PopupComboUserMode extends JPanel implements ActionListener {
		private static final long serialVersionUID = 1L;
		JPopupMenu pop;
		String[] menuList = { "Online", "Away", "Busy", "Offline" };
		JMenuItem item[] = new JMenuItem[menuList.length];
		JLabel label = new JLabel();
		private ImageIcon[] logos = {
				UtilityMethod.getImageIcon("/resources/logo_online.png"),
				UtilityMethod.getImageIcon("/resources/logo_away.png"),
				UtilityMethod.getImageIcon("/resources/logo_busy.png"),
				UtilityMethod.getImageIcon("/resources/logo_offline.png") };
		private ImageIcon[] logosMenu = {
				UtilityMethod.getImageIcon("/resources/logo_online_menu.png"),
				UtilityMethod.getImageIcon("/resources/logo_away_menu.png"),
				UtilityMethod.getImageIcon("/resources/logo_busy_menu.png"),
				UtilityMethod.getImageIcon("/resources/logo_offline_menu.png") };

		JLabel icoLabel = new JLabel(
				UtilityMethod.getImageIcon("/resources/downarrow.png"));
		int selected = 0;

		public PopupComboUserMode() {

			setLayout(null);
			pop = new JPopupMenu();
			setBackground(defaultColor);
			ButtonGroup group = new ButtonGroup();
			for (int i = 0; i < menuList.length; i++) {
				item[i] = new JMenuItem(menuList[i]);
				item[i].addActionListener(PopupComboUserMode.this);
				item[i].setIcon(logosMenu[i]);
				pop.add(item[i]);

			}
			// cbList[0].setSelected(true);
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					pop.show(PopupComboUserMode.this, 0, 30);
				}

				public void mouseEntered(MouseEvent e) {
					if (!IdentityModel.getIdentityModel().isSelected()) {
						IdentityUserView.this.setBackground(defaultColor);
						setBackground(rolloverColor);
					} else {
						setBackground(rolloverColor);
					}
				};

				public void mouseExited(MouseEvent e) {
					if (IdentityModel.getIdentityModel().isSelected()) {
						setBackground(selectedColor);
					} else {
						setBackground(defaultColor);
					}
				};
			});
			label.setIcon(logos[0]);
			label.setBounds(3, 7, 25, 25);

			icoLabel.setBounds(26, 8, 25, 25);

			add(label);
			add(icoLabel);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO
			if (arg0.getActionCommand().equals(menuList[0])) {
				label.setIcon(logos[0]);
				item[selected].setEnabled(true);
				item[0].setEnabled(false);
				selected = 0;
				try {
					AppController.getInstance().send(
							new Message(Constant.PRESENCE_CHANGE, IdentityModel
									.getIdentityModel().getUserName(),
									Constant.ONLINE + "", "All", -1));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (arg0.getActionCommand().equals(menuList[1])) {
				label.setIcon(logos[1]);
				item[selected].setEnabled(true);
				item[1].setEnabled(false);
				selected = 1;
				try {
					AppController.getInstance().send(
							new Message(Constant.PRESENCE_CHANGE, IdentityModel
									.getIdentityModel().getUserName(),
									Constant.AWAY + "", "All", -1));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (arg0.getActionCommand().equals(menuList[2])) {
				label.setIcon(logos[2]);
				item[selected].setEnabled(true);
				item[2].setEnabled(false);
				selected = 2;
				try {
					AppController.getInstance().send(
							new Message(Constant.PRESENCE_CHANGE, IdentityModel
									.getIdentityModel().getUserName(),
									Constant.BUSY + "", "All", -1));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (arg0.getActionCommand().equals(menuList[3])) {
				label.setIcon(logos[3]);
				item[selected].setEnabled(true);
				item[3].setEnabled(false);
				selected = 3;
				try {
					AppController.getInstance().send(
							new Message(Constant.PRESENCE_CHANGE, IdentityModel
									.getIdentityModel().getUserName(),
									Constant.OFFLINE + "", "All", -1));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		@SuppressWarnings("unchecked")
		ChangedProperty<Integer, ?> property = (ChangedProperty<Integer, ?>) arg;
		if (property.isChange(PropertyList.SELECTION_STATUS)) {

			boolean value = (boolean) property
					.getNewValue(PropertyList.SELECTION_STATUS);
			selectMyProfile(value);
		}
	}
}
