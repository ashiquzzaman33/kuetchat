package com.kuetchat.client.network;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

import com.common.Constant;
import com.common.Message;
import com.n.userinfo.controller.UserController;
import com.n.userinfo.model.IdentityModel;
import com.n.view.ChatFrame;

public class Upload implements Runnable {

	public String addr;
	public int port;
	public Socket socket;
	public FileInputStream In;
	public OutputStream Out;
	public File file;
	public ChatFrame ui;
	public String userNameOfFriend;

	public Upload(String f, String addr, int port, File filepath,
			ChatFrame frame) {
		super();
		try {
			userNameOfFriend = f;
			file = filepath;
			ui = frame;
			socket = new Socket(InetAddress.getByName(addr), port);
			Out = socket.getOutputStream();
			In = new FileInputStream(filepath);
		} catch (Exception ex) {
			System.out.println("Exception [Upload : Upload(...)]");
		}
	}

	@Override
	public void run() {
		try {
			byte[] buffer = new byte[1024];
			int count;
			long size = file.length();
			long st = 0;
			while ((count = In.read(buffer)) >= 0) {
				Out.write(buffer, 0, count);
				st += count;
				int per = (int) ((count * 100) / size);
			}
			Out.flush();
			UserController.getFriendInfoController()
					.messageArrived(
							userNameOfFriend,
							new Message(Constant.UNKNOWN_COMMAND,
									userNameOfFriend, "Upload Completed.\n"
											+ file.getAbsolutePath(),
									IdentityModel.getIdentityModel()
											.getUserName(), -1));
			// ui.jTextArea1.append("[Applcation > Me] : File upload complete\n");
			// ui.jButton5.setEnabled(true);
			// ui.jTextField5.setVisible(true);

			if (In != null) {
				In.close();
			}
			if (Out != null) {
				Out.close();
			}
			if (socket != null) {
				socket.close();
			}
		} catch (Exception ex) {
			System.out.println("Exception [Upload : run()]");
			ex.printStackTrace();
		}
	}
}