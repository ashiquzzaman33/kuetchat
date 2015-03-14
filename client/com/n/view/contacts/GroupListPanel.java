package com.n.view.contacts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.n.utility.view.JScrollPanelMod;

/**
 * 
 * Group list display here and user select a group to group chat
 */
public class GroupListPanel extends JPanel {

	private static final long serialVersionUID = -4552801915226960703L;
	/**
	 * Group List show in here
	 */
	private JScrollPanelMod groupListInScrollPane = new JScrollPanelMod();

	public GroupListPanel() {
		setLayout(new BorderLayout());
		{
			JPanel dummyPanel = new JPanel();
			dummyPanel.setBackground(Color.white);
			dummyPanel.setPreferredSize(new Dimension(0, 10));
			add(dummyPanel, BorderLayout.NORTH);
		}
		{
			groupListInScrollPane.setVerticalGap(1);
			// TODO Dummy to remove
			// JPanel panel = new JPanel();
			// panel.setSize(BasePanel.CONTACT_PANEL_WIDTH,
			// UserListPanel.USER_TILE_HEIGHT);
			// panel.setBackground(Color.red);
			// JPanel anotherPanel = new JPanel();
			// anotherPanel.setSize(BasePanel.CONTACT_PANEL_WIDTH,
			// UserListPanel.USER_TILE_HEIGHT);
			// anotherPanel.setBackground(Color.black);
			//
			// groupListInScrollPane.add(panel);
			// groupListInScrollPane.add(anotherPanel);
			// for (int i = 0; i < 8; i++) {
			// JPanel panel2 = new JPanel();
			// panel2.setSize(BasePanel.CONTACT_PANEL_WIDTH,
			// UserListPanel.USER_TILE_HEIGHT);
			// panel2.setBackground(Color.DARK_GRAY);
			// groupListInScrollPane.add(panel2);
			// }

			add(groupListInScrollPane, BorderLayout.CENTER);
		}
	}
}
