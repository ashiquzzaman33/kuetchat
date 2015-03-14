package com.kuetchat.client.network;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.common.Message;
import com.kuetchat.client.utility.IncommingHandeler;

public class ChatClient {
	private Socket socket = null;
	private Thread thread = null;
	private DataInputStream console = null;
	private ObjectOutputStream streamOut = null;
	private ChatClientThread client = null;
	private IncommingHandeler incomingHandelerMethod = null;
	private static String host = "localhost";
	private static int port = 13000;

	public ChatClient(String serverName, int serverPort)
			throws UnknownHostException, IOException {
		host = serverName;
		port = serverPort;
		System.out.println("Establishing connection. Please wait ...");

		socket = new Socket();
		socket.connect(new InetSocketAddress(host, port), 3000);

		System.out.println("Connected: " + socket);
		start();

	}

	public void addHandelerMethod(IncommingHandeler han) {
		incomingHandelerMethod = han;
	}

	public void sendMessage(Message msg) {
		try {
			streamOut.writeObject(msg);
			streamOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void handle(Message msg) {
		if (incomingHandelerMethod != null)
			incomingHandelerMethod.incomingHandle(msg);
	}

	public void start() throws IOException {
		streamOut = new ObjectOutputStream(socket.getOutputStream());
		streamOut.flush();
		client = new ChatClientThread(this, socket);

		// if (thread == null) {
		// //thread = new Thread(this);
		// thread.start();
		// }
	}

	public void stop() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
		try {
			if (console != null)
				console.close();
			if (streamOut != null)
				streamOut.close();
			if (socket != null)
				socket.close();
		} catch (IOException ioe) {
			System.out.println("Error closing ...");
		}
		client.close();
		client.stop();
	}

}