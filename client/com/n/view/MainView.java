package com.n.view;

import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import com.n.userinfo.controller.UserController;
import com.n.utility.CONSTANT;
import com.n.utility.ChangedProperty;
import com.n.utility.PropertyList;

/**
 * Main View. Every Information show's here Example: Chat History, Chat Edit
 * Box, Selected Friend information, Add of company, and other Setting. Layout
 * </br><b>Layout use: CardLayout<b>
 * 
 * @see CardLayout
 */
public class MainView extends JPanel implements Observer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1365134734671690632L;
	/**
	 * Layout of this MainView. Card layout to show One View at a time
	 * 
	 * @see CardLayout
	 */
	CardLayout layout = new CardLayout();
	/**
	 * Remember panels name with the panel for further use
	 */
	private HashMap<String, JPanel> mainPanels = new HashMap<String, JPanel>();
	private ChatFrame chatFrame;
	private UserController userController;
	private static MainView mainView;

	public MainView(ChatFrame chatFrame) {
		setLayout(layout);
		mainView = this;
		userController = chatFrame.getUserController();
		Browser browser = new Browser(CONSTANT.BROWSER_URL);
		addMainPanel(browser, CONSTANT.BROWSER_STRING);
		userController.addObserver(this);
	}

	public static MainView getMainView() {
		return mainView;
	}

	/**
	 * 
	 * @param panel
	 *            Panel to be added
	 * @param panelName
	 *            Panel to to determine the panel
	 */
	public void addMainPanel(JPanel panel, String panelName) {
		mainPanels.put(panelName, panel);
		this.add(panel, panelName);
	}

	/**
	 * 
	 * @param panelName
	 */
	public void removeMainPanel(String panelName) {
		JPanel panel = mainPanels.get(panelName);
		if (panel != null) {
			layout.removeLayoutComponent(mainPanels.get(panelName));
			this.remove(mainPanels.get(panelName));
			mainPanels.remove(panelName);

		}
	}

	/**
	 * 
	 * @param panelName
	 *            Panel to show in Main View
	 */
	public void showPanel(String panelName) {
		JPanel panel = mainPanels.get(panelName);
		if (panel != null) {
			layout.show(this, panelName);
		}
	}

	@Override
	public void update(Observable o, Object arg) {

		@SuppressWarnings("unchecked")
		ChangedProperty<Integer, ?> changedProperty = (ChangedProperty<Integer, ?>) arg;
		if (changedProperty.isChange(PropertyList.CHANGE_MAIN_CHAT_WINDOW)) {
			String userName = (String) changedProperty
					.getNewValue(PropertyList.CHANGE_MAIN_CHAT_WINDOW);
			if (userName != null)
				showPanel(userName);
		}

	}
}
