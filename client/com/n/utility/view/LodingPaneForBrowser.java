package com.n.utility.view;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class LodingPaneForBrowser extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4149591700458674012L;

	public LodingPaneForBrowser() {
		setLayout(new MigLayout("", "[][][][][][][][][][][][][][][grow]",
				"[][][][][][][][][][grow]"));
		setBackground(Color.WHITE);

		JLabel lblLoading = new JLabel();
		lblLoading
				.setIcon(UtilityMethod.getImageIcon("/resources/loading.gif"));
		lblLoading.setOpaque(false);
		lblLoading.setPreferredSize(new Dimension(100, 100));
		add(lblLoading, "cell 0 0 15 10,alignx center,aligny center");

	}
}
