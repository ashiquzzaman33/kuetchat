package com.kuetchat.client.view;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class JPanelX extends JPanel {
	private static final long serialVersionUID = 1L;
	private ImageIcon image = null;
	private ImageIcon rollover;
	private ImageIcon workingImage;
	private ImageIcon backupImage;
	private ImageIcon pressedIcon;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean rolloverEnable;
	private boolean pressEnable;
	private boolean flagInternal;
	private Point location;

	private ArrayList<Component> componentList = new ArrayList<>();

	private boolean select;
	
	public JPanelX(ImageIcon ico, int x, int y, int width, int height, boolean notMouse) {
		super();
		setLayout(null);
		location = new Point(0, 0);
		pressEnable = false;
		select = false;
		if (ico != null)
			this.image = ico;
		this.x = x;
		this.y = y;
		backupImage = null;
		this.width = width;
		this.height = height;
		this.workingImage = this.image;
		this.rollover = null;
		rolloverEnable = false;
		pressedIcon = null;
		flagInternal = false;
		setBounds(x, y, width, height);
		if(!notMouse)
			addMouseAction();
	}
	public JPanelX(ImageIcon ico, int x, int y, int width, int height) {
		this(ico, x, y, width, height, false);
	}
	private void addMouseAction() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				super.mouseEntered(arg0);
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						if (rolloverEnable&&!select) {
							setBackgroundImageInternal(rollover);
						}

					}
				});

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						if (pressEnable&&!select) {
							backupImage = workingImage;
							setBackgroundImageInternal(pressedIcon);
						}

					}
				});
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						if (pressEnable&&!select) {
							setBackgroundImageInternal(backupImage);
						}

					}
				});
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				super.mouseExited(arg0);

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						if(!select)
						setBackgroundImageInternal(image);
					}
				});

			}
		});
	}

	public JPanelX(ImageIcon image, int width, int height) {
		this(image, 0, 0, width, height);
	}

	public JPanelX(ImageIcon img) {
		this(img, 0, 0, 0, 0);
	}
	public JPanelX(ImageIcon img, boolean f){
		this(img, 0, 0, 0, 0, f);
	}

	public JPanelX(String s, Object c) {
		this(new ImageIcon(c.getClass().getResource(s)));

	}
	public JPanelX(String s, Object c, boolean f) {
		this(new ImageIcon(c.getClass().getResource(s)), f);

	}

	public JPanelX() {

		this(null, 0, 0, 0, 0);
	}

	public void setBackgroundImage(ImageIcon icon) {
		this.image = icon;
		setBackgroundImageInternal(image);
	}
	public void setBackgroundImage(String url, Object c)
	{
		setBackgroundImage(new ImageIcon(c.getClass().getResource(url)));
	}
	private void setBackgroundImageInternal(ImageIcon im) {
		removeAll();
		workingImage = im;
		flagInternal = true;
		for (Component c : componentList) {
			add(c);
		}
		repaint();
		flagInternal = false;

	}

	public void setRolloverEnable(boolean f) {
		rolloverEnable = f;
	}

	public void setRolloverIcon(ImageIcon icon) {
		rollover = icon;
		setRolloverEnable(true);

	}
	public void setRolloverIcon(String url, Object c){
		setRolloverIcon(new ImageIcon(c.getClass().getResource(url)));
	}
	public void setPressedIcon(ImageIcon icon) {
		pressedIcon = icon;
		setPressEnable(true);

	}
	public void setPressedIcon(String url, Object c)
	{
		setPressedIcon(new ImageIcon(c.getClass().getResource(url)));
	}
	public void setSelect(boolean f)
	{
		if(pressedIcon!=null){
			select = f;
			repaint();
		}
		
	}
	public void setPressEnable(boolean f) {
		pressEnable = true;
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (workingImage != null) {
			g.drawImage(workingImage.getImage(), x-location.x, y-location.y, width, height, this);
		}
	}
	@Override
	public void setLocation(int arg0, int arg1) {
		
		super.setLocation(arg0, arg1);
		location = new Point(arg0, arg1);
		repaint();
	}
	@Override
	public void setSize(int arg0, int arg1) {
		super.setSize(arg0, arg1);
		this.width = arg0;
		this.height = arg1;
	}

	@Override
	public void setBounds(int arg0, int arg1, int arg2, int arg3) {

		super.setBounds(arg0, arg1, arg2, arg3);
		this.x = arg0;
		this.y = arg1;
		this.width = arg2;
		this.height = arg3;
	}
	@Override
	public Component add(Component arg0) {
		if (!flagInternal)
			componentList.add(arg0);
		return super.add(arg0);
	}
}
