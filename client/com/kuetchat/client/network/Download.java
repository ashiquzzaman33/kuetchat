package com.kuetchat.client.network;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.common.Constant;
import com.common.Message;
import com.n.userinfo.controller.UserController;
import com.n.userinfo.model.IdentityModel;
import com.n.view.ChatFrame;

public class Download implements Runnable {

	public ServerSocket server;
	public Socket socket;
	public int port;
	public String saveTo = "";
	public InputStream In;
	public FileOutputStream Out;
	public ChatFrame ui;
	private String userNameOfFriend;

	public Download(String u, String saveTo, ChatFrame ui) {
		try {

			userNameOfFriend = u;
			server = new ServerSocket(0);
			port = server.getLocalPort();
			this.saveTo = saveTo;
			this.ui = ui;
		} catch (IOException ex) {
			System.out.println("Exception [Download : Download(...)]");
		}
	}

	@Override
	public void run() {
		try {

			socket = server.accept();
			System.out.println("Download : " + socket.getRemoteSocketAddress());

			In = socket.getInputStream();
			Out = new FileOutputStream(saveTo);

			byte[] buffer = new byte[1024];
			int count;

			while ((count = In.read(buffer)) >= 0) {
				Out.write(buffer, 0, count);
			}

			Out.flush();

			// ui.jTextArea1.append("[Application > Me] : Download complete\n");
			UserController.getFriendInfoController().messageArrived(
					userNameOfFriend,
					new Message(Constant.UNKNOWN_COMMAND, userNameOfFriend,
							"Download Completed.\n" + saveTo, IdentityModel
									.getIdentityModel().getUserName(), -1));
			if (Out != null) {
				Out.close();
			}
			if (In != null) {
				In.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (Exception ex) {
			System.out.println("Exception [Download : run(...)]");
		}
	}
}