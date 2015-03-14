package com.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import com.common.Constant;
import com.common.Message;

class ServerThread extends Thread {

	private SocketServer server = null;
	public Socket socket = null;
	public int ID = -1;
	public String username = null;
	private ObjectInputStream streamIn = null;
	private ObjectOutputStream streamOut = null;

	private Database db;

	public ServerThread(SocketServer _server, Socket _socket) {
		super();
		server = _server;
		socket = _socket;
		ID = socket.getPort();
		try {
			db = new Database();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setPresenceStatus(int flag) {
		try {
			db.setOnline(username, flag);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Database error: " + e.getMessage() + "\n");
		}
	}

	public void send(Message msg) {

		try {
			streamOut.writeObject(msg);
			streamOut.flush();
		} catch (IOException ex) {
			System.out.println(ID + " ERROR sending: " + ex.getMessage());
			setPresenceStatus(Constant.OFFLINE);
			server.remove(ID);
			stop();
		}
	}

	public int getID() {
		return ID;
	}

	@SuppressWarnings("deprecation")
	public void run() {
		System.out.println("\nServer Thread " + ID + " running.");
		while (true) {
			try {
				Message msg = (Message) streamIn.readObject();
				server.handle(ID, msg);
			} catch (Exception ioe) {
				server.remove(ID);
				stop();
			}
		}
	}

	public void logout() {
		if (server.findClient(ID) != -1) {
			try {
				System.out.println("\nUser logout: "
						+ server.clients[server.findClient(ID)].username);
				server.remove(ID);
			} catch (Exception e) {
				System.out.println("\n Exception while logout: "
						+ e.getMessage());
			}

		} else
			System.out.println("\n User logout -1 found");

	}

	public void open() throws IOException {

		streamOut = new ObjectOutputStream(socket.getOutputStream());
		streamOut.flush();
		streamIn = new ObjectInputStream(socket.getInputStream());
	}

	public void close() throws Exception {
		if (socket != null)
			socket.close();
		if (streamIn != null)
			streamIn.close();
		if (streamOut != null)
			streamOut.close();
	}
}
