package com.n.utility.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class ModButton extends JButton {

	private Color hoverBackgroundColor;
	private Color pressedBackgroundColor;
	private Color defaultBackgroundColor;

	public ModButton(String text, Color defaultColor, Color hover, Color pressed) {
		this(text);
		defaultBackgroundColor = defaultColor;
		pressedBackgroundColor = pressed;
		hoverBackgroundColor = hover;
	}

	public ModButton(String text) {
		super(text);
		super.setContentAreaFilled(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isPressed()) {
			g.setColor(pressedBackgroundColor);
		} else if (getModel().isRollover()) {
			g.setColor(hoverBackgroundColor);
		} else {
			g.setColor(defaultBackgroundColor);
		}
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}

	@Override
	public void setContentAreaFilled(boolean b) {
	}

	public Color getHoverBackgroundColor() {
		return hoverBackgroundColor;
	}

	public void setHoverBackgroundColor(Color hoverBackgroundColor) {
		this.hoverBackgroundColor = hoverBackgroundColor;
	}

	public Color getPressedBackgroundColor() {
		return pressedBackgroundColor;
	}

	public void setPressedBackgroundColor(Color pressedBackgroundColor) {
		this.pressedBackgroundColor = pressedBackgroundColor;
	}
}