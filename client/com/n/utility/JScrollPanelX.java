package com.n.utility;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.annotation.Generated;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class JScrollPanelX extends JScrollPane {

	private static final long serialVersionUID = 1L;

	// Private Declatration
	private JPanel contentPanel;
	private int verticalGap = 0;
	private Dimension size = new Dimension(0, 0);
	private Dimension scrollPaneSize = new Dimension();

	public JScrollPanelX() {
	
		setBackground(Color.WHITE);
		contentPanel = new JPanel();
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setLayout(null);
		contentPanel.setSize(size);
		contentPanel.setPreferredSize(size);
		verticalScrollBarPolicy = getVerticalScrollBarPolicy();
		setViewportView(contentPanel);
	}

	@Override
	public Component add(Component arg0) {

		arg0.setLocation(size.width, size.height);
		size.height = size.height + getVerticalGap() + arg0.getSize().height;
		contentPanel.setPreferredSize(size);
		contentPanel.setSize(size);
		Component result = contentPanel.add(arg0);
		contentPanel.validate();
		validate();

		JScrollBar vsb = getVerticalScrollBar();
		vsb.setValue(vsb.getMaximum());
		return result;
	}

	public void refresh() {
		size = new Dimension(0, 0);
		Component[] com = contentPanel.getComponents();
		contentPanel.removeAll();

		for (Component component : com) {
			this.add(component);
		}
	}
	public void clear() {
		size = new Dimension(0, 0);
		contentPanel.removeAll();
		contentPanel.repaint();
		contentPanel.validate();
	}

	public int getVerticalGap() {
		return verticalGap;
	}

	public void setVerticalGap(int verticalGap) {
		this.verticalGap = verticalGap;
	}

	@Override
	public void setSize(Dimension arg0) {
		super.setSize(arg0);
		scrollPaneSize.setSize(arg0);
	}

	@Override
	public void setSize(int arg0, int arg1) {
		super.setSize(arg0, arg1);
		scrollPaneSize.setSize(new Dimension(arg0, arg1));
	}

	@Override
	public void setBounds(int arg0, int arg1, int arg2, int arg3) {
		super.setBounds(arg0, arg1, arg2, arg3);
		scrollPaneSize.setSize(new Dimension(arg2, arg3));
	}

}
