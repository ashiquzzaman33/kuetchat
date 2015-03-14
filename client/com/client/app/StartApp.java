package com.client.app;

import com.kuetchat.client.view.LoginWindow;

/**
 * Start Client App of KuetChat
 * 
 * @author Ashiquzzaman & Rashik Hasnat
 *
 */
public class StartApp {
	private static LoginWindow loginWindow;
	private static StartApp app;

	/**
	 * Main Method of App
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new StartApp().start();
	}

	/**
	 * 
	 * Get instance of StartApp
	 * 
	 * @return App
	 */
	public static StartApp getInstance() {
		if (app == null)
			app = new StartApp();
		return app;
	}

	/**
	 * Start The application
	 */
	public void start() {
		loginWindow = new LoginWindow();
		loginWindow.setVisible(true);
	}

	/**
	 * Private Constructor ensuring only one Instance of StartApp
	 */
	private StartApp() {

	}

}
