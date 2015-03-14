package com.kuetchat.client.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import com.common.Message;

public class ChatClientThread extends Thread {
	private Socket socket = null;
	private ChatClient client = null;
	private ObjectInputStream streamIn = null;

	public ChatClientThread(ChatClient _client, Socket _socket) {
		client = _client;
		socket = _socket;
		open();
		start();
	}

	public void open() {
		try {
			streamIn = new ObjectInputStream(socket.getInputStream());
		} catch (IOException ioe) {
			System.out.println("Error getting input stream: " + ioe);
			client.stop();
		}
	}

	public void close() {
		try {
			if (streamIn != null)
				streamIn.close();
		} catch (IOException ioe) {
			System.out.println("Error closing input stream: " + ioe);
		}
	}

	public void run() {
		while (true) {
			try {
				client.handle((Message) streamIn.readObject());
			} catch (IOException | ClassNotFoundException ioe) {
				client.stop();
			}
		}
	}
}