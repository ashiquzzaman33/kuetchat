package com.n.utility.view;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JPanelBackImage extends JPanel {
	private static final long serialVersionUID = 1L;
	Image image;

	public JPanelBackImage(ImageIcon icon) {
		this.image = icon.getImage();
	}

	public void setImage(Image img) {
		this.image = img;
		repaint();
		revalidate();
	}

	protected void paintComponent(java.awt.Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, getSize().width, getSize().height, null);
	};
}
