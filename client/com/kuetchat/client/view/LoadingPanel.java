package com.kuetchat.client.view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4149591700458674012L;

	public LoadingPanel() {
		setLayout(null);
		setLocation(0, 0);
		setSize(100, 100);
		setOpaque(false);
		JLabel lblLoading = new JLabel("");
		lblLoading.setIcon(new ImageIcon(LoginWindow.class
				.getResource("/resources/loading.gif")));
		lblLoading.setBounds(0, 0, 100, 100);
		add(lblLoading);
	}

}
