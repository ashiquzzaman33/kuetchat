package com.client.app;

import com.kuetchat.client.view.LoginWindow;

public class StartApp {
	private static LoginWindow loginWindow;
	private static StartApp app;

	public static void main(String[] args) {
		new StartApp().start();
	}

	public static StartApp getInstance() {
		if (app == null)
			app = new StartApp();
		return app;
	}

	public void start() {
		loginWindow = new LoginWindow();
		loginWindow.setVisible(true);
	}

	private StartApp() {
		// TODO Auto-generated constructor stub
	}

}
