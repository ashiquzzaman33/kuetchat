package com.n.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.n.utility.view.ComponentResizer;
import com.n.utility.view.JPanelBackImage;
import com.n.utility.view.UtilityMethod;

/**
 * Base JFrame with layout of the main chat window
 */
public abstract class BasePanel extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int CONTACT_PANEL_WIDTH = 275;
	private Dimension maxSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Dimension defaultSize = new Dimension(1100, 575);
	private Dimension minSize = new Dimension(1000, 500);
	private static java.awt.Point initialClick = new java.awt.Point();
	private boolean isMaximaiz = false;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private MenuBar menuBar = new MenuBar();
	private ComponentResizer resizer = new ComponentResizer();
	public static final String APPLICATION_DIR = System
			.getProperty("user.home") + "\\kuetchat";
	public static final String APPLICATION_IMAGE_DIR = System
			.getProperty("user.home") + "\\kuetchat\\images";
	public static final String APPLICATION_LOG_DIR = System
			.getProperty("user.home") + "\\kuetchat\\log";

	public static final String APPLICATION_DATABASE_DIR = System
			.getProperty("user.home") + "\\kuetchat\\db";

	/**
	 * Default Constructor
	 */
	public BasePanel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Resizer setup
		resizer.setMinimumSize(minSize);
		resizer.setMaximumSize(maxSize);
		resizer.registerComponent(this);

		setIconImage(UtilityMethod.getImage("/resources/lg.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(minSize);
		setSize(defaultSize);
		setPreferredSize(defaultSize);

		setUndecorated(true);

		// ********************* Title Bar**************************

		{
			JPanelBackImage titleBar = new JPanelBackImage(
					UtilityMethod.getImageIcon("/resources/top.png"));
			titleBar.setPreferredSize(new Dimension(0, 27));
			titleBar.setLayout(new BorderLayout(5, 5));
			add(titleBar, BorderLayout.NORTH);
			JLabel kuetlogo = new JLabel("");

			{
				JPanel leftSide = new JPanel(new FlowLayout(FlowLayout.LEFT,
						10, FlowLayout.CENTER));
				leftSide.setOpaque(false);
				kuetlogo.setIcon(new ImageIcon(BasePanel.class
						.getResource("/resources/lgtop.png")));
				JLabel kuetChatLebe = new JLabel("");
				kuetChatLebe.setIcon(new ImageIcon(BasePanel.class
						.getResource("/resources/kuettop.png")));
				leftSide.add(kuetlogo);
				leftSide.add(kuetChatLebe);
				titleBar.add(leftSide, BorderLayout.WEST);
			}
			{
				JPanel rightSideJPanel = new JPanel(null);

				// Min button
				JButton minButton = new JButton(
						UtilityMethod.getImageIcon("/resources/min.png"));
				minButton.setContentAreaFilled(false);
				minButton.setBorderPainted(false);
				minButton.setRolloverIcon(UtilityMethod
						.getImageIcon("/resources/minr.png"));
				minButton.setPressedIcon(UtilityMethod
						.getImageIcon("/resources/minp.png"));
				minButton.setFocusPainted(false);
				minButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						BasePanel.this.setState(JFrame.ICONIFIED);
					}
				});

				// Max button
				JButton maxButton = new JButton(
						UtilityMethod.getImageIcon("/resources/max.png"));
				maxButton.setFocusPainted(false);
				maxButton.setContentAreaFilled(false);
				maxButton.setBorderPainted(false);
				maxButton.setRolloverIcon(UtilityMethod
						.getImageIcon("/resources/maxr.png"));
				maxButton.setPressedIcon(UtilityMethod
						.getImageIcon("/resources/maxp.png"));
				maxButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (isMaximaiz) {
							setDefaultSize();
						} else {
							BasePanel.this.setSize(maxSize);
							setMaxSize();
						}

						setCenter();
					}
				});

				// Cross button
				JButton crossButton = new JButton(
						UtilityMethod.getImageIcon("/resources/cross.png"));
				crossButton.setFocusPainted(false);
				crossButton.setContentAreaFilled(false);
				crossButton.setBorderPainted(false);
				crossButton.setRolloverIcon(UtilityMethod
						.getImageIcon("/resources/crossr.png"));
				crossButton.setPressedIcon(UtilityMethod
						.getImageIcon("/resources/crossp.png"));
				crossButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								System.exit(0);

							}
						});

					}
				});
				rightSideJPanel.setOpaque(false);
				rightSideJPanel.setPreferredSize(new Dimension(100, 27));
				minButton.setBounds(0, 0, 30, 30);
				maxButton.setBounds(32, 0, 30, 30);
				crossButton.setBounds(64, 0, 30, 30);
				minButton.addMouseListener(new HandCursorContition());
				maxButton.addMouseListener(new HandCursorContition());
				crossButton.addMouseListener(new HandCursorContition());

				rightSideJPanel.add(maxButton);
				rightSideJPanel.add(minButton);
				rightSideJPanel.add(crossButton);
				titleBar.add(rightSideJPanel, BorderLayout.EAST);
				titleBar.addMouseListener(new MoveHandeler());
				titleBar.addMouseMotionListener(new MoveHandeler());

			}
		}
		// ******************Title Bar End*****************************

		JPanel backPanel = new JPanel(new BorderLayout()); // Main container
															// Title bar and
															// backPanel
		// Border
		JSeparator separatorLeft = new JSeparator();
		separatorLeft.setOrientation(SwingConstants.VERTICAL);
		separatorLeft.setForeground(SystemColor.textHighlight);
		backPanel.add(separatorLeft, BorderLayout.WEST);
		JSeparator seperatorRight = new JSeparator();
		seperatorRight.setOrientation(SwingConstants.VERTICAL);
		seperatorRight.setForeground(SystemColor.textHighlight);
		add(seperatorRight, BorderLayout.EAST);
		JSeparator seperatorBottom = new JSeparator();
		seperatorBottom.setForeground(SystemColor.textHighlight);
		add(seperatorBottom, BorderLayout.SOUTH);
		backPanel.setOpaque(false);

		JPanel wrapperMainJPanel = new JPanel(new BorderLayout()); // Main
																	// Content
																	// panel
		wrapperMainJPanel.setOpaque(false);
		{
			leftPanel = new JPanel(new BorderLayout());
			leftPanel.setBackground(Color.WHITE);

			leftPanel.setPreferredSize(new Dimension(
					BasePanel.CONTACT_PANEL_WIDTH, 0));
			wrapperMainJPanel.add(leftPanel, BorderLayout.WEST);
			leftPanel.setBackground(Color.WHITE);

		}

		{
			rightPanel = new JPanel(new BorderLayout()); // Hold menubar,
															// chatpanel
			rightPanel.setBackground(Color.WHITE);

			JPanelBackImage shadow = new JPanelBackImage(
					UtilityMethod.getImageIcon("/resources/shado.png"));
			shadow.setPreferredSize(new Dimension(30, 0));
			menuBar.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));

			menuBar.setBackground(new Color(206, 223, 240));
			menuBar.setPreferredSize(new Dimension(0, 28));
			menuBar.setBorder(null);

			menuBar.setPreferredSize(new Dimension(0, 28));
			JPanel menuBarHolder = new JPanel();
			menuBarHolder.setLayout(new BorderLayout());
			JPanelBackImage shadowSmall = new JPanelBackImage(
					UtilityMethod.getImageIcon("/resources/sahdo1.png"));
			shadowSmall.setPreferredSize(new Dimension(1, 0));
			menuBarHolder.add(shadowSmall, BorderLayout.WEST);
			menuBarHolder.add(menuBar);
			rightPanel.add(menuBarHolder, BorderLayout.NORTH);
			rightPanel.add(shadow, BorderLayout.WEST);

		}
		wrapperMainJPanel.add(rightPanel, BorderLayout.CENTER);

		backPanel.add(wrapperMainJPanel);
		add(backPanel, BorderLayout.CENTER);

		pack();
		setCenter();
		// Check and Create Directory
		{

			File file = new File(BasePanel.APPLICATION_DATABASE_DIR);
			if (!file.exists())
				file.mkdirs();
			file = new File(BasePanel.APPLICATION_IMAGE_DIR);
			if (!file.exists())
				file.mkdirs();
			file = new File(BasePanel.APPLICATION_LOG_DIR);
			if (!file.exists())
				file.mkdirs();
		}
	}

	private void setCenter() {
		Dimension sizeDimension = getSize();
		setLocation((maxSize.width - sizeDimension.width) / 2,
				(maxSize.height - sizeDimension.height) / 2);
	}

	public void setDefaultSize() {
		BasePanel.this.setSize(defaultSize);
		resizer.registerComponent(this);
		isMaximaiz = false;
	}

	public void setMaxSize() {
		BasePanel.this.setSize(maxSize);
		resizer.deregisterComponent(this);
		isMaximaiz = true;
	}

	public void addMenu(JButton menu) {
		menu.setFont(new Font("Arial", Font.BOLD, 13));
		menu.setForeground(new Color(50, 50, 50));
		menu.setFocusPainted(false);
		menu.setCursor(new Cursor(Cursor.HAND_CURSOR));
		menuBar.add(menu);
	}

	public void addContactPanel(JPanel panel) {
		leftPanel.add(panel, BorderLayout.CENTER);
	}

	/**
	 * Main Chat Panel hold history, editbox and send button
	 */
	public void addMainView(JPanel panel) {
		rightPanel.add(panel, BorderLayout.CENTER);
	}

	public void addProfilePanel(JPanel panel) {
		panel.setPreferredSize(new Dimension(0, 54));
		leftPanel.add(panel, BorderLayout.NORTH);
	}

	/**
	 * Cursor for cross, min and max button
	 */
	class HandCursorContition extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			super.mouseEntered(e);
			BasePanel.this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			super.mouseExited(e);
			BasePanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

		}
	}

	/**
	 * Move activate
	 */
	public class MoveHandeler extends MouseAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			try {
				if (!isMaximaiz) {
					int thisX = BasePanel.this.getLocation().x;
					int thisY = BasePanel.this.getLocation().y;

					int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
					int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);

					int X = thisX + xMoved;
					int Y = thisY + yMoved;
					BasePanel.this.setLocation(X, Y);
					BasePanel.this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				}
			} catch (Exception es) {

			}

		}

		@Override
		public void mouseClicked(MouseEvent arg0) {

			if (arg0.getClickCount() == 2) {
				if (BasePanel.this.isMaximaiz == true) {
					BasePanel.this.setDefaultSize();
				} else {

					BasePanel.this.setMaxSize();
				}
				setCenter();
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			initialClick = e.getPoint();

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
			BasePanel.this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}

	}

	/**
	 * 
	 * MenuBar
	 */
	class MenuBar extends JMenuBar {
		private Color color = new Color(206, 223, 240);

		public MenuBar() {
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D graphics2d = (Graphics2D) g;
			graphics2d.setColor(color);
			graphics2d.fillRect(0, 0, getWidth(), getHeight());
		}
	}
}
