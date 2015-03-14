package com.kuetchat.client.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.kuetchat.client.view.LoginWindow;

public class SaveLoginInfo {

	public void saveData(String username, String password, String path) {
		if (username.equals("") || password.equals("")) {
			username = "*";
			password = "*";
		}
		try (FileOutputStream fileOutputStream = new FileOutputStream(path);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
						fileOutputStream)) {
			objectOutputStream.writeObject(new Info(username, password));

		} catch (IOException e) {
			System.err.println("Error while saving userinfo.");
		}
	}

	public String getData(String path) {
		File file = new File(path);
		if (file.exists()) {

			try (FileInputStream fileInputStream = new FileInputStream(path);
					ObjectInputStream objectInputStream = new ObjectInputStream(
							fileInputStream)) {
				Info in = (Info) objectInputStream.readObject();
				return in.toString();

			} catch (IOException | ClassNotFoundException e) {
				System.err.println("Error while Reading object");
			}
		}
		return "*" + LoginWindow.SEPERATOR + "*";
	}
}

class Info implements Serializable {

	private static final long serialVersionUID = 4191433483456327644L;
	String username;
	String password;

	public Info(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public String toString() {
		return username + LoginWindow.SEPERATOR + password;
	}
}