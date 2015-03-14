package com.client.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.HashMap;

import com.common.Constant;
import com.common.Message;
import com.common.UserDetails;
import com.kuetchat.client.network.ChatClient;
import com.kuetchat.client.utility.CallBackable;
import com.kuetchat.client.utility.IncommingHandeler;
import com.n.userinfo.controller.UserController;
import com.n.view.ChatFrame;

public class AppController implements IncommingHandeler {
	private boolean[] space = new boolean[1000];
	private HashMap<Integer, Object> objects = new HashMap<>();
	ChatClient chatClient;
	private static AppController _appController = null;

	private AppController() throws UnknownHostException, IOException {
		chatClient = new ChatClient("192.168.137.142", 13000);
		chatClient.addHandelerMethod(this);
	}

	public static AppController getInstance() throws UnknownHostException,
			IOException {
		return _appController;
	}

	public static AppController makeInstance() throws UnknownHostException,
			IOException {
		_appController = new AppController();
		return _appController;
	}

	@Override
	public void incomingHandle(Message msg) {
		if (msg.callBackId != -1) {
			try {
				invoke(msg.callBackId, msg);
			} catch (InvocationTargetException | IllegalAccessException
					| NoSuchMethodException e) {
				e.printStackTrace();
			}
		} else {
			handleMessage(msg);
		}

	}

	public void handleMessage(Message msg) {
		if (msg.type == Constant.USER_LIST) {
			UserDetails u = new UserDetails(msg.content);

			try {
				ChatFrame
						.getInstance()
						.getContactsPanel()
						.addFriend(u.userName, u.fullName, u.address, u.online,
								false);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else if (msg.type == Constant.PRESENCE_CHANGE) {
			try {
				ChatFrame.getInstance().getContactsPanel()
						.setPresence(msg.sender, Integer.parseInt(msg.content));
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (msg.type == Constant.MESSAGE) {
			UserController.getFriendInfoController().messageArrived(msg.sender,
					msg);
		} else if (msg.type == Constant.NOTIFICATION_LIST) {
			UserController.getFriendInfoController().setInitialNotification(
					msg.content);
		} else if (msg.type == Constant.HISTORY) {
			UserController.getFriendInfoController().historyArrived(msg);
		}
	}

	public void send(CallBackable c, Message msg) {

		if (msg == null) {
			throw new NullPointerException("Null Pointer in message");
		}
		if (msg.callBackId != -1) {
			msg.callBackId = registerObject(c);
		}
		chatClient.sendMessage(msg);
	}

	public void send(Message msg) {
		if (msg != null)
			chatClient.sendMessage(msg);
	}

	private void invokeAll(Object... parameters) {
		for (int i = 0; i < 1000; i++) {
			if (space[i]) {
				try {
					invoke(i, parameters);
				} catch (InvocationTargetException | IllegalAccessException
						| NoSuchMethodException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void invoke(CallBackable c, Object... parameters) {
		try {
			Method method = c.getClass().getMethod("callBack",
					getParameterClasses(parameters));
			method.invoke(c, parameters);
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private void invoke(int id, Object... parameters)
			throws InvocationTargetException, IllegalAccessException,
			NoSuchMethodException {
		if (id != -1) {
			Object scope = getObject(id);
			unregisterObject(id);
			if (scope != null) {
				Method method = scope.getClass().getMethod("callBack",
						getParameterClasses(parameters));
				method.invoke(scope, parameters);
				scope = null;
			}
		}
	}

	private Class<?>[] getParameterClasses(Object... parameters) {
		Class<?>[] classes = new Class[parameters.length];
		for (int i = 0; i < classes.length; i++) {
			classes[i] = parameters[i].getClass();
		}
		return classes;
	}

	public int registerObject(CallBackable arg0) {
		int id = getFreeId();
		objects.put(id, arg0);
		return id;
	}

	private void unregisterObject(int i) {
		objects.remove(i);
		space[i] = false;
	}

	private Object getObject(int id) {
		return objects.get(id);
	}

	private int getFreeId() {
		for (int i = 1; i < space.length; i++) {
			if (!space[i]) {
				space[i] = true;
				return i;
			}
		}
		return -1;
	}

}
