package com.kuetchat.client.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

public class InputField extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean focus = false;
	private Image imageDefault;
	private Image imageRollover;
	private int width;
	private int height;
	private JLabel label;
	private JTextField textField;
	private JPasswordField passwordField;
	private boolean password = false;

	public InputField(int x, int y, int width, int height, String placeHolder) {
		this(x, y, width, height, placeHolder, false);
	}

	public InputField(int x, int y, int width, int height, String placeHolder,
			boolean isPass) {

		this.width = width;
		this.height = height;
		password = isPass;
		imageRollover = new ImageIcon(
				InputField.class.getResource("/resources/fieldfocus.png"))
				.getImage();
		imageDefault = new ImageIcon(
				InputField.class.getResource("/resources/field.png"))
				.getImage();
		setBounds(x, y, width, height);
		setSize(width, height);
		setOpaque(false);

		setFocusable(true);

		setLayout(null);
		{
			label = new JLabel(placeHolder);
			label.setForeground(Color.LIGHT_GRAY);
			label.setFont(new Font("Tahoma", Font.PLAIN, 14));
			label.setBounds(20, 0, width - 40, height);
			label.setOpaque(false);
			label.setFocusable(false);
			add(label);
		}
		{
			textField = new JTextField();
			textField.setColumns(10);
			textField.setBounds(20, 3, width - 40, height - 6);
			textField.setBorder(null);
			textField.setOpaque(false);
			textField.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					label.setVisible(false);
					focus = true;
					repaint();
				}

				@Override
				public void focusLost(FocusEvent arg0) {
					if (textField.getText().equals("")) {
						label.setVisible(true);
					}
					focus = false;
					repaint();
				}
			});

		}
		{
			passwordField = new JPasswordField();
			passwordField.setColumns(10);

			passwordField.setBorder(null);
			passwordField.setOpaque(false);
			passwordField.setBounds(20, 3, width - 40, height - 6);
			passwordField.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent arg0) {
					label.setVisible(false);
					focus = true;
					repaint();
				}

				@Override
				public void focusLost(FocusEvent arg0) {
					char[] ch = passwordField.getPassword();
					if (new String(ch).equals("")) {
						label.setVisible(true);
					}
					focus = false;
					repaint();
				}
			});
			textField.setFocusTraversalKeysEnabled(false);
			passwordField.setFocusTraversalKeysEnabled(false);
		}

		textField.getInputMap().put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, true),
				"Enter released");
		textField.getActionMap().put("Enter released", new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("EnterPressed");

			}
		});

		if (password)
			add(passwordField);
		else
			add(textField);

		repaint();
	}

	public void addKeyListener(int key, Object keyName,
			javax.swing.AbstractAction action) {
		if (!password) {
			textField.getInputMap().put(KeyStroke.getKeyStroke(key, 0, true),
					keyName);
			textField.getActionMap().put(keyName, action);

		} else {
			passwordField.getInputMap().put(
					KeyStroke.getKeyStroke(key, 0, true), keyName);
			passwordField.getActionMap().put(keyName, action);
		}
	}

	public void setPlaceHolderString(String str) {
		label.setText(str);
		repaint();
		validate();
	}

	public void setText(String str) {
		if (!str.equals(""))
			label.setVisible(false);
		else
			label.setVisible(true);
		textField.setText(str);
		passwordField.setText(str);
	}

	public void setEditable(boolean x) {

		label.setVisible(true);
		textField.setEditable(x);
	}

	public String getText() {
		if (!password)
			return textField.getText();
		else {
			throw new RuntimeException(
					"InputField is now password mode, try getPassword()");
		}
	}

	public char[] getPassword() {
		if (password) {
			return passwordField.getPassword();
		} else {
			throw new RuntimeException(
					"InputField is now Text mode, try getText()");
		}

	}

	public void refresh() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				passwordField.setText("");
				textField.setText("");
				label.setVisible(true);

			}
		});
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (focus) {
			g.drawImage(imageRollover, 0, 0, width, height, this);
		} else {
			g.drawImage(imageDefault, 0, 0, width, height, this);
		}
	}
}
