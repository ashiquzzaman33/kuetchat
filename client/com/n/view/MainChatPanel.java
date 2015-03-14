package com.n.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import com.client.controller.AppController;
import com.common.Constant;
import com.common.Message;
import com.n.userinfo.controller.UserController;
import com.n.userinfo.model.FriendInfoModel;
import com.n.userinfo.model.IdentityModel;
import com.n.utility.ChangedProperty;
import com.n.utility.JScrollPanelX;
import com.n.utility.PropertyList;
import com.n.utility.view.UtilityMethod;

public class MainChatPanel extends JPanel implements Observer {
	private static final long serialVersionUID = -5691429178970325474L;
	private ImageIcon profilePic = UtilityMethod
			.getImageIcon("/resources/dummyprofile.png");
	private JLabel lblFullName;
	private JButton plusButton;
	private JTextArea textArea = new JTextArea();
	private JButton sendButton = new JButton();
	private int width = 0;
	private JScrollPanelX scrollPanelX = new JScrollPanelX();;
	private JPanel messageHolderPanel;
	private FriendInfoModel model;

	public MainChatPanel(FriendInfoModel model, ImageIcon imageIcon) {
		this.model = model;
		this.profilePic = imageIcon;
		setLayout(new BorderLayout());
		setBackground(Color.white);
		// Upper Panel
		JPanel upperPanel = new JPanel(new BorderLayout());
		upperPanel.setBackground(Color.white);
		upperPanel.setPreferredSize(new Dimension(0, 50));
		JLabel separatero = new JLabel(
				UtilityMethod.getImageIcon("/resources/seperator.png"));
		separatero.setPreferredSize(new Dimension(2, 1));
		upperPanel.add(separatero, BorderLayout.SOUTH);

		// setLayout(new BorderLayout());
		// TODO Remove setPreferredSize

		// Header PANEL
		HeaderPanel headerPanel = new HeaderPanel();
		upperPanel.add(headerPanel, BorderLayout.CENTER);
		headerPanel.setPreferredSize(new Dimension(0, 50));
		// setBackground(Color.white);
		// add(headerPanel, BorderLayout.NORTH);
		messageHolderPanel = new JPanel();
		scrollPanelX.setPreferredSize(new Dimension(700, 270));
		scrollPanelX.setVerticalGap(5);
		scrollPanelX.setBorder(null);
		scrollPanelX.setBackground(Color.WHITE);
		messageHolderPanel.setBackground(Color.white);
		add(messageHolderPanel);

		GroupLayout gl_messageHolderPanel = new GroupLayout(messageHolderPanel);
		gl_messageHolderPanel.setHorizontalGroup(gl_messageHolderPanel
				.createParallelGroup(Alignment.CENTER).addGroup(
						gl_messageHolderPanel.createSequentialGroup()
								.addGap(0, 150, 400)
								.addComponent(scrollPanelX, 700, 700, 700)
								.addGap(0, 150, 400)));
		gl_messageHolderPanel.setVerticalGroup(gl_messageHolderPanel
				.createParallelGroup(Alignment.LEADING).addGroup(
						gl_messageHolderPanel
								.createSequentialGroup()
								.addGap(10)
								.addComponent(scrollPanelX,
										GroupLayout.DEFAULT_SIZE, 235,
										Short.MAX_VALUE).addGap(5)));
		messageHolderPanel.setLayout(gl_messageHolderPanel);
		add(upperPanel, BorderLayout.NORTH);
		add(new EditBoxHolder(), BorderLayout.SOUTH);
		// setPreferredSize(new Dimension(800, 600));

	}

	public void addMessage(String sender, String message, Date date,
			boolean isUser) {
		HistoryBlock historyBlock = new HistoryBlock(sender, message, date,
				isUser);
		historyBlock.setSize(700, historyBlock.getPreferredSize().height);
		scrollPanelX.add(historyBlock);
	}

	public void sendMessage() {
		if (textArea.getText().equals(""))
			return;
		String message = textArea.getText();
		textArea.setText("");
		// TODO Send Message
		;
		try {
			AppController.getInstance().send(
					new Message(Constant.MESSAGE, IdentityModel
							.getIdentityModel().getUserName(), message,
							UserController.getFriendInfoController()
									.getSelectedFriend(), -1));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		addMessage(IdentityModel.getIdentityModel().getUserName(), message,
				Calendar.getInstance().getTime(), true);
	}

	class HeaderPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1248959103476296683L;

		public HeaderPanel() {

			setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
			setBackground(Color.WHITE);
			// JPanelBackImage image = new JPanelBackImage(profilePic);
			JLabel image = new JLabel(profilePic);
			image.setPreferredSize(new Dimension(40, 35));
			lblFullName = new JLabel(model.getFullNameString());

			// nameLabel.setPreferredSize(new Dimension(300, 50));
			JButton callButton = new JButton(
					UtilityMethod.getImageIcon("/resources/call.png"));
			callButton.setRolloverIcon(UtilityMethod
					.getImageIcon("/resources/callr.png"));
			callButton.setPressedIcon(UtilityMethod
					.getImageIcon("/resources/callp.png"));
			callButton.setBorderPainted(false);
			callButton.setContentAreaFilled(false);
			callButton.setFocusPainted(false);
			plusButton = new JButton(
					UtilityMethod.getImageIcon("/resources/plus.png"));
			plusButton.setRolloverIcon(UtilityMethod
					.getImageIcon("/resources/plusr.png"));
			plusButton.setPressedIcon(UtilityMethod
					.getImageIcon("/resources/plusp.png"));
			plusButton.setBorderPainted(false);
			plusButton.setContentAreaFilled(false);
			plusButton.setFocusPainted(false);

			add(image);
			add(lblFullName);
			add(callButton);
			add(plusButton);

		}
	}

	class EditBoxHolder extends JPanel {

		public EditBoxHolder() {
			setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
			setBackground(Color.WHITE);
			setPreferredSize(new Dimension(594, 195 + 20));
			sendButton.setIcon(UtilityMethod
					.getImageIcon("/resources/send.png"));
			sendButton.setRolloverIcon(UtilityMethod
					.getImageIcon("/resources/sendr.png"));
			sendButton.setPressedIcon(UtilityMethod
					.getImageIcon("/resources/sendp.png"));
			sendButton.setBorderPainted(false);
			sendButton.setContentAreaFilled(false);
			sendButton.setFocusPainted(false);
			sendButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					sendMessage();
				}
			});

			Box box = new Box();
			box.setBounds(0, 0, 594, 129);
			sendButton.setBounds(0, 145, 59, 27);
			JPanel holder = new JPanel(null);
			holder.setPreferredSize(new Dimension(594, 195));
			holder.setOpaque(false);
			holder.add(sendButton);
			holder.add(box);
			add(holder);

		}

		class Box extends JPanel {

			private Image[] icons = {
					UtilityMethod.getImage("/resources/chateditbox.png"),
					UtilityMethod.getImage("/resources/chateditboxactive.png") };
			private int iconNum = 0;

			public Box() {
				setCursor(new Cursor(Cursor.TEXT_CURSOR));

				setLayout(null);
				setPreferredSize(new Dimension(594, 129));
				textArea.setLineWrap(true);
				textArea.setMargin(new Insets(5, 5, 5, 5));
				textArea.setWrapStyleWord(true);
				textArea.setText("");
				KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0,
						false);
				InputMap inputMap = textArea.getInputMap();
				inputMap.put(enter, "none");
				// add shift+enter keybinding can be on pressed or released i.e
				// false or
				// true
				textArea.getInputMap(JComponent.WHEN_FOCUSED).put(
						KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,
								KeyEvent.SHIFT_DOWN_MASK, true),
						"Shift+Enter released");
				textArea.getActionMap().put("Shift+Enter released",
						new AbstractAction() {
							private static final long serialVersionUID = 1L;

							@Override
							public void actionPerformed(ActionEvent ae) {
								textArea.setText(textArea.getText() + "\n");
							}
						});
				textArea.getInputMap(JComponent.WHEN_FOCUSED).put(
						KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
						"Enter released");
				textArea.getActionMap().put("Enter released",
						new AbstractAction() {
							@Override
							public void actionPerformed(ActionEvent ae) {
								sendMessage();
							}
						});
				JScrollPane pane = new JScrollPane(textArea);
				pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				pane.setBorder(null);
				pane.setBounds(3, 3, 588, 123);
				add(pane);
				textArea.addFocusListener(new FocusListener() {

					@Override
					public void focusLost(FocusEvent e) {
						iconNum = 0;
						repaint();
						textArea.repaint();

					}

					@Override
					public void focusGained(FocusEvent e) {
						iconNum = 1;
						repaint();
						textArea.repaint();
					}
				});
			}

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(icons[iconNum], 0, 0, 594, 129, this);
			}
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		ChangedProperty<Integer, ?> changedProperty = (ChangedProperty<Integer, ?>) arg;
		if (changedProperty.isChange(PropertyList.FULL_NAME)) {
			lblFullName.setText((String) changedProperty
					.getNewValue(PropertyList.FULL_NAME));
		}
		if (changedProperty.isChange(PropertyList.IMAGE_PROFILE_PIC)) {
			this.profilePic = (ImageIcon) changedProperty
					.getNewValue(PropertyList.PROFILE_PIC);
		}
		if (changedProperty.isChange(PropertyList.ARRIVE_MESSAGE)) {
			Message message = (Message) changedProperty
					.getNewValue(PropertyList.ARRIVE_MESSAGE);
			addMessage(message.sender, message.content,
					message.date == null ? Calendar.getInstance().getTime()
							: message.date, IdentityModel.getIdentityModel()
							.getUserName().equals(message.sender));
		}
	}

	public String getUserName() {
		return model.getUserName();
	}
}
