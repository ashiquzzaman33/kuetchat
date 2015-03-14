package com.kuetchat.client.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.client.controller.AppController;
import com.common.Constant;
import com.common.Message;
import com.common.UserDetails;
import com.kuetchat.client.utility.BackgroundWork;
import com.kuetchat.client.utility.BackgroundWorkable;
import com.kuetchat.client.utility.CallBackable;
import com.kuetchat.client.utility.SaveLoginInfo;
import com.n.view.ChatFrame;

public class LoginWindow extends JFrame implements BackgroundWorkable,
		CallBackable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String infoFilePath = System.getProperty("user.home")
			+ "/d_info";
	private JPanel back_panel;
	private Point initialClick;
	private JCheckBox cbRememberMe;
	private LoginWindow frm = this;
	private JButton btnSignIn;
	public JLabel lblError;
	public static String SEPERATOR = "@!1!@";
	private LoginWindow loginWindow;
	private JPanel loadingPanel;
	private JButton crossButton;
	private boolean success;
	private JPanel contentPanel;
	private InputField userNameInputField;
	private InputField passwordInputField;
	private JButton btnTyrAgain = new JButton();

	/**
	 * Create the frame.
	 */
	public LoginWindow() {

		setIconImage(new ImageIcon(
				LoginWindow.class.getResource("/resources/lg.png")).getImage());
		loginWindow = this;
		back_panel = new JPanelX("/resources/login.png", this, true);
		setContentPane(back_panel);
		back_panel.setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(450, 450);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		addMoveListener(back_panel);
		setFocusTraversalKeysEnabled(false);
		// TODO Create Account
		// createAccount = new CreateAccount(LoginWindow.this);
		// {
		contentPanel = new JPanel();
		contentPanel.setLayout(null);
		contentPanel.setOpaque(false);
		contentPanel.setBounds(44, 187, 374, 214);
		back_panel.add(contentPanel);
		contentPanel.setVisible(false);

		btnTyrAgain.setIcon(new ImageIcon(LoginWindow.class
				.getResource("/resources/try.png")));
		btnTyrAgain.setRolloverIcon(new ImageIcon(LoginWindow.class
				.getResource("/resources/tryr.png")));
		btnTyrAgain.setPressedIcon(new ImageIcon(LoginWindow.class
				.getResource("/resources/tryp.png")));
		btnTyrAgain.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnTyrAgain.setBounds(170, 215, 135, 70);
		btnTyrAgain.setFont(new Font("serif", Font.BOLD, 25));
		btnTyrAgain.setForeground(Color.WHITE);
		btnTyrAgain.setContentAreaFilled(false);
		btnTyrAgain.setFocusPainted(false);
		btnTyrAgain.setBorderPainted(false);
		btnTyrAgain.setVisible(false);
		btnTyrAgain.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				LoginWindow.this.initNetwork();

			}
		});
		getContentPane().add(btnTyrAgain);
		// }
		// createAccount.setVisible(false);
		{
			loadingPanel = new LoadingPanel();
			loadingPanel.setLocation(180, 200);
			loadingPanel.setVisible(true);
			getContentPane().add(loadingPanel);
		}
		setFocusable(true);
		{
			crossButton = new JButton("");
			crossButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {

					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							dispose();
							System.exit(0);
						}
					});

				}
			});

			crossButton.setIcon(new ImageIcon(LoginWindow.class
					.getResource("/resources/cross.png")));

			crossButton.setRolloverIcon(new ImageIcon(LoginWindow.class
					.getResource("/resources/cra.png")));
			crossButton.setBounds(405, 11, 24, 35);
			crossButton.setMargin(new Insets(0, 0, 0, 0));
			crossButton.setFocusable(false);
			crossButton.setContentAreaFilled(false);
			crossButton.setBorderPainted(false);
			back_panel.add(crossButton);

		}
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent arg0) {
				userNameInputField.transferFocus();
			}
		});

		lblError = new JLabel();
		lblError.setHorizontalAlignment(SwingConstants.CENTER);
		lblError.setForeground(Color.decode("#C3C3C3"));
		lblError.setFont(new Font("Serif", Font.BOLD, 17));
		lblError.setBounds(20, 380, 420, 35);
		back_panel.add(lblError);
		// User Name Field
		{

			userNameInputField = new InputField(55, 5, 280, 35, "User Name");
			contentPanel.add(userNameInputField);

			userNameInputField.addKeyListener(KeyEvent.VK_ENTER, "ENTER",
					new AbstractAction() {

						private static final long serialVersionUID = 1L;

						@Override
						public void actionPerformed(ActionEvent arg0) {
							if (!userNameInputField.getText().equals("")
									&& passwordInputField.getPassword().length != 0) {
								validate();
								new BackgroundWork(LoginWindow.this).execute();
							} else {
								lblError.setText("Please Input Username and Password.");
							}
						}
					});

			userNameInputField.addKeyListener(KeyEvent.VK_TAB, "TAB",
					new AbstractAction() {
						private static final long serialVersionUID = 1L;

						@Override
						public void actionPerformed(ActionEvent arg0) {
							passwordInputField.transferFocus();
						}
					});
		}

		// Password Input Field
		{

			passwordInputField = new InputField(55, 50, 280, 35, "Password",
					true);
			contentPanel.add(passwordInputField);
			passwordInputField.addKeyListener(KeyEvent.VK_ENTER, "ENTER",
					new AbstractAction() {

						private static final long serialVersionUID = 1L;

						@Override
						public void actionPerformed(ActionEvent arg0) {
							if (!userNameInputField.getText().equals("")
									&& passwordInputField.getPassword().length != 0) {
								validate();
								new BackgroundWork(LoginWindow.this).execute();
							} else {
								lblError.setText("Please Input Username and Password.");
							}
						}
					});
			passwordInputField.addKeyListener(KeyEvent.VK_TAB, "TAB",
					new AbstractAction() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							userNameInputField.transferFocus();
						}
					});
			passwordInputField.setBorder(null);
			passwordInputField.setOpaque(false);

		}

		cbRememberMe = new JCheckBox();
		cbRememberMe.setBounds(63, 107, 119, 23);
		contentPanel.add(cbRememberMe);
		cbRememberMe.setBackground(Color.WHITE);
		cbRememberMe.setBorderPainted(false);
		cbRememberMe.setContentAreaFilled(false);
		cbRememberMe.setBorderPainted(false);

		JLabel label_1 = new JLabel("");
		label_1.setBounds(67, 112, 200, 14);
		contentPanel.add(label_1);

		label_1.setIcon(new ImageIcon(LoginWindow.class
				.getResource("/resources/remme.png")));
		final JLabel plusAc = new JLabel(new ImageIcon(
				LoginWindow.class.getResource("/resources/newac.png")));
		plusAc.setBounds(24, 129, 185, 30);
		contentPanel.add(plusAc);

		final JLabel forgotPassword = new JLabel(new ImageIcon(
				LoginWindow.class.getResource("/resources/forpas.png")));
		forgotPassword.setBounds(30, 157, 200, 27);
		contentPanel.add(forgotPassword);

		btnSignIn = new JButton("");
		btnSignIn.setFocusPainted(false);
		btnSignIn.setBounds(224, 103, 89, 23);
		contentPanel.add(btnSignIn);
		btnSignIn.setCursor(new Cursor(Cursor.HAND_CURSOR));

		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (!userNameInputField.getText().equals("")
						&& passwordInputField.getPassword().length != 0) {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							validate();
							// TODO
							new BackgroundWork(LoginWindow.this).execute();
						}

					});
				} else {
					lblError.setText("Please Input Username and Password.");
				}
			}
		});
		btnSignIn.setIcon(new ImageIcon(LoginWindow.class
				.getResource("/resources/signin.png")));
		btnSignIn.setRolloverIcon(new ImageIcon(LoginWindow.class
				.getResource("/resources/signinr.png")));
		btnSignIn.setPressedIcon(new ImageIcon(LoginWindow.class
				.getResource("/resources/signinp.png")));
		btnSignIn.setMargin(new Insets(0, 0, 0, 0));

		this.btnSignIn.setContentAreaFilled(false);
		this.btnSignIn.setBorderPainted(false);

		this.btnSignIn.setSelected(false);
		forgotPassword.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent arg0) {
				forgotPassword.setIcon(new ImageIcon(LoginWindow.class
						.getResource("/resources/forpasroll.png")));
				forgotPassword.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				forgotPassword.setIcon(new ImageIcon(LoginWindow.class
						.getResource("/resources/forpas.png")));
				forgotPassword.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				com.n.utility.Browser
						.openLink("http://localhost/kuetchat/forgotpass.php");
			}
		});
		plusAc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				plusAc.setIcon(new ImageIcon(LoginWindow.class
						.getResource("/resources/newacroll1.png")));
				plusAc.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				plusAc.setIcon(new ImageIcon(LoginWindow.class
						.getResource("/resources/newac.png")));
				plusAc.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
				super.mouseClicked(arg0);
				com.n.utility.Browser
						.openLink("http://localhost/kuetchat/register.php");
			}
		});

		SaveLoginInfo s = new SaveLoginInfo();
		String str[] = s.getData(infoFilePath).split(SEPERATOR);
		if (str.length == 2 && (!str[0].equals("*") && !str[1].equals("*"))) {
			userNameInputField.setText(str[0]);
			passwordInputField.setText(str[1]);

			cbRememberMe.setSelected(true);

			// SwingUtilities.invokeLater(new Runnable() {
			// public void run() {
			//
			// new BackgroundWork(LoginWindow.this).execute();
			// validate();
			// }
			//
			// });
		}
		refresh();
		repaint();
		validate();
		revalidate();
		initNetwork();
	}

	public void refresh() {
		SaveLoginInfo s = new SaveLoginInfo();
		String str[] = s.getData(infoFilePath).split(SEPERATOR);
		if (str.length == 2 && (!str[0].equals("*") && !str[1].equals("*"))) {
			userNameInputField.setText(str[0]);
			passwordInputField.setText(str[1]);
			lblError.setText("");
		} else {
			userNameInputField.setText("");
			passwordInputField.setText("");

			lblError.setText("");

		}
		repaint();
	}

	private void initNetwork() {
		new BackgroundWork(new BackgroundWorkable() {

			@Override
			public void finished() {

			}

			@Override
			public Long donInBackground() {
				try {
					contentPanel.setVisible(false);
					btnTyrAgain.setVisible(false);
					loadingPanel.setVisible(true);
					AppController.makeInstance();
					loadingPanel.setVisible(false);
					contentPanel.setVisible(true);
				} catch (IOException e) {
					loadingPanel.setVisible(false);
					btnTyrAgain.setVisible(true);
					lblError.setText("Error: " + "Server not found.");
					StackTraceElement l = new Exception().getStackTrace()[0];
					System.err.println(l.getClassName() + "/"
							+ l.getMethodName() + ":" + l.getLineNumber());
				}
				return null;
			}
		}).execute();
	}

	private void addMoveListener(JPanel panel) {
		panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
			}
		});
		panel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {

				int thisX = LoginWindow.this.getLocation().x;
				int thisY = LoginWindow.this.getLocation().y;

				int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
				int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				LoginWindow.this.setLocation(X, Y);
			}
		});
	}

	@Override
	public Long donInBackground() {
		try {
			contentPanel.setVisible(false);
			loadingPanel.setVisible(true);
			Message message = new Message(Constant.LOGIN,
					userNameInputField.getText(), new String(
							passwordInputField.getPassword()), "server", 1);
			AppController.getInstance().send(this, message);

		} catch (IOException e) {
			loadingPanel.setVisible(false);
			btnTyrAgain.setVisible(true);
			lblError.setText("Error: " + "Server not found.");
			StackTraceElement l = new Exception().getStackTrace()[0];
			System.err.println(l.getClassName() + "/" + l.getMethodName() + ":"
					+ l.getLineNumber());
		}
		return null;
	}

	@Override
	public void finished() {

	}

	@Override
	public void callBack(String exception) {
		System.err.println(exception);

	}

	@Override
	public void callBack(Message msg) {
		System.out.println(msg);
		if (msg.type == Constant.LOGIN) {
			if (msg.content.equalsIgnoreCase("true")) {
				if (cbRememberMe.isSelected()) {
					SaveLoginInfo si = new SaveLoginInfo();
					si.saveData(userNameInputField.getText(), new String(
							passwordInputField.getPassword()), infoFilePath);
				} else {

					SaveLoginInfo si = new SaveLoginInfo();
					si.saveData("", "", infoFilePath);

				}
				UserDetails uDetails = new UserDetails(msg.sender);
				ChatFrame frame = ChatFrame.getFreshInstance(uDetails.userName,
						uDetails.fullName, "", uDetails.email,
						uDetails.userType);
				frame.setVisible(true);
				dispose();
			} else {
				contentPanel.setVisible(true);
				loadingPanel.setVisible(false);
				lblError.setText(msg.sender);
			}
		} else
			lblError.setText(msg.sender);

		// TODO Call Chat Window

	}

	@Override
	public void send(Message msg) {
		// TODO Auto-generated method stub

	}

}
