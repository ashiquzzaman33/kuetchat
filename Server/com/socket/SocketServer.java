package com.socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.common.Constant;
import com.common.Message;
import com.common.UserDetails;

/**
 * Multi Threaded Server . Accept client and made link between them <br/>
 * 
 * @author Ashiquzzaman & Rashik Hasnat
 *
 */
public class SocketServer implements Runnable {

	public ServerThread clients[];
	public ServerSocket server = null;
	public Thread thread = null;
	public int clientCount = 0, port = 13000;
	public Database db;

	public SocketServer(int _port) {
		port = _port;
		clients = new ServerThread[50];
		try {
			db = new Database();

		} catch (Exception e) {
			e.printStackTrace();
			System.err
					.print("Database server not found.\n"
							+ "Server Error! Please try again Leter. Communications link failure");
			System.exit(1);
		}
		try {
			server = new ServerSocket(port);
			port = server.getLocalPort();
			System.out.println("Server started. IP : "
					+ InetAddress.getLocalHost() + ", Port : "
					+ server.getLocalPort());
			start();
		} catch (IOException ioe) {

			System.out.println("Can not bind to port : " + port + "\nRetrying");
		}

	}

	public void run() {
		while (thread != null) {

			try {
				System.out.println("Waiting for a client ...\n");
				addThread(server.accept());
			} catch (Exception ioe) {
				System.out.println("Server accept error: \n");
			}
		}
	}

	public void start() {

		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}

	}

	@SuppressWarnings("deprecation")
	public void stop() {
		if (thread != null) {
			thread.stop();
			thread = null;
		}
	}

	public void stopServer() throws Exception {
		for (ServerThread t : clients) {
			if (t != null) {
				t.close();
			}
		}
		server.close();
		stop();
	}

	int findClient(int ID) {
		for (int i = 0; i < clientCount; i++) {
			if (clients[i].getID() == ID) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Handle incoming message form client
	 * 
	 * @param ID
	 * @param msg
	 */
	public synchronized void handle(int ID, Message msg) {
		switch (msg.type) {
		case Constant.SIGN_OUT:
			try {
				db.setOnline(msg.sender, Constant.OFFLINE);
				clients[findClient(ID)].logout();

			} catch (Exception e) {
				System.out.println("Error while removing id.");
			}
			break;
		case Constant.LOGIN:
			try {

				UserDetails ud = db.login(msg.sender, msg.content);
				if (findUserThread(msg.sender) != null) {
					clients[findClient(ID)].send(new Message(Constant.LOGIN,
							"User Already Login.", "false", msg.sender,
							msg.callBackId));

				} else if (ud != null) {
					clients[findClient(ID)].username = msg.sender;
					clients[findClient(ID)].setPresenceStatus(Constant.ONLINE);
					clients[findClient(ID)].send(new Message(Constant.LOGIN, ud
							.toString(), "TRUE", msg.sender, msg.callBackId));
					ArrayList<UserDetails> uds = db.getUserList();
					changePresence(msg.sender, Constant.ONLINE);
					for (UserDetails d : uds) {
						if (!d.userName
								.equals(clients[findClient(ID)].username))
							clients[findClient(ID)].send(new Message(
									Constant.USER_LIST, "server", d.toString(),
									clients[findClient(ID)].username, -1));
					}
					clients[findClient(ID)].send(new Message(
							Constant.NOTIFICATION_LIST, "server", db
									.getNotificationList(msg.sender, true),
							clients[findClient(ID)].username, -1));
					// TODO afsd
					ArrayList<Message> messages = db
							.getHistoryMessage(clients[findClient(ID)].username);
					for (Message message : messages) {
						clients[findClient(ID)].send(message);
					}
					System.out.println("\nUser login Success: "
							+ clients[findClient(ID)].username);
					// }

				} else {
					clients[findClient(ID)].send(new Message(Constant.LOGIN,
							"Password and Username do not match.", "false",
							msg.sender, msg.callBackId));
				}
			} catch (Exception e) {
				clients[findClient(ID)].send(new Message(Constant.SERVER_ERROR,
						"SERVER", e.getMessage(), msg.sender, msg.callBackId));

			}
			break;
		case Constant.SINE_UP:
			try {
				if (db.userRegistration(new UserDetails(msg.content),
						msg.recipient)) {
					clients[findClient(ID)].send(new Message(Constant.SINE_UP,
							"Server", "true", "", msg.callBackId));
				}

			} catch (Exception e) {
				clients[findClient(ID)].send(new Message(Constant.SINE_UP,
						"Server", e.getMessage(), "", msg.callBackId));
			}
			break;
		case Constant.MESSAGE:
			if (findUserThread(msg.recipient) != null) {
				findUserThread(msg.recipient).send(msg);
			}
			try {
				db.setNotification(msg.sender, msg.recipient, true);
				db.saveChatHistory(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case Constant.PRESENCE_CHANGE:
			changePresence(msg.sender, Integer.parseInt(msg.content));
			break;
		case Constant.UPLOAD_REQUEST:
			if (findUserThread(msg.recipient) != null) {
				String rec = msg.recipient;
				msg.recipient = msg.sender;
				msg.sender = clients[findClient(ID)].username;
				findUserThread(rec).send(msg);
			}
			break;
		case Constant.UPLOAD_RESPONSE:
			if (findUserThread(msg.recipient) != null) {
				String rec = msg.recipient;
				msg.recipient = msg.sender;
				msg.sender = clients[findClient(ID)].username;
				findUserThread(rec).send(msg);
			}
			break;
		case Constant.NOTIFICATION_CLEAR:
			try {
				db.setNotification(msg.recipient, msg.sender, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	public void Announce(int type, String sender, String content) {
		Message msg = new Message(type, sender, content, "All", -1);
		for (int i = 0; i < clientCount; i++) {
			clients[i].send(msg);
		}
	}

	public void changePresence(String userName, int presenceType) {

		Message msg = new Message(Constant.PRESENCE_CHANGE, userName,
				presenceType + "", "All", -1);
		for (int i = 0; i < clientCount; i++) {
			if (userName != null && clients[i].username != null
					&& !clients[i].username.equals(userName))
				clients[i].send(msg);
		}
		try {
			db.setOnline(userName, presenceType);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void AnnounceWithoutSender(int type, String user, String sender,
			String content) {
		Message msg = new Message(type, sender, content, "All", -1);
		for (int i = 0; i < clientCount; i++) {
			if (!clients[i].username.equals(user))
				clients[i].send(msg);
		}
	}

	public void SendUserList(String toWhom, int callBack, String ud) {
		try {
			findUserThread(toWhom).send(
					new Message(Constant.USER_LIST, "SERVER", ud.toString(),
							toWhom, callBack));
		} catch (Exception e) {
			System.out.println(e.getMessage());

			findUserThread(toWhom).send(
					new Message(Constant.SERVER_ERROR, "SERVER",
							"Server Error: Error while connection database",
							toWhom, -1));

		}
	}

	/*
	 * Send message to a user
	 */
	public void sendToUser(int type, String sender, String content,
			String receiver) {
		findUserThread(receiver).send(
				new Message(type, sender, content, receiver, -1));

	}

	/**
	 * Remove a thread when logout or disconnected.
	 * 
	 * @param ID
	 */
	@SuppressWarnings("deprecation")
	synchronized void remove(int ID) {
		int pos = findClient(ID);

		if (pos >= 0) {

			try {
				ServerThread toTerminate = clients[pos];
				if (toTerminate != null && toTerminate.username != null)
					changePresence(toTerminate.username, Constant.OFFLINE);
				db.setOnline(toTerminate.username, Constant.OFFLINE);
				System.out.println("Removing client thread " + ID + " at "
						+ pos);
				if (pos < clientCount - 1) {
					for (int i = pos + 1; i < clientCount; i++) {
						clients[i - 1] = clients[i];
					}
				}
				clientCount--;
				try {
					toTerminate.close();

				} catch (IOException ioe) {
					System.out.println("\nError closing thread: " + ioe);
				}
				toTerminate.stop();
				toTerminate = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ServerThread findUserThread(String usr) {
		for (int i = 0; i < clientCount; i++) {
			if (clients[i].username != null && clients[i].username.equals(usr)) {
				return clients[i];
			}
		}

		return null;
	}

	private void addThread(Socket socket) {
		if (clientCount < clients.length) {
			clients[clientCount] = new ServerThread(this, socket);
			try {
				clients[clientCount].open();
				clients[clientCount].start();
				clientCount++;
			} catch (IOException ioe) {
				System.out.println("\nError opening thread: " + ioe);
			}
		} else {
			System.out.println("\nClient refused: maximum " + clients.length
					+ " reached.");
		}
	}
}
